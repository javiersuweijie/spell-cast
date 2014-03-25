package server;

import game.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
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
			newSocket.setSoTimeout(500);
	    }
	    GameRoomHandler g = new GameRoomHandler(sockets);
	    g.start();
	    g.join();
	}
}

class GameRoomHandler extends Thread{
	private boolean active = true;
	private Socket[] clients;
    private BufferedReader[] inChannels = new BufferedReader[2];
    private PrintWriter[] outChannels = new PrintWriter[2];
    private Board board;
	
	public GameRoomHandler(List<Socket> sockets) throws Exception {
		System.out.println("Gameroom created");
		this.clients =  (sockets.toArray(new Socket[2]));
	    board = new Board();
		
		for (int i=0;i<clients.length;i++) {
			inChannels[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
			outChannels[i] = new PrintWriter(clients[i].getOutputStream());
			outChannels[i].println("You are connected");
		}
		for (Tile[] sets:board.getBoard()) {
			sendClients(Arrays.toString(sets));
			System.out.println(Arrays.toString(sets));
		}
		flush();
		
	}
	
	private void sendClients(String message) {
		for (int i=0;i<clients.length;i++) {
			outChannels[i].println(message);
		}
	}
	
	private void flush() {
		for (int i=0;i<clients.length;i++) {
			outChannels[i].flush();
		}
	}
	
	private void endGame(int loser) {
		return; //send some info to the winner
	}
	
	private int[][] parseMessage(String message) {
		System.out.println("Message from client: "+message);
		int[][] output = new int[2][10];
		String[] messageArray = message.split(" ");
		System.out.println(Arrays.toString(messageArray));
		int count = 0;
		for (String coord:messageArray) {
			String[] splitted_coord = coord.split(",");
			output[0][count] = Integer.valueOf(splitted_coord[0]);
			output[1][count] = Integer.valueOf(splitted_coord[1]);
			count++;
		}
		return output;
	}
	
	public void run(){
		while (active) {
			for (int i=0;i<clients.length;i++) {
				try {
					//System.out.println("Listening to client: "+i);
					if (clients[i].isClosed()) endGame(i);
					String message = inChannels[i].readLine();				
					if (message!=null) {
						int[][] set = parseMessage(message);
						board.deleteTile(set);
						for (Tile[] sets:board.getBoard()) {
							sendClients(Arrays.toString(sets));
						}
						flush();
					}
				}	
				catch (SocketTimeoutException e) {} 
				catch (IOException e) {}
			}
		}
	}
}



