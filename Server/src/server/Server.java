package server;

import java.net.ServerSocket;
import java.util.ArrayList;

import threads.CleanThread;
import threads.ICThread;
import utils.Timer;
public class Server
{
	private ServerSocket socket;
	private Timer updateTimer;
	private ArrayList<User> activeConnections;
	private ICThread icThread;
	private CleanThread cleanThread;
	private Thread thread;
	
	private final int MAX_CONNECTIONS, MAX_QUEUE,PORT;
	private int threadID;
	private boolean started;
	public Server(int maxConnections, int maxQueue, int port)
	{
		MAX_CONNECTIONS = maxConnections;
		MAX_QUEUE = maxQueue;
		PORT = port;
		threadID = 0;	
		started = false;
		
		updateTimer = new Timer();	
		activeConnections = new ArrayList<User>();
		icThread = new ICThread(MAX_QUEUE, port);
		cleanThread = new CleanThread(activeConnections, 250);
	}
	
	public void start()
	{
		if(started)
		{
			System.err.println("Server is already started!");
			return;
		}
		icThread.start();
		cleanThread.start();
		started = true;
	}
	
	public void update()
	{
		// The try catch is required otherwise the other threads don't get started?? I don't know why.
		// It seems like the main thread override the other threads created if there isn't a block to it.
		try
		{
			Thread.sleep(1_000);
		} 
		catch (InterruptedException e){e.printStackTrace();}
		
		while(activeConnections.size() < MAX_CONNECTIONS && icThread.getSize() != 0)
		//if(activeConnections.size() < MAX_CONNECTIONS && icThread.getSize() != 0)
		{
			activeConnections.add(new User(icThread.getNextSocket(),threadID));
			threadID++;
			thread = new Thread(activeConnections.get(activeConnections.size()-1));
			thread.start();
			System.out.println("Thread created for thread: " + (threadID-1));
			//System.exit(0);
		}
	}
	
	public void end()
	{
		// End Server
	}
	
	public static void main(String[] args)
	{
		int connections = 20,
				queues = 5 * connections,
				port = 8000;
		
		Server server = new Server(connections, queues, port);
		server.start();
		while(true)
		{
			server.update();			
		}
	}
}