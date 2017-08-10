package Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.ExpenseDetails;
import Model.GroupDetails;
import Model.UserDetails;

@WebServlet("/getgroupmembers")
public class getgroupmembers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getgroupmembers() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		GroupDetails grpdetails = gson.fromJson(reader, GroupDetails.class);

		 ArrayList<UserDetails> userdetails = new ArrayList<UserDetails>();
		
		
		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from user LEFT JOIN expense_group on user.userid=expense_group.userid where expense_group.groupid=?");

			stmt.setInt(1, grpdetails.getGroup_id());

			ResultSet rs = stmt.executeQuery();

			//ArrayList<GroupDetails> groupdetails = new ArrayList<GroupDetails>();
			while (rs.next()) {
				userdetails.add(new UserDetails(rs.getInt("userid"),rs.getString("username"),rs.getString("email")));
		//		groupdetails.add(new GroupDetails(rs.getString("groupname"), rs.getInt("userid"), rs.getInt("groupid")));
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
				e.printStackTrace();
			}
		}

	}

}
