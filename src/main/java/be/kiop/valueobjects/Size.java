package be.kiop.valueobjects;

import java.awt.Dimension;

public class Size {
	private int width;
	private int height;
	
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Dimension toDimension() {
		return new Dimension(width, height);
	}
}
