package chat;
import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private ChatServer chatServer;
	String inMessage;

	public ServerThread(Socket socket, ChatServer chatServer){
		super("Server Thread.");
		this.chatServer = chatServer;
		this.socket = socket;
		try{//IO getting action
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("IO Accomplished on server-side");
		}catch(IOException e){
			e.printStackTrace();
		}
		run();
	}
	public void send(String message){//just a method for the server to access and use.
			out.println(message);
			System.out.println("Message being sent");
	}
	public void run(){
		//This polls for input from the client
		//upon getting a message, it posts it to the server
		System.out.println("Running");
		while(chatServer.listening){
			try {
				System.out.println("polling for input");
				while(in.ready()){
					System.out.print("Input received");
					inMessage = in.readLine();
					chatServer.update(inMessage);
					System.out.print("this means message was recieved");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
