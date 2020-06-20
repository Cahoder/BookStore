package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * 	处理文件上传
 * 	请求头 content-type must to be 'multipart/form-data; boundary='
 */
@WebServlet(asyncSupported = true,urlPatterns = {"/UpLoadFileServlet"})
@MultipartConfig
public class UpLoadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json;charset=UTF-8");

		Part file = request.getPart("file");
		if (file == null)
			response.getWriter().print("{error:No Match Any File upload, code:404}"); // 无文件上传
		String operator = request.getParameter("operator");
		if (operator == null || "".equalsIgnoreCase(operator))
			response.getWriter().print("{error:No Match Any 'operator' Parameter, code:404}"); // 无操作指示
		if (file == null | operator == null || "".equalsIgnoreCase(operator)) return; // 以防万一

		// 根据操作放在不同文件夹下
		String path = getSavePath(operator);
		String fn = getFileName(file);
		
		StringBuilder jsonStr = new StringBuilder("{");
		
		boolean write = writeToPath(file, path + fn); //写入
		
		if(write) jsonStr.append("\"success\":\""+reCutPath(operator,path+fn)+"\", \"code\":\"200\"");
		else jsonStr.append("error:File Upload failed, code:500");
		
		response.getWriter().print(jsonStr.append("}").toString());
	}
	
	/**
	 *	 写入指定本地路径
	 * @param file
	 * @param path
	 * @throws IOException 
	 */
	private boolean writeToPath(Part file, String path) {
		boolean res = false;
		try {
			file.write(path);
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	/**
	 * 	获取文件保存路径
	 * @param operator
	 * @return
	 */
	private String getSavePath(String operator) {
		String path = getServletContext().getRealPath("");
		switch (operator) {
		case "productImg":
			path+="client\\images\\productImg\\"; 	//实际生产路径
			//path = "F:\\eclipse\\workspaces\\BookStore\\WebContent\\client\\images\\productImg";  //开发路径
			break;
		case "userAvatar":
			path+="client\\images\\userImg\\";
			break;
		case "sliderImg":
			path+="client\\images\\ad\\";
			break;
		default :
			path += "";
		}
		return path;
	}

	/**
	 * 	获取上传的文件名字
	 * @param part
	 * @return
	 */
	private String getFileName(Part part) {
		String header = part.getHeader("Content-Disposition");
		String fileName = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
		return fileName;
	}
	
	/**
	 * 	重新裁剪一下图片路径
	 * @param path
	 * @return
	 */
	private String reCutPath(String operator , String path) {
		String res = "";
		switch (operator) {
		case "productImg":
		case "sliderImg":
		case "userAvatar":
			res = path.substring(path.indexOf("images")).replace("\\", "/");
			break;
		default :
			res += "";
		}
		return res;
	}
}
