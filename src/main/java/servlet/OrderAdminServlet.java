package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import dao.IOrderDao;
import dao.IOrderItemDao;
import dao.IUserDao;
import factory.DaoFactory;
import model.Order;
import model.OrderItem;
import model.User;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 * 	后台订单管理
 */
@WebServlet("/admin/OrderAdminServlet")
public class OrderAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "search":
			searchOrder(request,response);
			break;
		case "delete":
			deleteOrder(request,response);
			break;
		case "detail":
			detailOrder(request,response);
			break;
		case "show":
		default:
			showOrder(request,response);
			break;
		}
	}

	//订单搜索
	private void searchOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String order_id = request.getParameter("id") == null ? "" : request.getParameter("id").trim();
		String receiverName = request.getParameter("receiverName")==null?"":request.getParameter("receiverName").trim();
		List<Order> orders = new ArrayList<Order>();
		
		IOrderDao orderDao = DaoFactory.getOrderDao();
		
		if(!"".equals(receiverName)) 
			orders = orderDao.getOrderByReceiverName(receiverName);
		
		if(!"".equals(order_id)) {
			if(orders.stream().filter(o -> order_id.equals(o.getId())).count() < 1) {
				Order order = orderDao.getOrderById(order_id);
				orders.add(order);
			}
		}
		
		request.setAttribute("OrderList", orders);
		request.getRequestDispatcher(response.encodeURL
				("/admin/login/home.jsp?item=order_list")).forward(request, response);
	}

	//订单删除
	private void deleteOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameterValues("ids")==null) 
			response.getWriter().print("{error-request:Please Send Param 'ids', code:404}");
		else {
			String[] ids = request.getParameterValues("ids");
			IOrderDao dao = DaoFactory.getOrderDao();
			Integer dels = dao.delOrderByIds(ids);
			if(dels>0)
				response.getWriter().print("{\"success-request\":\"Delete "+dels+" Records\", \"code\":200}");
			else 
				response.getWriter().print("{\"error-request\":\"Delete Failed\", \"code\":502}");
		}
	}

	//订单详情展示
	private void detailOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("id")==null) 
			response.getWriter().print("{error-request:Please Send Param 'order-id', code:404}");
		else {
			String id = request.getParameter("id").trim();
			IOrderDao orderDao = DaoFactory.getOrderDao();
			Order order = orderDao.getOrderById(id);
			IUserDao userDao = DaoFactory.getUserDao();
			User user = userDao.getUserById(order.getUser().getId());
			order.setUser(user);
			IOrderItemDao orderItemDao = DaoFactory.getOrderItemDao();
			List<OrderItem> orderItems = orderItemDao.getOrderItemByOrder(order);
			order.setOrderItems(orderItems);
			
			response.getWriter().print(JSON.toJSONString(order));
		}
	}

	//订单展示
	private void showOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		IOrderDao dao = DaoFactory.getOrderDao();
		PageUtils page = new PageUtils(pageNo, pageSize, dao.getOrdersCount());
		
		request.setAttribute("OrderList", dao.getOrdersData(page.getPageNo(),page.getPageSize()));
		
		request.setAttribute("pageNo", page.getPageNo());
		request.setAttribute("totalNo", page.getTotalNo());
		request.setAttribute("pageSize", page.getPageSize());
	}
	
}
