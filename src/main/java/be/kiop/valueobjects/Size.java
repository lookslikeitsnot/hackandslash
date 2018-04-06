package be.kiop.valueobjects;

import java.awt.Dimension;

public class Size {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Size other = (Size) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

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

	public static Size sum(Size size1, Size size2) {
		return new Size(size1.width + size2.width, size1.height + size2.height);
	}
	
	public static Size product(Size size, int widthFactor, int heightFactor) {
		return new Size(size.width*widthFactor, size.height*heightFactor);
	}

	public Dimension toDimension() {
		return new Dimension(width, height);
	}
}
