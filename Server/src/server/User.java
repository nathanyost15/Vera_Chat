package server;

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
	private String name;
	private Timer time;
	private int threadID;
	private boolean ended;
	public User(Socket socket, int currentThread)
	{		
		ended = false;
		this.socket = socket;
		threadID = currentThread;
		try
		{
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
		}
		catch(IOException e) {e.printStackTrace();}
		time = new Timer();
	}
	
	public int getThreadID()
	{
		return threadID;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public void run()
	{
		while(true)
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
				System.out.println(message.trim());
			}
			catch(SocketException e)
			{
				System.out.println("Connection on thread: " + threadID + " was terminated by client..");
				//System.out.println("Error writing to socket closing thread: " + threadID + "..");
				ended = true;
				break;
			}
			catch(IOException e) 
			{
				System.out.println("Connection on thread: " + threadID + " was terminated by server..");
				ended = true;
				break;		
			}			
		}
		closeSocket();
		ended = true;
	}
	
	public boolean isDone()
	{
		return ended;
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