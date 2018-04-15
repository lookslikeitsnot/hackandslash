package be.kiop.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.exceptions.IllegalOffsetException;
import be.kiop.exceptions.IllegalPositionException;
import be.kiop.exceptions.IllegalTileException;
import be.kiop.exceptions.IllegalTileSizeException;
import be.kiop.exceptions.NegativeIncrementException;

public class PositionTest {
	private Position position;
	
	private final int POS_X = Board.MAX_SIZE.getWidth()/2;
	private final int POS_Y = Board.MAX_SIZE.getHeight()/2;
	
	@Before
	public void before() {
		position = new Position(POS_X,POS_Y);
	}
	
	@Test
	public void setX_between0AndMaxSizeWidth_setValue() {
		int newX = position.getX()-1;
		position.setX(newX);
		assertEquals(newX, position.getX());
	}
	
	@Test(expected=IllegalPositionException.class)
	public void setX_negativeValue_OutOfBoardException() {
		position.setX(-1);
	}
	
	@Test(expected=IllegalPositionException.class)
	public void setX_moreThanBoardWidth_OutOfBoardException() {
		position.setX(Board.MAX_SIZE.getWidth()+1);
	}
	
	@Test
	public void setY_between0AndMaxSizeHeight_setValue() {
		int newY = position.getY()-1;
		position.setY(newY);
		assertEquals(newY, position.getY());
	}
	
	@Test(expected=IllegalPositionException.class)
	public void setY_negativeValue_OutOfBoardException() {
		position.setY(-1);
	}
	
	@Test(expected=IllegalPositionException.class)
	public void setY_moreThanBoardWidth_OutOfBoardException() {
		position.setY(Board.MAX_SIZE.getHeight()+1);
	}
	
