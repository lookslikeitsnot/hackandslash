package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Warriors implements Texture {
	Warrior_Large("warrior", new Position(0, 0), new Size(64, 64)),
	Warrior_ShinyHelmet_Large("warrior-shiny-helmet", new Position(0, 0), new Size(64, 64));

	private Path path;
	private Position position;
	private Size size;

	Warriors(String path, Position position, Size size) {
		this.path = Paths.get("src/main/resources/images/heroes/warriors/" + path + ".png");
		this.position = position;
		this.size = size;
	}

	public BufferedImage getSkin() {
		try {
			BufferedImage skin = ImageIO.read(path.toFile());
			return skin.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
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