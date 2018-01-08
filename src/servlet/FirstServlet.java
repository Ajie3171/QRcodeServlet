package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(description = "Servlet学习", urlPatterns = { "/FirstServlet" }, initParams = {
		@WebInitParam(name = "userName", value = "abc", description = "用户名称") })
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FirstServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		System.out.println("account:" + account + "password:" + password);

		String result = "";
		if ("abc".equals(account) && "123".equals(password)) {
			result = "login success!";
		} else {
			result = "sorry,account or password error";
		}
		
		PrintWriter pw = response.getWriter();
		pw.println(result);
		pw.flush();
		// response.getWriter().append("\n初始化参数userName= " +
		// getInitParameter("userName"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
