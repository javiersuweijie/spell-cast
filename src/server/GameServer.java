package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
	private static List<Socket> sockets = new ArrayList<Socket>();
	private static List<GameRoomHandler> game_rooms = new ArrayList<GameRoomHandler>();
    
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
	    GameRoomHandler g = new GameRoomHandler(sockets);
	    g.start();
	    g.wait();
	}
}

class GameRoomHandler extends Thread{
	private boolean active = true;
	private Socket[] clients = new Socket[2];
    private BufferedReader[] inChannels = new BufferedReader[2];
    private PrintWriter[] outChannels = new PrintWriter[2];
	
	public GameRoomHandler(List<Socket> sockets) throws Exception {
		this.clients = (Socket[]) sockets.toArray();
		for (int i=0;i<clients.length;i++) {
			inChannels[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
			outChannels[i] = new PrintWriter(clients[i].getOutputStream(), true);
			outChannels[i].println("You are connected");
		}
	}
	
	private void sendClients(String message) {
		for (int i=0;i<clients.length;i++) {
			outChannels[i].println(message);
		}
	}
	
	private void endGame(int loser) {
		return; //send some info to the winner
	}
	
	public void run(){
		while (active) {
			try {
				for (int i=0;i<clients.length;i++) {
					if (clients[i].isClosed()) endGame(i);
					String message = inChannels[i].readLine();				
					if (message!=null) {
						String[] messageArray = message.split(" ");
						System.out.println(messageArray);
						String update = null;//Call board.destroyTile
						sendClients(update);
					}
				}	
			} catch (SocketTimeoutException e) {
				continue;
			} catch (IOException e) {
			}
		}
	}
}



