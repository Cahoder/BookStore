package utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtils {
	
	/**
	 * 	简单判断是否是Ajax异步请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		return false;
	}
	
}
