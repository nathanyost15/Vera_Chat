

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
/**
 * @author Nathaniel Yost
 * Dr. Frye
 * CSC328
 * DUE: 5 December 2016
 * Purpose: Listen for incoming connections and create a thread to handle each connection.
 */
public class Server
{
	private ServerSocket socket;
	private ChatRoom room;
	
	/**
	 * Initializes the server with chatrooms and binds the socket to specified port.
	 * @param port Port number supplied through command line arguments.
	 */
	public Server(int port)
	{		
		try
		{
			socket = new ServerSocket(port);
		}
		catch(SocketException exception) {exception.printStackTrace();}
		catch(IOException exception) {exception.printStackTrace();}
		room = new ChatRoom();
	}
	
	/**
	 * Infinite loop that continually listens for incoming connections and supplies each connection with a new thread (ie: User object).
	 */
	public void run()
	{
		while(true)
		{
			try
			{
				System.out.println("Listening for incoming connections..");				
				Socket clientSocket = socket.accept();
				room.addUser(clientSocket);
				/* 
				 * Display how many threads are running, includes main thread running server and clean/maitenance thread.
				 * Number of clients = n-2 | n = threadcount and subtract 2 for server and cleaning thread.
				 */
				System.out.println("Thread Count: " + Thread.activeCount());
			} 
			catch (IOException exception) {exception.printStackTrace();}	
		}
	}
	
	/**
	 * Main method that constructs/instantiates a new server object and starts it.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args)
	{
		// Usage Clause
		switch(args.length)
		{
			case 1:
				break;
			default:
				System.out.println("Usage: port");
				System.exit(-1);
				break;			
		}
		Server server = new Server(Integer.parseInt(args[0]));
		server.run();
	}
}
