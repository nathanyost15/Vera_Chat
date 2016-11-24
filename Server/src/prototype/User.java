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
					System.out.println("Client wants to close");
					break;
				case "CRESET": // Connection Reset
					ended = true;
					break;
				case "NICK":
					getUsername();
					break;
				case "ECHO":
					room.echo("["+ username + "] "+functions.recvS());
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
				String name = functions.recv();
				if(room.isUnique(name))
				{
					functions.send("READY");
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