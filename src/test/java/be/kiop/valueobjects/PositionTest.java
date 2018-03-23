package be.kiop.valueobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.exceptions.OutOfBoardException;

public class PositionTest {
	private Position position;
	
	@Before
	public void before() {
		position = new Position(Board.getWidth()/2,Board.getHeight()/2);
	}
	
	@Test
	public void setX_validValue_setValue() {
		int newX = position.getX()-1;
		position.setX(newX);
		assertEquals(newX, position.getX());
	}
	
	@Test(expected=OutOfBoardException.class)
	public void setX_negativeValue_OutOfBoardException() {
		position.setX(-1);
	}
	
	@Test(expected=OutOfBoardException.class)
	public void setX_moreThanBoardWidth_OutOfBoardException() {
		position.setX(Board.getWidth()+1);
	}
	
	@Test
	public void setY_validValue_setValue() {
		int newY = position.getY()-1;
		position.setY(newY);
		assertEquals(newY, position.getY());
	}
	
	@Test(expected=OutOfBoardException.class)
	public void setY_negativeValue_OutOfBoardException() {
		position.setY(-1);
	}
	
	@Test(expected=OutOfBoardException.class)
	public void setY_moreThanBoardWidth_OutOfBoardException() {
		position.setY(Board.getHeight()+1);
	}
}
