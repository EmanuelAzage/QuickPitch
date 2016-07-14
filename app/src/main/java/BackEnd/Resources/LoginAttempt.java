package BackEnd.Resources;

// import statements
import java.io.Serializable;

// LoginAttemptClass
public class LoginAttempt implements Serializable {
	
	// private member variables
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	// Constructor
	public LoginAttempt(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	// encapsulation methods
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void settPassword(String password){
		this.password = password;
	}
}
