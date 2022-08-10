package Objects;

public class Seller extends User{

	private int workCount;
	
	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seller(int iD, String name, String type, String loginId, String password, String phoneNumber,
			String location,int workCount) {
		super(iD, name, type, loginId, password, phoneNumber, location);
		this.workCount = workCount;
		// TODO Auto-generated constructor stub
	}

	public int getWorkCount() {
		return workCount;
	}

	public void setWorkCount(int workCount) {
		this.workCount = workCount;
	}

	
	

}
