package Expense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
import com.google.gson.GsonBuilder;

import Dbutils.Database_credentials;
import Model.ExpenseDetails;
import Model.UserDetails;

@WebServlet("/addexpense")
public class addexpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public addexpense() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		out.println("oiaedfjkpskefjewklfnjerws");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String description = request.getParameter("description");
		// String category = request.getParameter("category");
		// int amount = Integer.parseInt(request.getParameter("amount"));
		// int user_id = Integer.parseInt(request.getParameter("user_id"));
		// Integer group_id =
		// Integer.parseInt(request.getParameter("group_id"));
		// java.util.Date date = null;
		// try {
		// date = new
		// SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
		// } catch (ParseException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		BufferedReader reader = request.getReader();

		// Gson gson = new Gson();

		Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();
		ExpenseDetails expensedetails = gson.fromJson(reader, ExpenseDetails.class);
		System.out.println(expensedetails.getDate());
		Database_credentials.start_connection();

		try {
			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"Insert into expense (amount,expense_date,userID,description,groupID,category) VALUES(?,?,?,?,?,?)");

			stmt.setFloat(1, expensedetails.getAmount());
			stmt.setDate(2, java.sql.Date.valueOf(expensedetails.getDate()));
			stmt.setInt(3, expensedetails.getUserID());
			stmt.setString(4, expensedetails.getDescription());
			
		
			if(expensedetails.getGroupID() !=null){
				stmt.setInt(5, expensedetails.getGroupID());
			}
			
			else{
				stmt.setNull(5,java.sql.Types.INTEGER);
			}
				
			
				
			
			
			stmt.setString(6, expensedetails.getCategory());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();

				if (expensedetails.getGroupID() != null) {

					
					System.out.println("group_id is"+expensedetails.getGroupID());
					request.setAttribute("userID", expensedetails.getUserID());
					request.setAttribute("groupID", expensedetails.getGroupID());

				//	response.sendRedirect("sendPushNotification");
					request.getRequestDispatcher("sendPushNotification").forward(request, response);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
