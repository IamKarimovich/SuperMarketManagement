package Objects;

public class User {
	private int ID;
	private String name,type,loginId,password,phoneNumber,location;
	
	public User() {}
	
	public User(int iD, String name, String type, String loginId, String password, String phoneNumber,
			String location) {
		super();
		ID = iD;
		this.name = name;
		this.type = type;
		this.loginId = loginId;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.location = location;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	
	
}
