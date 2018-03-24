package be.kiop.characters.heroes.warriors;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.characters.heroes.Hero;
import be.kiop.textures.Texture;
import be.kiop.textures.Warriors;
import be.kiop.textures.Weapons;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;

public class Warrior extends Hero {
	private float shield;
	public static final float MAX_SHIELD = 100;
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(Warriors.values()).collect(Collectors.toSet());
	private static Set<Weapons> availableWeapons = Set.of(Weapons.Sword);

	public Warrior(Warriors warrior, Position position, String name, float health, Weapon weapon, int level, float armor, int lives, float experience,
			float shield) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(warrior);
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

	public float getShield() {
		return shield;
	}

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
