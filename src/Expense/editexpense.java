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

@WebServlet("/editexpense")
public class editexpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public editexpense() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		int expense_id = Integer.parseInt(request.getParameter("expense_id"));
//		String description = request.getParameter("description");
//		String category = request.getParameter("category");
//		int amount = Integer.parseInt(request.getParameter("amount"));
//		int user_id = Integer.parseInt(request.getParameter("user_id"));
//		Integer group_id = Integer.parseInt(request.getParameter("group_id"));
//		java.util.Date date = null;
//		try {
//			date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		ExpenseDetails expensedetails = gson.fromJson(reader, ExpenseDetails.class);

	

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"update expense set amount = ? ,expense_date=?,userID=?,description=?,groupID=?,category=? where expenseid = ?");

			stmt.setFloat(1, expensedetails.getAmount());
			stmt.setDate(2, java.sql.Date.valueOf( expensedetails.getDate()));
			stmt.setInt(3, expensedetails.getUserID());
			stmt.setString(4, expensedetails.getDescription());
			
		//	System.out.println("value"+expensedetails.getGroupID());
			if(expensedetails.getGroupID() !=null){
				System.out.println("value: "+expensedetails.getGroupID());
			stmt.setInt(5, expensedetails.getGroupID());
			}else{
				System.out.println("value      "+expensedetails.getGroupID());
				stmt.setNull(5,java.sql.Types.INTEGER);	
			}
			stmt.setString(6, expensedetails.getCategory());
			
			
			
			
				stmt.setInt(7, expensedetails.getId());
			 
			//	stmt.setNull(7,java.sql.Types.INTEGER);
			
			

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
