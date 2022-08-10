package Objects;

public class Deliever extends User{

	private int workCount;
	
	public Deliever() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Deliever(int iD, String name, String type, String loginId, String password, String phoneNumber,
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
