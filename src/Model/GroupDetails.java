package Model;

import java.util.ArrayList;

public class GroupDetails {
	
	String name;
	Integer user_id;
	Integer group_id;
	ArrayList<UserDetails> userDetails;
	
	public GroupDetails(String name, int user_id, int group_id) {
		super();
		this.name = name;
		this.user_id = user_id;
		this.group_id = group_id;
	}

	public GroupDetails(String name, int user_id, int group_id, ArrayList<UserDetails> userDetails) {
		super();
		this.name = name;
		this.user_id = user_id;
		this.group_id = group_id;
		this.userDetails = userDetails;
	}
	
	
	public GroupDetails(int group_id,String name) {
		super();
		this.name = name;
		this.group_id = group_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public ArrayList<UserDetails> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(ArrayList<UserDetails> userDetails) {
		this.userDetails = userDetails;
	}
	
	
	
	

}
