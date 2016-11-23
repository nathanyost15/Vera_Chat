package client;


public class Timer
{
	private boolean checkTime, tick;
	private long startTime, endTime, difference;

	public Timer()
	{
		checkTime = true;
		tick = false;
	}
	
	public boolean checkTick(double ms)
	{
		double nanoSeconds = ms*1000000.0;
		return getTick(nanoSeconds);
	}
	
	private boolean getTick(double nanoSeconds)
	{
		if (checkTime)
		{
			startTime = System.nanoTime();
			checkTime = false;
		}
		endTime = System.nanoTime();
		difference = endTime - startTime;

		if (difference >= nanoSeconds)
		{
			tick = true;
			difference = 0;
			checkTime = true;
		} 
		else
			tick = false;
		
		return tick;
	}

}