package Friend;

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
import Model.Emaillist;
import Model.ExpenseDetails;
import Model.FriendDetails;

@WebServlet("/deletefriend")
public class deletefriend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deletefriend() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		FriendDetails frienddetails = gson.fromJson(reader, FriendDetails.class);

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("delete from  friendlist where userID=? and friendID=?");

			stmt.setInt(1, frienddetails.getUserid());
			stmt.setInt(2, frienddetails.getSingle_friend());

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
