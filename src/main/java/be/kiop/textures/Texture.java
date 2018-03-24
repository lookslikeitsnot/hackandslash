package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public interface Texture {
	public BufferedImage getSkin();
	public Position getPosition();
	public Size getSize();
	public Path getPath();
}
