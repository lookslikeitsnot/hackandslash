package be.kiop.valueobjects;

public class Tile {
	private final int horizontalPosition;
	private final int verticalPosition;
	
	public static final Tile ORIGIN = new Tile(0,0); 

	public Tile(int horizontalPosition, int verticalPosition) {
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
	}

	public int getHorizontalPosition() {
		return horizontalPosition;
	}

	public int getVerticalPosition() {
		return verticalPosition;
	}
	
	public Tile getEastwardTile() {
		return new Tile(horizontalPosition + 1, verticalPosition);
	}

	public Tile getSouthwardTile() {
		return new Tile(horizontalPosition, verticalPosition + 1);
	}
	
	public Tile getWestwardTile() {
		return new Tile(horizontalPosition + 1, verticalPosition);
	}
	
	public Tile getNorthwardTile() {
		return new Tile(horizontalPosition, verticalPosition + 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + horizontalPosition;
		result = prime * result + verticalPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (horizontalPosition != other.horizontalPosition)
			return false;
		if (verticalPosition != other.verticalPosition)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tile [horizontalPosition=" + horizontalPosition + ", verticalPosition=" + verticalPosition + "]";
	}

	
}
