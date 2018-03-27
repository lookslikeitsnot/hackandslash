package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Staff;
import be.kiop.weapons.Sword;

public enum Weapons implements Texture {
	Bone(Bone.class, "bones", "Bone", new Position(0, 64), new Size(32, 32)),
	Fist(Fist.class, "fist", "Fist", new Position(0, 0), new Size(32, 32)),
	Staff(Staff.class, "staffs", "Staff", new Position(0, 0), new Size(32, 32)),
	Sword(Sword.class, "swords", "Sword", new Position(0, 0), new Size(32, 32));

	private Class<?> weapon;
	private String name;
	private Path path;
	private Position position;
	private Size size;
	private BufferedImage skin;

	Weapons(Class<?> weapon, String path, String name, Position position, Size size) {
		this.weapon = weapon;
		this.path = Paths.get("src/main/resources/images/weapons/" + path + ".png");
		this.name = name;
		this.position = position;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO.read(this.path.toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	public Class<?> getWeaponClass() {
		return weapon;
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
	public BufferedImage getSkin() {
		return skin;
	}
}