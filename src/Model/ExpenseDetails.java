package Model;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseDetails {

	private Integer id;
	private Float amount;
	private String date;
	private Integer userID;
	private String description;
	private String category;
	private Integer groupID;
	private String groupName;
	private UserDetails userDetails;
	private GroupDetails groupDetails;

	public ExpenseDetails(Integer id, Float amount, String date, Integer userID, String description, String category,
			Integer groupID, String groupName) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.userID = userID;
		this.description = description;
		this.category = category;
		this.groupID = groupID;
		this.groupName = groupName;
	}

	public ExpenseDetails(Float amount, String date, Integer userID, String decription, Integer groupID,
			String category) {
		super();
		this.amount = amount;
		this.date = date;
		this.userID = userID;
		this.description = decription;
		this.groupID = groupID;
		this.category = category;
	}

	public ExpenseDetails(int id, Float amount, String date, int userID, String decription, Integer groupID,
			String category) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.userID = userID;
		this.description = decription;
		this.groupID = groupID;
		this.category = category;
	}

	public ExpenseDetails(Integer id, Float amount, String date, String description, String category,
			  UserDetails userDetails) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category = category;
		this.userDetails = userDetails;
	}
	
	public ExpenseDetails(Integer id, Float amount, String date, String description, String category,
			  UserDetails userDetails,GroupDetails groupDetails) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category = category;
		this.userDetails = userDetails;
		this.groupDetails = groupDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public GroupDetails getGroupDetails() {
		return groupDetails;
	}

	public void setGroupDetails(GroupDetails groupDetails) {
		this.groupDetails = groupDetails;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	

}
