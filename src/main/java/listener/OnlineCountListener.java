package listener;

import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener(value = "通过session判断网站在线人数")
public class OnlineCountListener implements HttpSessionAttributeListener,ServletContextListener,HttpSessionListener {

	private static ServletContext application;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		application = sce.getServletContext();
		application.setAttribute("online", new LinkedList<String>());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

	/**
	 * 	当往session中添加属性时候会触发
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		@SuppressWarnings( "unchecked" )
		LinkedList<String> onlines = (LinkedList<String>)application.getAttribute("online");
		if ("username".equals(se.getName())) {
			onlines.add(se.getName());
			application.setAttribute("online", onlines);
			System.out.println("当前在线人数："+onlines.size());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {}

	@Override
	public void sessionCreated(HttpSessionEvent se) {}

	/**
	 * 当该用户的session过期或销毁时候会触发
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		@SuppressWarnings( "unchecked" )
		LinkedList<String> onlines = (LinkedList<String>)application.getAttribute("online");
		if (se.getSession().getAttribute("username")!=null) {
			onlines.remove(se.getSession().getAttribute("username"));
			application.setAttribute("online", onlines);
			System.out.println("当前在线人数："+onlines.size());
		}
	}
	
}
