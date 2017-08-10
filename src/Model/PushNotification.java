package Model;

import java.util.ArrayList;

public class PushNotification {

	public PushNotification(ArrayList<String> registration_ids, Notification notification) {
		super();
		this.registration_ids = registration_ids;
		this.notification = notification;
	}

	ArrayList<String> registration_ids;
	Notification notification;
	

	public static class Notification {
		String title;
		String body;
		String  icon;

		public Notification(String title, String body) {
			this.title = title;
			this.body = body;
		}
		
		public Notification(String title, String body,String icon) {
			this.title = title;
			this.body = body;
			this.icon = icon;
		}
		
		

	}

}
