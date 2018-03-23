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
	public void setWidth_validWidth_boardWidthCChanged() {
		int newWidth = Board.getWidth() + 1;
		Board.setWidth(newWidth);
		assertEquals(newWidth, Board.getWidth());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWidth_invalidWidth_boardWidthCChanged() {
		Board.setWidth(-1);
	}

	@Test
	public void setHeight_validHeight_boardHeightCChanged() {
		int newHeight = Board.getHeight() + 1;
		Board.setHeight(newHeight);
		assertEquals(newHeight, Board.getHeight());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setHeight_invalidHeight_boardHeightCChanged() {
		Board.setHeight(-1);
	}
}
