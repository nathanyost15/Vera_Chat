package threads;

import java.util.ArrayList;

import server.User;
import utils.Timer;

public class CleanThread extends Thread
{
	private ArrayList<User> activeConnections;
	private Timer time;
	private int interval;
	public CleanThread(ArrayList<User> connections, int interval)
	{
		activeConnections = connections;
		time = new Timer();
		this.interval = interval;
	}
	
	@Override
	public void run()
	{		
		while(true)
			if(time.checkTick((double)interval))
				clean();		
	}
	
	private void clean()
	{
		int count = 0;
		if(activeConnections.size() == 0)
			return;
		for(int index = 0; index < activeConnections.size(); index++)
		{
			if(activeConnections.get(index).getDone())
			{
				activeConnections.remove(index);
				count++;
				index--;
			}
		}
		if(count != 0)
		System.out.println("Connections removed: " + count);
	}
}
