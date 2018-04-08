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
	
	private final int POS_X = Board.MAX_SIZE.getWidth()/2;
	private final int POS_Y = Board.MAX_SIZE.getHeight()/2;
	
	@Before
	public void before() {
		position = new Position(POS_X,POS_Y);
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
		position.setX(Board.MAX_SIZE.getWidth()+1);
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
		position.setY(Board.MAX_SIZE.getHeight()+1);
	}
	
	@Test
	public void equals_sameObject_true() {
		assertEquals(position, position);
	}
	
	@Test
	public void equals_samePosition_true() {
		assertEquals(new Position(Board.MAX_SIZE.getWidth()/2,Board.MAX_SIZE.getHeight()/2), position);
	}
	
	@Test
	public void equals_different7Position_false() {
		assertNotEquals(new Position(Board.MAX_SIZE.getWidth()/2,0), position);
	}
	
	@Test
	public void equals_differentXPosition_false() {
		assertNotEquals(new Position(0,Board.MAX_SIZE.getWidth()/2), position);
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
		assertEquals(new Position(Board.MAX_SIZE.getWidth()/2,Board.MAX_SIZE.getHeight()/2).hashCode(), position.hashCode());
	}
	
	@Test
	public void toString_nA_goodRepresentation() {
		assertEquals("Position [x=" + Board.MAX_SIZE.getWidth()/2 + ", y=" + Board.MAX_SIZE.getHeight()/2 + "]", position.toString());
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void add_null_Exception() {
//		position.add(null);
//	}
	
	@Test
	public void add_validPosition_PositionIncremented() {
		position.add(new Position(1,1));
		assertEquals(POS_X+1, position.getX());
		assertEquals(POS_Y+1, position.getY());
	}
	
	@Test
	public void add_validValues_PositionIncremented() {
		position.add(1,1);
		assertEquals(POS_X+1, position.getX());
		assertEquals(POS_Y+1, position.getY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void substract_null_Exception() {
		position.substract(null);
	}
	
	@Test
	public void substract_validPosition_PositionIncremented() {
		position.substract(new Position(1,1));
		assertEquals(POS_X-1, position.getX());
		assertEquals(POS_Y-1, position.getY());
	}
	
	@Test
	public void substract_validValues_PositionIncremented() {
		position.substract(1,1);
		assertEquals(POS_X-1, position.getX());
		assertEquals(POS_Y-1, position.getY());
	}
	
	@Test
	public void difference_validValues_PositionSubstracted() {
		Position pos1 = new Position(3,3);
		Position pos2 = new Position(2,2);
		Position dif = Position.difference(pos1, pos2);
		assertEquals(new Position(1,1), dif);
	}
	
	@Test
	public void getAssociatedPosition_validValues_associatedValue() {
		Tile tile = new Tile(1,1);
		Size size = new Size(32,32);
		Position dif = Position.getAssociatedPosition(tile, size);
		assertEquals(new Position(32,32), dif);
	}
	
	@Test
	public void getAssociatedPosition_validValuesAndOffset_associatedValue() {
		Tile tile = new Tile(1,1);
		Size size = new Size(32,32);
		int offsetX = 1;
		int offsetY = 2;
		Position dif = Position.getAssociatedPosition(tile, size, offsetX, offsetY);
		assertEquals(new Position(33,34), dif);
	}
}
