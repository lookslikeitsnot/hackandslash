package be.kiop.valueobjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Directions {
	EAST,
	SOUTH,
	WEST,
	NORTH;
	
//	private int dirToInt;
//	Directions(int dirToInt){
//		this.dirToInt = dirToInt;
//	}
//	
//	public int getDirToInt() {
//		return dirToInt;
//	}
	private static final List<Directions> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	
	public static Directions getRandomDirection() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
