package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Mages implements Texture{
	Mage_Blue_Large("blue-mage.png", new Position(0, 0), new Size(64, 64)), 
	Mage_Red_Large("red-mage.png", new Position(0, 0), new Size(64, 64)),
	Mage_White_Large("white-mage.png", new Position(0, 0), new Size(64, 64));

	private Path path;
	private Position position;
	private Size size;
	private BufferedImage skin;

	Mages(String path, Position position, Size size) {
		this.path = Paths.get("src/main/resources/images/heroes/mages/" + path);
		this.position = position;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO.read(this.path.toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	public BufferedImage getSkin() {
		return skin;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public Size getSize() {
		return size;
	}
}