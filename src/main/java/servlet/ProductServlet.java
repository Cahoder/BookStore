package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ProductExistException;
import exception.UserExistException;
import factory.ServiceFactory;
import model.Favor;
import model.Product;
import model.User;
import service.IProductService;
import utils.PageUtils;

/**
 * 	前台展示查询商品列表
 */
public class ProductServlet extends HttpServlet {
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
			productInfo(request,response);
			break;
		case "list":
			productList(request,response);
			break;
		default:
			response.getWriter().print("REQUEST-ERROR: Parameter 'action' Missing or Error Occur !");
			break;
		}
	}

	private void productInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			Product product = ServiceFactory.getProductService().getProduct(id);
			
			User user = (User)request.getSession().getAttribute("user");
			if(user != null) {
				try {
					List<Favor> userFavors = ServiceFactory.getUserService()
							.getUserFavorsById(user, user.getId().toString());
					List<Favor> favor = userFavors.stream()
							.filter((f)->{return f.getProduct().getId().equals(product.getId());})
							.limit(1).collect(Collectors.toList());
					if(favor.size() == 1)
						request.setAttribute("isFavor", favor.get(0));
				} catch (UserExistException e) {}
			}
			
			request.setAttribute("Product", product);
			request.getRequestDispatcher(response.encodeURL("productinfo.jsp")).forward(request, response);
		} catch (ProductExistException e) {
			response.getWriter().print("REQUEST-ERROR: "+e.getMessage()+"");
		}
	}

	private void productList(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		String category = request.getParameter("category");
		IProductService service = ServiceFactory.getProductService();
		Integer totalSize = 0;
		PageUtils page = null;
		List<Product> products = null;
		if(category==null||"".equals(category)) {
			totalSize = service.getProductsCount();
			page = new PageUtils(pageNo, pageSize, totalSize);
			products = service.getProductsByPage(page.getPageNo(), page.getPageSize());
		} else {
			try {
				products = service.filterProducts(null, null, null, null, category);
				page = new PageUtils(pageNo, pageSize, products.size());
			} catch (ProductExistException e) {
				products = new ArrayList<Product>();
			}
		}
		
		request.setAttribute("ProductList", products);
		request.setAttribute("pageNo", page.getPageNo());
		request.setAttribute("totalNo", page.getTotalNo());
		request.setAttribute("pageSize", page.getPageSize());
		
		request.getRequestDispatcher(response.encodeURL("productlist.jsp")).forward(request, response);
	}
}
