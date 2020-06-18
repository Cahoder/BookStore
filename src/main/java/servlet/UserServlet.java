package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IUserReceiptDao;
import exception.ProductExistException;
import exception.UserExistException;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Favor;
import model.Product;
import model.User;
import model.UserReceipt;

/**
 * 	客户端用户管理
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "info":
			infoUser(request,response);
			break;
		case "updateInfo":
			updateInfoUser(request,response);
			break;
		case "favor":
			favortUser(request,response);
			break;
		case "addFavor":
			addFavortUser(request,response);
			break;
		case "deleteFavor":
			deleteFavorUser(request,response);
			break;
		case "receipt":
			receiptUser(request,response);
			break;
		case "deleteReceipt":
			deleteReceiptUser(request,response);
			break;
		case "updateReceipt":
			updateReceiptUser(request,response);
			break;
		case "register":
			registerUser(request,response);
			break;
		default:
			response.getWriter().print("REQUEST-ERROR: Parameter 'action' Missing or Error Occur !");
			break;
		}
	}

	//添加用户收藏
	private void addFavortUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		String product_id = request.getParameter("id");
		if(product_id!=null && !"".equals(product_id))
			try {
				Product product = ServiceFactory.getProductService().getProduct(product_id);
				
				DaoFactory.getFavorDao().addFavor(new Favor(user,product));
				response.sendRedirect(response.encodeURL(request.getHeader("Referer")));
			} catch (ProductExistException e) {
				response.getWriter().print("REQUEST-ERROR: Product Missing or Error Occur !");
			}
	}

	//删除用户收藏
	private void deleteFavorUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		String id = request.getParameter("id");
		if(id!=null && !"".equals(id)) 
			DaoFactory.getFavorDao().delFavors(Integer.valueOf(id));
		
		response.sendRedirect(response.encodeURL(request.getHeader("Referer")));
	}

	//查看用户收藏
	private void favortUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		try {
			List<Favor> userFavors = ServiceFactory.getUserService().getUserFavorsById(user, user.getId().toString());
			
			request.setAttribute("UserFavor", userFavors);
			request.getRequestDispatcher(response.encodeURL("favor.jsp")).forward(request, response);
		} catch (UserExistException e) {
			response.getWriter().print("REQUEST-ERROR: Error Occur "+e.getMessage()+"!");
		}
	}

	//删除用户地址
	private void deleteReceiptUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		String id = request.getParameter("id");
		if(id!=null && !"".equals(id)) 
			DaoFactory.getUserReceiptDao().delUserReceiptById(Integer.valueOf(id));
		
		response.sendRedirect(response.encodeURL(request.getHeader("Referer")));
	}

	//更新用户地址
	private void updateReceiptUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		String id = request.getParameter("id").trim();
		String name = request.getParameter("name").trim();
		String address = request.getParameter("address").trim();
		String phone = request.getParameter("phone").trim();
		
		IUserReceiptDao userReceiptDao = DaoFactory.getUserReceiptDao();
		if(id!=null && !"".equals(id)) {
			UserReceipt receipt = new UserReceipt(Integer.valueOf(id), user, name, address, phone);
			userReceiptDao.updateUserReceipt(receipt);
		} else {
			UserReceipt receipt = new UserReceipt(user, name, address, phone);
			userReceiptDao.addUserReceipt(receipt);
		}
		
		response.sendRedirect(response.encodeURL(request.getHeader("Referer")));
	}

	//查看用户地址
	private void receiptUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		try {
			user = ServiceFactory.getUserService().getUserDetailsById(user, user.getId().toString());
			List<UserReceipt> userReceipts = user.getUser_receipt();
			
			String id = request.getParameter("id");
			UserReceipt defaultReceipt = null;
			if(userReceipts!=null && userReceipts.size() > 1) {
				defaultReceipt = userReceipts.get(0);
				if(id!=null && !"".equals(id)) 
					for (UserReceipt u : userReceipts) 
						if(u.getId() == Integer.valueOf(id)) {
							defaultReceipt = u;
							break;
						}
			}
			
			request.setAttribute("DefaultUserReceipt", defaultReceipt);
			request.setAttribute("UserReceipt", userReceipts);
			request.getRequestDispatcher(response.encodeURL("receipt.jsp")).forward(request, response);
		} catch (UserExistException e) {
			response.getWriter().print("REQUEST-ERROR: Error Occur "+e.getMessage()+"!");
		}
	}

	//更新用户信息
	private void updateInfoUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User newUser = (User) request.getAttribute("NewUser");
		String handleUrl = "./";	 //默认跳转首页
		String Msg = "";
		String redirectUrl = "./";
		if(newUser!=null) {
			try {
				Integer res = ServiceFactory.getUserService().updateUser(newUser);
				if(res == 1) {
					request.getSession().setAttribute("user", 
							ServiceFactory.getUserService().getUser(newUser.getUsername(), newUser.getPassword()));
					handleUrl = "handle-success.jsp";
					redirectUrl = "UserServlet?action=info";
					Msg = "更新用户信息成功: Redirecting... !";
				}
			} catch (UserExistException e) {
				handleUrl = "handle-fail.jsp";
				redirectUrl = "./";
				Msg = e.getMessage();
			}
		}else {
			handleUrl = "handle-fail.jsp";
			redirectUrl = "./";
			Msg = "更新用户信息失败: Parameter Missing or Error Occur";
		}
		request.setAttribute("Msg", Msg);
		request.setAttribute("redirectUrl", response.encodeURL(redirectUrl));
		request.getRequestDispatcher(response.encodeURL(handleUrl)).forward(request, response);
	}

	//查看用户信息
	private void infoUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		request.setAttribute("User", user);
		request.getRequestDispatcher(response.encodeURL("user.jsp")).forward(request, response);
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User newUser = (User) request.getAttribute("NewUser");
		String handleUrl = "./";	 //默认跳转首页
		String Msg = "";
		String redirectUrl = "./";
		if(newUser!=null) {
			try {
				Integer res = ServiceFactory.getUserService().addUser(newUser);
				if(res == 1) {
					handleUrl = "handle-success.jsp";
					Msg = "注册成功";
					redirectUrl = "login.jsp";
				}
			} catch (UserExistException e) {
				handleUrl = "handle-fail.jsp";
				Msg = e.getMessage();
				redirectUrl = "register.jsp";
			}
		}else {
			handleUrl = "handle-fail.jsp";
			Msg = "注册失败: Parameter Missing or Error Occur";
			redirectUrl = "register.jsp";
		}
		
		request.setAttribute("Msg", Msg);
		request.setAttribute("redirectUrl", response.encodeURL(redirectUrl));
		request.getRequestDispatcher(response.encodeURL(handleUrl)).forward(request, response);
	}
	
}
