package BackEnd.Client;

// import statements
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import BackEnd.Resources.AddConnectionAttempt;
import BackEnd.Resources.AddLikeAttempt;
import BackEnd.Resources.Constants;
import BackEnd.Resources.EntSignUpAttempt;
import BackEnd.Resources.EntUpdateAttempt;
import BackEnd.Resources.GrabNewsFeedAttempt;
import BackEnd.Resources.InvSignUpAttempt;
import BackEnd.Resources.InvUpdateAttempt;
import BackEnd.Resources.LoginAttempt;
import BackEnd.Resources.RefreshEntrepreneurAttempt;

public class QuickPitchClient extends Thread {
	
	// singleton model
	private static QuickPitchClient instance = null;
	
	public static QuickPitchClient getInstance() {
		if(instance == null) {
	         instance = new QuickPitchClient(Constants.IPAddress, Constants.clientPort);
	      }
	      return instance;
	}
	
	// private member variables
	// input and output stream to communicate with the server
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket s;
	
	private String hostname;
	private int port;
	
	private Object ambiguousAccount;
	private InvestorAccount myInvAccount;
	private EntrepreneurAccount myEntAccount;
	private EntrepreneurAccount grabNewsFeedEntAccount;
	private boolean result;
	private Vector<InvestorAccount> newConnections;
	private boolean hasReceived;

	private boolean invUpdated;
	private boolean entUpdated;
	
	// constructor which takes the IPAddress and Port number
	public QuickPitchClient(String hostname, int port) {
		s = null;
		this.hostname = hostname;
		this.port = port;
		hasReceived = false;
		
		// starts the thread
		this.start();
		
	}
	
	// ecapsulations methods
	public InvestorAccount getMyInvAccount() {
		return this.myInvAccount;
	}
	
	public EntrepreneurAccount getMyEntAccount() {
		return this.myEntAccount;
	}

	public Socket checkServer() {
		if(s == null) {

		} else if(s.isClosed()) {
			s= null;
		}
		return s;
	}

	public void run() {
		while (true) {
			try {
				// setting up the socket and input and output streams
				s = new Socket(hostname, port);
				ois = new ObjectInputStream(s.getInputStream());
				oos = new ObjectOutputStream(s.getOutputStream());

				// introductory message to the server
				System.out.println("Connection to server...");
				oos.writeObject("Hey, Server, this is a Client calling.");
				oos.flush();


				try {
					while (true) {
						String message = (String) ois.readObject();
						System.out.println(message);

						if (message.equals("Hey Client, you're connected.")) {

						}

						if (message.equals("InvSignUpAttempt")) {
							myInvAccount = (InvestorAccount) ois.readObject();
							hasReceived = true;
						} else if (message.equals("EntSignUpAttempt")) {
							myEntAccount = (EntrepreneurAccount) ois.readObject();
							hasReceived = true;
						} else if (message.equals("LoginAttempt")) {
							ambiguousAccount = (Object) ois.readObject();
							hasReceived = true;
						} else if (message.equals("GrabNewsFeedAttempt")) {
							grabNewsFeedEntAccount = (EntrepreneurAccount) ois.readObject();
							hasReceived = true;
						} else if (message.equals("AddConnectionAttempt")) {
							result = (boolean) ois.readObject();
							hasReceived = true;
						} else if (message.equals("AddLikeAttempt")) {
							result = (boolean) ois.readObject();
							hasReceived = true;
						} else if (message.equals("RefreshEntrepreneurAttempt")) {
							newConnections = (Vector<InvestorAccount>) ois.readObject();
							hasReceived = true;
						} else if (message.equals("InvUpdateAttempt")) {
							InvestorAccount tempInvAccount = (InvestorAccount) ois.readObject();
							if (tempInvAccount != null) {
								invUpdated = true;
								myInvAccount = tempInvAccount;
							}
							hasReceived = true;
						} else if (message.equals("EntUpdateAttempt")) {
							EntrepreneurAccount tempEntAccount = (EntrepreneurAccount) ois.readObject();
							if (tempEntAccount != null) {
								entUpdated = true;
								myEntAccount = tempEntAccount;
							}
							hasReceived = true;
						}
					}
				} catch (ClassNotFoundException cnfe) {
					System.out.println("cnfe from QuickPitchClient.run(): " + cnfe.getMessage());
					cnfe.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("ioe from QuickPitchClient.run(): " + ioe.getMessage());
					ioe.printStackTrace();
				}

			} catch (IOException ioe) {
				s = null;
			}
		}
		
	}
	
