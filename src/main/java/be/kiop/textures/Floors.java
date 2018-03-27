package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Orientations;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Floors implements Texture, OrientedTexture {
	Floor_Parquet_HORIZONTAL("floors", "Floor_Parquet", new Position(0,0), new Size(32, 32), Orientations.HORIZONTAL),
	Floor_Parquet_VERTICAL("floors", "Floor_Parquet", new Position(32,0), new Size(32, 32), Orientations.VERTICAL);

	private Path path;
	private String name;
	private Position position;
	private Size size;
	private Orientations orientation;
	private BufferedImage skin;

	Floors(String path, String name, Position position, Size size, Orientations orientation) {
		this.path = Paths.get("src/main/resources/images/textures/floors/" + path + ".png");
		this.name= name;
		this.position = position;
		this.size = size;
		this.orientation = orientation;
		try {
			BufferedImage sprites = ImageIO.read(this.path.toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public String getName() {
		return name;
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
	public Orientations getOrientation() {
		return orientation;
	}

	@Override
	public BufferedImage getSkin() {
		return skin;
	}

}
