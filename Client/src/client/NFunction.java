package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * Used for most network functionality, sending/receiving data between sockets etc.
 * @author nathan
 *
 */
public class NFunction 
{
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	public NFunction()
	{
		init();
	}
	
	public void init()
	{		
	}
	
	public void connect(String host, int port)
	{
		try
		{
			if(socket != null && !socket.isClosed())
				socket.close();
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		}
		catch(IOException exception){System.err.println("Could not connect to " + host + " on port "+ port + "."); System.exit(-1);}
	}
	
	/**
	 * Send a string and wait for a return of a string from other connection.
	 */
	public String sendrecv(String send)
	{		
		String answer = "";
		try 
		{
			// Send message
			out.write(send.getBytes());
			
			// Receive message
			int character =0;			
			while((character = in.read()) != -1)
			{
				answer += (char)character;
			}
		} 
		catch (IOException e) {e.printStackTrace();}
		return answer;
	}
	
	public String recv()
	{
		String answer = "";
				
		try 
		{
			// Receive message
			int character =0;	
			while((character = in.read()) != -1)
			{
				answer += (char)character;
			}
		} 
		catch (IOException e) {e.printStackTrace();}
		return answer;
	}
	
	public void close()
	{
		try
		{
			socket.close();
		}
		catch(IOException exception){System.err.println("Could not disconnect");}
	}
	
	public Socket getSocket()
	{
		return socket;
	}
}
