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

@WebServlet("/getGroupExpense")
public class getGroupExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getGroupExpense() {
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

		System.out.println("group_id: " + grpdetails.getGroup_id());

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"Select * from user LEFT JOIN expense on user.userid =expense.userid  where expense.groupid = ?");

			stmt.setInt(1, grpdetails.getGroup_id());

			ResultSet rs = stmt.executeQuery();

			ArrayList<ExpenseDetails> expensedetails = new ArrayList<ExpenseDetails>();

			while (rs.next()) {
				System.out.println("I am here again");
				UserDetails userdetails = new UserDetails(rs.getInt("userid"), rs.getString("username"),
						rs.getString("email"));
				expensedetails.add(
						new ExpenseDetails(rs.getInt("expenseid"), rs.getFloat("amount"), rs.getString("expense_date"),
								rs.getString("description"), rs.getString("category"), userdetails));
			}

			for (ExpenseDetails g : expensedetails) {

				// System.out.println(g.getName());
			}

			String json = new Gson().toJson(expensedetails);
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
