package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import exception.UserExistException;
import factory.ServiceFactory;
import model.User;
import service.IUserService;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 * 	后台管理员管理
 */
@WebServlet("/admin/AdministratorAdmin")
public class AdministratorAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	//检测当前登陆用户是否超级管理员
	private boolean checkSuperAdmin(HttpServletRequest request) {
		User admin = (User)request.getSession().getAttribute("admin");
		IUserService userService = ServiceFactory.getUserService();
		try {
			String role = userService.getAdmin(admin.getUsername(), admin.getPassword()).getRole();
			if("超级管理员".equals(role)) return true;
		} catch (UserExistException e) {}
		return false;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!checkSuperAdmin(request)) return;	//不是超级管理员的请求不响应
		
		String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "updateSelf":
			updateSelfAdmin(request,response);
			break;
		case "search":
			searchAdmin(request,response);
			break;
		case "add":
			addAdmin(request,response);
			break;
		case "update":
			updateAdmin(request,response);
			break;
		case "delete":
			deleteAdmin(request,response);
			break;
		case "detail":
			detailAdmin(request,response);
			break;
		case "show":
		default:
			showAdmin(request, response);
			break;
		}
	}
	
	//管理员个人信息更新
	private void updateSelfAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User superAdmin = (User) request.getSession().getAttribute("admin");
		User newAdmin = (User) request.getAttribute("NewAdmin");
		if (superAdmin != null && newAdmin!=null) {
			try {
				ServiceFactory.getUserService().updateAdmin(superAdmin, newAdmin);
				newAdmin.setRegister_time(superAdmin.getRegister_time());
				request.getSession().setAttribute("admin", newAdmin);
			} catch (UserExistException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(response.encodeURL(request.getHeader("Referer")));
	}

	//超级管理员更新管理员信息
	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User newAdmin = (User) request.getAttribute("NewAdmin");
		if (newAdmin != null) {
			try {
				HttpSession session = request.getSession();
				User superAdmin = (User)session.getAttribute("admin");
				
				ServiceFactory.getUserService().updateAdmin(superAdmin, newAdmin);
				
			} catch (UserExistException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=admin_list"));
	}

	//超级管理员添加新的管理员
	private void addAdmin(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		User newAdmin = (User) request.getAttribute("NewAdmin");
		if (newAdmin != null) {
			try {
				HttpSession session = request.getSession();
				User superAdmin = (User)session.getAttribute("admin");
				
				ServiceFactory.getUserService().addAdmin(superAdmin, newAdmin);
				
			} catch (UserExistException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=admin_list"));
	}

	//超级管理员删除管理员
	private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameter("ids")==null) 
			response.getWriter().print("{error-request:Please Send Param delete 'admin-ids', code:404}");
		else {
			HttpSession session = request.getSession();
			User admin = (User)session.getAttribute("admin");
			
			IUserService userService = ServiceFactory.getUserService();
			String admin_ids = request.getParameter("ids");
			Integer dels = 0;
			
			try {
				dels = userService.delAdmin(admin, admin_ids);
				response.getWriter().print("{\"success-request\":\"Delete "+dels+" Records\", \"code\":200}");
			} catch (UserExistException e) {
				response.getWriter().print("{\"error-request\":\"Delete Failed "+e.getMessage() +" \", \"code\":502}");
			}
		}
	}

	//超级管理员查看管理员信息
	private void detailAdmin(HttpServletRequest request, HttpServletResponse response) 
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
				User adminDetail = userService.getAdminDetailsById(admin, request.getParameter("id"));
				response.getWriter().print(JSON.toJSONString(adminDetail));
			} catch (UserExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//超级管理员查询过滤管理员
	private void searchAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		HttpSession session = request.getSession();
		User admin = (User)session.getAttribute("admin");
		
		IUserService userService = ServiceFactory.getUserService();
		try {
			request.setAttribute("AdminList", userService.filterAdmins(admin, username));
		} catch (UserExistException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(response.encodeURL
				("/admin/login/home.jsp?item=admin_list")).forward(request, response);
	}

	//超级管理员查询管理员列表
	private void showAdmin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
				
		HttpSession session = request.getSession();
		User admin = (User)session.getAttribute("admin");
		
		IUserService userService = ServiceFactory.getUserService();
		try {
			PageUtils page = new PageUtils(pageNo, pageSize, userService.getAdminsCount(admin));
			request.setAttribute("AdminList", userService.getAdminsByPage(admin,page.getPageNo(),page.getPageSize()));
			request.setAttribute("pageNo", page.getPageNo());
			request.setAttribute("totalNo", page.getTotalNo());
			request.setAttribute("pageSize", page.getPageSize());
		} catch (UserExistException e) {
			e.printStackTrace();
		}	
	}
	
}
