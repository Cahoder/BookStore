package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IUserReceiptDao;
import exception.CartExistException;
import exception.UserExistException;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Cart;
import model.Product;
import model.User;
import model.UserReceipt;
import service.ICartService;
import service.IUserService;

/**
 * 	客户端购物车管理
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "cart":
			cartUser(request,response);
			break;
		case "delCart":
			delCartUser(request,response);
			break;
		case "addCart":
			addCartUser(request,response);
			break;
		case "addNum":
			addNumUser(request,response);
			break;
		case "subNum":
			subNumUser(request,response);
			break;
		case "preOrder":
			preSubmitCartUser(request,response);
			break;
		default:
			response.getWriter().print("REQUEST-ERROR: Parameter 'action' Missing or Error Occur !");
			break;
		}
	}

	//用户订单提交准备
	private void preSubmitCartUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(sessionCarts != null) {
			ICartService cartService = ServiceFactory.getCartService();
			try {
				//先同步一下用户的购物车数据
				cartService.updateUserCarts(user,sessionCarts);
				
				IUserReceiptDao receipt = DaoFactory.getUserReceiptDao();
				List<UserReceipt> userReceipts = receipt.getUserReceipts(user);
				
				request.setAttribute("UserReceipt", userReceipts);
				request.getRequestDispatcher(response.encodeURL("checkcart.jsp")).forward(request, response);
				
			} catch (CartExistException e) {
				response.getWriter().print("REQUEST-ERROR: Error Occur !");
			}
		}
	}

	private void subNumUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(sessionCarts != null) {
			for (Cart cart : sessionCarts) 
				if(id.equals(cart.getProduct().getId())) {
					cart.setAddnum(cart.getAddnum()-1);
					break;
				}
		}
		request.getSession().setAttribute("UserCarts", sessionCarts);
		request.getRequestDispatcher(response.encodeURL("cart.jsp")).forward(request, response);
	}

	private void addNumUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id");
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(sessionCarts != null && id != null) {
			for (Cart cart : sessionCarts) 
				if(id.equals(cart.getProduct().getId())) {
					cart.setAddnum(cart.getAddnum()+1);
					break;
				}
		}
		request.getSession().setAttribute("UserCarts", sessionCarts);
		request.getRequestDispatcher(response.encodeURL("cart.jsp")).forward(request, response);
	}

	private void addCartUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(sessionCarts == null) {
			IUserService userService = ServiceFactory.getUserService();
			//查数据库用户的购物车
			List<Cart> userCarts;
			try {
				userCarts = userService.getUserCartsById(user, user.getId().toString());
			} catch (UserExistException e) {
				userCarts = new ArrayList<Cart>();
			}
			sessionCarts = userCarts;
		}
		Product newProdcut = (Product) request.getAttribute("Product");
		if(newProdcut!=null && newProdcut.getPnum() > 1) {
			boolean exist = false;
			for (Cart cart : sessionCarts) 
				if(newProdcut.getId().equals(cart.getProduct().getId())) {
					cart.setAddnum(cart.getAddnum()+1);
					exist = true;
					break;
				}
			
			if(!exist) {
				Cart cart = new Cart();
				cart.setAddnum(1);
				cart.setUser(user);
				cart.setProduct(newProdcut);
				sessionCarts.add(cart);
			}
			
		}
		request.getSession().setAttribute("UserCarts", sessionCarts);
		request.getRequestDispatcher(response.encodeURL("cart.jsp")).forward(request, response);
	}

	private void delCartUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		String id = request.getParameter("id");
		if("".equals(id) || id == null) {
			response.getWriter().print("Parameter 'id' Missing or Error Occur!");
			return;
		}
		@SuppressWarnings("unchecked")
		List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
		if(sessionCarts != null && sessionCarts.size() >= 1 ) {
			List<Cart> collect = sessionCarts.stream().
				filter((c) -> {return !id.equals(c.getProduct().getId());}).collect(Collectors.toList());
			request.getSession().setAttribute("UserCarts", collect);
		}
		request.getRequestDispatcher(response.encodeURL("cart.jsp")).forward(request, response);
	}

	private void cartUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			request.getSession().removeAttribute("UserCarts");
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		try {
			//将用户的购物车记录缓存到session
			@SuppressWarnings("unchecked")
			List<Cart> sessionCarts = (List<Cart>)request.getSession().getAttribute("UserCarts");
			if(sessionCarts != null) {
				ICartService cartService = ServiceFactory.getCartService();
				cartService.updateUserCarts(user,sessionCarts); //更新用户缓存里的购物车到数据库
			}
			
			//然后再查数据库用户的购物车
			List<Cart> userCarts = ServiceFactory.getUserService().getUserCartsById(user, user.getId().toString());
			request.getSession().setAttribute("UserCarts", userCarts);
			
			request.getRequestDispatcher(response.encodeURL("cart.jsp")).forward(request, response);
		} catch (UserExistException | CartExistException  e) {
			response.getWriter().print("REQUEST-ERROR: Error Occur "+e.getMessage()+"!");
		}
	}

}
