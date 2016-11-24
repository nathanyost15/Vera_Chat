package client;

import java.util.Scanner;

public class Driver 
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
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
		{
			System.out.println("Server has responded with HELLO!");
			System.out.println("Enter username");
			while(!client.checkUsername(scan.nextLine()))
			{
				System.out.println("Enter username");
			}
			System.out.println("Username is unique");
		}
		else
			System.out.println("Server has not responded with HELLO!");
		client.echo(scan.nextLine());
		System.out.println(client.getMessage());
		System.exit(0);
	}
}
