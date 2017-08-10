package PushNotification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Model.PushNotification;
import Model.PushNotification.Notification;
import Model.SinglePushNotification;

@WebServlet("/addFriend")
public class addFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public addFriend() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = (String)(request.getAttribute("username"));
		String reg_token = (String) (request.getAttribute("reg_token"));
		
		System.out.println(reg_token);
		System.out.println(username);
		
		Notification notification = new Notification("New Friend",username+ " has added you in the friendList.","new_friend");
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		URLConnection connection = url.openConnection();

		connection.setRequestProperty("Authorization",
				"key=AAAAaTcioLE:APA91bHJI3EdGIvIY1PWw_IE1hlUPLvyaMxkNFH1Uv2ZNCD7C3_J9-yV9IruUn9CH7ystR6RtvwpP1wErmQFANqcV0YZGbAmWxOcEB9_LSZzIosnrLFFVLC0WlGqVT9v6g5KczYAmBbL");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		
		SinglePushNotification pushnotification = new SinglePushNotification(reg_token, notification);
		String json = new Gson().toJson(pushnotification);
		System.out.println(json);
		out.write(json);
		
		out.flush();
		InputStream res = connection.getInputStream();
		
		out.close();
		
		
		
		
	}

}
