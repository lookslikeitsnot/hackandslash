package be.kiop.valueobjects;

public enum Directions {
	EAST(1),
	SOUTH(2),
	WEST(3),
	NORTH(4);
	
	private int dirToInt;
	Directions(int dirToInt){
		this.dirToInt = dirToInt;
	}
	
	public int getDirToInt() {
		return dirToInt;
	}
}
