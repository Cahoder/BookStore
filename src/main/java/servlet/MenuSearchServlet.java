package servlet;

import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IProductDao;
import factory.DaoFactory;
import model.Product;

/**
 * 	商品列表搜索
 */
public class MenuSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String searchfield = request.getParameter("textfield").trim();
		if("".equals(searchfield)) response.sendRedirect(request.getHeader("Referer")); //从哪来回哪去
		IProductDao dao = DaoFactory.getProductDao();
		
		//在dao层中将数据整合到JavaBean中返回
		List<Product> products = dao.nameFilterProductsData(searchfield);
		
		request.setAttribute("ProductList", products);
		request.getRequestDispatcher(response.encodeURL("productlist.jsp")).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
