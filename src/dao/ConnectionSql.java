package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/*
 * 获取数据库连接和连接释放
 */
public class ConnectionSql {
	
	private static String DRIVER="com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/signin";
	private static String USERNAME = "root";
	private static String PASSWORD = "21424019";

	public Connection getConnection()
	{
		Connection conn=null;
		try {
			conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
