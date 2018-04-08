package be.kiop.UI;

import java.util.HashSet;
import java.util.Set;

import be.kiop.events.TileEvent;
import be.kiop.exceptions.IllegalTextureSetException;
import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.exceptions.OutOfBoardException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.listeners.TileListener;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Offset;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;

public abstract class Drawable implements Cloneable {
	private final Set<TileListener> tileListeners = new HashSet<>();
	private final Set<Texture> availableTextures;
	private final Offset offsetFromPosition;
	
	private Texture texture;
	private Position positionOfTextureCenterInTile;
	private Tile tile;

	public static final int ANIMATION_LENGTH = 4;

	public Drawable(Set<Texture> availableTextures, Texture texture, Tile tile) {
		if (availableTextures == null || availableTextures.isEmpty()) {
			throw new IllegalTextureSetException();
		}
		this.availableTextures = availableTextures;
		setTexture(texture);
		
		offsetFromPosition = new Offset(texture.getSize().getWidth()/2, texture.getSize().getHeight()/2);
		
		setPositionOfTextureCenterInTile(new Position(Board.TILE_SIZE.getWidth()/2, Board.TILE_SIZE.getHeight()/2));
		
		setTile(tile);
	}

	public Set<Texture> getAvailableTextures() {
		return availableTextures;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		if (texture == null || !availableTextures.contains(texture)) {
			throw new SkinNotFoundException();
		}
		this.texture = texture;
	}

	public int getAssociatedFrameNumber(int frameCounter) {
		if (frameCounter > ANIMATION_LENGTH) {
			throw new NoMoveAnimationException();
		}
		switch (frameCounter) {
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		default:
			return 1;
		}
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		if (tile == null) {
			throw new IllegalArgumentException();
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

	public Offset getOffsetFromPosition() {
		return offsetFromPosition;
	}
	
	public Position getPositionOfTextureCenterInTile() {
		return positionOfTextureCenterInTile;
	}

	public void setPositionOfTextureCenterInTile(Position positionOfTextureCenterInTile) {
		if(positionOfTextureCenterInTile == null) {
			throw new OutOfBoardException();
		}
		this.positionOfTextureCenterInTile = positionOfTextureCenterInTile;
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

	public abstract Drawable copy();

	

//	public abstract void setNextTexture();

//	public String getTextureAbsoluteName() {
//		String name = texture.name().substring(0, texture.name().lastIndexOf(String.valueOf(getAnimationFrame()))-1);
//		return name.substring(0, name.lastIndexOf("_"));
//	}
//	
//	public int getAnimationDirection() {
//		String name = texture.name().substring(0, texture.name().lastIndexOf(String.valueOf(getAnimationFrame()))-1);
//		return Integer.parseInt(name.substring(name.lastIndexOf("_")+1));
//	}
//	
//	public int getAnimationFrame() {
//		String name = texture.name();
//		return Integer.parseInt(name.substring(name.lastIndexOf("_")+1));
//	}
}
