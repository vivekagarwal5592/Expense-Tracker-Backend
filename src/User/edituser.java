package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.UserDetails;

@WebServlet("/edituser")
public class edituser extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		 PrintWriter out = response.getWriter();
			out.println("In doo get");
		//	doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 PrintWriter out = response.getWriter();
			out.println("In do post");
		
		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		UserDetails udetails = gson.fromJson(reader, UserDetails.class);
		
		System.out.println(udetails.getUsername());
		System.out.println(udetails.getEmail());

		// String password = request.getParameter("password");
		// String email = request.getParameter("email");
		// int id = Integer.parseInt(request.getParameter("id"));

		try {

			Database_credentials.start_connection();

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("update user set password = ? , email =? where userid=?");

			stmt.setString(1, udetails.getPassword());
			stmt.setString(2, udetails.getEmail());
			stmt.setInt(3, udetails.getId());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
