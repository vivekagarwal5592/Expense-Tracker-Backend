package Expense;

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

@WebServlet("/getsingleexpense")
public class getsingleexpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getsingleexpense() {
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

		// int expense_id =
		// Integer.parseInt(request.getParameter("expense_id"));

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from expense where expenseid=?");

			stmt.setInt(1, expdetails.getId());

			ResultSet rs = stmt.executeQuery();

			ExpenseDetails expensedetails = null;
			while (rs.next()) {
				expensedetails = new ExpenseDetails(rs.getInt("expenseid"), rs.getFloat("amount"), rs.getString("expense_date"),
						rs.getInt("userid"), rs.getString("description"), rs.getInt("groupid"),
						rs.getString("category"));
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
