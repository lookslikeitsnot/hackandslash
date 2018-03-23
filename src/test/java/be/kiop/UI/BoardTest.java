package be.kiop.UI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BoardTest {
	@Test
	public void board_nA_objectCreated() {
		assertNotNull(new Board());
	}
	@Test
	public void setWidth_validWidth_boardWidthChanged() {
		int newWidth = Board.getWidth() + 1;
		Board.setWidth(newWidth);
		assertEquals(newWidth, Board.getWidth());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWidth_invalidWidth_illegalArgumentException() {
		Board.setWidth(-1);
	}

	@Test
	public void setHeight_validHeight_boardHeightChanged() {
		int newHeight = Board.getHeight() + 1;
		Board.setHeight(newHeight);
		assertEquals(newHeight, Board.getHeight());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setHeight_invalidHeight_illegalArgumentException() {
		Board.setHeight(-1);
	}
}
