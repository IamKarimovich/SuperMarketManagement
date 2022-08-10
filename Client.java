package Objects;

public class Client extends User{

	private double bonus;
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(int iD, String name, String type, String loginId, String password, String phoneNumber,
			String location,double bonus) {
		super(iD, name, type, loginId, password, phoneNumber, location);
		this.bonus = bonus;
		// TODO Auto-generated constructor stub
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	
}
