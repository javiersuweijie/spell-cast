package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
	private static List<Socket> sockets = new ArrayList<Socket>();

    
	public static void main(String[] args) throws Exception {
		System.out.println("Server Initialized.");
	    ServerSocket serverSocket = new ServerSocket(4321);
	    BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in)); 
	    for (int i=0;i<2;i++) {
	    	Socket newSocket = serverSocket.accept();
			sockets.add(newSocket);	    		
			System.out.println(sockets.size() + " client connected."); 
			newSocket.setSoTimeout(100);
	    }
	    
	}
}

class GameRoomHandler extends Thread{
	private boolean active = true;
	private Socket[] clients = new Socket[2];
    private BufferedReader[] inChannels = new BufferedReader[2];
    private PrintWriter[] outChannels = new PrintWriter[2];
	
	public GameRoomHandler(Socket[] socket_array) throws Exception {
		this.clients = socket_array;
		for (int i=0;i<clients.length;i++) {
			inChannels[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
			outChannels[i] = new PrintWriter(clients[i].getOutputStream(), true);
			outChannels[i].println("You are connected");
		}
	}
	public void run(){
		while (active) {
			try {
				for (int i=0;i<clients.length;i++) {
					String message = inChannels[i].readLine();				
					if (message!=null) {
						String[] messageArray = message.split(" ");
						System.out.println(messageArray);
						//Call board.destroyTile
					}
				}	
			} catch (SocketTimeoutException e) {
				continue;
			} catch (IOException e) {
				
			}
		}
	}
}



