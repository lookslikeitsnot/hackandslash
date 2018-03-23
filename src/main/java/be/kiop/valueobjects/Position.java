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
}
