package be.kiop.items;

import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;

public interface Drop {
	public Texture getTexture();
	
	public Position getPosition();
	public void setPosition(Position position);
	public Position getTextureCenter();
}
