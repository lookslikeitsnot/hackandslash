package be.kiop.valueobjects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import be.kiop.UI.Board;

public class Tile {
	private final int horizontalPosition;
	private final int verticalPosition;

	private final Size size;

	public static final Tile ORIGIN = new Tile(0, 0);

	public Tile(int horizontalPosition, int verticalPosition) {
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
		this.size = new Size(32, 48);
	}

	public int getHorizontalPosition() {
		return horizontalPosition;
	}

	public int getVerticalPosition() {
		return verticalPosition;
	}

	public Tile getEASTwardTile() {
		if (horizontalPosition < Board.getMaxHorizontalTiles()) {
			return new Tile(horizontalPosition + 1, verticalPosition);
		}
		return null;
	}

	public Tile getSOUTHwardTile() {
		if (verticalPosition < Board.getMaxVerticalTiles()) {
			return new Tile(horizontalPosition, verticalPosition + 1);
		}
		return null;
	}

	public Tile getWESTwardTile() {
		if (horizontalPosition > 0) {
			return new Tile(horizontalPosition - 1, verticalPosition);
		}
		return null;
	}

	public Tile getNORTHwardTile() {
		if (verticalPosition > 0) {
			return new Tile(horizontalPosition, verticalPosition - 1);
		}
		return null;
	}

	public Tile getSOUTHEASTwardTile() {
		if (horizontalPosition < Board.getMaxHorizontalTiles() && verticalPosition < Board.getMaxVerticalTiles()) {
			return new Tile(horizontalPosition + 1, verticalPosition + 1);
		}
		return null;
	}

	public Tile getSOUTHWESTwardTile() {
		if (horizontalPosition > 0 && verticalPosition < Board.getMaxVerticalTiles()) {
			return new Tile(horizontalPosition - 1, verticalPosition + 1);
		}
		return null;
	}

	public Tile getNORTHWESTwardTile() {
		if (horizontalPosition > 0 && verticalPosition > 0) {
			return new Tile(horizontalPosition - 1, verticalPosition - 1);
		}
		return null;
	}

	public Tile getNORTHEASTwardTile() {
		if (horizontalPosition < Board.getMaxHorizontalTiles() && verticalPosition > 0) {
			return new Tile(horizontalPosition + 1, verticalPosition - 1);
		}
		return null;
	}

	public Set<Tile> getAdjacentTiles() {
		return new LinkedHashSetNoNull<>(getEASTwardTile(), getSOUTHwardTile(), getWESTwardTile(),
				getNORTHwardTile(), getNORTHEASTwardTile(), getSOUTHEASTwardTile(), getSOUTHWESTwardTile(),
				getNORTHWESTwardTile());
	}

	public Map<Directions, Tile> getAvailableAdjacentTiles(Set<Tile> availableTiles) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<Directions, Tile> map = new LinkedHashMap<>();
		Method getTileMethod;
		Tile tile;

		for (Directions direction : Directions.values()) {
			getTileMethod = this.getClass().getMethod("get" + direction.name() + "wardTile");
			Object obj = getTileMethod.invoke(this);
			if (obj != null || obj instanceof Tile) {
				tile = (Tile) obj;
				if (availableTiles.contains(tile)) {
					map.put(direction, tile);
				}
			}

		}

		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + horizontalPosition;
		result = prime * result + verticalPosition;
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
		Tile other = (Tile) obj;
		if (horizontalPosition != other.horizontalPosition)
			return false;
		if (verticalPosition != other.verticalPosition)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tile [horizontalPosition=" + horizontalPosition + ", verticalPosition=" + verticalPosition + "]";
	}

	public Size getSize() {
		return size;
	}

	public Set<Position> getTileHitBox(Size maxSize) {
		Set<Position> tileHitBox = new LinkedHashSet<>();
		int minX = Board.exteriorWallSize.getWidth() + horizontalPosition * size.getWidth();
		int minY = Board.exteriorWallSize.getHeight() + verticalPosition * size.getHeight();

		int maxX = minX + size.getWidth() - 1;
		int maxY = minY + size.getHeight() - 1;

		minX = minX > 0 ? minX : 0;
		minY = minY > 0 ? minY : 0;

		maxX = maxX > maxSize.getWidth() ? maxSize.getWidth() : maxX;
		maxY = maxY > maxSize.getHeight() ? maxSize.getHeight() : maxY;

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				tileHitBox.add(new Position(x, y));
			}
		}

		return tileHitBox;
	}

}
