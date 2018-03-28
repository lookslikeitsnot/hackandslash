package be.kiop.textures;

import java.awt.image.BufferedImage;

import be.kiop.valueobjects.Size;

public interface Texture {
	public BufferedImage getSkin();
	public Size getSize();
	public String getName();
}
