package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.characters.CharacterGender;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Genders;

public enum Warriors implements Texture, MoveAnimation, CharacterGender {
//	Warrior_Large("warrior", new Position(0, 0), new Size(64, 64)),
//	Warrior_Rectangular_2_1("warrior-rectangular", new Position(0, 0), new Size(32, 64)),
//	Warrior_Rectangular_2_2("warrior-rectangular", new Position(32, 0), new Size(32, 64)),
//	Warrior_Rectangular_2_3("warrior-rectangular", new Position(64, 0), new Size(32, 64)),
//	Warrior_Rectangular_2_4("warrior-rectangular", new Position(96, 0), new Size(32, 64)),
//	Warrior_Rectangular_2_5("warrior-rectangular", new Position(128, 0), new Size(32, 64)),
//	Warrior_Rectangular_2_6("warrior-rectangular", new Position(160, 0), new Size(32, 64)),
//	Warrior_Rectangular_4_1("warrior-rectangular", new Position(0, 64), new Size(32, 64)),
//	Warrior_Rectangular_4_2("warrior-rectangular", new Position(32, 64), new Size(32, 64)),
//	Warrior_Rectangular_4_3("warrior-rectangular", new Position(64, 64), new Size(32, 64)),
//	Warrior_Rectangular_4_4("warrior-rectangular", new Position(96, 64), new Size(32, 64)),
//	Warrior_Rectangular_4_5("warrior-rectangular", new Position(128, 64), new Size(32, 64)),
//	Warrior_Rectangular_4_6("warrior-rectangular", new Position(160, 64), new Size(32, 64)),
//	Warrior_Rectangular_1_1("warrior-rectangular", new Position(0, 128), new Size(32, 64)),
//	Warrior_Rectangular_1_2("warrior-rectangular", new Position(32, 128), new Size(32, 64)),
//	Warrior_Rectangular_1_3("warrior-rectangular", new Position(64, 128), new Size(32, 64)),
//	Warrior_Rectangular_1_4("warrior-rectangular", new Position(96, 128), new Size(32, 64)),
//	Warrior_Rectangular_1_5("warrior-rectangular", new Position(128, 128), new Size(32, 64)),
//	Warrior_Rectangular_1_6("warrior-rectangular", new Position(160, 128), new Size(32, 64)),
//	Warrior_Rectangular_3_1("warrior-rectangular", new Position(0, 192), new Size(32, 64)),
//	Warrior_Rectangular_3_2("warrior-rectangular", new Position(32, 192), new Size(32, 64)),
//	Warrior_Rectangular_3_3("warrior-rectangular", new Position(64, 192), new Size(32, 64)),
//	Warrior_Rectangular_3_4("warrior-rectangular", new Position(96, 192), new Size(32, 64)),
//	Warrior_Rectangular_3_5("warrior-rectangular", new Position(128, 192), new Size(32, 64)),
//	Warrior_Rectangular_3_6("warrior-rectangular", new Position(160, 192), new Size(32, 64)),
//	Warrior_Old_2_1("warrior-old", new Position(320, 128), new Size(32, 48)),
//	Warrior_Old_2_2("warrior-old", new Position(320, 128), new Size(32, 48)),
//	Warrior_Old_2_3("warrior-old", new Position(352, 128), new Size(32, 48)),
//	Warrior_Old_2_4("warrior-old", new Position(288, 128), new Size(32, 48)),
//	Warrior_Old_2_5("warrior-old", new Position(352, 128), new Size(32, 48)),
//	Warrior_Old_2_6("warrior-old", new Position(288, 128), new Size(32, 48)),
//	Warrior_Old_4_1("warrior-old", new Position(320, 224), new Size(32, 48)),
//	Warrior_Old_4_2("warrior-old", new Position(320, 224), new Size(32, 48)),
//	Warrior_Old_4_3("warrior-old", new Position(352, 224), new Size(32, 48)),
//	Warrior_Old_4_4("warrior-old", new Position(288, 224), new Size(32, 48)),
//	Warrior_Old_4_5("warrior-old", new Position(352, 224), new Size(32, 48)),
//	Warrior_Old_4_6("warrior-old", new Position(288, 224), new Size(32, 48)),
//	Warrior_Old_1_1("warrior-old", new Position(320, 160), new Size(32, 48)),
//	Warrior_Old_1_2("warrior-old", new Position(320, 160), new Size(32, 48)),
//	Warrior_Old_1_3("warrior-old", new Position(352, 160), new Size(32, 48)),
//	Warrior_Old_1_4("warrior-old", new Position(288, 160), new Size(32, 48)),
//	Warrior_Old_1_5("warrior-old", new Position(352, 160), new Size(32, 48)),
//	Warrior_Old_1_6("warrior-old", new Position(288, 192), new Size(32, 48)),
//	Warrior_Old_3_1("warrior-old", new Position(320, 192), new Size(32, 48)),
//	Warrior_Old_3_2("warrior-old", new Position(320, 192), new Size(32, 48)),
//	Warrior_Old_3_3("warrior-old", new Position(352, 192), new Size(32, 48)),
//	Warrior_Old_3_4("warrior-old", new Position(288, 192), new Size(32, 48)),
//	Warrior_Old_3_5("warrior-old", new Position(352, 192), new Size(32, 48)),
//	Warrior_Old_3_6("warrior-old", new Position(288, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_1("warrior-old", new Position(32, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_2("warrior-old", new Position(32, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_3("warrior-old", new Position(64, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_4("warrior-old", new Position(0, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_5("warrior-old", new Position(64, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_2_6("warrior-old", new Position(0, 128), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_1("warrior-old", new Position(32, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_2("warrior-old", new Position(32, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_3("warrior-old", new Position(64, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_4("warrior-old", new Position(0, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_5("warrior-old", new Position(64, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_4_6("warrior-old", new Position(0, 224), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_1("warrior-old", new Position(32, 160), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_2("warrior-old", new Position(32, 160), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_3("warrior-old", new Position(64, 160), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_4("warrior-old", new Position(0, 160), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_5("warrior-old", new Position(64, 160), new Size(32, 48)),
//	Warrior_Young_Long_Hair_1_6("warrior-old", new Position(0, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_1("warrior-old", new Position(32, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_2("warrior-old", new Position(32, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_3("warrior-old", new Position(64, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_4("warrior-old", new Position(0, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_5("warrior-old", new Position(64, 192), new Size(32, 48)),
//	Warrior_Young_Long_Hair_3_6("warrior-old", new Position(0, 192), new Size(32, 48)),
//	Warrior_Young_2_1("warrior-old", new Position(32, 0), new Size(32, 48)),
//	Warrior_Young_2_2("warrior-old", new Position(32, 0), new Size(32, 48)),
//	Warrior_Young_2_3("warrior-old", new Position(64, 0), new Size(32, 48)),
//	Warrior_Young_2_4("warrior-old", new Position(0, 0), new Size(32, 48)),
//	Warrior_Young_2_5("warrior-old", new Position(64, 0), new Size(32, 48)),
//	Warrior_Young_2_6("warrior-old", new Position(0, 0), new Size(32, 48)),
//	Warrior_Young_4_1("warrior-old", new Position(32, 144), new Size(32, 48)),
//	Warrior_Young_4_2("warrior-old", new Position(32, 144), new Size(32, 48)),
//	Warrior_Young_4_3("warrior-old", new Position(64, 144), new Size(32, 48)),
//	Warrior_Young_4_4("warrior-old", new Position(0, 144), new Size(32, 48)),
//	Warrior_Young_4_5("warrior-old", new Position(64, 144), new Size(32, 48)),
//	Warrior_Young_4_6("warrior-old", new Position(0, 144), new Size(32, 48)),
//	Warrior_Young_1_1("warrior-old", new Position(32, 48), new Size(32, 48)),
//	Warrior_Young_1_2("warrior-old", new Position(32, 48), new Size(32, 48)),
//	Warrior_Young_1_3("warrior-old", new Position(64, 48), new Size(32, 48)),
//	Warrior_Young_1_4("warrior-old", new Position(0, 48), new Size(32, 48)),
//	Warrior_Young_1_5("warrior-old", new Position(64, 48), new Size(32, 48)),
//	Warrior_Young_1_6("warrior-old", new Position(0, 48), new Size(32, 48)),
//	Warrior_Young_3_1("warrior-old", new Position(32, 96), new Size(32, 48)),
//	Warrior_Young_3_2("warrior-old", new Position(32, 96), new Size(32, 48)),
//	Warrior_Young_3_3("warrior-old", new Position(64, 96), new Size(32, 48)),
//	Warrior_Young_3_4("warrior-old", new Position(0, 96), new Size(32, 48)),
//	Warrior_Young_3_5("warrior-old", new Position(64, 96), new Size(32, 48)),
//	Warrior_Young_3_6("warrior-old", new Position(0, 96), new Size(32, 48)),
//	
//	Warrior_ShinyHelmet_Large("warrior-shiny-helmet", new Position(0, 0), new Size(64, 64)),

