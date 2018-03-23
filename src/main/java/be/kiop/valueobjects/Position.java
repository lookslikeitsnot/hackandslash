package be.kiop.valueobjects;

import be.kiop.UI.Board;
import be.kiop.exceptions.OutOfBoardException;

public class Position {
	private int x;
	private int y;
	//private int z;
	
	public Position(int x, int y) {
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x < 0 || x > Board.getWidth()) {
			throw new OutOfBoardException();
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y < 0 || y > Board.getHeight()) {
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
	
	
}
