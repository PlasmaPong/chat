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


public class ClientWindow extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	public static ObjectOutputStream output;
	public ClientWindow(){
		//creating window, and create the entry field for the client.
		JPanel pane = new JPanel(new GridBagLayout());
		textField = new JTextField(20);
		textField.addActionListener(this);
		//the text window that shows what you put into the server
		textArea = new JTextArea(5, 20);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		//components
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(textField, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		add(scrollPane, constraints);
	}
	public static void main(String[] args){//starting up server connection and basic setup of I/O

		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		}
		if(args.length != 2){
			System.err.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}
		//Scanner kb = new Scanner(System.in);
		/*
		 * TODO MAKE MESSAGE INTO OUTPUTSTREAM
		 * NEED OBJECT I/O STREAM
		 */
		//String chatInput;// a default for the keyboard input, may not be used...
		String hostName = args[0];//               this
		int portNumber = Integer.parseInt(args[1]);//and this allow for server address calling on while starting the program
		try{// use parentheses in this declaration in order to close it later, only for writer reader stuff tho. ;)
			Socket chatSocket = new Socket(hostName,portNumber);
			output = new ObjectOutputStream(chatSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}


		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
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
		//toServer = new Message(text);
		try {
			output.writeObject(timeStamp+" "+text);
		} catch (IOException e) {
			System.out.println("Error in finding the server to send to, message failed");
			e.printStackTrace();
		}
	}
	
	private static void createAndShowGUI(){
		//set up the window
		JFrame frame = new JFrame("Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//contents, 
		frame.add(new ClientWindow());
		//window display
		frame.pack();
		frame.setVisible(true);
	}
}
