package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import exception.StrExistException;
import factory.ServiceFactory;
import model.Str;
import service.IStrService;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 * 	后台轮播图管理
 */
@WebServlet("/admin/SliderAdminServlet")
public class SliderAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "show":
			showSlider(request,response);
			break;
		case "detail":
			detailSlider(request,response);
			break;
		case "delete":
			deleteSlider(request, response);
			break;
		case "update":
			updateSlider(request, response);
			break;
		case "add":
			addSlider(request, response);
			break;
		case "display":
		default:
			displaySlider(request, response);
			break;
		}
	}

	private void displaySlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 3;
				
		IStrService sliderService = ServiceFactory.getStrService();
		
		PageUtils page;
		try {
			page = new PageUtils(pageNo, pageSize, sliderService.getSliderCounts());
			request.setAttribute("SliderList", sliderService.getSlidersByPage(page.getPageNo(),page.getPageSize()));
			request.setAttribute("pageNo", page.getPageNo());
			request.setAttribute("totalNo", page.getTotalNo());
			request.setAttribute("pageSize", page.getPageSize());
		} catch (StrExistException e) {
			e.printStackTrace();
		}
	}

	private void detailSlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String id = request.getParameter("id");
			try {
				Str slider = ServiceFactory.getStrService().getSliderById(id);
				response.getWriter().print(JSON.toJSONString(slider));
			} catch (StrExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	private void deleteSlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String[] ids = request.getParameterValues("ids");
			try {
				Integer dels = ServiceFactory.getStrService().deleteSliders(ids);
				response.getWriter().print("{\"success-request\":\"Delete "+dels+" Records\", \"code\":200}");
			} catch (StrExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	private void updateSlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Str slider = (Str)request.getAttribute("NewSlider");
		try {
			ServiceFactory.getStrService().updateSlider(slider);
		} catch (StrExistException e) {
			e.printStackTrace();
		}
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=slider_list"));
	}

	private void addSlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Str slider = (Str)request.getAttribute("NewSlider");
		try {
			ServiceFactory.getStrService().addSlider(slider);
		} catch (StrExistException e) {
			e.printStackTrace();
		}
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=slider_list"));
	}

	private void showSlider(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String id = request.getParameter("id");
			try {
				Integer i = ServiceFactory.getStrService().showSlider(id);
				response.getWriter().print("{\"success-request\":\"Update "+i+" Records\", \"code\":200}");
			} catch (StrExistException e) {
				e.printStackTrace();
				response.getWriter().print("{error-request: "+e.getMessage()+", code:404}");
			}
		}
	}
	
}
