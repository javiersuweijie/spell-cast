package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class GameClient {
	
	static PrintWriter clientOut = null;
	static ArrayList<String> TileBuffer = new ArrayList<String>();
     
	public static void main(String[] args) throws Exception {
		String hostName = "localhost";
        int portNumber = 4321;
 
        Socket clientSocket = new Socket(hostName, portNumber);
        clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        
        PlayerHandler playerH = new PlayerHandler(clientSocket);
        ServerListener serverL = new ServerListener(clientSocket);
        
        playerH.start();
        serverL.start();
	
	}
}

class PlayerHandler extends Thread{
	private Socket sSocket;
    private BufferedReader stdIn;
    private String temp;
	
	public PlayerHandler(Socket clientSocket) throws Exception {
		sSocket = clientSocket;
		stdIn = new BufferedReader(new InputStreamReader(System.in)); 
	}
	
	public void run(){
		while (true){
			try {
				if (stdIn.ready()){
					temp = stdIn.readLine();
					if (ClientM.parsePlayerInput(temp)){
						ClientM.inputList(temp, GameClient.TileBuffer);
					} else if(temp.equals("SEND")) {
						ClientM.inputOut(GameClient.TileBuffer, GameClient.clientOut);
					} else {
						System.out.println("Erroneous Entry. Format Expected: Xpos,Ypos / SEND");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class ServerListener extends Thread{
	private Socket sSocket;
    private BufferedReader serverIn;
    private String temp;
	
	public ServerListener(Socket clientSocket) throws Exception {
		sSocket = clientSocket;
		serverIn = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
	}
	
	public void run(){
			while (true){
				try {
					if (serverIn.ready()){
						temp = serverIn.readLine();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}

class ClientM{
	
	static boolean parsePlayerInput(String s){
		
		if (s.matches("\\s*[1-8]\\s*,\\s*[1-8]\\s*")) return true;
		return false;
	}
	
	static void inputList(String s, ArrayList<String> list){
		
		s = s.trim();
		String[] tmparray = s.split(",");
		for (int i = 0; i < 2; i++){
			tmparray[i] = tmparray[i].trim();
		}
		list.add(tmparray[0] + "," + tmparray[1]);
	}
	
	static void inputOut(ArrayList<String> list, PrintWriter out){
		
		String temp = null;
		for (String s : list){
			temp = temp + s + " ";
			list.remove(s);
		}
		temp = temp.trim();
		out.println(temp);
	}
}