	Warrior_MALE_SOUTH_1("warriors", "Warrior", new Position(0, 0), new Size(32, 48), Genders.MALE, Directions.SOUTH, 1),
	Warrior_MALE_SOUTH_2("warriors", "Warrior", new Position(32, 0), new Size(32, 48), Genders.MALE, Directions.SOUTH, 2),
	Warrior_MALE_SOUTH_3("warriors", "Warrior", new Position(64, 0), new Size(32, 48), Genders.MALE, Directions.SOUTH, 3),
	
	Warrior_MALE_WEST_1("warriors", "Warrior", new Position(0, 48), new Size(32, 48), Genders.MALE, Directions.WEST, 1),
	Warrior_MALE_WEST_2("warriors", "Warrior", new Position(32, 48), new Size(32, 48), Genders.MALE, Directions.WEST, 2),
	Warrior_MALE_WEST_3("warriors", "Warrior", new Position(64, 48), new Size(32, 48), Genders.MALE, Directions.WEST, 3),
	
	Warrior_MALE_EAST_1("warriors", "Warrior", new Position(0, 96), new Size(32, 48), Genders.MALE, Directions.EAST, 1),
	Warrior_MALE_EAST_2("warriors", "Warrior", new Position(32, 96), new Size(32, 48), Genders.MALE, Directions.EAST, 2),
	Warrior_MALE_EAST_3("warriors", "Warrior", new Position(64, 96), new Size(32, 48), Genders.MALE, Directions.EAST, 3),
	
