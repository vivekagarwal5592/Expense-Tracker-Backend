package Dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;

public class Database_credentials {

	public static final String url = "";
	public static final String db_username = "";
	public static final String db_password = "";
	static Connection c;

	public static void start_connection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			try {
				throw new ServletException(e);
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		}

		try {
			c = DriverManager.getConnection(url, db_username, db_password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void end_connection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getC() {
		return c;
	}

}
