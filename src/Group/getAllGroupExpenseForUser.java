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

@WebServlet("/getAllGroupExpenseForUser")
public class getAllGroupExpenseForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getAllGroupExpenseForUser() {
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

		ArrayList<GroupDetails> groupdetails = getAllgroupnames(udetails.getId());

		Database_credentials.start_connection();
		ArrayList<ExpenseDetails> expensedetails = new ArrayList<ExpenseDetails>();
		try {

			for (GroupDetails g : groupdetails) {

				System.out.println("value of group id is" + g.getGroup_id());

				PreparedStatement stmt = Database_credentials.getC().prepareStatement(
						"Select * from user LEFT JOIN expense on user.userid =expense.userid where expense.groupid = ?");

				stmt.setInt(1, g.getGroup_id());

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					System.out.println("I am here again");

					String groupname = getGroupName(rs.getInt("groupid"));

					GroupDetails groupDetails = new GroupDetails(rs.getInt("groupid"), groupname);

					UserDetails userdetails = new UserDetails(rs.getInt("userid"), rs.getString("username"),
							rs.getString("email"));
					expensedetails.add(new ExpenseDetails(rs.getInt("expenseid"), rs.getFloat("amount"),
							rs.getString("expense_date"), rs.getString("description"), rs.getString("category"),
							userdetails, groupDetails));

				//	System.out.println("value of group name is " + rs.getString("groupname"));
				}

			}

			for (ExpenseDetails e : expensedetails) {

				System.out.println("Inside loop");
				System.out.println(e.getGroupDetails().getName());
			}

			String json = new Gson().toJson(expensedetails);

			System.out.println("value of json is " + json);

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

	public ArrayList<GroupDetails> getAllgroupnames(int userid) {

		System.out.println("I am here");

		ArrayList<GroupDetails> groupdetails = null;
		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from expense_group where userID = ?");

			stmt.setInt(1, userid);

			ResultSet rs = stmt.executeQuery();

			groupdetails = new ArrayList<GroupDetails>();
			while (rs.next()) {

				groupdetails
						.add(new GroupDetails(rs.getString("groupname"), rs.getInt("userid"), rs.getInt("groupid")));
			}

			System.out.println("before ");

			for (GroupDetails g : groupdetails) {

				System.out.println(g.getGroup_id());
			}

			System.out.println("after");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
				return groupdetails;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	public String getGroupName(int groupid) {

	//	Database_credentials.start_connection();
		String groupname = "";
		try {

			// System.out.println("value of group id is" + g.getGroup_id());

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select groupname from expense_group where groupid =? LIMIT 1");

			stmt.setInt(1, groupid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				groupname = rs.getString("groupname");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
		//		Database_credentials.end_connection();
				return groupname;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;

	}

}