	// all the client server communication methods
	public InvestorAccount sendInvSignUpAttempt(InvSignUpAttempt invSignUpAttempt) {
		
		myInvAccount = null;
		try {
			oos.writeObject(invSignUpAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendInvSignUpAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendInvSignUpAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		return myInvAccount;
	}
	
	public EntrepreneurAccount sendEntSignUpAttempt(EntSignUpAttempt entSignUpAttempt) {
		
		myEntAccount = null;
		try {
			oos.writeObject(entSignUpAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendEntSignUpAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendEntSignUpAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		
		return myEntAccount;
	}
	
	public Object sendLoginAttempt(LoginAttempt loginAttempt) {
		
		ambiguousAccount = null;
		try {
			oos.writeObject(loginAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendLoginAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendLoginAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		
		if (ambiguousAccount instanceof InvestorAccount) {
			myInvAccount = (InvestorAccount)ambiguousAccount;
		}
		else if (ambiguousAccount instanceof EntrepreneurAccount) {
			myEntAccount = (EntrepreneurAccount)ambiguousAccount;
		}
		
		// return object
		return ambiguousAccount;
	}
	
	public EntrepreneurAccount grabNewsfeed(GrabNewsFeedAttempt grabNewsFeedAttempt) {
		grabNewsFeedAttempt.setInvestorAccount(myInvAccount);
		grabNewsFeedEntAccount = null;
		try {
			oos.writeObject(grabNewsFeedAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.grabNewsFeed(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.grabNewsFeed(): " + ie.getMessage());
			ie.printStackTrace();
		}
		
		return grabNewsFeedEntAccount;
	}
	
	public boolean addConnection(AddConnectionAttempt addConnectionAttempt) {
		result = false;
		try {
			oos.writeObject(addConnectionAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.addConnection(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.addConnection(): " + ie.getMessage());
			ie.printStackTrace();
		}
		
		return result;
	}
	
	public boolean addLike(AddLikeAttempt addLikeAttempt) {
		result = false;
		try {
			oos.writeObject(addLikeAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.addLike(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.addLike(): " + ie.getMessage());
			ie.printStackTrace();
		}	
		return result;
	}

	public Vector<InvestorAccount> sendRefreshEntrepreneurAttempt(RefreshEntrepreneurAttempt refreshEntrepreneurAttempt) {
		newConnections = null;
		try {
			oos.writeObject(refreshEntrepreneurAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		return newConnections;
	}

	public boolean sendInvUpdateAttempt(InvUpdateAttempt invUpdateAttempt) {
		invUpdated = false;
		try {
			oos.writeObject(invUpdateAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		return invUpdated;
	}

	public boolean sendEntUpdateAttempt(EntUpdateAttempt entUpdateAttempt) {
		entUpdated = false;
		try {
			oos.writeObject(entUpdateAttempt);
			oos.flush();
			while(!hasReceived) {
				Thread.sleep(100);
			}
			hasReceived = false;
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("ie from QuickPitchClient.sendRefreshEntrepreneurAttempt(): " + ie.getMessage());
			ie.printStackTrace();
		}
		return entUpdated;
	}

	// main method to run the client
	public static void main(String [] args) {

		@SuppressWarnings("unused")
		QuickPitchClient instance = new QuickPitchClient(Constants.IPAddress, 6789);
		
	}

}
