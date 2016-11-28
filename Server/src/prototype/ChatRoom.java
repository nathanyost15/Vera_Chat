package prototype;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom
{
	private ArrayList<User> users;
	public ChatRoom()
	{
		users = new ArrayList<User>();
	}
	
	public void addUser(Socket clientSocket)
	{
		users.add(new User(this, clientSocket));
		new Thread(users.get(users.size()-1)).start();
	}
	
	public boolean isUnique(String username)
	{
		for(User u : users)
		{
			if(u.getUser() != null && u.getUser().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public void echo(String message)
	{
		for(User u : users)
		{
			new Thread(){
				@Override
				public void run()
				{
					u.echoMessage(message);
				}
			}.start();
		}
	}
}