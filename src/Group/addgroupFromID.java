package Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Constants;
import Dbutils.Database_credentials;
import Model.Emaillist;
import Model.UserDetails;


@WebServlet("/addgroupFromID")
public class addgroupFromID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public addgroupFromID() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		

		BufferedReader reader = request.getReader();

		
		Gson gson = new Gson();

		Emaillist email = gson.fromJson(reader, Emaillist.class);

		

	//	ArrayList<UserDetails> udetails = get_user_details(email.getEmail());

		Database_credentials.start_connection();

		try {

			for (int i = 0; i <= email.getUserids().size() - 1; i++) {
				PreparedStatement stmt = Database_credentials.getC()
						.prepareStatement("Insert into expense_group (groupname,userid,groupid) VALUES(?,?,?)");

				stmt.setString(1, email.getGroupname());
				stmt.setInt(2, email.getUserids().get(i));
				stmt.setInt(3, Constants.group_number);

				stmt.executeUpdate();

			}

			Constants.group_number += 1;

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

	public ArrayList<UserDetails> get_user_details(ArrayList<String> friends) {

		ArrayList<UserDetails> user = new ArrayList<UserDetails>();

		Database_credentials.start_connection();
		try {

			for (int i = 0; i <= friends.size() - 1; i++) {
				PreparedStatement stmt = Database_credentials.getC()
						.prepareStatement("select * from user where email= ?");

				stmt.setString(1, friends.get(i));
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					user.add(new UserDetails(rs.getInt("userid"), rs.getString("username"), rs.getString("email"),
							rs.getString("registration_token")));

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

		return user;

		
		
	}

}
