

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
/**
 * @author Nathaniel Yost
 * Dr. Frye
 * CSC328
 * DUE: 5 December 2016
 * Purpose: User is given an independent thread to communicate back and forth 
 * 			with the server and other clients.
 */
public class User implements Runnable
{	
	private String username;
	private ChatRoom room;
	private NFunction functions;
	
	private boolean ended;
	private final String[] CMDS = {"BYE", "HELP", "USERS", "COUNT"};
	
	/**
	 * Initializes new connection to server with the room, and socket.
	 * @param room Room which will connect all other users to communicate with.
	 * @param socket Socket used to open connection to server and client.
	 */
	public User(ChatRoom room, Socket socket)
	{		
		this.room = room;
		ended = false;
		functions = new NFunction(socket);
	}	
	
	/**
	 * Infinite method that will continue to receive input from client and carry out requests by user.
	 */
	public void run()
	{
		// Greet client connection
		functions.send("HELLO");
		getUsername();
		room.echo("["+ username + "] " + " has joined the room..");
		while(!ended)
		{
			String request = functions.recvS();
			switch(request)
			{
				case "BYE": 
					// Client requests closing of socket
					echoMessage("BYE");
					closeSocket();
					room.echo("["+ username + "] " + " has left the room..");
					ended = true;					
					break;
				case "HELP":
					// Displays all available commands that the server will recognize.
					echoMessage("Commands");
					for(String s : CMDS)
						echoMessage(s);
					echoMessage("DONE HELP");
					break;
				case "USERS":
					// Display all users currently connected to chat.
					echoMessage("Users in chat:");
					for(String s : room.getNames())
						echoMessage(s);
					break;
				case "COUNT":
					// Display how many users are currently connected to chat.
					echoMessage("Users in chat:");
					echoMessage(room.getUsers().size()+"");
					break;
				default:
					// If request doesn't correspond to a command output it to other users in room as a chat message.
					room.echo("["+ username + "] "+request);
					break;
			}
		}
	}	
	
	/**
	 * Echo message to current user.
	 * @param message Message send to client connected to server.
	 */
	public void echoMessage(String message)
	{
		functions.send(message);
	}
	
	/**
	 * Returns username of current user.
	 * @return
	 */
	public String getUser()
	{
		return username;
	}
	
	/**
	 * Returns socket that may or may not be connected to a client.
	 * @return
	 */
	public Socket getSocket()
	{
		return functions.getSocket();
	}
	
	/**
	 * Returns whether connection is ended or not.
	 * @return
	 */
	public boolean getDone()
	{
		return ended;
	}
	
	/**
	 * Method that does the process of setting up the user with a username.
	 */
	public void getUsername()
	{
		try
		{									
			while(true)
			{
				String name = functions.recvS();
				boolean unique = room.isUnique(name);
				if(unique && name.length() >= 3)
				{
					functions.send("READY");
					username = name;
					return;
				}
				else						
				{
					if(name.length() < 3)
						functions.send("Username must be 3 or more characters!");
					if(!unique)
						functions.send(name + " is already taken!");
					functions.send("RETRY");
				}
			}					
		}			
		catch(NullPointerException exception) {System.err.println("Null reference error"); closeSocket();}
		catch(Exception exception){closeSocket();}
	}
	
	/**
	 * Closes socket to the connected client.
	 */
	private void closeSocket()
	{
		functions.close();
	}
}