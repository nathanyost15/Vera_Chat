package prototype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import utils.NFunction;
import utils.Timer;
public class User implements Runnable
{	
	private Socket socket;
	private InputStream inStream;
	private OutputStream outStream;
	private String username;
	private Timer time;
	private ChatRoom room;
	private NFunction functions;
	
	private boolean ended;
	public User(ChatRoom room, Socket socket)
	{		
		this.room = room;
		ended = false;
		this.socket = socket;
		try
		{
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
		}
		catch(IOException e) {e.printStackTrace();}
		time = new Timer();
		functions = new NFunction(socket);
	}	
	
	public void run()
	{
		// Greet client connection
		functions.send("HELLO");
		while(!ended)
		{
			String request = functions.recvS();
			System.out.println(request);
			switch(request)
			{
				case "BYE": // Client requests closing of socket
					room.echo("["+ username + "] " + " has left the room..");
					closeSocket();
					ended = true;
					break;
				case "CRESET": // Connection Reset
					room.echo("["+ username + "] " + " has left the room..");
					closeSocket();
					ended = true;
					break;
				case "NICK":
					getUsername();
					break;
				default:
					room.echo("["+ username + "] "+request);
					break;
			}
		}
	}	
	
	public void echoMessage(String message)
	{
		functions.send(message);
	}
	
	public String getUser()
	{
		return username;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public boolean getDone()
	{
		return ended;
	}
	
	public void getUsername()
	{
		try
		{									
			while(true)
			{
				System.out.println("Getting ready to receive username");
				String name = functions.recv();
				System.out.println("Received username!");
				if(room.isUnique(name))
				{
					System.out.println("Sending READY");
					functions.send("READY");
					System.out.println("READY sent");
					username = name;
					return;
				}
				else						
				{
					functions.send("RETRY");
				}
			}					
		}			
		catch(NullPointerException exception) {System.err.println("Null reference error"); closeSocket();}
		catch(Exception exception){closeSocket();}
	}
	
	private boolean closeSocket()
	{
		try
		{
			socket.close();
			return true;
		}
		catch(IOException e){e.printStackTrace(); return false;}
	}
}