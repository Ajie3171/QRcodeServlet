package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class CompareServlet
 */
@WebServlet("/CompareServlet")
public class CompareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompareServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String data = request.getParameter("data");
		System.out.println(data);

		int resMsg = 0;
		String resCode = "302";

		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = connect.createStatement();
			ResultSet result;

			String sqlQuery = "select * from " + DBUtil.TABLE_QRDATA + " where QRData='" + data + "'";
			result = statement.executeQuery(sqlQuery);
			if (result.next()) {
				// int image = result.getInt("QRImage");
				resMsg = result.getInt("QRImage");
				// resMsg = Integer.toString(image);
				resCode = "301";
			} else {
				System.out.println("查询失败");
				// resMsg = "解锁失败";
				resCode = "302";
			}
			result.close();
			statement.close();
			connect.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("resMsg", resMsg);
		map.put("resCode", resCode);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式
		PrintWriter pw = response.getWriter(); // 获取 response 的输出流

		pw.write(json.toString());
		// pw.println(map.toString()); // 通过输出流把业务逻辑的结果输出
		pw.flush();
		pw.close();
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
