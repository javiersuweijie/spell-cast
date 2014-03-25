package game;

import java.util.Random;

public class Randomizer{
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

