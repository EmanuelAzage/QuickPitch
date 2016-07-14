package BackEnd.Resources;

// import statements
import java.io.Serializable;

// AddLikeAttemptClass
public class AddLikeAttempt implements Serializable {
	
	// private member variables
	private static final long serialVersionUID = 1L;
	private String entrepreneur;
	private String investor;
	
	// Constructor
	public AddLikeAttempt(String entrepreneur, String investor) {
		 this.entrepreneur = entrepreneur;
		 this.investor = investor;
	}
	
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
