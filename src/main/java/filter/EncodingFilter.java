package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

@WebFilter(
		description = "对站点进行统一编码", 
		urlPatterns = { "/*" },
		initParams = {
				@WebInitParam(name = "encoding", value = "UTF-8")
		})
public class EncodingFilter implements Filter {
	private static String encoding;

	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
	}
	
	public void destroy() {}

	/**
	 * 执行过滤
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getServletPath().toLowerCase();
		if(!path.matches("^\\S+(\\.jpg|\\.gif|\\.png|\\.css|\\.map|\\.js)$")) {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html;charset="+encoding);
		}
		//如果职责链还有其它过滤 提交给其它过滤器 没有就放行
		chain.doFilter(request, response);
	}
}
