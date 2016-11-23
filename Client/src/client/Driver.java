package client;

public class Driver 
{
	public static void main(String[] args)
	{
		switch(args.length)
		{
			case 2:
				break;
			case 1:
				System.out.println("Missing second argument!");
			default:
				System.out.println("Usage: hostname/ip port");
				System.exit(-1);
				
		}
		Client client = new Client();
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		boolean connected = client.connect(host, port);
		
		if(connected)
			System.out.println("Server has responded with HELLO!");
		else
			System.out.println("Server has not responded!");
	}
}
