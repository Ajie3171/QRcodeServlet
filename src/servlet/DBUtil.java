package servlet;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBUtil {
	public static final String TABLE_PASSWORD = "table_user_password";
	public static final String TABLE_USERINFO = "table_user_info";
	public static final String TABLE_QRDATA="table_qr_data";
	
	private static Connection mConnection;

	public static Connection getConnect() {
		String url = "jdbc:mysql://localhost:3306/first_mysql_test?useSSL=false";
		mConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = (Connection) DriverManager.getConnection(url, "root", "043869");
			// "jdbc:mysql://localhost:3306/first_mysql_test?useUnicode=true&characterEncoding=utf-8&useSSL=false"
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQLException" + e.getMessage());
			System.out.println("SQLState" + e.getSQLState());
			System.out.println("VendorError" + e.getErrorCode());
		}

		return mConnection;
	}

	public static void closeConnection() {
		if (mConnection != null) {
			try {
				mConnection.close();
				mConnection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("数据库关闭异常");
			}
		}
	}
}
