package game;

import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


class Types{
	public enum Elements{
		FIRE,WATER,LIGHTNING,LIGHT,DARK,EARTH
	}
}

public class BoardTest {
	
	public static void main(String[] args){
		Dictionary dictionaryInstantiate = new Dictionary();
		Map<Types.Elements,String> dictionary = dictionaryInstantiate.getDictionary();
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
		BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
		while(true){
			try {
				System.out.println("Input X");
				String x = stdIn.readLine();
				System.out.println("Input Y");
				String y = stdIn.readLine();
				int[][] a = new int[1][2];
				a[0][0] = Integer.valueOf(x);
				a[0][1] = Integer.valueOf(y);
				boardInstantiate.deleteTile(a);
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++){
						Tile tile = boardActual[i][j];
						String toPrint = dictionary.get(tile.getType());
						System.out.print(toPrint + " ");
					}
					System.out.println("");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
class Dictionary{
	private Map<Types.Elements,String> dictionary;
	
	Dictionary(){
		dictionary = new HashMap<Types.Elements,String>();
		dictionary.put(Types.Elements.FIRE, "RE");
		dictionary.put(Types.Elements.WATER, "BL");
		dictionary.put(Types.Elements.LIGHTNING, "YE");
		dictionary.put(Types.Elements.LIGHT, "WH");
		dictionary.put(Types.Elements.DARK, "DK");
		dictionary.put(Types.Elements.EARTH, "BR");
	}
	public Map<Types.Elements, String> getDictionary(){
		return dictionary;
	}
}

class Tile{
	private int[] coordinates;
	private Types.Elements type;
	
	Tile(int x, int y, Types.Elements type){
		this.type = type;
		this.coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public int[] getTile(){
		return coordinates;
	}
	public Types.Elements getType(){
		return type;
	}
	public void setType(){
		Randomizer random = new Randomizer();
		this.type = random.TypeRandomizer();
	}
}
class Randomizer{
	private Random randomSeed;
	
	Randomizer(){
		this.randomSeed = new Random();
	}
	public Types.Elements TypeRandomizer(){
		int random = randomSeed.nextInt(Board.numberOfTypes);
		Types.Elements type = null;
		switch(random){
		case 0: type = Types.Elements.FIRE;
		break;
		case 1: type = Types.Elements.WATER;
		break;
		case 2: type = Types.Elements.LIGHTNING;
		break;
		case 3: type = Types.Elements.EARTH;
		break;
		case 4: type = Types.Elements.LIGHT;
		break;
		case 5: type = Types.Elements.DARK;
		break;
		}
		return type;
	}
}

class Board{
	public static final int dimensions = 10;
	public static final int numberOfTypes = 6;
	private Tile[][] board;
	Board(){
		this.board = new Tile[dimensions][dimensions];
		generateBoard();
	}
	
	public synchronized void generateBoard(){
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){
				Randomizer random = new Randomizer();
				Types.Elements type = random.TypeRandomizer();
				Tile tile = new Tile(i,j,type);
				board[i][j] = tile;
			}
		}
	}
	public Tile[][] getBoard(){
		return board;
	}
	public synchronized void deleteTile(int[][] coordinates){
		for(int i=0;i<coordinates.length;i++){
			Tile tile = board[coordinates[i][0]][coordinates[i][1]];
			tile.setType();
		}
	}
}