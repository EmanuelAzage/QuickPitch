package BackEnd.Server;

// import statements
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
import BackEnd.Resources.AddConnectionAttempt;
import BackEnd.Resources.AddLikeAttempt;
import BackEnd.Resources.EntSignUpAttempt;
import BackEnd.Resources.EntUpdateAttempt;
import BackEnd.Resources.GrabNewsFeedAttempt;
import BackEnd.Resources.InvSignUpAttempt;
import BackEnd.Resources.InvUpdateAttempt;
import BackEnd.Resources.LoginAttempt;
import BackEnd.Resources.RefreshEntrepreneurAttempt;

// ServerThread constructor
public class ServerThread extends Thread {

	// private member variables
	@SuppressWarnings("unused")
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private QuickPitchServer mQuickPitchServer;
	private QuickPitchJDBC mQuickPitchJDBC;
	
	// constructor
	public ServerThread(Socket s, QuickPitchServer mQuickPitchServer, QuickPitchJDBC mQuickPitchDJBC){
		
		// initializes private member variables
		this.s = s;
		this.mQuickPitchServer = mQuickPitchServer;
		this.mQuickPitchJDBC = mQuickPitchDJBC;
		try {	
			// creates object input and output streams
			oos = new ObjectOutputStream(s.getOutputStream());
			ois =  new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread from Constructor: " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	// constantly checks to see if client is talking to server
	public void run() {
		try {
			while(true) {
				// reads clients message
				Object message = (Object) ois.readObject();
				
				// handle investor sign up request
				if (message instanceof InvSignUpAttempt) {
					InvSignUpAttempt invSignUpAttempt = (InvSignUpAttempt)message;
					InvestorAccount newInvAccount = mQuickPitchJDBC.insertInvestor(invSignUpAttempt);
					oos.writeObject("InvSignUpAttempt");
					oos.flush();
					oos.writeObject(newInvAccount);
					oos.flush();
				} 
				// handle entrepreneur sign up request
				else if (message instanceof EntSignUpAttempt) {
					EntSignUpAttempt entSignUpAttempt = (EntSignUpAttempt)message;
					EntrepreneurAccount newEntAccount = mQuickPitchJDBC.insertEntrepreneur(entSignUpAttempt);
					oos.writeObject("EntSignUpAttempt");
					oos.flush();
					oos.writeObject(newEntAccount);
					oos.flush();
				}
				// handle login in request
				else if (message instanceof LoginAttempt) {
					LoginAttempt loginAttempt = (LoginAttempt)message;
					Object currAccount = mQuickPitchJDBC.checkLogin(loginAttempt);
					oos.writeObject("LoginAttempt");
					oos.flush();
					oos.writeObject(currAccount);	
					oos.flush();
				}
				// handle news feed request
				else if (message instanceof GrabNewsFeedAttempt) {
					GrabNewsFeedAttempt grabNewsFeedAttempt = (GrabNewsFeedAttempt)message;
					EntrepreneurAccount newsFeedAccount = mQuickPitchJDBC.grabEntrepreneurAccount(grabNewsFeedAttempt);
					oos.writeObject("GrabNewsFeedAttempt");
					oos.flush();
					oos.writeObject(newsFeedAccount);	
					oos.flush();
				}
				// handle add connection request
				else if (message instanceof AddConnectionAttempt) {
					AddConnectionAttempt addConnectionAttempt = (AddConnectionAttempt)message;
					Boolean result = mQuickPitchJDBC.addConnection(addConnectionAttempt);
					oos.writeObject("AddConnectionAttempt");
					oos.flush();
					oos.writeObject(result);
					oos.flush();
				}
				// handle add like request
				else if (message instanceof AddLikeAttempt) {
					AddLikeAttempt addLikeAttempt = (AddLikeAttempt)message;
					Boolean result = mQuickPitchJDBC.addLike(addLikeAttempt);
					oos.writeObject("AddLikeAttempt");
					oos.flush();
					oos.writeObject(result);
					oos.flush();
				}
				// handle entrepreneur connections vector refresh
				else if (message instanceof RefreshEntrepreneurAttempt) {
					RefreshEntrepreneurAttempt refreshEntrepreneurAttempt = (RefreshEntrepreneurAttempt)message;
					Vector<InvestorAccount> newConnections = mQuickPitchJDBC.refreshEntrepreneur(refreshEntrepreneurAttempt);
					oos.writeObject("RefreshEntrepreneurAttempt");
					oos.flush();
					oos.writeObject(newConnections);
					oos.flush();
				}
				else if (message instanceof InvUpdateAttempt) {
					InvUpdateAttempt invUpdateAttempt = (InvUpdateAttempt)message;
					InvestorAccount updatedInvAccount = mQuickPitchJDBC.updateInvestor(invUpdateAttempt);
					oos.writeObject("InvUpdateAttempt");
					oos.flush();
					oos.writeObject(updatedInvAccount);
					oos.flush();
				}
				else if (message instanceof EntUpdateAttempt) {
					EntUpdateAttempt entUpdateAttempt = (EntUpdateAttempt)message;
					EntrepreneurAccount updatedEntAccount = mQuickPitchJDBC.updateEntrepreneur(entUpdateAttempt);
					oos.writeObject("EntUpdateAttempt");
					oos.flush();
					oos.writeObject(updatedEntAccount);
					oos.flush();
				}
				else if (message instanceof String) {
					String string = (String)message;
					oos.writeObject("Hey Client, you're connected.");
					oos.flush();
					System.out.println(string);
				}
				// this code should never run
				else {
					System.out.println("There was a massive error.");
				}
	
			}
		// handles all errors
		} catch (IOException ioe) {
			System.out.println("ioe in serverThread from run: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in ServerThread from run: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} finally { 
			// client has disconnected
			System.out.println("The client has disconnected");
			mQuickPitchServer.removeServerThread(this); 
		}	
	}
}
