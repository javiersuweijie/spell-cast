package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {
	
	public static void main(String[] args){
		Dictionary dictionaryInstantiate = new Dictionary();
		Map<String,String> dictionary = dictionaryInstantiate.getDictionary();
		Board boardInstantiate = new Board();
		Tile[][] boardActual = boardInstantiate.getBoard();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				Tile tile = boardActual[i][j];
				String toPrint = dictionary.get(tile.getType());
				System.out.print(toPrint + " ");
			}
			System.out.println("");
		}
	}
}
class Dictionary{
	private Map<String,String> dictionary;
	
	Dictionary(){
		dictionary = new HashMap<String,String>();
		dictionary.put("Fire", "RE");
		dictionary.put("Water", "BL");
		dictionary.put("Lightning", "YE");
		dictionary.put("Light", "WH");
		dictionary.put("Dark", "DK");
		dictionary.put("Earth", "BR");
	}
	public Map<String, String> getDictionary(){
		return dictionary;
	}
}

class Tile{
	private int[] coordinates;
	private String type;
	
	Tile(int x, int y, String type){
		this.type = type;
		this.coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public int[] getTile(){
		return coordinates;
	}
	public String getType(){
		return type;
	}
	
}
class Board{
	private static final int dimensions = 10;
	private Tile[][] board;
	private Random randomSeed;
	private int numberOfTypes = 6;
	
	Board(){
		this.board = new Tile[dimensions][dimensions];
		this.randomSeed = new Random();
		generateBoard();
	}
	
	public void generateBoard(){
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){
				int random = randomSeed.nextInt(numberOfTypes);
				String type=null;
				
				switch(random){
				case 0: type = "Fire";
				break;
				case 1: type = "Water";
				break;
				case 2: type = "Lightning";
				break;
				case 3: type = "Earth";
				break;
				case 4: type = "Light";
				break;
				case 5: type = "Dark";
				break;
				}
				Tile tile = new Tile(i,j,type);
				board[i][j] = tile;
			}
		}
	}
	public Tile[][] getBoard(){
		return board;
	}
}