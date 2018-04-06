package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum HeartTextures implements Texture{
	Heart_Full("hearts2", "Heart_Full", new Position(0,0), new Size(13,10)),
	Heart_Empty("hearts2", "Heart_Full", new Position(14,0), new Size(13,10));
	
	private String name;
	private Size size;
	private BufferedImage skin;
	
	HeartTextures(String path, String name, Position position, Size size) {
		this.name= name;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO.read(Paths.get("src/main/resources/images/icons/" + path + ".png").toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Size getSize() {
		return size;
	}
	
	@Override
	public BufferedImage getSkin() {
		return skin;
	}

}
