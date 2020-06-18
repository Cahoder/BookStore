package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebFilter(description = "后台登录检测",urlPatterns = { "/admin/*" })
public class LoginAdminFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		User admin = (User)session.getAttribute("admin");
		
		String path = req.getServletPath().toLowerCase();
		if(!path.matches("^\\S+(\\.jpg|\\.gif|\\.png|\\.css|\\.map|\\.js|loginadminservlet)$") && admin == null)
			//未登录跳转后台登陆页面
			req.getRequestDispatcher(res.encodeURL("/WEB-INF/page/loginAdmin.jsp")).forward(req, res);
		else
			chain.doFilter(request, response);
	}
	
}
