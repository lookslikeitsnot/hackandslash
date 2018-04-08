package be.kiop.items;

import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;

public interface Drop {
	public Texture getTexture();
	
	public Position getAbsolutePosition();
	public void setTile(Tile tile);
	public Position getTextureCenter();
}
