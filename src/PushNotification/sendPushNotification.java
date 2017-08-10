package PushNotification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dbutils.Database_credentials;
import Model.Emaillist;
import Model.GroupDetails;
import Model.PushNotification;
import Model.PushNotification.Notification;
import Model.UserDetails;

@WebServlet("/sendPushNotification")
public class sendPushNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public sendPushNotification() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		BufferedReader reader = request.getReader();
//		Gson gson = new Gson();
//
//		Emaillist emaillist = gson.fromJson(reader, Emaillist.class);
		
		System.out.print("User id is "+String.valueOf(request.getAttribute("userID")) );
		
		
		int userID = (int)(request.getAttribute("userID"));
		int groupId = (int)(request.getAttribute("groupID"));
		
		
		String username = get_username(userID);
		String groupname = get_groupname(groupId);
		
		ArrayList<String> reg_tokens = get_reg_token(groupId,userID);
		Notification notification = new Notification("New Expense",username+ " has added new Expense in "+ groupname,"bill");
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		URLConnection connection = url.openConnection();

		connection.setRequestProperty("Authorization",
				"key=AAAAaTcioLE:APA91bHJI3EdGIvIY1PWw_IE1hlUPLvyaMxkNFH1Uv2ZNCD7C3_J9-yV9IruUn9CH7ystR6RtvwpP1wErmQFANqcV0YZGbAmWxOcEB9_LSZzIosnrLFFVLC0WlGqVT9v6g5KczYAmBbL");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		System.out.print("I am here again");
		
		PushNotification pushnotification = new PushNotification(reg_tokens, notification);
		String json = new Gson().toJson(pushnotification);
		System.out.print(json);
		out.write(json);
		System.out.print("I am here");
		out.flush();
		InputStream res = connection.getInputStream();

		Scanner scanner = new Scanner(res);

		System.out.println("before");
		while (scanner.hasNextLine()) {
			System.out.println("I am here");
			System.out.println(scanner.nextLine());
		}
		
		out.close();

	}

	public ArrayList<String> get_reg_token(Integer group_id,int user_id) {

		System.out.println("group_id" + group_id);

		ArrayList<String> reg_token = new ArrayList<>();

		try {

			Database_credentials.start_connection();
			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"Select * from user LEFT JOIN expense_group on user.userid=expense_group.userid where expense_group.groupid=? and user.userid !=?");

			stmt.setInt(1, group_id);
			stmt.setInt(2,user_id);

			ResultSet rs = stmt.executeQuery();

			// ArrayList<GroupDetails> groupdetails = new
			// ArrayList<GroupDetails>();
			while (rs.next()) {
				reg_token.add(rs.getString("registration_token"));
			}

			return reg_token;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;

	}
	
	public String get_username(int user_id){
		
		
		try {

			Database_credentials.start_connection();
			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"Select * from  user where userid =?");

			stmt.setInt(1, user_id);
			

			ResultSet rs = stmt.executeQuery();

			// ArrayList<GroupDetails> groupdetails = new
			// ArrayList<GroupDetails>();
			String username="";
			while (rs.next()) {
				username = rs.getString("username");
			}

			return username;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;

		
		
		
	}
	
	
public String get_groupname(int group_id){
		
		
		try {

			Database_credentials.start_connection();
			PreparedStatement stmt = Database_credentials.getC().prepareStatement(
					"Select * from  expense_group where groupid =?");

			stmt.setInt(1, group_id);
			

			ResultSet rs = stmt.executeQuery();

			// ArrayList<GroupDetails> groupdetails = new
			// ArrayList<GroupDetails>();
			String username="";
			while (rs.next()) {
				username = rs.getString("groupname");
			}

			return username;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Database_credentials.end_connection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;

		
		
		
	}
	
	
	
}