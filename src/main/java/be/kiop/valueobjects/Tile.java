package be.kiop.valueobjects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
		return new Tile(horizontalPosition + 1, verticalPosition);
	}

	public Tile getSOUTHwardTile() {
		return new Tile(horizontalPosition, verticalPosition + 1);
	}

	public Tile getWESTwardTile() {
		return new Tile(horizontalPosition - 1, verticalPosition);
	}

	public Tile getNORTHwardTile() {
		return new Tile(horizontalPosition, verticalPosition - 1);
	}

	public Set<Tile> getAdjacentTiles() {
		return Set.of(getEASTwardTile(), getSOUTHwardTile(), getWESTwardTile(), getNORTHwardTile());
	}

	public Map<Directions, Tile> getAvailableAdjacentTiles(Set<Tile> availableTiles) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<Directions, Tile> map = new LinkedHashMap<>();
		Method getTileMethod;
		Tile tile;

		for (Directions direction : Directions.values()) {
			getTileMethod = this.getClass().getMethod("get" + direction.name() + "wardTile");
			tile = (Tile) getTileMethod.invoke(this);
			if(availableTiles.contains(tile)) {
				map.put(direction, tile);
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

}
