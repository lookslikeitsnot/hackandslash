package be.kiop.valueobjects;

public enum NextDirection {
	NORTH(Directions.WEST),
	WEST(Directions.SOUTH), 
	SOUTH(Directions.EAST), 
	EAST(Directions.NORTH);
	
	Directions nextDirection;
	NextDirection(Directions next) {
		nextDirection = next;
	}
	
	public Directions getNextDirection() {
		return nextDirection;
	}
}
