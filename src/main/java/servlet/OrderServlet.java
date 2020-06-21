package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.CartExistException;
import exception.OrderExistException;
import factory.ServiceFactory;
import model.Cart;
import model.Order;
import model.User;
import model.UserReceipt;
import service.IOrderService;
import utils.PageUtils;

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

	private void payOrderUser(HttpServletRequest request, HttpServletResponse response) 
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
		
		try {
			ServiceFactory.getOrderService().payUserOrder(user, id);
			
			//成功支付
			request.setAttribute("Msg", "订单支付成功，您的商品正在飞速打包中！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
		} catch (OrderExistException e) {
			//失败支付
			request.setAttribute("Msg", e.getMessage());
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
		}
		
	}

	private void infoOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		try {
			Order order = ServiceFactory.getOrderService().detailUserOrder(user, id);
			request.setAttribute("Order", order);
			request.getRequestDispatcher(response.encodeURL("orderinfo.jsp")).forward(request, response);
		} catch (OrderExistException e) {
			request.setAttribute("Msg", e.getMessage());
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
		}
	}

	private void delOrderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null ) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		try {
			ServiceFactory.getOrderService().deleteUserOrder(user, id);
			//订单删除成功提示
			request.setAttribute("Msg", "订单删除成功！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
		} catch (OrderExistException e) {
			//订单删除失败提示
			request.setAttribute("Msg", e.getMessage());
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
		}
		
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
		if(receiptID == null || "".equals(receiptID)) 
			newReceipt = new UserReceipt(user, receiverName, receiverAddress, receiverPhone);
		else
			newReceipt = new UserReceipt(Integer.valueOf(receiptID));
		
		try {
			String order_id = ServiceFactory.getOrderService().addNewOrder(user, sessionCarts, newReceipt);
			
			//清空用户购物车
			try {
				ServiceFactory.getCartService().clearUserCarts(user);
				request.getSession().setAttribute("UserCarts", new ArrayList<Cart>());
			} catch (CartExistException e) {
				//清除购物车失败 那就算了吧
			}
			
			//生成订单成功提示
			request.setAttribute("Msg", "成功生成订单号 :"+order_id+",请尽快支付以免库存不足！");
			request.setAttribute("redirectUrl", response.encodeURL("OrderServlet?action=order"));
			request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
		} catch (OrderExistException e) {
			//生成订单失败提示
			request.setAttribute("Msg", e.getMessage());
			request.setAttribute("redirectUrl", response.encodeURL("CartServlet?action=preOrder"));
			request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
		}
		
	}

	private void orderUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		try {
			IOrderService service = ServiceFactory.getOrderService();
			PageUtils page = new PageUtils(pageNo, pageSize, service.getUserOrdersCount(user));
			
			List<Order> userOrder = service.
					getUserOrders(user, user.getId().toString(),page.getPageNo(),page.getPageSize());
			
			request.setAttribute("pageNo", page.getPageNo());
			request.setAttribute("totalNo", page.getTotalNo());
			request.setAttribute("pageSize", page.getPageSize());
			request.setAttribute("UserOrder", userOrder);
			request.getRequestDispatcher(response.encodeURL("order.jsp")).forward(request, response);
		} catch (OrderExistException e) {
			response.getWriter().print("REQUEST-ERROR: Error Occur "+e.getMessage()+"!");
		}
	}
}
