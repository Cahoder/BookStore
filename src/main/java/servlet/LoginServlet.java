package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.CartExistException;
import exception.UserExistException;
import factory.ServiceFactory;
import model.Cart;
import model.User;
import service.ICartService;
import service.IUserService;

/**
 * 	客户端登录管理
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "login":
			loginUser(request,response);
			break;
		case "logout":
			logoutUser(request,response);
			break;
		default:
			response.getWriter().print("REQUEST-ERROR: Parameter 'action' Missing !");
			break;
		}
	}

	private void logoutUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		@SuppressWarnings("unchecked")
		List<Cart> UserCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(user != null) {
			if(UserCarts!=null && UserCarts.size() > 0) {
				ICartService cartService = ServiceFactory.getCartService();
				try {
					cartService.updateUserCarts(user,UserCarts); //更新用户缓存里的购物车到数据库
				} catch (CartExistException e) {}
			}
			session.removeAttribute("user");
			session.removeAttribute("UserCarts");
		}
		response.sendRedirect(response.encodeURL("./"));
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		IUserService service = ServiceFactory.getUserService();
		try {
			User user = service.getUser(username, password);
			if("普通用户".equals(user.getRole())) {
				request.getSession().setAttribute("user", user);
				
				request.setAttribute("Msg", "登录成功");
				request.setAttribute("redirectUrl", response.encodeURL("./"));
				request.getRequestDispatcher(response.encodeURL("handle-success.jsp")).forward(request, response);
				return;
			}
		} catch (UserExistException e) {}
		request.setAttribute("Msg", "登录失败");
		request.setAttribute("redirectUrl", response.encodeURL("login.jsp"));
		request.getRequestDispatcher(response.encodeURL("handle-fail.jsp")).forward(request, response);
	}
}
