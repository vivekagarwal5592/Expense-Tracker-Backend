package Expense;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
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
import Model.UserDetails;

@WebServlet("/deleteexpense")
public class deleteexpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deleteexpense() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		ExpenseDetails expensedetails = gson.fromJson(reader, ExpenseDetails.class);
		
		//int expense_id = Integer.parseInt(request.getParameter("expense_id"));

	//	ArrayList<Integer> group_members = new ArrayList<Integer>();

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("delete from expense where expenseid = ?");

			stmt.setInt(1, expensedetails.getId());

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
