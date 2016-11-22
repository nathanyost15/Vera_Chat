package threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;

public class IOThread extends Thread
{
	private Socket socket;
	private InputStream inStream;
	private OutputStream outStream;
	private DefaultListModel<String> outputModel;
	
	public IOThread(String host, int port)
	{		
		try
		{
			socket = new Socket(host, port);
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
		} 
		catch (ConnectException e) {System.err.println("Connection Refused!"); System.exit(-1);}
		catch (UnknownHostException e) {System.err.println("Check hostname and try again!"); System.exit(-1);} 
		catch (IOException e) {e.printStackTrace(); System.exit(-1);}
		this.outputModel = outputModel;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			String message = "";
			try
			{
				message = inStream.read()+"";
			} 
			catch (IOException e) {e.printStackTrace(); System.exit(-1);}
			//outputModel.addElement(message);
		}
//		try
//		{
//			outputModel.addElement(""+inStream.read());
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
