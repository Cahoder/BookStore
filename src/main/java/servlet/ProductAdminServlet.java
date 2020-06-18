package servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import exception.ProductExistException;
import factory.ServiceFactory;
import model.Product;
import service.IProductService;
import utils.HttpRequestUtils;
import utils.PageUtils;

/**
 * 	后台商品管理
 */
@WebServlet("/admin/ProductAdminServlet")
public class ProductAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action")!=null ? request.getParameter("action") : "";
		switch (action) {
		case "filter":
			filterProduct(request,response);
			break;
		case "delete":
			deleteProduct(request,response);
			break;
		case "detail":
			detailProduct(request,response);
			break;
		case "update":
			updateProduct(request,response);
			break;
		case "add":
			addProduct(request,response);
			break;
		case "show":
		default:
			showProduct(request,response);
			break;
		}
	}

	//更新商品
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Product product = (Product) request.getAttribute("NewProduct");
		if (product != null) {
			try {
				ServiceFactory.getProductService().editProduct(product);
			} catch (ProductExistException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=product_list"));
	}

	//搜索商品
	private void filterProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String min = request.getParameter("min").trim();
		String max = request.getParameter("max").trim();
		String category = request.getParameter("category").trim();
		String id = request.getParameter("id").trim();
		String name = request.getParameter("pname").trim();
		
		try {
			List<Product> filterProducts = 
					ServiceFactory.getProductService().filterProducts(min,max,id,name,category);
			request.setAttribute("ProductList", filterProducts);
		} catch (ProductExistException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(response.encodeURL
				("/admin/login/home.jsp?item=product_list")).forward(request, response);
	}

	//删除商品
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else if (request.getParameterValues("ids")==null) 
			response.getWriter().print("{error-request:Please Send Param 'ids', code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String[] ids = request.getParameterValues("ids");
			Integer dels = 0;
			try {
				dels = ServiceFactory.getProductService().delProducts(ids);
			} catch (ProductExistException e) {
				e.printStackTrace();
			}
			if(dels>0)
				response.getWriter().print("{\"success-request\":\"Delete "+dels+" Records\", \"code\":200}");
			else 
				response.getWriter().print("{\"error-request\":\"Delete Failed\", \"code\":502}");
		}
	}

	//查看商品详情
	private void detailProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(!HttpRequestUtils.isAjaxRequest(request)) 
			response.getWriter().print("{error-request:Please Send Ajax Request, code:404}");
		else {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String id = request.getParameter("id");
			
			IProductService productService = ServiceFactory.getProductService();
			try {
				Product product = productService.getProduct(id);
				response.getWriter().print(JSON.toJSONString(product));
			} catch (ProductExistException e) {
				response.getWriter().print("{error-request:"+e.getMessage()+", code:404}");
			}
		}
	}
	
	//添加新商品
	private void addProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Product product = (Product) request.getAttribute("NewProduct");
		if (product != null) {
			product.setId(UUID.randomUUID().toString());
			try {
				ServiceFactory.getProductService().addProduct(product);
			} catch (ProductExistException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(response.encodeURL("../login/home.jsp?item=product_list"));
	}
	
	//展示商品列表
	private void showProduct(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer pageNo = request.getParameter("pageNo")!=null 
				? Integer.parseInt(request.getParameter("pageNo")) : 1;
		Integer pageSize = request.getParameter("pageSize")!=null 
				? Integer.parseInt(request.getParameter("pageSize")) : 10;
		
		IProductService service = ServiceFactory.getProductService();
		Integer totalSize = service.getProductsCount();
		
		PageUtils page = new PageUtils(pageNo, pageSize, totalSize);
		
		List<Product> products = service.getProductsByPage(page.getPageNo(), page.getPageSize());
		
		request.setAttribute("ProductList", products);
		request.setAttribute("pageNo", page.getPageNo());
		request.setAttribute("totalNo", page.getTotalNo());
		request.setAttribute("pageSize", page.getPageSize());
	}
}
