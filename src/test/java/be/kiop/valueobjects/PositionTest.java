package be.kiop.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.exceptions.OutOfBoardException;

public class PositionTest {
	private Position position;
	
	@Before
	public void before() {
		position = new Position(Board.getSize(true).getWidth()/2,Board.getSize(true).getHeight()/2);
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
		position.setX(Board.getSize(true).getWidth()+1);
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
		position.setY(Board.getSize(true).getHeight()+1);
	}
	
	@Test
	public void equals_sameObject_true() {
		assertEquals(position, position);
	}
	
	@Test
	public void equals_samePosition_true() {
		assertEquals(new Position(Board.getSize(true).getWidth()/2,Board.getSize(true).getHeight()/2), position);
	}
	
	@Test
	public void equals_different7Position_false() {
		assertNotEquals(new Position(Board.getSize(true).getWidth()/2,0), position);
	}
	
	@Test
	public void equals_differentXPosition_false() {
		assertNotEquals(new Position(0,Board.getSize(true).getWidth()/2), position);
	}
	
	@Test
	public void equals_null_false() {
		assertFalse(position.equals(null));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equals_otherObjectType_false() {
		assertFalse(position.equals(new String()));
	}
	
	@Test
	public void hashCode_samePosition_equal() {
		assertEquals(new Position(Board.getSize(true).getWidth()/2,Board.getSize(true).getHeight()/2).hashCode(), position.hashCode());
	}
	
	@Test
	public void toString_nA_goodRepresentation() {
		assertEquals("Position [x=" + Board.getSize(true).getWidth()/2 + ", y=" + Board.getSize(true).getHeight()/2 + "]", position.toString());
	}
}
