package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IOrderDao;
import dao.IProductDao;
import exception.UserExistException;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import model.UserReceipt;
import utils.JDBCUtils;

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "order":
			orderUser(request,response);
			break;
		case "infoOrder":
			infoOrderUser(request,response);
			break;
		case "newOrder":
			newOrderUser(request,response);	
			break;
		case "payOrder":
			payOrderUser(request,response);
			break;
		case "delOrder":
			delOrderUser(request,response);
			break;
		default:
			response.getWriter().print("REQUEST-ERROR: Parameter 'action' Missing or Error Occur !");
			break;
		}
	}

	private synchronized void payOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		String id = request.getParameter("id");
		String payType = request.getParameter("paytype");
		
		//选择支付方式
		if(payType == null || "".equals(payType)) {
			request.getRequestDispatcher(response.encodeURL("handle-paying.jsp")).forward(request, response);
			return;
		}
		
		if(id == null || "".equals(id)) {
			request.setAttribute("Msg", "订单支付失败，该订单编号有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		IOrderDao orderDao = DaoFactory.getOrderDao();
		//查订单详情
		Order order = orderDao.getOrderById(id);
		if(order==null || order.getPayState() == 1) {
			request.setAttribute("Msg", "订单支付失败，该订单不存在或已支付！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		//查订单购买清单详情
		List<OrderItem> orderItem = DaoFactory.getOrderItemDao().getOrderItemByOrder(order);
		if(orderItem == null || orderItem.size() < 1) {
			request.setAttribute("Msg", "订单支付失败，该订单信息有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		//查购买的商品详情
		IProductDao productDao = DaoFactory.getProductDao();
		List<String> orderItemProductIds = orderItem.stream()
				.map(OrderItem::getProduct).map(Product::getId).collect(Collectors.toList());
		
		String[] pids = orderItemProductIds.toArray(new String[orderItemProductIds.size()]);
		
		List<Product> products = productDao.getProductByIdsData(pids);
		if(products.size()!=orderItem.size()) {
			request.setAttribute("Msg", "订单支付失败，该订单信息有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		//判断库存是否支持订单数
		boolean isEnough = true;
		for (Product product : products) {
			long count = orderItem.stream()
					.filter((p)->{
					return p.getProduct().getId().equals(product.getId())&&product.getPnum() >= p.getBuynum();}
					).count();
			if(count != 1) {
				isEnough = false;
				break;
			}
		}
		if(!isEnough) {
			request.setAttribute("Msg", "订单支付失败，订单的商品库存不足！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		//修改订单支付状态
		Integer updateOrder = orderDao.updateOrder(id, 1);
		if(updateOrder!=1) {
			request.setAttribute("Msg", "订单支付失败，请尝试重新支付！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		//修改库存数量
		for (Product p : products) {
			for (OrderItem i : orderItem) {
				if(i.getProduct().getId().equals(p.getId())) {
					p.setPnum(p.getPnum()-i.getBuynum());
					break;
				}
			}
		}
		Integer updateProduct = productDao.updateProduct(products);
		if(updateProduct!=products.size()) {
			orderDao.delOrderByIds(id);		//只是删掉订单 TODO recover products pnum
			request.setAttribute("Msg", "订单支付失败，订单的商品库存有误，自动删除此订单！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		//成功支付
		request.setAttribute("Msg", "订单支付成功，您的商品正在飞速打包中！");
		request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
		request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
	}

	private void infoOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		if(id == null || "".equals(id)) {
			request.setAttribute("Msg", "查看详情失败，该订单编号有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		Order order = DaoFactory.getOrderDao().getOrderById(id);
		if(order == null) {
			request.setAttribute("Msg", "查看详情失败，该订单不存在！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		List<OrderItem> orderItem = DaoFactory.getOrderItemDao().getOrderItemByOrder(order);
		if(orderItem == null || orderItem.size() < 1) {
			request.setAttribute("Msg", "查看详情失败，该订单信息有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		order.setOrderItems(orderItem);
		
		request.setAttribute("Order", order);
		request.getRequestDispatcher(response.encodeURL("orderinfo.jsp")).forward(request, response);
	}

	private void delOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		if(id == null || "".equals(id)) {
			request.setAttribute("Msg", "删除失败，订单编号有误！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		DaoFactory.getOrderDao().delOrderByIds(id);
		
		request.setAttribute("Msg", "订单删除成功！");
		request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
		request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
	}

	//创建新的订单
	private void newOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		//如果空购物车
		if(sessionCarts == null || sessionCarts.size() < 1) {
			request.setAttribute("Msg", "购物车空空如也，请先挑选心仪的商品吧！");
			request.setAttribute("redirectUrl", response.encodeURL("CartServlet?action=cart"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		//检测收货信息
		String receiptID = request.getParameter("receiptID"); //如果用户使用已有的地址 此参数有效
		String receiverAddress = request.getParameter("receiverAddress");
		String receiverName = request.getParameter("receiverName");
		String receiverPhone = request.getParameter("receiverPhone");
		UserReceipt newReceipt = null;
		Integer res = 0;
		if(receiptID == null || "".equals(receiptID)) {
			newReceipt = new UserReceipt(user, receiverName, receiverAddress, receiverPhone);
			res = DaoFactory.getUserReceiptDao().addUserReceipt(newReceipt);
		} else 
			newReceipt = DaoFactory.getUserReceiptDao().getUserReceiptById(Integer.valueOf(receiptID));
		if(res == 0 && newReceipt == null) {
			request.setAttribute("Msg", "订单收货地址填写有误，请检查一下噢！");
			request.setAttribute("redirectUrl", response.encodeURL("CartServlet?action=preOrder"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		//生产一张新的订单
		Double totalMoney = 0.0;
		for (Cart cart : sessionCarts) 
			totalMoney+=cart.getAddnum()*cart.getProduct().getPrice();
		
		String order_id = UUID.randomUUID().toString();
		Order newOrder = new Order(order_id,totalMoney,receiverAddress,receiverName,receiverPhone,0,JDBCUtils.getCurrentTimestamp());
		newOrder.setUser(user);
		
		Integer orderRes = DaoFactory.getOrderDao().addOrder(newOrder);
		if(orderRes != 1) {
			request.setAttribute("Msg", "抱歉，订单生成失败了，请稍后再试一下呗！");
			request.setAttribute("redirectUrl", response.encodeURL("CartServlet?action=preOrder"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		//添加订单购买的产品详情
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (Cart cart : sessionCarts) 
			orderItems.add(new OrderItem(newOrder, cart.getProduct(), cart.getAddnum()));
		
		Integer orderItemRes = DaoFactory.getOrderItemDao().addOrderItemByOrder(newOrder, orderItems);
		if(orderItemRes < 1) {
			DaoFactory.getOrderDao().delOrderByIds(order_id);
			request.setAttribute("Msg", "抱歉，订单生成失败了，请稍后再试一下呗！");
			request.setAttribute("redirectUrl", response.encodeURL("CartServlet?action=preOrder"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
			return;
		}
		
		//清空用户购物车
		ServiceFactory.getCartService().clearUserCarts(user);
		request.getSession().setAttribute("UserCarts", new ArrayList<Cart>());
		
		//生成订单成功提示
		request.setAttribute("Msg", "成功生成订单号 :"+order_id+",请尽快支付以免库存不足！");
		request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
		request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
	}

	private void orderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		try {
			List<Order> userOrder = ServiceFactory.getUserService()
					.getUserOrdersById(user, user.getId().toString());
			
			request.setAttribute("UserOrder", userOrder);
			request.getRequestDispatcher(response.encodeURL("order.jsp")).forward(request, response);
		} catch (UserExistException e) {
			response.getWriter().print("REQUEST-ERROR: Error Occur "+e.getMessage()+"!");
		}
	}
}
