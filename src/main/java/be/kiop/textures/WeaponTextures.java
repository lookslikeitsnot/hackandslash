package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Staff;
import be.kiop.weapons.Sword;

public enum WeaponTextures implements Texture {
	Bone(Bone.class, "bones", "Bone", new Position(0, 64), new Size(32, 32)),
	Fist(Fist.class, "fist", "Fist", new Position(0, 0), new Size(32, 32)),
	Staff(Staff.class, "staffs", "Staff", new Position(0, 0), new Size(32, 32)),
	Sword(Sword.class, "swords_resized", "Sword", new Position(0, 0), new Size(32, 32));

	private final Class<?> weapon;
	private final String name;
	private final Size size;
	private final BufferedImage skin;

	WeaponTextures(Class<?> weapon, String path, String name, Position position, Size size) {
		this.weapon = weapon;
		this.name = name;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO
					.read(Paths.get("src/main/resources/images/weapons/" + path + ".png").toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	public Class<?> getWeaponClass() {
		return weapon;
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