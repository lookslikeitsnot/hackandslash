package be.kiop.UI;

public class Board {
	private static int width = 100;
	private static int height = 100;
	
//	public Board(int width, int height) {
//		Board.width = width;
//		Board.height = height;
//	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		if (width < 0) {
			throw new IllegalArgumentException();
		}
		Board.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		if (height < 0) {
			throw new IllegalArgumentException();
		}
		Board.height = height;
	}
}
