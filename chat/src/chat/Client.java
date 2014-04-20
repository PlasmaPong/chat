package chat;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.net.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class Client extends Thread implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	public static PrintWriter output;
	public static BufferedReader in;
	public boolean active = true;
	public Client(){
		JFrame frame = new JFrame("Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320,240);

		textField = new JTextField();
		textField.addActionListener(this);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		frame.add(textArea, BorderLayout.CENTER);
		frame.add(textField, BorderLayout.SOUTH);

		frame.setVisible(true);
		Scanner kb = new Scanner(System.in);
		/*
		 * TODO MAKE MESSAGE INTO OUTPUTSTREAM
		 * NEED OBJECT I/O STREAM
		 */
		//String chatInput;// a default for the keyboard input, may not be used...
		System.out.println("Enter the IP of the server you wish to access.");
		String hostName = kb.next();//               this
		System.out.println("enter the port number.");
		int portNumber = kb.nextInt();//and this allow for server address calling on while starting the program
		try{// use parentheses in this declaration in order to close it later, only for writer reader stuff tho. ;)
			Socket chatSocket = new Socket(hostName,portNumber);
			output = new PrintWriter(chatSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public static void main(String[] args){//starting up server connection and basic setup of I/O
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Client().start();
			}
		});
		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		//String fromServer; Might be used, these are potentially useless
	}

	public String getTimeStamp(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String str = dateFormat.format(calendar.getTime());
		return str;
	}
	public void actionPerformed(ActionEvent event) {//just a thing for the action of the textField
		String text = textField.getText();
		String timeStamp = getTimeStamp();
		output.write(timeStamp+" "+text);
	}
	public void run(){//this just checks for a message from the server
		while(active){
			System.out.println("Polling for input(ClientSide Message)");
			try {
				while(!in.ready()){
					System.out.println("Input detected, attempting to append");
					textArea.append(in.readLine()+"\n");
					System.out.println("Input received from server");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
