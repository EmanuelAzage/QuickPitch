package BackEnd.Resources;

/**
 * Created by anthonytu on 4/11/16.
 */
public class InvUpdateAttempt {

    // private member variables
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String email;
    private String companyName;

    // Constructor
    public InvUpdateAttempt(String username, String password, String email, String companyName){
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.email = email;
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
}
