package be.kiop.weapons;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Weapons {
	Bone(Bone.class, "bone"), Fist(Fist.class, "fist"), Staff(Staff.class, "staff"), Sword(Sword.class, "sword");

	private Class<?> weapon;
	private Path path;

	Weapons(Class<?> weapon, String path) {
		this.weapon = weapon;
		this.path = Paths.get("src/main/resources/images/weapons/" + path + ".png");
	}

	public Class<?> getWeaponClass() {
		return weapon;
	}
	
	public Path getPath() {
		return path;
	}
}
