package servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.UserExistException;
import factory.ServiceFactory;
import model.User;

/**
 * 	后台用户登录管理
 */
@WebServlet("/admin/LoginAdminServlet")
public class LoginAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action") != null ? request.getParameter("action").trim() : "";
		switch (action) {
		case "code":
			getRandomCode(request, response);
			break;
		case "logout":
			logoutAdmin(request, response);
			break;
		case "login":
		default:
			loginAdmin(request, response);
			break;
		}
	}

	// 后台退出登录
	private void logoutAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("admin");
		if (admin != null)
			session.removeAttribute("admin");
		response.sendRedirect(response.encodeURL(request.getContextPath() + "/admin/login"));
	}

	// 后台登录
	private void loginAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String adminname = request.getParameter("username");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		String url = request.getContextPath() + "/admin/login";
		try {
			User admin = ServiceFactory.getUserService().getAdmin(adminname, password);
			if(request.getSession().getAttribute("validateCode").toString().equalsIgnoreCase(code)) {
				request.getSession().setAttribute("admin", admin);
				request.getSession().removeAttribute("validateCode");
				url += "/home.jsp";
			}
		} catch (UserExistException e) {
			// e.printStackTrace();
		}
		response.sendRedirect(response.encodeURL(url));
	}

	//获取验证码
	private void getRandomCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type", "text/html;charset=UTF-8");

		int width = 80;
		int codeLength = 4;
		int height = 30;

		Random semen = new Random();
		String validateCode = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 内存中创建图片
		Graphics graphics = image.getGraphics(); // 获取画板
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1, 1, width - 1, height - 1); // 填充底色
		graphics.setFont(new Font("微软雅黑", Font.BOLD, height - (width / height))); // 设置字体

		// 生成随机字符
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			int r = semen.nextInt(validateCode.length());
			String s = validateCode.substring(r, r + 1);

			graphics.setColor(new Color(semen.nextInt(255), semen.nextInt(255), semen.nextInt(255)));
			graphics.drawString(s, (width / codeLength * i) + (width / height), height - (width / height)); // 画验证码

			randomCode.append(s);
		}

		// 验证码 进行session 缓存
		HttpSession session = req.getSession();
		session.setAttribute("validateCode", randomCode.toString());

		// 设置响应头数据类型
		resp.setContentType("image/jpeg");
		// 输出到响应流
		ImageIO.write(image, "jpg", resp.getOutputStream());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
