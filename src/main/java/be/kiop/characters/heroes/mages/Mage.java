package be.kiop.characters.heroes.mages;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.OutOfManaException;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Mage extends Hero {
	private float mana;
	public static final float MAX_MANA = 100;
	private final static Set<Path> AVAILABLE_SKIN_PATHS = Arrays.stream(Mages.values())
			.map(skeleton -> skeleton.getPath()).collect(Collectors.toSet());
	private static Set<Weapons> availableWeapons = Set.of(Weapons.Staff);

	public Mage(Path skinPath, Position position, String name, float health, Weapon weapon, int level, float armor,
			int lives, float experience, float mana) {
		super.setAvailableSkinPaths(AVAILABLE_SKIN_PATHS);
		super.setSkinPath(skinPath);
		super.setPosition(position);
		super.setName(name);
		super.setLevel(level);
		super.setHealth(health);
		super.setAvailableWeapons(availableWeapons);
		super.setWeapon(weapon);
		super.setArmor(armor);
		super.setLives(lives);
		super.setExperience(experience);
		this.mana = mana;
	}

	// public Mage(String name, float health, Weapon weapon, int level, float armor)
	// {
	// super(name, health, weapon, AVAILABLE_WEAPONS, level, armor);
	// this.mana = 100;
	// }
	//
	// public Mage(String name, float health, Weapon weapon) {
	// super(name, health, weapon);
	// this.mana = 100;
	// }
	//
	// public Mage() {
	// super("Mage", 100, new Staff());
	// this.mana = 100;
	// }
	//
	public void decreaseMana(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		mana = decrement > mana ? 0 : mana - decrement;
		if (mana == 0) {
			throw new OutOfManaException();
		}
	}

	public void increaseMana(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		mana = increment + mana > MAX_MANA ? MAX_MANA : mana + increment;
	}

	public float getMana() {
		return mana;
	}
}
