package Friend;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.Emaillist;
import Model.UserDetails;

@WebServlet("/addfriend")
public class addfriend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public addfriend() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		int flag = 3;

		Emaillist details = gson.fromJson(reader, Emaillist.class);
		UserDetails friend = null;
		if ((friend = getUserID(details.getSingleEmail())) != null) {
			if (!(getUserFrienList(details.getUserid(), friend.getId()) == 2)) {

				try {
					// out.println("before third line");
					Database_credentials.start_connection();
					// out.println("third second line");
					PreparedStatement stmt = Database_credentials.getC()
							.prepareStatement("insert into friendlist (userID,friendID) VALUES(?,?)");

					stmt.setInt(1, details.getUserid());
					stmt.setInt(2, friend.getId());
					stmt.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						Database_credentials.end_connection();

						
						
						String json = new Gson().toJson(flag);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
						
						 
							
						request.setAttribute("username", details.getUsername());
						request.setAttribute("reg_token", friend.getRegistration_token());
						request.getRequestDispatcher("addFriend").forward(request, response);
						System.out.println("last line");
				
				
						
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				flag = 2;
			}
		} else {
			flag = 1;
		}
		
		String json = new Gson().toJson(flag);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

		

	}

	public UserDetails getUserID(String userEmail) {

		UserDetails user = null;
		int id = 0;
		try {
			// out.println("before third line");
			Database_credentials.start_connection();
			// out.println("third second line");
			PreparedStatement stmt = Database_credentials.getC().prepareStatement("select * from user where email=?");

			stmt.setString(1, userEmail);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				user = new UserDetails(rs.getInt("userid"), rs.getString("username"), rs.getString("email"),
						rs.getString("registration_token"));
				id = rs.getInt("userid");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
				return user;
			} catch (Exception e) {
				// out.println(e);
				e.printStackTrace();

			}
		}

		return null;

	}

	public int getUserFrienList(int userid, int friendid) {

		UserDetails user = null;
		int flag = 2;
		try {
			// out.println("before third line");
			Database_credentials.start_connection();
			// out.println("third second line");
			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("select * from friendlist where userID=? and friendID=? ");

			stmt.setInt(1, userid);
			stmt.setInt(2, friendid);

			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				flag = 0;
			}

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

		return flag;

	}

}
