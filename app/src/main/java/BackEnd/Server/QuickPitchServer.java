package BackEnd.Server;

// import statements
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import BackEnd.Resources.Constants;

// QuickPitchServerClass
public class QuickPitchServer {

	// private member variables
	private Vector<ServerThread> serverThreads;
	private QuickPitchJDBC mQuickPitchJDBC;
	
	// constructor
	public QuickPitchServer(int port) {
		
		// initializes private member variables
		ServerSocket ss = null;
		serverThreads = new Vector<ServerThread>();
		mQuickPitchJDBC = new QuickPitchJDBC();
		
		try {
			// creates server socket for this server
			ss = new ServerSocket(port);
			// permanently listens for clients to connect to
			while(true) {
				System.out.println("Waiting for connections...");
				Socket s = ss.accept();
				System.out.println("connection from " + s.getInetAddress());
				// creates ServerThread associated with this client
				ServerThread st = new ServerThread(s, this, mQuickPitchJDBC);
				serverThreads.add(st);
			}
		// catching errors
		} catch (IOException ioe) {
			System.out.println("ioe from QuickPitchServer constructor: " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			try {
				if(ss != null) {
					ss.close();
				}
			} catch (IOException ioe) {
				System.out.println("ioe closing ss: " + ioe.getMessage());
				ioe.printStackTrace();
			}			
		}
	}
	
	// removes a thread when a client disconnects
	public void removeServerThread (ServerThread st) {
		serverThreads.remove(st);
	}
	
	// main method for running the server
	public static void main(String [] args) {
		
		new QuickPitchServer(Constants.serverPort);
	}
	
}
