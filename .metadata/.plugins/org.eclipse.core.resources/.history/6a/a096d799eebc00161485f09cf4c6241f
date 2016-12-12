

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ICThread extends Thread
{
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	
	private final int MAX_QUEUE, PORT;
	private boolean running;
	
	public ICThread(int maxQueue, int port)
	{
		PORT = port;
		running = true;
		MAX_QUEUE = maxQueue;
		clients = new ArrayList<Socket>();
		try
		{
			socket = new ServerSocket(PORT);
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	@Override
	public void run()
	{
		while(running)
		{
			if(clients.size() >= MAX_QUEUE)
			{
				System.err.println("Too many Connections in Queue!");
				try
				{
					Thread.sleep(500);
				} 
				catch (InterruptedException e) {e.printStackTrace();}
				continue;
			}
			try
			{
				System.out.println("Looking for incoming connections!");
				clients.add(socket.accept());
			} 
			catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void stopRunning()
	{
		if(running)
			running = false;
		return;
	}
	
	public Socket getNextSocket()
	{
		Socket clientSocket = clients.get(0);
		clients.remove(0);
		return clientSocket;
	}
	
	public int getSize()
	{
		return clients.size();
	}
}
