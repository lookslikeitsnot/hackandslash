package be.kiop.textures;

import be.kiop.valueobjects.Directions;

public interface MoveAnimation {
	public final int EAST = 1;
	public final int SOUTH = 2;
	public final int WEST = 3;
	public final int NORTH = 4;
	
	public int getMovementFrame();
	public Directions getDirection();
}
