package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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

		String account = request.getParameter("account"); // 从 request 中获取名为 account的参数的值
		String password = request.getParameter("password"); // 从 request 中获取名为password 的参数的值
		System.out.println("account:" + account + "\npassword:" + password); // 打印出来看一看

		String resCode = "";
		String resMsg = "";
		String userId = "";

		/* 这里我们做一个最简单的注册逻辑，当然，你的实际业务可以相当复杂 */
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement)connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;

			String sqlQuery = "select * from " + DBUtil.TABLE_PASSWORD + " where userAccount='" + account + "'";

			// 查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0
			result = statement.executeQuery(sqlQuery); // 先查询同样的账号（比如手机号）是否存在
			if (result.next()) { // 已存在
				resCode = "201";
				resMsg = "该账号已注册，请使用此账号直接登录或使用其他账号注册";
				userId = "";
			} else { // 不存在
				String sqlInsertPass = "insert into " + DBUtil.TABLE_PASSWORD + "(userAccount,userPassword) values('"
						+ account + "','" + password + "')";
				// 更新类操作返回一个int类型的值，代表该操作影响到的行数
				int row1 = statement.executeUpdate(sqlInsertPass); // 插入帐号密码
				if (row1 == 1) {
					String sqlQueryId = "select userId from " + DBUtil.TABLE_PASSWORD + " where userAccount='" + account
							+ "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的userId
					if (result2.next()) {
						userId = result2.getInt("userId") + "";
					}
					String sqlInsertInfo = "insert into " + DBUtil.TABLE_USERINFO + "(userId)values('" + userId + "')";
					int row2 = statement.executeUpdate(sqlInsertInfo); // 在用户信息表中插入刚注册的Id
					if (row2 == 1) { // 两个表中都插入成功，从业务角度认定为注册成功
						resCode = "100";
						resMsg = "注册成功";
					}
				} else {
					resCode = "202";
					resMsg = "用户信息表插入错误";
					userId = "";
				}
			}

			result.close();
			statement.close();
			connect.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		HashMap<String, String> map = new HashMap<>();
		map.put("resCode", resCode);
		map.put("resMsg", resMsg);
		map.put("userId", userId);
		JSONObject json = JSONObject.fromObject(map);// 将数据转换成json格式输出

		response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式
		PrintWriter pw = response.getWriter(); // 获取 response 的输出流

		pw.write(json.toString());
//		pw.println(map.toString()); // 通过输出流把业务逻辑的结果输出
		pw.flush();
		pw.close();

		// String account = request.getParameter("account");
		// String password = request.getParameter("password");
		// System.out.println("account:" + account + " " + "password:" + password);
		//
		// String resCode = "";
		// String resMsg = "";
		// String userId = "";
		//
		// try {
		// Connection connect = DBUtil.getConnect();
		// Statement statement = (Statement) connect.createStatement();
		// ResultSet result;
		// // String sqlQuery = "select * from " + DBUtil.TABLE_PASSWORD + " where
		// // userAccount='" + account + "'";
		// String sqlQuery = "select * from " + DBUtil.TABLE_PASSWORD + " where
		// userAccount='" + account + "'";
		// result = statement.executeQuery(sqlQuery);
		// if (result.next()) {
		// resCode = "201";
		// resMsg = "已注册";
		// userId = " ";
		// } else {
		// String sqlInsertPass = "insert into " + DBUtil.TABLE_PASSWORD +
		// "(userAccount,userPassword) values('"
		// + account + "','" + password + "')";
		// int raw1 = statement.executeUpdate(sqlInsertPass);
		// if (raw1 == 1) {
		// String sqlQueryId = "select userId from " + DBUtil.TABLE_PASSWORD + " where
		// userAccount='" + account
		// + "'";
		// ResultSet result2 = statement.executeQuery(sqlQueryId);
		// if (result2.next()) {
		// userId = result2.getInt("userId") + "";
		// }
		// String sqlInsertInfo = "insert into " + DBUtil.TABLE_USERINFO + "(userId)
		// values('" + userId + "')";
		// int row2 = statement.executeUpdate(sqlInsertInfo);
		// if (row2 == 1) {
		// resCode = "100";
		// resMsg = "注册成功";
		// }
		// } else {
		// resCode = "202";
		// resMsg = "用户信息表插入错误";
		// userId = "";
		// }
		//
		// }
		// } catch (SQLException e) { // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// HashMap<String, String> map = new HashMap<>();
		// map.put("resMsg", resMsg);
		// map.put("resCode", resCode);
		// map.put("userId", userId);
		//
		// response.setContentType("text/html;charset=utf-8");
		// PrintWriter pw = response.getWriter();
		// pw.println(map.toString());
		// pw.flush();

		// response.getWriter().append("Served at: ").append(request.getContextPath());

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
