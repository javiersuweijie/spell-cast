package game;

public class Board {
	public static final int dimensions = 6;
	public static final int numberOfTypes = 6;
	private Tile[][] board;
	public Board(){
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
