package BackEnd.Resources;

// import statements
import java.io.Serializable;

// AddConnectionAttemptClass
public class AddConnectionAttempt implements Serializable {
	
	// private member variables
	private static final long serialVersionUID = 1L;
	private String entrepreneur;
	private String investor;
	
	// constructor
	public AddConnectionAttempt(String entrepreneur, String investor) {
		this.entrepreneur = entrepreneur;
		this.investor = investor;
	}
	
	// encapsulation methods
	public String getEntrepreneur() {
		return entrepreneur;
	}
	
	public void setEntrepreneur(String entrepreneur) {
		this.entrepreneur = entrepreneur;
	}
	
	public String getInvestor() {
		return investor;
	}
	
	public void setInvestor(String investor) {
		this.investor = investor;
	}
}
