/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.Timer;

import client.Client;
import client.NFunction;

/**
 *
 * @author nyost448
 */
public class GUI extends JFrame
{
	// Variables declaration
	private JTextField inputField;
	private JList<String> outputList;
	private JScrollPane outputPanel;
	private JPanel panel;
	private JButton sendButton;
	private DefaultListModel<String> outputModel;
	
	private Timer timer;
	private Client client;
	private NFunction functions;
	private Thread reader, sender;

	// End of variables declaration
	public GUI(String host, int port)
	{
		initComponents();
		this.getRootPane().setDefaultButton(sendButton);
		functions = new NFunction();
		// Connect to server
		functions.connect(host, port);
		// Receive Hello message
		String answer = functions.recv();
		outputModel.addElement(answer);
		if(!answer.equalsIgnoreCase("HELLO"))
		{
			outputModel.addElement("Could not connect to server!");
		}
		else
		{
			outputModel.addElement("Successfully connected to \nServer: " + host + "\nPort: " + port);
			outputModel.addElement("type NICK and hit Enter");
			outputModel.addElement("type desired username and Enter.");
		}
		reader = new Thread(){
			@Override
			public void run()
			{
				while(true)
				{
					System.out.println("Attempting to get output");
					String answer = functions.recvS();
					System.out.println("GOT OUTPUT");
					outputModel.addElement(answer);
					if(outputModel.size() >13)
						outputModel.remove(0);
//					if(answer == null)
//					{
//						outputModel.addElement("Server connection is reset!");
//						break;
//					}
				}				
			}
		};
		reader.start();
	}

	private void initComponents()
	{
		// Initialize all components
		panel = new javax.swing.JPanel();
		outputPanel = new JScrollPane();
		outputModel = new DefaultListModel<String>();
		outputList = new JList<String>(outputModel);
		inputField = new JTextField();
		sendButton = new JButton();		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outputPanel.setViewportView(outputList);

		sendButton.setText("Send");
		sendButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendButtonActionPerformed(e);				
			}
		});

		GroupLayout panelLayout = new GroupLayout(panel);
		panel.setLayout(panelLayout);
		panelLayout.setHorizontalGroup(panelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelLayout.createSequentialGroup().addContainerGap()
						.addGroup(panelLayout
								.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(panelLayout.createSequentialGroup()
										.addComponent(inputField,
												GroupLayout.PREFERRED_SIZE, 405,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(sendButton))
								.addComponent(outputPanel))
						.addContainerGap()));
		panelLayout.setVerticalGroup(panelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panelLayout.createSequentialGroup()
						.addComponent(outputPanel, GroupLayout.PREFERRED_SIZE,
								250, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
								35, Short.MAX_VALUE)
						.addGroup(panelLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(inputField,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(sendButton))));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void sendButtonActionPerformed(ActionEvent e)
	{	
		new Thread(){
			@Override
			public void run()
			{				
				functions.send(inputField.getText());
				inputField.setText("");
				inputField.requestFocusInWindow();
			}
		}.start();		
	}
	
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
		GUI gui = new GUI(args[0], Integer.parseInt(args[1]));
		//GUI gui = new GUI("73.13.216.20", 8000);
		//GUI gui = new GUI("10.0.0.248", 8000);
	}
}