package be.kiop.characters.heroes.warriors;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.characters.heroes.Hero;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Warrior extends Hero {
	private float shield;
	public static final float MAX_SHIELD = 100;
	private final static Set<Path> AVAILABLE_SKIN_PATHS = Arrays.stream(Warriors.values())
			.map(skeleton -> skeleton.getPath()).collect(Collectors.toSet());
	private static Set<Weapons> availableWeapons = Set.of(Weapons.Sword);

	public Warrior(Path skinPath, Position position, String name, float health, Weapon weapon, int level, float armor, int lives, float experience,
			float shield) {
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
		this.shield = shield;
	}

//	public Warrior(String name, float health, Weapon weapon, int level, float armor) {
//		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor);
//		this.shield = 0;
//	}
//
//	public Warrior(String name, float health, Weapon weapon) {
//		super(name, health, weapon);
//		this.shield = 0;
//	}
//
//	public Warrior() {
//		super("Warrior", 200, new Sword());
//		this.shield = 0;
//	}

	public float getShield() {
		return shield;
	}

//	private void setShield(float shield) {
//		if (shield < 0) {
//			throw new IllegalArgumentException();
//		} else {
//			this.shield = shield > MAX_SHIELD ? MAX_SHIELD : shield;
//		}
//	}

	@Override
	public void takeDamage(float damage) {
		if(damage < 0) {
			throw new IllegalArgumentException();
		}
		if (shield > 0) {
			float remainingShield = shield;
			remainingShield -= damage;
			if (remainingShield < 0) {
				damage -= shield;
				shield = 0;
			} else {
				shield = remainingShield;
				damage = 0;
			}
		}
		super.takeDamage(damage);
	}
}
