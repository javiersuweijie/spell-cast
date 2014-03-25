package game;
public class Tile{
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
	public String toString() {
		return this.type.toString();
	}
}
