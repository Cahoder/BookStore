package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ISaleDao;
import factory.DaoFactory;
import model.Sale;
import utils.PageUtils;

@WebServlet(description = "后台销售商品记录", urlPatterns = { "/admin/SaleAdminServlet" })
public class SaleAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action") : "";
		switch (action) {
		case "download":
			downloadSale(request,response);
			break;
		case "filter":
			filterSale(request,response);
			break;
		case "show":
		default:
			showSale(request, response);
			break;
		}
	}

	//销售记录下载
	private void downloadSale(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException,UnsupportedEncodingException {
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		ISaleDao dao = DaoFactory.getSaleDao();
		String filename = null;
		List<Sale> data = null;
		
		if("0".equals(month)&&"".equals(year)) {
			filename = "销售榜单总计";
			data = dao.getSalesData();
		}
		else {
			Timestamp[] params = getBetweenTimestamp(year, month);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(params[0].getTime());
		
			filename = cal.get(Calendar.YEAR)+"年";
			filename += "0".equals(month) ? "全年" : cal.get(Calendar.MONTH)+1+"月";
			filename += "销售榜单";
			
			data = dao.getSalesData(params);
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=" 
				+ new String ((filename + ".csv").getBytes("GBK"), "ISO-8859-1"));
		//windows默认编码为GBK
		response.setCharacterEncoding("GBK");
		PrintWriter pw = response.getWriter();
		pw.println("商品编号,商品名称,商品类型,商品总销量");
		for (int i = 0; i < data.size(); i++) {
			Sale sale = data.get(i);
			pw.println(
				sale.getProduct_id() + "," + 
				sale.getProduct_name() + "," + 
				sale.getCategory() + "," + 
				sale.getBuynum()
			);
		}
		pw.flush();
		pw.close();
	}

	//搜索过滤
	private void filterSale(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		Timestamp[] between = getBetweenTimestamp(year,month);
		
		ISaleDao dao = DaoFactory.getSaleDao();
		
		List<Sale> products = dao.getSalesData(between);
		request.setAttribute("ProductList", products);
//		request.setAttribute("year", year);
//		request.setAttribute("month", month);
		
		request.getRequestDispatcher(response.encodeURL
				("/admin/login/home.jsp?item=sale_list")).forward(request, response);
	}

	// 展示商品
	private void showSale(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		ISaleDao dao = DaoFactory.getSaleDao();
		
		List<Sale> products = dao.getSalesData();
		List<Sale> collect = products.stream().skip((pageNo-1)*pageSize).limit(pageSize).collect(Collectors.toList());
		
		PageUtils page = new PageUtils(pageNo, pageSize, products.size());
		
		request.setAttribute("ProductList", collect);
		request.setAttribute("pageNo", page.getPageNo());
		request.setAttribute("totalNo", page.getTotalNo());
		request.setAttribute("pageSize", page.getPageSize());
	}
	
	//根据前台提供的年/月 分析出合适的间隔时间
	private Timestamp[] getBetweenTimestamp(String year,String month) {
		if("".equals(year)) year += Calendar.getInstance().get(Calendar.YEAR);	//没有给年就按当年
		
		Calendar cal=Calendar.getInstance();
		cal.set(Integer.valueOf(year), "0".equals(month) ? 0:Integer.valueOf(month)-1, 1, 0, 0, 0);
		Timestamp begin = new Timestamp(cal.getTimeInMillis());
		
		if("0".equals(month)) 
			cal.add(Calendar.YEAR, 1); //没有给月份就搜全年
		else
			cal.add(Calendar.MONTH, 1);
		Timestamp end = new Timestamp(cal.getTimeInMillis()-1000);
		return new Timestamp[]{begin,end};
	}
	
}
