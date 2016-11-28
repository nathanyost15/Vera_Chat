package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
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
	}
	
	public NFunction(Socket socket)
	{
		this.socket = socket;
		try 
		{
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void connect(String host, int port)
	{
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(host);
		} catch (UnknownHostException e) {e.printStackTrace(); System.exit(-1);}
		try
		{
			if(socket != null && !socket.isClosed())
				socket.close();
			socket = new Socket(addr.getHostName(), port);
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
		catch(SocketException exception) {}
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
			timeout.start();
			// Send message
			send += ";";
			out.write(send.getBytes("UTF-8"));
			
			// Receive message
			int character =0;
			
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
	
	public String recvS()
	{
		String answer = "";
		try 
		{
			// Receive message
			int character =0;	
			while((character = in.read()) != -1)
			{
				if((char)character == ';')
					break;
				answer += (char)character;				
			}
		} 
		catch (SocketException exception) {System.err.println("Connection reset"); return "CRESET";}
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
