package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
	private static List<Socket> sockets = new ArrayList<Socket>();
    private static List<BufferedReader> inChannels = new ArrayList<BufferedReader>();
    private static List<PrintWriter> outChannels = new ArrayList<PrintWriter>();
    
	public static void main(String[] args) throws Exception {
		System.out.println("Server Initialized.");
	    ServerSocket serverSocket = new ServerSocket(4321);
	    BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in)); 
    	Socket newSocket = serverSocket.accept();
		sockets.add(newSocket);	    		
		System.out.println(sockets.size() + " client connected."); 
		
		PrintWriter out = new PrintWriter(newSocket.getOutputStream(), true);
		outChannels.add(out);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
		inChannels.add(in);
		
		
		if(sockets.size()==2){
			for(int i=0;i<2;i++){
				outChannels.get(i).println("Clients connected. Game start!");
				outChannels.get(i).flush();
				inChannels.get(i).wait();
			}
		}
	}
}
class Server extends Thread{
	public void run(){
		
	}
}
