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
		System.out.println("Attempting");
		this.chatServer = chatServer;
		this.socket = socket;
		try{//IO getting action
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("IO Accomplished on server-side");
		}catch(IOException e){
			e.printStackTrace();
		}
		run();
	}
	public void send(String message){//just a method for the server to access and use.
		System.out.println("Message being sent(send method)(ServerThread)");
		out.println(message);
	}
	public void run(){
		//This polls for input from the client
		//upon getting a message, it posts it to the server
		System.out.println("Running");
		send("hello");
		while(chatServer.listening){
			try {
				System.out.println("polling for input(ServerThread)");
				System.out.print("Input received(ServerThread)");
				inMessage = in.readLine();
				chatServer.update(inMessage);
				System.out.print("this means message was recieved(ServerThread)");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
