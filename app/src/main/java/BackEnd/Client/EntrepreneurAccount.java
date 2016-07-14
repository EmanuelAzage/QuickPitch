package BackEnd.Client;

import java.io.Serializable;
import java.util.Vector;

public class EntrepreneurAccount implements Serializable {

	// all private member variables needed for the EntrepreneurAccount
	private static final long serialVersionUID = 1L;
	private String username;
	private String password; // probably don't actually want this to be saved
	private String email;
	private String companyName;
	private String companyInfo;
	private String productDescription;
	private String myVideoURL;
	private Vector<InvestorAccount> connections;
	
	// constructor
	public EntrepreneurAccount(String username, String password, String email, 
			String companyName, String companyInfo, String productDescription, String myVideoURL) {
		this.myVideoURL = myVideoURL;
		this.productDescription = productDescription;
		this.username = username;
		this.password = password;
		this.email = email;
		this.companyName = companyName;
		this.companyInfo = companyInfo;
		connections = new Vector<InvestorAccount>();
	}
	
	public Vector<InvestorAccount> getConnections() {
		return connections;
	}

	public void setConnections(Vector<InvestorAccount> newConnections) {
		this.connections = newConnections;
	}
	
	public void addConnection(InvestorAccount like) {
		this.connections.addElement(like);;
	}

	public boolean equals(EntrepreneurAccount entrepreneurAccount) {
		return (this.username).equals(entrepreneurAccount.getUsername());
	}
	
	// encapsulation methods
	public String getVideo() {
		return myVideoURL;
	}
	
	public void setVideo(String myVideoURL) {
		this.myVideoURL = myVideoURL;
	}
	
	public String getProductDescription() {
		return productDescription;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyInfo() {
		return companyInfo;
	}
	
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
}
