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
	Bone(Bone.class, "bone", new Position(0,0), new Size(64, 64)), 
	Fist(Fist.class, "fist", new Position(0,0), new Size(64, 64)), 
	Staff(Staff.class, "staff", new Position(0,0), new Size(64, 64)), 
	Sword(Sword.class, "sword", new Position(0,0), new Size(64, 64));

	private Class<?> weapon;
	private Path path;
	private Position position;
	private Size size;
	private BufferedImage skin;

	Weapons(Class<?> weapon, String path, Position position, Size size) {
		this.weapon = weapon;
		this.path = Paths.get("src/main/resources/images/weapons/" + path + ".png");
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
	public BufferedImage getSkin() {
//		try {
//		BufferedImage skin = ImageIO.read(path.toFile());
//		return skin.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
//	} catch (IOException e) {
//		throw new SkinNotFoundException();
//	}
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
}