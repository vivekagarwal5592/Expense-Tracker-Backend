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


@WebServlet("/deletegroup")
public class deletegroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public deletegroup() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		BufferedReader reader = request.getReader();

		Gson gson = new Gson();

		GroupDetails grpdetails = gson.fromJson(reader, GroupDetails.class);


		Database_credentials.start_connection();

		try {

			PreparedStatement stmt = Database_credentials.getC()
					.prepareStatement("delete from expense_group where groupid=?");

			stmt.setInt(1, grpdetails.getGroup_id());

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
