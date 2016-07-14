package BackEnd.Resources;

// import statements
import java.io.Serializable;

// Constructor
public class EntSignUpAttempt implements Serializable {
	
	// private member variables
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String companyName;
	private String companyInfo;
	private String productDescription;
	private String videoURL;
	
	// Constructor
	public EntSignUpAttempt(String username, String password, String email, 
			String companyName, String companyInfo, String productDescription, String videoURL){
		this.username = username;
		this.password = password;
		this.email = email;
		this.companyName = companyName;
		this.companyInfo = companyInfo;
		this.productDescription = productDescription;
		this.videoURL = videoURL;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	public String getCompanyInfo(){
		return companyInfo;
	}
	
	public void setCompanyInfo(String companyInfo){
		this.companyInfo = companyInfo;
	}
	
	public String getProductDescription() {
		return productDescription;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public String getVideoURL() {
		return videoURL;
	}
	
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
}
