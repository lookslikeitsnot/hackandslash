package be.kiop.UI;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	
	@Before
	public void before() {
		board = new Board(15,15);
	}
	
	@Test
	public void board_nA_objectCreated() {
		assertNotNull(board);
	}
//	@Test
//	public void setWidth_validWidth_boardWidthChanged() {
//		int newWidth = board.getWidth() + 1;
//		Board.setWidth(newWidth);
//		assertEquals(newWidth, board.getWidth());
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setWidth_invalidWidth_illegalArgumentException() {
//		Board.setWidth(-1);
//	}
//
//	@Test
//	public void setHeight_validHeight_boardHeightChanged() {
//		int newHeight = board.getHeight() + 1;
//		Board.setHeight(newHeight);
//		assertEquals(newHeight, board.getHeight());
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setHeight_invalidHeight_illegalArgumentException() {
//		Board.setHeight(-1);
//	}
}
