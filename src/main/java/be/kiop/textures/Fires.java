package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Fires implements Texture{
	Fire_1("fire", new Position(0,0), new Size(32, 55)),
	Fire_2("fire", new Position(32,0), new Size(32, 55)),
	Fire_3("fire", new Position(64,0), new Size(32, 55)),
	Fire_4("fire", new Position(0,0), new Size(32, 55)),
	Fire_5("fire", new Position(32,0), new Size(32, 55)),
	Fire_6("fire", new Position(64,0), new Size(32, 55));

	private Path path;
	private Position position;
	private Size size;
	private BufferedImage skin;

	Fires(String path, Position position, Size size) {
		this.path = Paths.get("src/main/resources/images/textures/fires/" + path + ".png");
		this.position = position;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO.read(this.path.toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}
	@Override
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
