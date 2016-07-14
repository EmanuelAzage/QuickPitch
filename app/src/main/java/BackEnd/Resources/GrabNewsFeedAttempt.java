package BackEnd.Resources;

// import statements
import java.io.Serializable;
import java.util.Set;

import BackEnd.Client.InvestorAccount;

// GrabNewsFeedAttempt
public class GrabNewsFeedAttempt implements Serializable {
	
	// private member variables
	private static final long serialVersionUID = 1L;
	private String entrepreneur;
	private InvestorAccount invAccount;
	
	// Constructor
	public GrabNewsFeedAttempt(String entrepreneur) {
		this.entrepreneur = entrepreneur;
	}
	
	// encapsulation methods
	public String getEntrepreneur() {
		return entrepreneur;
	}
	
	public void setEntrepreneur(String entrepreneur) {
		this.entrepreneur = entrepreneur;
	}

	public void setInvestorAccount(InvestorAccount invAccount){
		this.invAccount = invAccount;
	}

	public InvestorAccount getInvestorAccount(){
		return invAccount;
	}
}
