package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import exception.NoticeExistException;
import factory.ServiceFactory;
import model.Notice;
import service.INoticeService;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 *	 后台公告管理
 */
@WebServlet("/admin/NoticeAdminServlet")
public class NoticeAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "add":
			addNotice(request,response);
			break;
		case "update":
			updateNotice(request,response);
			break;
		case "delete":
			deleteNotice(request,response);
			break;
		case "detail":
			detailNotice(request,response);
			break;
		case "show":
		default:
			showNotice(request, response);
			break;
		}
	}
	
	//删除指定公告
	private void deleteNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			String[] ids = request.getParameterValues("ids");
			INoticeService noticeService = ServiceFactory.getNoticeService();
			try {
				Integer dels = noticeService.delNotices(ids);
				response.getWriter().print("{\"success-request\":\"Delete "+dels+" Records\", \"code\":200}");
			} catch (NoticeExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}

	//查找公告详情
	private void detailNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String id = request.getParameter("id");
			
			INoticeService noticeService = ServiceFactory.getNoticeService();
			try {
				Notice notice = noticeService.getNotice(id);
				response.getWriter().print(JSON.toJSONString(notice));
			} catch (NoticeExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}
	
	//更新指定公告
	private void updateNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Notice notice = new Notice();
		notice.setId(Integer.valueOf(request.getParameter("id")));
		notice.setTitle(request.getParameter("title"));
		notice.setDetails(request.getParameter("details"));
		
		INoticeService noticeService = ServiceFactory.getNoticeService();
		try {
			noticeService.editNotice(notice);
		} catch (NoticeExistException e) {}
		response.sendRedirect(response.encodeURL("login/home.jsp?item=notice_list"));
	}

	//新增公告通知
	private void addNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Notice notice = new Notice();
		notice.setTitle(request.getParameter("title"));
		notice.setDetails(request.getParameter("details"));
		
		INoticeService noticeService = ServiceFactory.getNoticeService();
		try {
			noticeService.addNotice(notice);
		} catch (NoticeExistException e) {
			e.printStackTrace();
		}
		response.sendRedirect(response.encodeURL("login/home.jsp?item=notice_list"));
	}

	// 展示公告列表
	private void showNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		INoticeService noticeService = ServiceFactory.getNoticeService();
		
		PageUtils page = new PageUtils(pageNo, pageSize, noticeService.getNoticesCount());
		request.setAttribute("NoticeList", noticeService.getNoticesByPage(page.getPageNo(),page.getPageSize()));
		
		request.setAttribute("pageNo", page.getPageNo());
		request.setAttribute("totalNo", page.getTotalNo());
		request.setAttribute("pageSize", page.getPageSize());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
