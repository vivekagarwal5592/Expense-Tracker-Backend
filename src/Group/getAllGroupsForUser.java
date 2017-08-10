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
import Model.GroupDetails;


@WebServlet("/getAllGroupsForUser")
public class getAllGroupsForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public getAllGroupsForUser() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		GroupDetails grpdetails = gson.fromJson(reader, GroupDetails.class);

		System.out.println("group_id: "+grpdetails.getUser_id());

		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("Select * from expense_group where userID = ?");

			stmt.setInt(1, grpdetails.getUser_id());

			ResultSet rs = stmt.executeQuery();

			ArrayList<GroupDetails> groupdetails = new ArrayList<GroupDetails>();
			while (rs.next()) {
				System.out.println("I am here");
				groupdetails.add(new GroupDetails(rs.getString("groupname"), rs.getInt("userid"), rs.getInt("groupid")));
			}
			
			for(GroupDetails g:groupdetails){
				
			//	System.out.println(g.getName());
			}

			String json = new Gson().toJson(groupdetails);
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
