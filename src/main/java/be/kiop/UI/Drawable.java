package be.kiop.UI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;

public abstract class Drawable {
	public final static Path VALID_SKIN = Paths.get("src/main/resources/images/test.png");
	
	private Texture texture;
	private Set<Texture> availableTextures;
	private Position position;

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
	
	public String getTextureAbsoluteName() {
		String name = texture.name();
		return name.substring(0, name.lastIndexOf("_"));
	}
}
