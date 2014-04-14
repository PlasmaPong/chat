package chat;
import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	

	public ServerThread(Socket socket, ChatServer chatServer){
		super("Server Thread.");
		this.socket = socket;
		try{
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public void send(String message){
			out.println(message);
	}
	
	
}
