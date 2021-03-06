package be.kiop.valueobjects;

import be.kiop.UI.Board;
import be.kiop.exceptions.OutOfBoardException;

public class Position {
	private int x;
	private int y;
	// private int z;
	
	public final static Position ORIGIN = new Position(0,0);

	public Position(int x, int y) {
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if (x < 0 || x > Board.MAX_SIZE.getWidth()) {
			throw new OutOfBoardException();
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if (y < 0 || y > Board.MAX_SIZE.getHeight()) {
			throw new OutOfBoardException();
		}
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	public Position right() {
		try {
			return new Position(x + 1, y);
		} catch (OutOfBoardException ex) {
			return null;
		}
	}

	public Position down() {
		try {
			return new Position(x, y + 1);
		} catch (OutOfBoardException ex) {
			return null;
		}
	}

	public Position left() {
		try {
			return new Position(x - 1, y);
		} catch (OutOfBoardException ex) {
			return null;
		}
	}

	public Position up() {
		try {
			return new Position(x, y - 1);
		} catch (OutOfBoardException ex) {
			return null;
		}
	}

	public void add(Position position) {
		if(position == null) {
			throw new IllegalArgumentException();
		}
		this.x += position.getX();
		this.y += position.getY();
	}

	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public static Position sum(Position pos1, Position pos2) {
		int sumX = pos1.x + pos2.x;
		int sumY = pos1.y + pos2.y;
		return new Position(sumX, sumY);
	}

	public void substract(Position position) {
		if(position == null) {
			throw new IllegalArgumentException();
		}
		this.x -= position.getX();
		this.y -= position.getY();
	}

	public void substract(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	public static Position difference(Position pos1, Position pos2) {
		if(pos1 == null || pos2 == null) {
			throw new IllegalArgumentException();
		}
		int difX = pos1.x - pos2.x;
		int difY = pos1.y - pos2.y;
		return new Position(difX, difY);
	}

	public static Position getAssociatedPosition(Tile tile, Size tileSize) {
		if(tile == null || tileSize == null) {
			throw new IllegalArgumentException();
		}
		return new Position(tile.getHorizontalPosition() * tileSize.getWidth(),
				tile.getVerticalPosition() * tileSize.getHeight());
	}
	
	public static Position getAssociatedPosition(Tile tile, Size tileSize, int offsetX, int offsetY) {
		if(tile == null || tileSize == null) {
			throw new IllegalArgumentException();
		}
		return sum(getAssociatedPosition(tile, tileSize), new Position(offsetX, offsetY));
	}
}
