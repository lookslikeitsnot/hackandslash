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

public enum Mages implements Texture, MoveAnimation, CharacterGender {
	Mage_FEMALE_SOUTH_1("mages", "Mage", new Position(0, 0), new Size(32, 32), Genders.FEMALE, Directions.SOUTH, 1),
	Mage_FEMALE_SOUTH_2("mages", "Mage", new Position(32, 0), new Size(32, 32), Genders.FEMALE, Directions.SOUTH, 2),
	Mage_FEMALE_SOUTH_3("mages", "Mage", new Position(64, 0), new Size(32, 32), Genders.FEMALE, Directions.SOUTH, 3),

	Mage_FEMALE_WEST_1("mages", "Mage", new Position(0, 32), new Size(32, 32), Genders.FEMALE, Directions.WEST, 1),
	Mage_FEMALE_WEST_2("mages", "Mage", new Position(32, 32), new Size(32, 32), Genders.FEMALE, Directions.WEST, 2),
	Mage_FEMALE_WEST_3("mages", "Mage", new Position(64, 32), new Size(32, 32), Genders.FEMALE, Directions.WEST, 3),

	Mage_FEMALE_EAST_1("mages", "Mage", new Position(0, 32), new Size(32, 32), Genders.FEMALE, Directions.EAST, 1),
	Mage_FEMALE_EAST_2("mages", "Mage", new Position(32, 32), new Size(32, 32), Genders.FEMALE, Directions.EAST, 2),
	Mage_FEMALE_EAST_3("mages", "Mage", new Position(64, 32), new Size(32, 32), Genders.FEMALE, Directions.EAST, 3),

	Mage_FEMALE_NORTH_1("mages", "Mage", new Position(0, 48), new Size(32, 32), Genders.FEMALE, Directions.NORTH, 1),
	Mage_FEMALE_NORTH_2("mages", "Mage", new Position(32, 48), new Size(32, 32), Genders.FEMALE, Directions.NORTH, 2),
	Mage_FEMALE_NORTH_3("mages", "Mage", new Position(64, 48), new Size(32, 32), Genders.FEMALE, Directions.NORTH, 3),

	Mage_MALE_SOUTH_1("mages", "Mage", new Position(96, 128), new Size(32, 32), Genders.MALE, Directions.SOUTH, 1),
	Mage_MALE_SOUTH_2("mages", "Mage", new Position(128, 128), new Size(32, 32), Genders.MALE, Directions.SOUTH, 2),
	Mage_MALE_SOUTH_3("mages", "Mage", new Position(160, 128), new Size(32, 32), Genders.MALE, Directions.SOUTH, 3),

	Mage_MALE_WEST_1("mages", "Mage", new Position(96, 160), new Size(32, 32), Genders.MALE, Directions.WEST, 1),
	Mage_MALE_WEST_2("mages", "Mage", new Position(128, 160), new Size(32, 32), Genders.MALE, Directions.WEST, 2),
	Mage_MALE_WEST_3("mages", "Mage", new Position(160, 160), new Size(32, 32), Genders.MALE, Directions.WEST, 3),

	Mage_MALE_EAST_1("mages", "Mage", new Position(96, 192), new Size(32, 32), Genders.MALE, Directions.EAST, 1),
	Mage_MALE_EAST_2("mages", "Mage", new Position(128, 192), new Size(32, 32), Genders.MALE, Directions.EAST, 2),
	Mage_MALE_EAST_3("mages", "Mage", new Position(160, 192), new Size(32, 32), Genders.MALE, Directions.EAST, 3),

	Mage_MALE_NORTH_1("mages", "Mage", new Position(96, 224), new Size(32, 32), Genders.MALE, Directions.NORTH, 1),
	Mage_MALE_NORTH_2("mages", "Mage", new Position(128, 224), new Size(32, 32), Genders.MALE, Directions.NORTH, 2),
	Mage_MALE_NORTH_3("mages", "Mage", new Position(160, 224), new Size(32, 32), Genders.MALE, Directions.NORTH, 3);

	private String name;
	private Size size;
	private Genders gender;
	private Directions direction;
	private int movementFrame;
	private BufferedImage skin;

	Mages(String path, String name, Position position, Size size, Genders gender, Directions direction, int frame) {
		this.name = name;
		this.size = size;
		this.gender = gender;
		this.direction = direction;
		this.movementFrame = frame;

		try {
			BufferedImage sprites = ImageIO
					.read(Paths.get("src/main/resources/images/heroes/mages/" + path + ".png").toFile());
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
}