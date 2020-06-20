package servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ISaleDao;
import exception.StrExistException;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Notice;
import model.Sale;
import model.Str;
import service.INoticeService;
import service.IStrService;

/**
 *  前台公告管理
 */
public class NoticeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取轮播图
		IStrService sliderService = ServiceFactory.getStrService();
		try {
			List<Str> sliders = sliderService.getSliders().stream()
				.filter((s)->{return s.getIs_show()==0;}).collect(Collectors.toList());
			request.setAttribute("Slider", sliders);
		} catch (StrExistException e) {
			e.printStackTrace();
		}
		
		//获取公告通知
		Integer NoticeIndex = request.getParameter("ni") != null && !"".equals(request.getParameter("ni")) 
				? Integer.valueOf(request.getParameter("ni")) : 0;
		INoticeService service = ServiceFactory.getNoticeService();
		List<Notice> NoticeList = service.getNotices().stream().limit(5).collect(Collectors.toList());
		request.setAttribute("ni", NoticeIndex%NoticeList.size());
		request.setAttribute("Notice", NoticeList);
		
		//获取销售榜单
		ISaleDao dao = DaoFactory.getSaleDao();
		List<Sale> sale = dao.getOnSalesData().stream().limit(6).collect(Collectors.toList());
		request.setAttribute("Sale", sale);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
