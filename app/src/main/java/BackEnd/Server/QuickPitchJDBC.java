package BackEnd.Server;

// import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import BackEnd.Client.EntrepreneurAccount;
import BackEnd.Client.InvestorAccount;
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

// QuickPitchJDBC 
public class QuickPitchJDBC {
	
	// private member variables
	private Connection conn;
	private ResultSet rs;
	// Constructor
	public QuickPitchJDBC() {
		
		// sets up database including connection, result set and JDBC driver
		conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/QuickPitchData?user=root&password=" + Constants.SQLPassword);
			rs = null;
		
		// catches errors
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
			cnfe.printStackTrace();
		}
	}
	
	// adds an investor account data to JDBC
	public InvestorAccount insertInvestor(InvSignUpAttempt attempt) {
		
		// gets encapsulated private member variables from invSignUpAttempt
		String username = attempt.getUsername();
		String password = attempt.getPassword();
		String email = attempt.getEmail();
		String companyName = attempt.getCompanyName();
		String accountType = "Investors";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Investors WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				rs.close();
				ps.close();
				return null;
			}
			else {
				ps = conn.prepareStatement("INSERT INTO UserAccountTypes (username, accountType) "
											+ "VALUES (?,?);");
				ps.setString(1, username);
				ps.setString(2, accountType);
				ps.executeUpdate();
				ps.close();
				
				ps = conn.prepareStatement("INSERT INTO Investors (username, pword, email, companyName) "
											+ "VALUES (?,?,?,?);");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, companyName);
				ps.executeUpdate();
				rs.close();
				ps.close();
				InvestorAccount invAccount = new InvestorAccount(username, password, email, companyName);
				return invAccount;
			}
		// catches errors
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		return null;
	}
	
	// adds entrepreneur account data to JDBC
	public EntrepreneurAccount insertEntrepreneur(EntSignUpAttempt attempt) {
		
		// gets encapsulated private member variables from entSignUpAttempt
		String username = attempt.getUsername();
		String password = attempt.getPassword();
		String email = attempt.getEmail();
		String companyName = attempt.getCompanyName();
		String companyInfo = attempt.getCompanyInfo();
		String productDescription = attempt.getProductDescription();
		String videoURL = attempt.getVideoURL();
		String accountType = "Entrepreneurs";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Entrepreneurs WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				rs.close();
				ps.close();
				return null;
			}
			else {
				ps = conn.prepareStatement("INSERT INTO UserAccountTypes (username, accountType) "
											+ "VALUES (?,?);");
				ps.setString(1, username);
				ps.setString(2, accountType);
				ps.executeUpdate();
				ps.close();
				
				ps = conn.prepareStatement("INSERT INTO Entrepreneurs (username, pword, email, companyName, companyInfo, productDescription, videoURL) "
											+ "VALUES (?,?,?,?,?,?,?);");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, companyName);
				ps.setString(5, companyInfo);
				ps.setString(6, productDescription);
				ps.setString(7, videoURL);
				ps.executeUpdate();
				rs.close();
				ps.close();

