package Model;

import java.util.ArrayList;

public class Emaillist {
	
	ArrayList<String> email;
	
	String SingleEmail;
	int userid;
	String groupname;
	int group_id;
	ArrayList<Integer> userids;
	String title;
	String body;
	String username;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public ArrayList<Integer> getUserids() {
		return userids;
	}

	public void setUserids(ArrayList<Integer> userids) {
		this.userids = userids;
	}

	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getSingleEmail() {
		return SingleEmail;
	}

	public void setSingleEmail(String singleEmail) {
		SingleEmail = singleEmail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
	

}
