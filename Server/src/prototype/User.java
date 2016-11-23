package prototype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import utils.Timer;
public class User implements Runnable
{	
	private Socket socket;
	private InputStream inStream;
	private OutputStream outStream;
	private String username;
	private Timer time;
	private ChatRoom room;
	
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
	}	
	
	// GETTERS
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
		System.out.println("Started getting Username");
		String tempUser = "";
		try
		{									
			while(true)
			{
				int character = 0;
				while((character = inStream.read()) != -1 && tempUser != "")
				{
					tempUser += (char)character;
					System.out.println(tempUser);
				}
				if(room.isUnique(tempUser))
					break;
				else						
					outStream.write(-1);						
			}					
		}			
		catch(Exception exception){closeSocket();}
		username = tempUser;
	}
	
	public void run()
	{
		send("HELLO");
		/*getUsername();
		System.out.println("Done getting username");
		while(!ended)
		{
			try
			{
				// if it writes a 0 to client, connection is active otherwise SocketException thrown and closes thread and socket.
				
				outStream.write(1);
				outStream.flush();
				
				byte[] buffer = new byte[500];
				inStream.read(buffer);	
				String message = new String(buffer, "UTF-8");
				message = message.trim();
				room.echo(message);
			}
			catch(SocketException e)
			{
				System.out.println("Connection on thread: " + username + " was terminated by client..");
				ended = true;
				break;
			}
			catch(IOException e) 
			{
				System.out.println("Connection on thread: " + username + " was terminated by server..");
				ended = true;
				break;		
			}			
		}
		closeSocket();
		ended = true;*/
	}
	
	public void send(String message)
	{
		message += ";";
		try
		{
			outStream.write(message.getBytes("UTF-8"));
		} 
		catch (IOException e) {e.printStackTrace();}		
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