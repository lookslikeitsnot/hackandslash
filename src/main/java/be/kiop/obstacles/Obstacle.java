package be.kiop.obstacles;

import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Tile;

public abstract class Obstacle extends Drawable {
	private final boolean destructible;
	
	public Obstacle(Set<Texture> availableTextures, Texture texture, Tile tile, boolean destructible) {
		super(availableTextures, texture, tile);
		this.destructible = destructible;
	}

	public boolean isDestructible() {
		return destructible;
	}
}
