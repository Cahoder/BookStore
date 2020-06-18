package servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ISaleDao;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Sale;
import service.INoticeService;

/**
 *  前台公告管理
 */
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取公告通知
		INoticeService service = ServiceFactory.getNoticeService();
		request.setAttribute("Notice", service.getLastestNotice());
		//获取销售榜单
		ISaleDao dao = DaoFactory.getSaleDao();
		List<Sale> sale = dao.getOnSalesData().stream().limit(6).collect(Collectors.toList());
		request.setAttribute("Sale", sale);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
