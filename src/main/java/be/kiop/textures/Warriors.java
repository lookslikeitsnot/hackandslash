package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.characters.CharacterGender;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Genders;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Warriors implements Texture, MoveAnimation, CharacterGender, HitBoxTexture {
	Warrior_MALE_SOUTH_1("warriors", "Warrior", new Position(0, 0), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.SOUTH,
			1),
	Warrior_MALE_SOUTH_2("warriors", "Warrior", new Position(32, 0), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.SOUTH,
			2),
	Warrior_MALE_SOUTH_3("warriors", "Warrior", new Position(64, 0), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.SOUTH,
			3),

	Warrior_MALE_WEST_1("warriors", "Warrior", new Position(0, 48), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.WEST, 1),
	Warrior_MALE_WEST_2("warriors", "Warrior", new Position(32, 48), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.WEST,
			2),
	Warrior_MALE_WEST_3("warriors", "Warrior", new Position(64, 48), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.WEST,
			3),

	Warrior_MALE_EAST_1("warriors", "Warrior", new Position(0, 96), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.EAST, 1),
	Warrior_MALE_EAST_2("warriors", "Warrior", new Position(32, 96), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.EAST,
			2),
	Warrior_MALE_EAST_3("warriors", "Warrior", new Position(64, 96), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.EAST,
			3),

	Warrior_MALE_NORTH_1("warriors", "Warrior", new Position(0, 144), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.NORTH,
			1),
	Warrior_MALE_NORTH_2("warriors", "Warrior", new Position(32, 144), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.NORTH,
			2),
	Warrior_MALE_NORTH_3("warriors", "Warrior", new Position(64, 144), new Size(32, 48), new Size(18, 36), Genders.MALE, Directions.NORTH,
			3),

	Warrior_FEMALE_SOUTH_1("warriors", "Warrior", new Position(96, 0), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.SOUTH, 1),
	Warrior_FEMALE_SOUTH_2("warriors", "Warrior", new Position(128, 0), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.SOUTH, 2),
	Warrior_FEMALE_SOUTH_3("warriors", "Warrior", new Position(160, 0), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.SOUTH, 3),

	Warrior_FEMALE_WEST_1("warriors", "Warrior", new Position(96, 48), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.WEST, 1),
	Warrior_FEMALE_WEST_2("warriors", "Warrior", new Position(128, 48), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.WEST, 2),
	Warrior_FEMALE_WEST_3("warriors", "Warrior", new Position(160, 48), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.WEST, 3),

	Warrior_FEMALE_EAST_1("warriors", "Warrior", new Position(96, 96), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.EAST, 1),
	Warrior_FEMALE_EAST_2("warriors", "Warrior", new Position(128, 96), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.EAST, 2),
	Warrior_FEMALE_EAST_3("warriors", "Warrior", new Position(160, 96), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.EAST, 3),

	Warrior_FEMALE_NORTH_1("warriors", "Warrior", new Position(96, 144), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.NORTH, 1),
	Warrior_FEMALE_NORTH_2("warriors", "Warrior", new Position(128, 144), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.NORTH, 2),
	Warrior_FEMALE_NORTH_3("warriors", "Warrior", new Position(160, 144), new Size(32, 48), new Size(18, 36), Genders.FEMALE,
			Directions.NORTH, 3);

	private String name;
	private Size size;
	private Size hitBoxSize;
	private Genders gender;
	private Directions direction;
	private int movementFrame;
	private BufferedImage skin;

	Warriors(String path, String name, Position position, Size size, Size hitBoxSize, Genders gender, Directions direction, int frame) {
		this.name = name;
		this.size = size;
		this.hitBoxSize = hitBoxSize;
		this.gender = gender;
		this.direction = direction;
		this.movementFrame = frame;

		try {
			BufferedImage sprites = ImageIO
					.read(Paths.get("src/main/resources/images/heroes/warriors/" + path + ".png").toFile());
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
	public Genders getGender() {
		return gender;
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
	
	@Override
	public Size getHitBoxSize() {
		return hitBoxSize;
	}

}