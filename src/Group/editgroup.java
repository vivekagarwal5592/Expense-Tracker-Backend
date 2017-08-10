package Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
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

@WebServlet("/editgroup")
public class editgroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public editgroup() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		Emaillist email = gson.fromJson(reader, Emaillist.class);

		ArrayList<Integer> userids = email.getUserids();
		String groupname = email.getGroupname();
		int group_id = email.getGroup_id();

		delete_group(group_id);
		add_group(userids,groupname, group_id);
		

	}
	
	
	public void delete_group(int groupID){
		
		
		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("delete from expense_group where groupid=?");

			stmt.setInt(1, groupID);

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
	
	public void add_group(ArrayList<Integer> userids,String groupname,int groupid){
		
		
		Database_credentials.start_connection();

		try {

			for (int i = 0; i <= userids.size() - 1; i++) {
				PreparedStatement stmt = Database_credentials.getC()
						.prepareStatement("Insert into expense_group (groupname,userid,groupid) VALUES(?,?,?)");

				stmt.setString(1, groupname);
				stmt.setInt(2, userids.get(i));
				stmt.setInt(3, groupid);

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

}
