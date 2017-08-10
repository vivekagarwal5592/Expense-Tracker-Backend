package Model;

public class UserDetails {

	private int id;
	private String username;
	private String email;
	private String password;
	private String registration_token;

	public UserDetails(int id, String username, String email, String password, String registration_token) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.registration_token = registration_token;
	}

	public UserDetails(String username, String email, String password, String registration_token) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.registration_token = registration_token;
	}


	public UserDetails(int id, String username, String email, String registration_token) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.registration_token = registration_token;
	}
	
	

	public UserDetails(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistration_token() {
		return registration_token;
	}

	public void setRegistration_token(String registration_token) {
		this.registration_token = registration_token;
	}

}
