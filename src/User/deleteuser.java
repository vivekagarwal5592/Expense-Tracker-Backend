package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.UserDetails;

@WebServlet("/deleteuser")
public class deleteuser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deleteuser() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		UserDetails udetails = gson.fromJson(reader, UserDetails.class);

		try {

			Database_credentials.start_connection();

			PreparedStatement stmt = Database_credentials.getC().prepareStatement("delete from user where userid =?");

			stmt.setInt(1, udetails.getId());
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
