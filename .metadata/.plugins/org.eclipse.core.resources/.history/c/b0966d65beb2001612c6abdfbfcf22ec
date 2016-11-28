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
	
	public void send(String send)
	{
		try
		{
			send += ";";
			out.write(send.getBytes("UTF-8"));
		}
		catch(IOException exception) {exception.printStackTrace();}		
	}
	
	/**
	 * Send a string and wait for a return of a string from other connection.
	 */
	public String sendrecv(String send)
	{		
		String answer = "";
		TimeOut timeout = new TimeOut(10);
		try 
		{			
			// Send message
			send += ";";
			out.write(send.getBytes());
			
			// Receive message
			int character =0;
			//timeout.start();
			while((character = in.read()) != -1)
			{
				if((char)character == ';')
					break;
				answer += (char)character;
			}
			//timeout.end();
		} 
		catch (IOException e) {e.printStackTrace();}
		return answer;
	}
	
	public String recv()
	{
		String answer = "";
		TimeOut timeout = new TimeOut(10);
		try 
		{
			// Receive message
			int character =0;	
			timeout.start();
			while((character = in.read()) != -1)
			{
				if((char)character == ';')
					break;
				answer += (char)character;				
			}
			timeout.end();
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
