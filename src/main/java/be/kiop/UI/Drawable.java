package be.kiop.UI;

import java.util.HashSet;
import java.util.Set;

import be.kiop.events.TileEvent;
import be.kiop.exceptions.IllegalPositionException;
import be.kiop.exceptions.IllegalTextureSetException;
import be.kiop.exceptions.IllegalTileException;
import be.kiop.exceptions.InvalidTextureException;
import be.kiop.exceptions.OutOfTileException;
import be.kiop.listeners.TileListener;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Offset;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;

public abstract class Drawable implements Cloneable {
	private final Set<Texture> availableTextures;
	private final Offset offsetFromPosition;
	private final Set<TileListener> tileListeners = new HashSet<>();

	private Texture texture;
	private Position positionOfTextureCenterInTile;
	private Tile tile;

	

	public Drawable(Set<Texture> availableTextures, Texture texture, Tile tile) {
		if (availableTextures == null || availableTextures.isEmpty()) {
			throw new IllegalTextureSetException();
		}
		this.availableTextures = availableTextures;
		setTexture(texture);

		offsetFromPosition = new Offset(texture.getSize().getWidth() / 2, texture.getSize().getHeight() / 2);

		setTile(tile);

		setPositionOfTextureCenterInTile(new Position(tile.getSize().getWidth() / 2, tile.getSize().getHeight() / 2));

	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		if (texture == null || !availableTextures.contains(texture)) {
			throw new InvalidTextureException();
		}
		this.texture = texture;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		if (tile == null) {
			throw new IllegalTileException();
		}
		TileEvent event;
		synchronized (tileListeners) {
			event = new TileEvent(this.tile, tile);
			this.tile = tile;
		}
		if (event.oldTile != event.newTile) {
			broadcast(event);
		}
	}

	public Position getPositionOfTextureCenterInTile() {
		return positionOfTextureCenterInTile;
	}

	public void setPositionOfTextureCenterInTile(Position positionOfTextureCenterInTile) {
		if (positionOfTextureCenterInTile == null) {
			throw new IllegalPositionException();
		}
		if (positionOfTextureCenterInTile.getX() > tile.getSize().getWidth()
				|| positionOfTextureCenterInTile.getY() > tile.getSize().getHeight()) {
			throw new OutOfTileException();
		}
		this.positionOfTextureCenterInTile = positionOfTextureCenterInTile;
	}

	public void addTileListener(TileListener listener) {
		synchronized (tileListeners) {
			tileListeners.add(listener);
		}
	}

	public void removeTileListener(TileListener listener) {
		synchronized (tileListeners) {
			tileListeners.remove(listener);
		}
	}

	private void broadcast(TileEvent tileEvent) {
		Set<TileListener> snapshot;
		synchronized (tileListeners) {
			snapshot = new HashSet<>(tileListeners);
		}
		for (TileListener listener : snapshot) {
			listener.tileChanged(tileEvent);
		}
	}

	public Position getAbsolutePosition() {
		return Position.getAbsolutePosition(positionOfTextureCenterInTile, tile, offsetFromPosition);
	}

	public Position getAbsoluteCenterPosition() {
		return Position.getAbsolutePosition(positionOfTextureCenterInTile, tile);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((positionOfTextureCenterInTile == null) ? 0 : positionOfTextureCenterInTile.hashCode());
		result = prime * result + ((offsetFromPosition == null) ? 0 : offsetFromPosition.hashCode());
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
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
		Drawable other = (Drawable) obj;
		if (positionOfTextureCenterInTile == null) {
			if (other.positionOfTextureCenterInTile != null)
				return false;
		} else if (!positionOfTextureCenterInTile.equals(other.positionOfTextureCenterInTile))
			return false;
		if (offsetFromPosition == null) {
			if (other.offsetFromPosition != null)
				return false;
		} else if (!offsetFromPosition.equals(other.offsetFromPosition))
			return false;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		if (tile == null) {
			if (other.tile != null)
				return false;
		} else if (!tile.equals(other.tile))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
