package BackEnd.Client;

// import statements
import java.util.Scanner;
import java.util.Vector;

import BackEnd.Resources.AddConnectionAttempt;
import BackEnd.Resources.AddLikeAttempt;
import BackEnd.Resources.EntSignUpAttempt;
import BackEnd.Resources.GrabNewsFeedAttempt;
import BackEnd.Resources.InvSignUpAttempt;
import BackEnd.Resources.LoginAttempt;
import BackEnd.Resources.RefreshEntrepreneurAttempt;

// TestClass
public class Test {

		// main method
		public static void main(String [] args) {
			
			// initialize and get user input
			QuickPitchClient testClient = QuickPitchClient.getInstance();
			Scanner scanner = new Scanner(System.in);
			int cas = 0;
			
			while(cas != 13) {
				System.out.print("Which case would you like to test?\n");  
				cas = scanner.nextInt(); // Get what the user types.
				
				// checks if investor can successfully sign up
				if (cas == 1) {
					InvSignUpAttempt invSignUpAttempt = new InvSignUpAttempt("uddhav", "root", "joglekar@usc.edu",
							"QuickPitch");
					InvestorAccount investorAccount = testClient.sendInvSignUpAttempt(invSignUpAttempt);
					
					if (investorAccount instanceof InvestorAccount) {
						System.out.println(investorAccount.getUsername());
						System.out.println(investorAccount.getPassword());
						System.out.println(investorAccount.getEmail());
						System.out.println(investorAccount.getCompanyName());
					}
					else if (investorAccount == null) {
						System.out.println("The username was already taken.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
					
				}
				
				// checks if entrepreneur can successfully sign up
				else if (cas == 2) {
					EntSignUpAttempt entSignUpAttempt = new EntSignUpAttempt("tony", "csci", "tua@usc.edu",
							"QuickPitch", "Technology Firm", "Andorid App", "videoURL");
					EntrepreneurAccount entrepreneurAccount = testClient.sendEntSignUpAttempt(entSignUpAttempt);
					
					if (entrepreneurAccount instanceof EntrepreneurAccount) {
						System.out.println(entrepreneurAccount.getUsername());
						System.out.println(entrepreneurAccount.getPassword());
						System.out.println(entrepreneurAccount.getEmail());
						System.out.println(entrepreneurAccount.getCompanyName());
						System.out.println(entrepreneurAccount.getCompanyInfo());
						System.out.println(entrepreneurAccount.getProductDescription());
						System.out.println(entrepreneurAccount.getVideo());
					}
					else if (entrepreneurAccount == null) {
						System.out.println("The username was already taken.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
					
					entSignUpAttempt = new EntSignUpAttempt("srivas", "201", "stirumal@usc.edu",
							"Jack Of fMan", "I make Mopeds", "Motocycles", "videoURL");
					entrepreneurAccount = testClient.sendEntSignUpAttempt(entSignUpAttempt);
					
					if (entrepreneurAccount instanceof EntrepreneurAccount) {
						System.out.println(entrepreneurAccount.getUsername());
						System.out.println(entrepreneurAccount.getPassword());
						System.out.println(entrepreneurAccount.getEmail());
						System.out.println(entrepreneurAccount.getCompanyName());
						System.out.println(entrepreneurAccount.getCompanyInfo());
						System.out.println(entrepreneurAccount.getProductDescription());
						System.out.println(entrepreneurAccount.getVideo());
					}
					else if (entrepreneurAccount == null) {
						System.out.println("The username was already taken.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// checks if ambiguous account can sign in successfully
				else if (cas == 3) {
					LoginAttempt loginAttempt = new LoginAttempt("uddhav", "root");
					Object account = testClient.sendLoginAttempt(loginAttempt);
					
					if (account instanceof InvestorAccount) {
						InvestorAccount investorAccount = (InvestorAccount)account;
						System.out.println(investorAccount.getUsername());
						System.out.println(investorAccount.getPassword());
						System.out.println(investorAccount.getEmail());
						System.out.println(investorAccount.getCompanyName());
					}
					else if (account instanceof EntrepreneurAccount) {
						EntrepreneurAccount entrepreneurAccount = (EntrepreneurAccount)account;
						System.out.println(entrepreneurAccount.getUsername());
						System.out.println(entrepreneurAccount.getPassword());
						System.out.println(entrepreneurAccount.getEmail());
						System.out.println(entrepreneurAccount.getCompanyName());
						System.out.println(entrepreneurAccount.getCompanyInfo());
						System.out.println(entrepreneurAccount.getProductDescription());
						System.out.println(entrepreneurAccount.getVideo());
					}
					else if (account == null) {
						System.out.println("The username or password is incorrect.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// checks if ambiguous account cannot sign in successfully
				else if (cas == 4) {
					LoginAttempt loginAttempt = new LoginAttempt("uddhav", "fuckme");
					Object account = testClient.sendLoginAttempt(loginAttempt);
					
					if (account instanceof InvestorAccount) {
						InvestorAccount investorAccount = (InvestorAccount)account;
						System.out.println(investorAccount.getUsername());
						System.out.println(investorAccount.getPassword());
						System.out.println(investorAccount.getEmail());
						System.out.println(investorAccount.getCompanyName());
					}
					else if (account instanceof EntrepreneurAccount) {
						EntrepreneurAccount entrepreneurAccount = (EntrepreneurAccount)account;
						System.out.println(entrepreneurAccount.getUsername());
						System.out.println(entrepreneurAccount.getPassword());
						System.out.println(entrepreneurAccount.getEmail());
						System.out.println(entrepreneurAccount.getCompanyName());
						System.out.println(entrepreneurAccount.getCompanyInfo());
						System.out.println(entrepreneurAccount.getProductDescription());
						System.out.println(entrepreneurAccount.getVideo());
					}
					else if (account == null) {
						System.out.println("The username or password is incorrect.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// checks if news feed can grab an entrepreneur account
				else if (cas == 5) {
					GrabNewsFeedAttempt grabNewsFeedAttempt = new GrabNewsFeedAttempt("uddhav");
					EntrepreneurAccount entrepreneurAccount = testClient.grabNewsfeed(grabNewsFeedAttempt);
					
					if (entrepreneurAccount instanceof EntrepreneurAccount) {
						System.out.println(entrepreneurAccount.getUsername());
						System.out.println(entrepreneurAccount.getPassword());
						System.out.println(entrepreneurAccount.getEmail());
						System.out.println(entrepreneurAccount.getCompanyName());
						System.out.println(entrepreneurAccount.getCompanyInfo());
						System.out.println(entrepreneurAccount.getProductDescription());
						System.out.println(entrepreneurAccount.getVideo());
					}
					else if(entrepreneurAccount == null) {
						System.out.println("There was no entrepreneur account returned.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// checks if account can add like
				else if (cas == 6) {
					AddLikeAttempt addLikeAttempt = new AddLikeAttempt("tony", "uddhav");
					Boolean result = testClient.addLike(addLikeAttempt);
					if (result) {
						System.out.println("result is true");
					} else {
						System.out.println("result is false");
					}
				}
				
				// checks if account can add like
				else if (cas == 7) {
					AddLikeAttempt addLikeAttempt = new AddLikeAttempt("srivas", "uddhav");
					Boolean result = testClient.addLike(addLikeAttempt);
					if (result) {
						System.out.println("result is true");
					} else {
						System.out.println("result is false");
					}
				}
				
				// gets all likes
				else if (cas == 8) {
					LoginAttempt loginAttempt = new LoginAttempt("uddhav", "root");
					Object account = testClient.sendLoginAttempt(loginAttempt);
					
					if (account instanceof InvestorAccount) {
						InvestorAccount investorAccount = (InvestorAccount)account;
						Vector<EntrepreneurAccount> myLikes = investorAccount.getLikes();
						for (EntrepreneurAccount entrepreneurAccount : myLikes) {
							System.out.println(entrepreneurAccount.getUsername());
							System.out.println(entrepreneurAccount.getPassword());
							System.out.println(entrepreneurAccount.getEmail());
							System.out.println(entrepreneurAccount.getCompanyName());
							System.out.println(entrepreneurAccount.getCompanyInfo());
							System.out.println(entrepreneurAccount.getProductDescription());
							System.out.println(entrepreneurAccount.getVideo());
						}
					}
					else if (account instanceof EntrepreneurAccount) {

					}
					else if (account == null) {
						System.out.println("This username and password does not exist.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// gets all connections
				else if (cas == 9) {
					LoginAttempt loginAttempt = new LoginAttempt("uddhav", "root");
					Object account = testClient.sendLoginAttempt(loginAttempt);
					
					if (account instanceof InvestorAccount) {
						InvestorAccount investorAccount = (InvestorAccount)account;
						Vector<EntrepreneurAccount> myLikes = investorAccount.getConnections();
						for (EntrepreneurAccount entrepreneurAccount : myLikes) {
							System.out.println(entrepreneurAccount.getUsername());
							System.out.println(entrepreneurAccount.getPassword());
							System.out.println(entrepreneurAccount.getEmail());
							System.out.println(entrepreneurAccount.getCompanyName());
							System.out.println(entrepreneurAccount.getCompanyInfo());
							System.out.println(entrepreneurAccount.getProductDescription());
							System.out.println(entrepreneurAccount.getVideo());
						}
					}
					else if (account instanceof EntrepreneurAccount) {

					}
					else if (account == null) {
						System.out.println("This username and password does not exist.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				// checks if account can add connection
				else if (cas == 10) {
					AddConnectionAttempt addConnectionAttempt = new AddConnectionAttempt("tony", "uddhav");
					Boolean result = testClient.addConnection(addConnectionAttempt);
					if (result) {
						System.out.println("result is true");
					} else {
						System.out.println("result is false");
					}
				}
				
				// gets all connections
				else if (cas == 11) {
					LoginAttempt loginAttempt = new LoginAttempt("tony", "csci");
					Object account = testClient.sendLoginAttempt(loginAttempt);
					
					if (account instanceof InvestorAccount) {
						
					}
					else if (account instanceof EntrepreneurAccount) {
						EntrepreneurAccount entrepreneurAccount = (EntrepreneurAccount)account;
						Vector<InvestorAccount> myConnections = entrepreneurAccount.getConnections();
						for (InvestorAccount investorAccount : myConnections) {
							System.out.println(investorAccount.getUsername());
							System.out.println(investorAccount.getPassword());
							System.out.println(investorAccount.getEmail());
							System.out.println(investorAccount.getCompanyName());
						}
					}
					else if (account == null) {
						System.out.println("This username and password does not exist.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				
				else if (cas == 12) {
					LoginAttempt loginAttempt = new LoginAttempt("tony", "csci");
					Object account = testClient.sendLoginAttempt(loginAttempt);


					if (account instanceof InvestorAccount) {

					}
					else if (account instanceof EntrepreneurAccount) {
						EntrepreneurAccount entrepreneurAccount = (EntrepreneurAccount)account;
						RefreshEntrepreneurAttempt refreshEntrepreneurAttempt = new RefreshEntrepreneurAttempt(entrepreneurAccount.getUsername());
						Vector<InvestorAccount> newConnections = testClient.sendRefreshEntrepreneurAttempt(refreshEntrepreneurAttempt);
						entrepreneurAccount.setConnections(newConnections);
						Vector<InvestorAccount> myConnections = entrepreneurAccount.getConnections();

						for (InvestorAccount investorAccount : myConnections) {
							System.out.println(investorAccount.getUsername());
							System.out.println(investorAccount.getPassword());
							System.out.println(investorAccount.getEmail());
							System.out.println(investorAccount.getCompanyName());
						}
					}
					else if (account == null) {
						System.out.println("This username and password does not exist.");
					}
					else {
						System.out.println("Test.main(): This line of code should never print...");
					}
				}
				

			}
			// close the scanner
			scanner.close();
		}
}
