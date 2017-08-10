package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.UserDetails;

@WebServlet("/registeruser")
public class registeruser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public registeruser() {
		super();
	}

	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("In do get");
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String user_name = request.getParameter("username");
		// String password = request.getParameter("password");
		// String email = request.getParameter("email");
		// String reg_token = request.getParameter("reg_token");
		//
		// PrintWriter out = response.getWriter();
		//
		// out.println("In first line");

		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		UserDetails udetails = gson.fromJson(reader, UserDetails.class);
		System.out.println("I am here");

		int flag = checkusername(udetails.getUsername(), udetails.getEmail());
		System.out.println(flag);
		if (flag != 1) {
			System.out.println();
			try {
				// out.println("before third line");
				Database_credentials.start_connection();
				// out.println("third second line");
				PreparedStatement stmt = Database_credentials.getC().prepareStatement(
						"insert into user (username,password,email,registration_token) VALUES(?,?,?,?)");

				stmt.setString(1, udetails.getUsername());
				stmt.setString(2, udetails.getPassword());
				stmt.setString(3, udetails.getEmail());
				stmt.setString(4, udetails.getRegistration_token());

				stmt.executeUpdate();

				int response_code = 0;
				String json = new Gson().toJson(response_code);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Database_credentials.end_connection();
				} catch (Exception e) {
					// out.println(e);
					e.printStackTrace();
				}
			}
		} else {
			int response_code = 1;
			String json = new Gson().toJson(response_code);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}

	public int checkusername(String username, String email) {
		int flag =1;
		try {
			
			Database_credentials.start_connection();
			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("select * from user where username = ? || email = ? ");

			stmt.setString(1, username);
			stmt.setString(2, email);

			ResultSet rs = stmt.executeQuery();

			// UserDetails userdetails = null;
//			if (!rs.wasNull()) {
//				System.out.println("I am checking null");
//				return 0;
//			}

			if(!rs.next()) {
				System.out.println("I am inside next");
			//	System.out.println(rs.getString("username"));
				flag =0;;
			}
			
			if(rs.next()) {
				System.out.println("I am inside next yyyy");
			//	System.out.println(rs.getString("username"));
			//	return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("I am inside next uuuuu");
				Database_credentials.end_connection();
				return flag;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return 1;

	}

}
