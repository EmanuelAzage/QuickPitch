package BackEnd.Client;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class InvestorAccount implements Serializable {
	
	// all private member variables needed for the InvestorAccount
	private static final long serialVersionUID = 1L;
	private String username;
	private String password; // probably don't actually want this to be saved
	private String email;
	private String companyName;
	private Vector<EntrepreneurAccount> likes;
	private Vector<EntrepreneurAccount> connections;
	private Set<String> hasSeenEntrepreneurs;
	
	// constructor
	public InvestorAccount(String username, String password, String email, String companyName) {
		likes = new Vector<EntrepreneurAccount>();
		connections = new Vector<EntrepreneurAccount>();
		this.username = username;
		this.password = password;
		this.email = email;
		this.companyName = companyName;
		hasSeenEntrepreneurs = new HashSet<String>();

	}
	
	// encapsulation methods
	public Vector<EntrepreneurAccount> getLikes() {
		return likes;
	}
	
	public void addLike(EntrepreneurAccount like) {
		this.likes.addElement(like);
	}

	public void removeLike(EntrepreneurAccount removeLike) {
		this.likes.removeElement(removeLike);
	}
	
	public Vector<EntrepreneurAccount> getConnections() {
		return connections;
	}
	
	public void addConnection(EntrepreneurAccount connection) {
		this.connections.addElement(connection);
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

	public void addToHasSeen(String entUsername){
		hasSeenEntrepreneurs.add(entUsername);
	}

	public Set<String> getHasSeenEntrepreneurs(){
		return hasSeenEntrepreneurs;
	}
}
