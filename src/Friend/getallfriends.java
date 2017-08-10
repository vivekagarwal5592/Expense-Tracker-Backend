package Friend;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import java.sql.PreparedStatement;

import Dbutils.Database_credentials;
import Model.UserDetails;

@WebServlet("/getallfriends")
public class getallfriends extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getallfriends() {
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
		System.out.println("user id is " + udetails.getId());
		ArrayList<Integer> frienddetails = getUserDetails(udetails.getId());
		ArrayList<UserDetails> userDetails = new ArrayList<UserDetails>();
		Database_credentials.start_connection();
		try {

			for (int i = 0; i <= frienddetails.size() - 1; i++) {

				PreparedStatement statement = Database_credentials.getC()
						.prepareStatement("Select * from user where userid = ?");

				statement.setInt(1, frienddetails.get(i));
				ResultSet rs = statement.executeQuery();

				while (rs.next()) {
					userDetails.add(new UserDetails(rs.getInt("userid"), rs.getString("username"), rs.getString("email")));
				}
			}

			String json = new Gson().toJson(userDetails);
			System.out.println(json);

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

	public ArrayList<Integer> getUserDetails(int userID) {

		Database_credentials.start_connection();
		ArrayList<Integer> userids = new ArrayList<Integer>();
		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("select * from friendlist where userID = ? ");

			stmt.setInt(1, userID);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				userids.add(rs.getInt("friendID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
				return userids;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