//				allEntrepreneurs.add(username);
				EntrepreneurAccount entAccount = new EntrepreneurAccount(username, password, email, companyName, companyInfo, productDescription, videoURL);
				return entAccount;
			}
		// catches errors
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		return null;
	}
	
	// checks a username and password login of user
	public Object checkLogin(LoginAttempt attempt){
		String username = attempt.getUsername();
		String password = attempt.getPassword();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM UserAccountTypes WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()){
				String accountType = rs.getString("accountType");
				if(accountType.equals("Investors")){
					ps = conn.prepareStatement("SELECT * FROM Investors WHERE username=? AND pword=?");
					ps.setString(1, username);
					ps.setString(2, password);
					rs = ps.executeQuery();
					if(rs.next()) {
						String email = rs.getString("email");
						String companyName = rs.getString("companyName");
						InvestorAccount investorAccount = new InvestorAccount(username, password, email, companyName);
						
						// get likes for investor
						ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? AND likeOrConnection=?");
						ps.setString(1, username);
						ps.setString(2, "like");
						rs = ps.executeQuery();
						while (rs.next()) {
							ps = conn.prepareStatement("SELECT * FROM Entrepreneurs WHERE username=?");
							ps.setString(1, rs.getString("entrepreneur"));
							ResultSet likeSet = ps.executeQuery();
							if (likeSet.next()) {
								String entUsername = likeSet.getString("username");
								String entPassword = likeSet.getString("pword");
								String entEmail = likeSet.getString("email");
								String entCompanyName = likeSet.getString("companyName");
								String companyInfo = likeSet.getString("companyInfo");
								String productDescription = likeSet.getString("productDescription");
								String videoURL = likeSet.getString("videoURL");
								EntrepreneurAccount entrepreneurAccount = new EntrepreneurAccount(entUsername, entPassword, entEmail, 
										entCompanyName, companyInfo, productDescription, videoURL);
								investorAccount.addLike(entrepreneurAccount);
								
							}
						}
						
						// get connections for investor
						ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? AND likeOrConnection=?");
						ps.setString(1, username);
						ps.setString(2, "connection");
						rs = ps.executeQuery();
						while (rs.next()) {
							ps = conn.prepareStatement("SELECT * FROM Entrepreneurs WHERE username=?");
							ps.setString(1, rs.getString("entrepreneur"));
							ResultSet connectionSet = ps.executeQuery();
							if (connectionSet.next()) {
								String entUsername = connectionSet.getString("username");
								String entPassword = connectionSet.getString("pword");
								String entEmail = connectionSet.getString("email");
								String entCompanyName = connectionSet.getString("companyName");
								String companyInfo = connectionSet.getString("companyInfo");
								String productDescription = connectionSet.getString("productDescription");
								String videoURL = connectionSet.getString("videoURL");
								EntrepreneurAccount entrepreneurAccount = new EntrepreneurAccount(entUsername, entPassword, entEmail, 
										entCompanyName, companyInfo, productDescription, videoURL);
								investorAccount.addConnection(entrepreneurAccount);
								
								
								
								
								
							}
						}			
						
						rs.close();
						ps.close();
						return investorAccount;
					}
					else {
						rs.close();
						ps.close();
						return null;
					}
				}
				else if (accountType.equals("Entrepreneurs")){
					ps.close();
					ps = conn.prepareStatement("SELECT * FROM Entrepreneurs WHERE username=? AND pword=?");
					ps.setString(1, username);
					ps.setString(2, password);
					rs = ps.executeQuery();
					if (rs.next()){
						String email = rs.getString("email");
						String companyName = rs.getString("companyName");
						String companyInfo = rs.getString("companyInfo");
						String productDescription = rs.getString("productDescription");
						String videoURL = rs.getString("videoURL");
						EntrepreneurAccount entrepreneurAccount = new EntrepreneurAccount(username, password, email, 
								companyName, companyInfo, productDescription, videoURL);
						
						
						
						// get connections for entrepreneur
						ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE entrepreneur=? AND likeOrConnection=?");
						ps.setString(1, username);
						ps.setString(2, "connection");
						rs = ps.executeQuery();
						while (rs.next()) {
							ps = conn.prepareStatement("SELECT * FROM Investors WHERE username=?");
							ps.setString(1, rs.getString("investor"));
							ResultSet likeSet = ps.executeQuery();
							if (likeSet.next()) {
								String invUsername = likeSet.getString("username");
								String invPassword = likeSet.getString("pword");
								String invEmail = likeSet.getString("email");
								String invCompanyName = likeSet.getString("companyName");
								InvestorAccount investorAccount = new InvestorAccount(invUsername, invPassword, invEmail, invCompanyName);
								
								entrepreneurAccount.addConnection(investorAccount);
								
							}
						}
						
						
						
						
						
						rs.close();
						ps.close();
						return entrepreneurAccount;
					}
					else {
						rs.close();
						ps.close();
						return null;
					}
				}
			} 
			else {
				rs.close();
				ps.close();
				return null;
			}
		} catch(SQLException sqle){
			System.out.println("sqle from JDBC.checkLogin(): " + sqle.getMessage());
			sqle.printStackTrace();
		}
		return null;
	}
	
	// adds a connection between investor and entrepreneur
	public Boolean addConnection(AddConnectionAttempt attempt) {
		String investor = attempt.getInvestor();
		String entrepreneur = attempt.getEntrepreneur();
		Boolean successfulConnection = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? and entrepreneur=? and likeOrConnection=?");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "connection");
			rs = ps.executeQuery();
			if(rs.next()) {
				return false;
			}
			rs.close();
			ps.close();
			
			// checks if the investor and entrepreneur already liked each other
			ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? and entrepreneur=? and likeOrConnection=?");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "like");
			rs = ps.executeQuery();
			if(rs.next()) {
				ps = conn.prepareStatement("UPDATE LikesConnections SET likeOrConnection=? where investor=? and entrepreneur=?");
				
				ps.setString(1, "connection");
				ps.setString(2, investor);
				ps.setString(3, entrepreneur);
				int rowsAffected = ps.executeUpdate();
				if (rowsAffected > 0){
					return true;
				}
				ps.close();
			}
			rs.close();
			ps.close();
			
			
			
			ps = conn.prepareStatement("INSERT INTO LikesConnections (investor, entrepreneur, likeOrConnection) "
										+ "VALUES (?,?,?);");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "connection");
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0){
				successfulConnection = true;
			}
			ps.close();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		return successfulConnection;
	}
	
	// grabs a random entrepreneur account to display on news feed
	public EntrepreneurAccount grabEntrepreneurAccount(GrabNewsFeedAttempt attempt) {
		Set<String> allEntrepreneurs = new HashSet<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Entrepreneurs");
			rs = ps.executeQuery();
			while(rs.next()) {
				String username = rs.getString("username");
				allEntrepreneurs.add(username);
			}
			rs.close();
			ps.close();

			if (attempt.getInvestorAccount() != null) {
				Set<String> hasSeenEntrepreneurs = attempt.getInvestorAccount().getHasSeenEntrepreneurs();
				if (hasSeenEntrepreneurs.size() < allEntrepreneurs.size()) {
					allEntrepreneurs.removeAll(hasSeenEntrepreneurs);
				}
			}

			Random rand = new Random();
			//System.out.println("all entrepreneurs size: " + allEntrepreneurs.size());
			int value = rand.nextInt(allEntrepreneurs.size());
			Object[] obj = allEntrepreneurs.toArray();
			Vector<String> entVector = new Vector<String>();
			for(int i = 0; i < obj.length; i++) {
				String curr = (String) obj[i];
				entVector.add(curr);
			}
			String selectedEntrepreneur = entVector.elementAt(value);

			ps = conn.prepareStatement("SELECT * FROM Entrepreneurs");
			rs = ps.executeQuery();
			while(rs.next()) {
				String username = rs.getString("username");
				allEntrepreneurs.add(username);
			}
			rs.close();
			ps.close();

			ps = conn.prepareStatement("SELECT * FROM Entrepreneurs WHERE username=?");
			ps.setString(1, selectedEntrepreneur);
			rs = ps.executeQuery();
			if(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("pword");
				String email = rs.getString("email");
				String companyName = rs.getString("companyName");
				String companyInfo = rs.getString("companyInfo");
				String productDescription = rs.getString("productDescription");
				String videoURL = rs.getString("videoURL");
				EntrepreneurAccount entrepreneurAccount = new EntrepreneurAccount(username, password, email, companyName, companyInfo, productDescription, videoURL);
				return entrepreneurAccount;
			}
			else {
				return null;
			}
		} catch (SQLException sqle) {
			System.out.println("sqle from JDBC.grabEntrepreneurAccount(); " + sqle.getMessage());
			sqle.printStackTrace();
		}
		return null;
	}
	
	// adds like between investor and entrepreneur
	public Boolean addLike(AddLikeAttempt attempt) {
		String investor = attempt.getInvestor();
		String entrepreneur = attempt.getEntrepreneur();
		Boolean successfulLike = false;
		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? and entrepreneur=? and likeOrConnection=?");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "connection");
			rs = ps.executeQuery();
			if(rs.next()) {
				return false;
			}
			rs.close();
			ps.close();
			
			
			ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE investor=? and entrepreneur=? and likeOrConnection=?");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "like");
			rs = ps.executeQuery();
			if(rs.next()) {
				return false;
			}
			rs.close();
			ps.close();
			ps = conn.prepareStatement("INSERT INTO LikesConnections (investor, entrepreneur, likeOrConnection) "
														+ "VALUES (?,?,?);");
			ps.setString(1, investor);
			ps.setString(2, entrepreneur);
			ps.setString(3, "like");
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0){
				successfulLike = true;
			}
			ps.close();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
		return successfulLike;
	}

	public Vector<InvestorAccount> refreshEntrepreneur(RefreshEntrepreneurAttempt refreshEntrepreneurAttempt) {
		Vector<InvestorAccount> newConnections = new Vector<InvestorAccount>();

		String entUsername = refreshEntrepreneurAttempt.getEntUsername();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM LikesConnections WHERE entrepreneur=? and likeOrConnection=?");
			ps.setString(1, entUsername);
			ps.setString(2, "connection");
			rs = ps.executeQuery();
			while(rs.next()) {
				String invUsername = rs.getString("investor");
				ps = conn.prepareStatement("SELECT * FROM Investors WHERE username=?");
				ps.setString(1, invUsername);
				ResultSet connectionSet = ps.executeQuery();
				if(connectionSet.next()) {
					String invPassword = connectionSet.getString("pword");
					String invEmail = connectionSet.getString("email");
					String invCompanyName = connectionSet.getString("companyName");
					InvestorAccount currInvestor = new InvestorAccount(invUsername, invPassword, invEmail, invCompanyName);
					newConnections.add(currInvestor);
				}
				connectionSet.close();
			}
			rs.close();
			ps.close();
		}
		catch (SQLException sqle) {
			System.out.println("sqle from JDBC.refreshEntrepreneur: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		return newConnections;
	}

	public InvestorAccount updateInvestor(InvUpdateAttempt invUpdateAttempt){
		String username = invUpdateAttempt.getUsername();
		String password = invUpdateAttempt.getPassword();
		String email = invUpdateAttempt.getEmail();
		String companyName = invUpdateAttempt.getCompanyName();
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Investors SET pword=?, email=?, " +
					"companyName=? WHERE username=?");
			ps.setString(1, password);
			ps.setString(2, email);
			ps.setString(3, companyName);
			ps.setString(4, username);
			ps.executeUpdate();
			InvestorAccount updatedInvAccount = new InvestorAccount(username, password, email, companyName);
			ps.close();
			return updatedInvAccount;

		} catch(SQLException sqle){
			System.out.println("sqle from JDBC.updateInvestor" + sqle.getMessage());
		}
		return null;
	}


	public EntrepreneurAccount updateEntrepreneur(EntUpdateAttempt entUpdateAttempt){
		String username = entUpdateAttempt.getUsername();
		String password = entUpdateAttempt.getPassword();
		String email = entUpdateAttempt.getEmail();
		String companyName = entUpdateAttempt.getCompanyName();
		String companyInfo = entUpdateAttempt.getCompanyInfo();
		String productDescription = entUpdateAttempt.getProductDescription();
		String videoURL = entUpdateAttempt.getVideoURL();
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Entrepreneurs SET pword=?, email=?, companyName=?, " +
					"companyInfo=?, productDescription=?, videoURL=? WHERE username=?");
			ps.setString(1, password);
			ps.setString(2, email);
			ps.setString(3, companyName);
			ps.setString(4, companyInfo);
			ps.setString(5, productDescription);
			ps.setString(6, videoURL);
			ps.setString(7, username);
			ps.executeUpdate();
			EntrepreneurAccount updatedEntAccount = new EntrepreneurAccount(username, password, email, companyName,
					companyInfo, productDescription, videoURL);
			ps.close();
			return updatedEntAccount;
		} catch(SQLException sqle){
			System.out.println("sqle from JDBC.updateEntrepreneur" + sqle.getMessage());
		}
		return null;
	}
	
	// main function for testing purposes only
	public static void main(String [] args) {
		QuickPitchJDBC myJDBC = new QuickPitchJDBC();
//		InvSignUpAttempt invSignUpAttempt = new InvSignUpAttempt("ABRARMATING", "password", "stirumal@usc.edu", "QuickPitch");
//		myJDBC.insertInvestor(invSignUpAttempt);
//

		EntSignUpAttempt entSignUpAttempt1 = new EntSignUpAttempt("mister chander", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt1);
		EntSignUpAttempt entSignUpAttempt2 = new EntSignUpAttempt("nosejob", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt2);
		EntSignUpAttempt entSignUpAttempt3 = new EntSignUpAttempt("desk", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt3);

		EntSignUpAttempt entSignUpAttempt4 = new EntSignUpAttempt("mymom", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt4);

		EntSignUpAttempt entSignUpAttempt5 = new EntSignUpAttempt("yourmom", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt5);

		EntSignUpAttempt entSignUpAttempt6 = new EntSignUpAttempt("bryant", "password", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt6);

		EntSignUpAttempt entSignUpAttempt7 = new EntSignUpAttempt("timelife", "passwrod", "tua@usc.edu", "QuickPitch", "omg", "omgahd", "video.com");
		myJDBC.insertEntrepreneur(entSignUpAttempt7);

//		LoginAttempt loginAttempt1 = new LoginAttempt("Srivas", "password");
//		InvestorAccount invAccount = (InvestorAccount)myJDBC.checkLogin(loginAttempt1);
//		System.out.println("investor account: " + invAccount.getUsername());

//		LoginAttempt loginAttempt2 = new LoginAttempt("Tony", "password");
//		EntrepreneurAccount entAccount = (EntrepreneurAccount)myJDBC.checkLogin(loginAttempt2);
//		System.out.println("entrepreneur account: " + entAccount.getUsername());

//		AddLikeAttempt addLikeAttempt = new AddLikeAttempt("Tony", "Srivas");
//		Boolean success1 = myJDBC.addLike(addLikeAttempt);
//		System.out.println("like added: " + success1);
//
//		AddConnectionAttempt addConnectionAttempt = new AddConnectionAttempt("Tony", "Srivas");
//		Boolean success2 = myJDBC.addConnection(addConnectionAttempt);
//		System.out.println("connection added: " + success2);
//
//		Boolean success3 = myJDBC.addConnection(addConnectionAttempt);
//		System.out.println("connection added : " + success3);

//		InvUpdateAttempt invUpdateAttempt = new InvUpdateAttempt("ABRARMATING", "my_name", "tirumala@gmail.com", "QuickBitch");
//		myJDBC.updateInvestor(invUpdateAttempt);
//
//		EntUpdateAttempt entUpdateAttempt = new EntUpdateAttempt("AMAZAGE", "tutu", "uddhav@gmail.com", "Kobe", "lol", "backpack", "dodgerssuck.com");
//		myJDBC.updateEntrepreneur(entUpdateAttempt);

		InvestorAccount invAccount = new InvestorAccount("phone", "paswerd", "hello.com", "coolbeans");
		for(int i = 0; i < 10; i++) {
			GrabNewsFeedAttempt grabNewsFeedAttempt = new GrabNewsFeedAttempt("phone");
			grabNewsFeedAttempt.setInvestorAccount(invAccount);
			EntrepreneurAccount entrepreneurAccount = myJDBC.grabEntrepreneurAccount(grabNewsFeedAttempt);
			invAccount.addToHasSeen(entrepreneurAccount.getUsername());
			System.out.println(entrepreneurAccount.getUsername());
		}

	}
}

