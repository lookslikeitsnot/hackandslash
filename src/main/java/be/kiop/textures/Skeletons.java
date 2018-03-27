package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Skeletons implements Texture, MoveAnimation {
	Skeleton_SOUTH_1("skeletons", "Skeleton", new Position(0, 0), new Size(48, 48), Directions.SOUTH, 1),
	Skeleton_SOUTH_2("skeletons", "Skeleton", new Position(48, 0), new Size(48, 48), Directions.SOUTH, 2),
	Skeleton_SOUTH_3("skeletons", "Skeleton", new Position(96, 0), new Size(48, 48), Directions.SOUTH, 3),

	Skeleton_WEST_1("skeletons", "Skeleton", new Position(0, 48), new Size(48, 48), Directions.WEST, 1),
	Skeleton_WEST_2("skeletons", "Skeleton", new Position(48, 48), new Size(48, 48), Directions.WEST, 2),
	Skeleton_WEST_3("skeletons", "Skeleton", new Position(96, 48), new Size(48, 48), Directions.WEST, 3),

	Skeleton_EAST_1("skeletons", "Skeleton", new Position(0, 96), new Size(48, 48), Directions.EAST, 1),
	Skeleton_EAST_2("skeletons", "Skeleton", new Position(48, 96), new Size(48, 48), Directions.EAST, 2),
	Skeleton_EAST_3("skeletons", "Skeleton", new Position(96, 96), new Size(48, 48), Directions.EAST, 3),

	Skeleton_NORTH_1("skeletons", "Skeleton", new Position(0, 144), new Size(48, 48), Directions.NORTH, 1),
	Skeleton_NORTH_2("skeletons", "Skeleton", new Position(48, 144), new Size(48, 48), Directions.NORTH, 2),
	Skeleton_NORTH_3("skeletons", "Skeleton", new Position(96, 144), new Size(48, 48), Directions.NORTH, 3),
	
	Skeleton_Dog_SOUTH_1("skeleton-dogs", "Skeleton_Dog", new Position(0, 0), new Size(48, 48), Directions.SOUTH, 1),
	Skeleton_Dog_SOUTH_2("skeleton-dogs", "Skeleton_Dog", new Position(48, 0), new Size(48, 48), Directions.SOUTH, 2),
	Skeleton_Dog_SOUTH_3("skeleton-dogs", "Skeleton_Dog", new Position(96, 0), new Size(48, 48), Directions.SOUTH, 3),
	
	Skeleton_Dog_WEST_1("skeleton-dogs", "Skeleton_Dog", new Position(0, 48), new Size(48, 48), Directions.WEST, 1),
	Skeleton_Dog_WEST_2("skeleton-dogs", "Skeleton_Dog", new Position(48, 48), new Size(48, 48), Directions.WEST, 2),
	Skeleton_Dog_WEST_3("skeleton-dogs", "Skeleton_Dog", new Position(96, 48), new Size(48, 48), Directions.WEST, 3),
	
	Skeleton_Dog_EAST_1("skeleton-dogs", "Skeleton_Dog", new Position(0, 96), new Size(48, 48), Directions.EAST, 1),
	Skeleton_Dog_EAST_2("skeleton-dogs", "Skeleton_Dog", new Position(48, 96), new Size(48, 48), Directions.EAST, 2),
	Skeleton_Dog_EAST_3("skeleton-dogs", "Skeleton_Dog", new Position(96, 96), new Size(48, 48), Directions.EAST, 3),
	
	Skeleton_Dog_NORTH_1("skeleton-dogs", "Skeleton_Dog", new Position(0, 144), new Size(48, 48), Directions.NORTH, 1),
	Skeleton_Dog_NORTH_2("skeleton-dogs", "Skeleton_Dog", new Position(48, 144), new Size(48, 48), Directions.NORTH, 2),
	Skeleton_Dog_NORTH_3("skeleton-dogs", "Skeleton_Dog", new Position(96, 144), new Size(48, 48), Directions.NORTH, 3);
	
	private Path path;
	private String name;
	private Position position;
	private Size size;
	private Directions direction;
	private int movementFrame;
	private BufferedImage skin;

	Skeletons(String path, String name, Position position, Size size, Directions direction, int frame) {
		this.path = Paths.get("src/main/resources/images/ennemies/skeletons/" + path + ".png");
		this.name= name;
		this.position = position;
		this.size = size;
		this.direction = direction;
		this.movementFrame = frame;
		
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
	public Directions getDirection() {
		return direction;
	}

	@Override
	public int getMovementFrame() {
		return movementFrame;
	}

	@Override
	public BufferedImage getSkin() {
		return skin;
	}
}
