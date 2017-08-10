package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.UserDetails;



@WebServlet("/getuser")
public class getuser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}

	}

	public getuser() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("In do get");
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		UserDetails udetails = gson.fromJson(reader, UserDetails.class);

		Database_credentials.start_connection();
		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("select * from user where username = ? and password =?");

			stmt.setString(1, udetails.getUsername());
			stmt.setString(2, udetails.getPassword());

			ResultSet rs = stmt.executeQuery();

			UserDetails userdetails = null;
			while (rs.next()) {
				if (rs.getString("username").equals(udetails.getUsername())
						&& rs.getString("password").equals(udetails.getPassword())) {
					userdetails = new UserDetails(rs.getInt("userid"), rs.getString("username"), rs.getString("email"),
							rs.getString("registration_token"));

					break;
				}
			}

			String json = new Gson().toJson(userdetails);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
