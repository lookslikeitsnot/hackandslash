package be.kiop.UI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;

public abstract class Drawable {
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
