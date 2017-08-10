package Expense;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.ExpenseDetails;

@WebServlet("/getallexpenses")
public class getallexpenses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getallexpenses() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		ExpenseDetails expdetails = gson.fromJson(reader, ExpenseDetails.class);

		// int user_id = Integer.parseInt(request.getParameter("user_id"));

		Database_credentials.start_connection();
		
		System.out.println("I am here in here");

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from expense where userid=? order by expense_date desc ");

			stmt.setInt(1, expdetails.getUserID());

			ResultSet rs = stmt.executeQuery();
			ArrayList<ExpenseDetails> expensedetails = new ArrayList<ExpenseDetails>();
			while (rs.next()) {
				expensedetails.add(new ExpenseDetails(rs.getInt("expenseid"), rs.getFloat("amount"),
						rs.getString("expense_date"), rs.getInt("userid"), rs.getString("description"),
						 rs.getString("category"),rs.getInt("groupid"),""));
			}

			System.out.println("I am here");
			String json = new Gson().toJson(get_group_names(expensedetails));

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

	public ArrayList<ExpenseDetails> get_group_names(ArrayList<ExpenseDetails> expensedetails) {

		// int user_id = Integer.parseInt(request.getParameter("user_id"));

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from expense_group where groupid=? LIMIT 1");

			for (int i = 0; i <= expensedetails.size() - 1; i++) {

				stmt.setInt(1, expensedetails.get(i).getGroupID());

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {

					expensedetails.get(i).setGroupName(rs.getString("groupname"));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return expensedetails;

	}

}
