package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Fires implements Texture, IdleAnimation{
	Fire_1("fires", "Fire", new Position(0,0), new Size(32, 55), 1),
	Fire_2("fires", "Fire", new Position(32,0), new Size(32, 55), 2),
	Fire_3("fires", "Fire", new Position(64,0), new Size(32, 55), 3),
	
	Fire_Off_1("fire", "Fire_Off", new Position(0,0), new Size(32, 55), 1),
	Fire_Off_2("fire", "Fire_Off", new Position(32,0), new Size(32, 55), 2),
	Fire_Off_3("fire", "Fire_Off", new Position(64,0), new Size(32, 55), 3);

	private Path path;
	private String name;
	private Position position;
	private Size size;
	private int animationFrame;
	private BufferedImage skin;

	Fires(String path, String name, Position position, Size size, int animationFrame) {
		this.path = Paths.get("src/main/resources/images/textures/fires/" + path + ".png");
		this.name = name;
		this.position = position;
		this.size = size;
		this.animationFrame = animationFrame;
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
	@Override
	public int getAnimationFrame() {
		return animationFrame;
		
	}
	@Override
	public String getName() {
		return name;
	}


}
