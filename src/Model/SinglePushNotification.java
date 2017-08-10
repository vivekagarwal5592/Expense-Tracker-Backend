package Model;

import java.util.ArrayList;

import Model.PushNotification.Notification;

public class SinglePushNotification {
	
	String to;
	Model.PushNotification.Notification notification;

	public SinglePushNotification(String registrationtoken, Model.PushNotification.Notification notification) {
		super();
		this.to = registrationtoken;
		this.notification = notification;
	}

	public static class Notification {
		String title;
		String body;

		public Notification(String title, String body) {
			this.title = title;
			this.body = body;
		}

	}

}
