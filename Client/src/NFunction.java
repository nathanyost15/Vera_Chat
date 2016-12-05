

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
 * Purpose: Used for most network functionality, sending/receiving data between sockets etc.
 */
public class NFunction 
{
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	/**
	 * Initializes NFunction without a socket object.
	 */
	public NFunction()
	{
		socket = null;
		in = null;
		out = null;
	}
	
	/**
	 * Initializes NFunction with a socket object that is already connected.
	 * @param socket Socket that will be used in I/O methods.
	 */
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
	
	/**
	 * Connects socket to specified host and port and initializes I/O streams.
	 * @param host Hostname of destination socket.
	 * @param port Port number of destination socket.
	 */
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
		catch(SocketException exception) {return "BYE";}
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
		catch(SocketException exception) {}
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
		catch (SocketException exception) {return "BYE";}
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