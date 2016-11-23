package prototype;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import server.User;

public class Server
{
	private ServerSocket socket;
	private ArrayList<ChatRoom> chatrooms;
	
	public Server(int port, int chatroomIndex, int roomCount)
	{		
		try
		{
			socket = new ServerSocket(port);
		}
		catch(SocketException exception) {exception.printStackTrace();}
		catch(IOException exception) {exception.printStackTrace();}
		chatrooms = new ArrayList<ChatRoom>();
		chatrooms.add(new ChatRoom());
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				System.out.println("Listening for incoming connections..");
				Socket clientSocket = socket.accept();
				chatrooms.get(0).addUser(clientSocket);
				//new Thread(new User(clientSock, 0)).start();
			} 
			catch (IOException exception) {exception.printStackTrace();}	
		}
	}
	
	public static void main(String[] args)
	{
		// CLA = port chatroom#
		int chatroom = 1;
		Server server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]), chatroom);
		new Thread()
		{
			@Override
			public void run()
			{
				server.run();
			}
		}.start();
		System.out.println("Blah main thread is at the end! :D");
	}
}
