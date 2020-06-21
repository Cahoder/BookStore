package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import exception.CartExistException;
import exception.OrderExistException;
import exception.UserExistException;
import factory.ServiceFactory;
import model.Cart;
import model.Favor;
import model.Order;
import model.User;
import service.ICartService;
import service.IOrderService;
import service.IUserService;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 * 	 后台用户管理
 */
@WebServlet("/admin/UserAdminServlet")
public class UserAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "search":
			searchUser(request,response);
			break;
		case "detailCart":
			detailUserCart(request,response);
			break;
		case "detailFavor":
			detailUserFavor(request,response);
			break;
		case "detailOrder":
			detailUserOrder(request,response);
			break;
		case "detailInfo":
			detailUserInfo(request,response);
			break;
		case "show":
		default:
			showUser(request, response);
			break;
		}
	}

	//查看用户收藏列表
	private void detailUserFavor(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("id")==null) 
			response.getWriter().print("{error-request:Please Send Param 'user-id', code:404}");
		else {
			HttpSession session = request.getSession();
			User admin = (User)session.getAttribute("admin");
			
			IUserService userService = ServiceFactory.getUserService();
			try {
				List<Favor> userFavors = userService.getUserFavorsById(admin, request.getParameter("id"));
				response.getWriter().print(JSON.toJSONString(userFavors));
			} catch (UserExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//查看用户购物车列表
	private void detailUserCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("id")==null) 
			response.getWriter().print("{error-request:Please Send Param 'user-id', code:404}");
		else {
			HttpSession session = request.getSession();
			User admin = (User)session.getAttribute("admin");
			
			ICartService cartService = ServiceFactory.getCartService();
			try {
				List<Cart> userCarts = cartService.getUserCartsById(admin, request.getParameter("id"));
				response.getWriter().print(JSON.toJSONString(userCarts));
			} catch (CartExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//查看用户订单列表
	private void detailUserOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("id")==null) 
			response.getWriter().print("{error-request:Please Send Param 'user-id', code:404}");
		else {
			HttpSession session = request.getSession();
			User admin = (User)session.getAttribute("admin");
			
			IOrderService orderService = ServiceFactory.getOrderService();
			try {
				List<Order> userOrders = orderService.getUserOrders(admin,request.getParameter("id"));
				response.getWriter().print(JSON.toJSONString(userOrders));
			} catch (OrderExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//查看用户个人信息详情
	private void detailUserInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("id")==null) 
			response.getWriter().print("{error-request:Please Send Param 'user-id', code:404}");
		else {
			HttpSession session = request.getSession();
			User admin = (User)session.getAttribute("admin");
			
			IUserService userService = ServiceFactory.getUserService();
			try {
				User userDetail = userService.getUserDetailsById(admin, request.getParameter("id"));
				response.getWriter().print(JSON.toJSONString(userDetail));
			} catch (UserExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//用户搜索过滤
	private void searchUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		HttpSession session = request.getSession();
		User admin = (User)session.getAttribute("admin");
		
		IUserService userService = ServiceFactory.getUserService();
		try {
			request.setAttribute("UserList", userService.filterUsers(admin, username));
		} catch (UserExistException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(response.encodeURL
				("/admin/login/home.jsp?item=user_list")).forward(request, response);
	}

	//查看用户列表
	private void showUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
				
		HttpSession session = request.getSession();
		User admin = (User)session.getAttribute("admin");
		
		IUserService userService = ServiceFactory.getUserService();
		try {
			PageUtils page = new PageUtils(pageNo, pageSize, userService.getUsersCount(admin));
			request.setAttribute("UserList", userService.getUsersByPage(admin,page.getPageNo(),page.getPageSize()));
			request.setAttribute("pageNo", page.getPageNo());
			request.setAttribute("totalNo", page.getTotalNo());
			request.setAttribute("pageSize", page.getPageSize());
		} catch (UserExistException e) {
			response.getWriter().print("{error-request:"+e.getMessage()+", code:502}");
		}
	}

}
