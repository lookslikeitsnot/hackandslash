package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Orientations;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum WallTextures implements Texture, OrientedTexture {
	Wall_Metallic("walls", "Wall_Metallic", new Position(0, 0), new Size(32, 32), Orientations.NONE),
	Wall_Mettalic_Dark("walls", "Wall_Metallic", new Position(64, 0), new Size(32, 32), Orientations.NONE),
	Wall_Stone("walls1", "Wall_Stone", new Position(32, 32), new Size(32, 48), Orientations.NONE);

	private String name;
	private Size size;
	private Orientations orientation;
	private BufferedImage skin;

	WallTextures(String path, String name, Position position, Size size, Orientations orientation) {
		this.name = name;
		this.size = size;
		this.orientation = orientation;
		try {
			BufferedImage sprites = ImageIO
					.read(Paths.get("src/main/resources/images/obstacles/walls/" + path + ".png").toFile());
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
	public Orientations getOrientation() {
		return orientation;
	}

	@Override
	public BufferedImage getSkin() {
		return skin;
	}
}