	@Test
	public void hashCode_positionsWithSameCoordinatesHaveSameHascode_equalHashCode() {
		Position pos1 = new Position(1,1);
		Position pos2 = new Position(1,1);
		assertEquals(pos1.hashCode(), pos2.hashCode());
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
	public void equals_differentYPosition_false() {
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
	
	@Test
	public void east_eastIsInBoard_eastPosition() {
		assertEquals(new Position(POS_X+1, POS_Y), position.east());
	}
	
	@Test
	public void south_southIsInBoard_southPosition() {
		assertEquals(new Position(POS_X, POS_Y+1), position.south());
	}
	
	@Test
	public void west_westIsInBoard_westPosition() {
		assertEquals(new Position(POS_X-1, POS_Y), position.west());
	}
	
	@Test
	public void north_northInOfBoard_northPosition() {
		assertEquals(new Position(POS_X, POS_Y-1), position.north());
	}
	
	@Test
	public void east_eastIsOutOfBoard_exception() {
		Position pos = new Position(Board.MAX_SIZE.getWidth(), Board.MAX_SIZE.getHeight());
		assertNull(pos.east());
	}
	
	@Test
	public void south_southIsOutOfBoard_exception() {
		Position pos = new Position(Board.MAX_SIZE.getWidth(), Board.MAX_SIZE.getHeight());
		assertNull(pos.south());
	}
	
	@Test
	public void west_westIsOutOfBoard_exception() {
		Position pos = Position.ORIGIN;
		assertNull(pos.west());
	}
	
	@Test
	public void north_northIsOutOfBoard_exception() {
		Position pos = Position.ORIGIN;
		assertNull(pos.north());
	}
	
	@Test
	public void add_reachPositionBetweenOriginAndMax_positionIncremented() {
		position.add(new Position(1,1));
		assertEquals(POS_X+1, position.getX());
		assertEquals(POS_Y+1, position.getY());
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_null_exception() {
		position.add((Position)null);
	}
	
	@Test
	public void add_originPosition_positionUnchanged() {
		position.add(Position.ORIGIN);
		assertEquals(POS_X, position.getX());
		assertEquals(POS_Y, position.getY());
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_reachMoreThanMaxWidth_exception() {
		position.add(new Position(Board.MAX_SIZE.getWidth(), 0));
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_reachMoreThanMaxHeight_exception() {
		position.add(new Position(0, Board.MAX_SIZE.getHeight()));
	}
	
	@Test
	public void add_valuesToReachPositionBetweenOriginAndMax_positionIncremented() {
		position.add(1,1);
		assertEquals(POS_X+1, position.getX());
		assertEquals(POS_Y+1, position.getY());
	}
	
	@Test
	public void add_0_positionUnchanged() {
		position.add(0,0);
		assertEquals(POS_X, position.getX());
		assertEquals(POS_Y, position.getY());
	}
	
	@Test(expected = NegativeIncrementException.class)
	public void add_negativeX_exception() {
		position.add(-1,1);
	}
	
	@Test(expected = NegativeIncrementException.class)
	public void add_negativeY_exception() {
		position.add(1,-1);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_valueToReachMoreThanMaxWidth_exception() {
		position.add(Board.MAX_SIZE.getWidth(), 0);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_valueToReachMoreThanMaxHeight_exception() {
		position.add(0, Board.MAX_SIZE.getHeight());
	}
	
	@Test
	public void add_offsetToReachPositionBetweenOriginAndMax_positionDecremented() {
		position.add(new Offset(1,1));
		assertEquals(POS_X-1, position.getX());
		assertEquals(POS_Y-1, position.getY());
	}
	
	@Test
	public void add_0Offset_positionUnchanged() {
		position.add(new Offset(0,0));
		assertEquals(POS_X, position.getX());
		assertEquals(POS_Y, position.getY());
	}
	
	@Test(expected = IllegalOffsetException.class)
	public void add_nullasOffset_exception() {
		position.add((Offset)null);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_offsetToReachMoreThanMaxWidth_exception() {
		position.add(new Offset(Board.MAX_SIZE.getWidth(), 0));
	}
	
	@Test(expected = IllegalPositionException.class)
	public void add_offsetToReachMoreThanMaxHeight_exception() {
		position.add(new Offset(0, Board.MAX_SIZE.getHeight()));
	}
	
	@Test
	public void sum_2PositionsWithSumInferiorToMaxBoardSize_newPosition() {
		Position pos1 = new Position(1, 1);
		Position pos2 = new Position(2, 2);
		Position sum = Position.ORIGIN;
		sum.add(pos1);
		sum.add(pos2);
		assertEquals(sum, Position.sum(pos1, pos2));
	}
	
	@Test(expected = IllegalPositionException.class)
	public void sum_2PositionsWithSumSuperiorToMaxBoardSize_exception() {
		Position pos = new Position(Board.MAX_SIZE.getWidth(), Board.MAX_SIZE.getHeight());
		Position.sum(pos, pos);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void sum_nullAsFirstPosition_exception() {
		Position.sum(null, position);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void sum_nullAsSecondPosition_exception() {
		Position.sum(position, null);
	}
	
	@Test
	public void substract_reachPosittionBetweenOriginAndMaxSize_positionDecremented() {
		position.substract(new Position(1,1));
		assertEquals(POS_X-1, position.getX());
		assertEquals(POS_Y-1, position.getY());
	}
	
	@Test
	public void substract_originPosition_positionUnchanged() {
		position.substract(Position.ORIGIN);
		assertEquals(POS_X, position.getX());
		assertEquals(POS_Y, position.getY());
	}
	
	@Test(expected = IllegalPositionException.class)
	public void substract_null_Exception() {
		position.substract(null);
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
	
	@Test(expected = IllegalPositionException.class)
	public void difference_nullAsFirstPosition_exception() {
		Position.difference(null, position);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void difference_nullAsSecondPosition_exception() {
		Position.difference(position, null);
	}
	
	@Test
	public void getAssociatedPosition_validValues_associatedValue() {
		Tile tile = new Tile(1,1);
		Size size = new Size(32,32);
		Position dif = Position.getAssociatedPosition(tile, size);
		assertEquals(new Position(32,32), dif);
	}
	
	@Test(expected = IllegalTileException.class)
	public void getAssociatedPosition_nullAsTile_exception() {
		Size size = new Size(32,32);
		Position.getAssociatedPosition(null, size);
	}
	
	@Test(expected = IllegalTileSizeException.class)
	public void getAssociatedPosition_nullAsSize_exception() {
		Tile tile = new Tile(1,1);
		Position.getAssociatedPosition(tile, null);
	}
	
//	@Test
//	public void getAssociatedPosition_validValuesAndOffset_associatedValue() {
//		Tile tile = new Tile(1,1);
//		Size size = new Size(32,32);
//		Offset offset = new Offset(1,2);
//		Position dif = Position.getAssociatedPosition(tile, size, offset);
//		assertEquals(new Position(33,34), dif);
//	}
	
	@Test
	public void getAbsolutePosition_positionInTileAndTileInBoardAndNonNullOffset_associatedPosition() {
		Tile tile = new Tile(1,1);
		Offset offset = new Offset(0,0);
		assertNotNull(Position.getAbsolutePosition(position, tile, offset));
	}
	
	@Test(expected = IllegalPositionException.class)
	public void getAbsolutePosition_nullAsPosition_exception() {
		Tile tile = new Tile(1,1);
		Offset offset = new Offset(0,0);
		assertNotNull(Position.getAbsolutePosition(null, tile, offset));
	}
	
	@Test(expected = IllegalTileException.class)
	public void getAbsolutePosition_nullAsTile_exception() {
		Offset offset = new Offset(0,0);
		assertNotNull(Position.getAbsolutePosition(position, null, offset));
	}
	
	@Test(expected = IllegalOffsetException.class)
	public void getAbsolutePosition_nullAsOffset_exception() {
		Tile tile = new Tile(1,1);
		assertNotNull(Position.getAbsolutePosition(position, tile, null));
	}
	
}
