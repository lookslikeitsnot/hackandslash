package be.kiop.UI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;

public abstract class Drawable implements Cloneable{
	public final static Path VALID_SKIN = Paths.get("src/main/resources/images/test.png");
	
	private Texture texture;
	private Set<Texture> availableTextures;
	private Position position;
	
	public static final int ANIMATION_LENGTH = 4;

	public Position getPosition() {
		return position;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		if(texture == null || !availableTextures.contains(texture)) {
			throw new SkinNotFoundException();
		}
		this.texture = texture;
	}
	
	public void setPosition(Position position) {
		if(position == null) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	public Set<Texture> getAvailableTextures() {
		return availableTextures;
	}

	public void setAvailableTextures(Set<Texture> availableTextures) {
		if(availableTextures == null || availableTextures.isEmpty())
		{
		throw new IllegalArgumentException();
		}
		this.availableTextures = availableTextures;
	}
	
	public int getAssociatedFrameNumber(int frameCounter) {
		if (frameCounter>ANIMATION_LENGTH) {
			throw new NoMoveAnimationException();
		}
		switch(frameCounter) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Drawable))
			return false;
		Drawable other = (Drawable) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		return true;
	}
	
	public Position getCenter() {
		return new Position(position.getX() + texture.getSize().getWidth() / 2,
				position.getY() + texture.getSize().getHeight() / 2);
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
