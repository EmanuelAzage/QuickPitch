package BackEnd.Resources;

import java.io.Serializable;

import BackEnd.Client.EntrepreneurAccount;

/**
 * Created by anthonytu on 4/10/16.
 */
public class RefreshEntrepreneurAttempt implements Serializable {

    // private member variables
    private static final long serialVersionUID = 1L;
    private String entUsername;

    public RefreshEntrepreneurAttempt(String entUsername) {
        this.entUsername = entUsername;
    }

    public String getEntUsername() {
        return entUsername;
    }
}
