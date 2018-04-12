package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Orientations;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum FloorTextures implements Texture, OrientedTexture {
	Floor_Parquet_HORIZONTAL("floors", "Floor_Parquet", new Position(0,0), new Size(32, 32), Orientations.HORIZONTAL),
	Floor_Parquet_VERTICAL("floors", "Floor_Parquet", new Position(32,0), new Size(32, 32), Orientations.VERTICAL),
	Floor_Stone_Light_Brown_NONE("floors", "Floor_Stone_Light_Brown", new Position(0,64), new Size(32, 32), Orientations.NONE),
	Floor_Stone_Brown_NONE("floors", "Floor_Stone_Brown", new Position(64,96), new Size(32, 32), Orientations.NONE),
	Floor_Stone_Light_Grey_NONE("floors", "Floor_Stone_Light_Grey", new Position(32,64), new Size(32, 32), Orientations.NONE),
	Floor_Stone_Dark_Putple_NONE("floors", "Floor_Stone_Dark_Putple", new Position(64,64), new Size(32, 32), Orientations.NONE);

	private final String name;
	private final Size size;
	private final Orientations orientation;
	private final BufferedImage skin;

	FloorTextures(String path, String name, Position position, Size size, Orientations orientation) {
		this.name= name;
		this.size = size;
		this.orientation = orientation;
		try {
			BufferedImage sprites = ImageIO.read(Paths.get("src/main/resources/images/textures/floors/" + path + ".png").toFile());
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
