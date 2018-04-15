package be.kiop.valueobjects;

import be.kiop.exceptions.NegativeOffsetException;

public class Offset {
	private final int offsetX;
	private final int offsetY;

	public Offset(int offsetX, int offsetY) {
		if (offsetX < 0 || offsetY < 0) {
			throw new NegativeOffsetException();
		}
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public Offset(Size size) {
		offsetX = size.getWidth() / 2;
		offsetY = size.getHeight() / 2;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + offsetX;
		result = prime * result + offsetY;
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
		Offset other = (Offset) obj;
		if (offsetX != other.offsetX)
			return false;
		if (offsetY != other.offsetY)
			return false;
		return true;
	}

}
