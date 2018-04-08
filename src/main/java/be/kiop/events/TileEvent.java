package be.kiop.events;

import be.kiop.valueobjects.Tile;

public class TileEvent {
	public final Tile oldTile;
	public final Tile newTile;
	public TileEvent(Tile oldTile, Tile newTile) {
		this.oldTile = oldTile;
		this.newTile = newTile;
	}
}
