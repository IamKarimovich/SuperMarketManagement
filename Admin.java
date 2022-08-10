package Objects;

public class Admin {

	private int ID;
	private String Name,loginId,password;
	
	public Admin() {}
	
	public Admin(int iD, String name, String loginId, String password) {
		super();
		ID = iD;
		Name = name;
		this.loginId = loginId;
		this.password = password;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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
	
	
	
}
