package be.kiop.valueobjects;

import java.awt.Dimension;

import be.kiop.exceptions.IllegalFactorException;
import be.kiop.exceptions.IllegalSizeException;
import be.kiop.exceptions.NegativeHeightException;
import be.kiop.exceptions.NegativeWidthException;

public class Size {
	private int width;
	private int height;

	public Size(int width, int height) {
		if (width < 0){
			throw new NegativeWidthException();
		}
		if (height < 0){
			throw new NegativeHeightException();
		}
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
		if(size1 == null || size2 == null) {
			throw new IllegalSizeException();
		}
		return new Size(size1.width + size2.width, size1.height + size2.height);
	}
	
	public static Size product(Size size, int widthFactor, int heightFactor) {
		if(size == null) {
			throw new IllegalSizeException();
		}
		if(widthFactor < 0 || heightFactor < 0) {
			throw new IllegalFactorException();
		}
		return new Size(size.width*widthFactor, size.height*heightFactor);
	}
	
	public Position getCenter() {
		return new Position(width/2, height/2);
	}

	public Dimension toDimension() {
		return new Dimension(width, height);
	}
	
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
}
