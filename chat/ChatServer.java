package chat;
import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class ChatServer {
	private ArrayList<ServerThread> list;
	public ChatServer(){
		list = new ArrayList();
	}
	public void Run(){
		try{
			Scanner kb = new Scanner(System.in);
			System.out.print("enter port number");
			int portNumber = kb.nextInt();
			ServerSocket s;

			s = new ServerSocket(portNumber);
			boolean listening = true;
			while(listening){
				list.add(new ServerThread(s.accept(),this));
				list.get(list.size()-1).start();
			}
			s.close();
		}
		catch(IOException e){
			System.out.println("I cant let you do that Starfox :)");
			e.printStackTrace();
		}
	}
	public void Update(String message){
		for(int i=0;i<list.size();i++){
			list.get(i).send(message);
		}
	}
}