	Warrior_MALE_NORTH_1("warriors", "Warrior", new Position(0, 144), new Size(32, 48), Genders.MALE, Directions.NORTH, 1),
	Warrior_MALE_NORTH_2("warriors", "Warrior", new Position(32, 144), new Size(32, 48), Genders.MALE, Directions.NORTH, 2),
	Warrior_MALE_NORTH_3("warriors", "Warrior", new Position(64, 144), new Size(32, 48), Genders.MALE, Directions.NORTH, 3),

	Warrior_FEMALE_SOUTH_1("warriors", "Warrior", new Position(96, 0), new Size(32, 48), Genders.FEMALE, Directions.SOUTH, 1),
	Warrior_FEMALE_SOUTH_2("warriors", "Warrior", new Position(128, 0), new Size(32, 48), Genders.FEMALE, Directions.SOUTH, 2),
	Warrior_FEMALE_SOUTH_3("warriors", "Warrior", new Position(160, 0), new Size(32, 48), Genders.FEMALE, Directions.SOUTH, 3),
	
	Warrior_FEMALE_WEST_1("warriors", "Warrior", new Position(96, 48), new Size(32, 48), Genders.FEMALE, Directions.WEST, 1),
	Warrior_FEMALE_WEST_2("warriors", "Warrior", new Position(128, 48), new Size(32, 48), Genders.FEMALE, Directions.WEST, 2),
	Warrior_FEMALE_WEST_3("warriors", "Warrior", new Position(160, 48), new Size(32, 48), Genders.FEMALE, Directions.WEST, 3),
	
	Warrior_FEMALE_EAST_1("warriors", "Warrior", new Position(96, 96), new Size(32, 48), Genders.FEMALE, Directions.EAST, 1),
	Warrior_FEMALE_EAST_2("warriors", "Warrior", new Position(128, 96), new Size(32, 48), Genders.FEMALE, Directions.EAST, 2),
	Warrior_FEMALE_EAST_3("warriors", "Warrior", new Position(160, 96), new Size(32, 48), Genders.FEMALE, Directions.EAST, 3),
	
	Warrior_FEMALE_NORTH_1("warriors", "Warrior", new Position(96, 144), new Size(32, 48), Genders.FEMALE, Directions.NORTH, 1),
	Warrior_FEMALE_NORTH_2("warriors", "Warrior", new Position(128, 144), new Size(32, 48), Genders.FEMALE, Directions.NORTH, 2),
	Warrior_FEMALE_NORTH_3("warriors", "Warrior", new Position(160, 144), new Size(32, 48), Genders.FEMALE, Directions.NORTH, 3);
	
	private Path path;
	private String name;
	private Position position;
	private Size size;
	private Genders gender;
	private Directions direction;
	private int movementFrame;
	private BufferedImage skin;

	Warriors(String path, String name, Position position, Size size, Genders gender, Directions direction, int frame) {
		this.path = Paths.get("src/main/resources/images/heroes/warriors/" + path + ".png");
		this.name= name;
		this.position = position;
		this.size = size;
		this.gender = gender;
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