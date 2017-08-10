package Model;

import java.util.ArrayList;

public class FriendDetails {

	int userid;
	ArrayList<UserDetails> friendDetails ;
	int single_friend;
	
	public FriendDetails(int userid, ArrayList<UserDetails> friendDetails) {
		super();
		this.userid = userid;
		this.friendDetails = friendDetails;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public ArrayList<UserDetails> getFriendDetails() {
		return friendDetails;
	}

	public void setFriendDetails(ArrayList<UserDetails> friendDetails) {
		this.friendDetails = friendDetails;
	}

	public int getSingle_friend() {
		return single_friend;
	}

	public void setSingle_friend(int single_friend) {
		this.single_friend = single_friend;
	}
	
	
	
	
	
	
	
	
	
	
}
