/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.Timer;

/**
 *
 * @author nyost448
 */
public class Client extends JFrame
{
	NFunction functions;
	public Client()
	{
		functions = new NFunction();
	}
	
	public boolean connect(String host, int port)
	{
		functions.connect(host, port);
		if((functions.recv()).equalsIgnoreCase("HELLO"))
			return true;
		return false;
	}	
	
	public void createUser()
	{
		
	}
}
