package be.kiop.characters.heroes.warriors;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.NegativeDamageException;
import be.kiop.exceptions.NegativeShieldException;
import be.kiop.textures.Texture;
import be.kiop.textures.WarriorTextures;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public class Warrior extends Hero {
	private static final float MAX_SHIELD = 100;
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WarriorTextures.values())
			.collect(Collectors.toSet());
	private static Set<WeaponTextures> AVAILABLE_WEAPONS = Set.of(WeaponTextures.Sword);

	private float shield;

	public Warrior(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			int lives, float shield) {
		super(AVAILABLE_TEXTURES, texture, tile, name, AVAILABLE_WEAPONS, health, weapon, level, armor, lives);
		setShield(shield);
	}

	public float getShield() {
		return shield;
	}

	private void setShield(float shield) {
		if(shield < 0) {
			throw new NegativeShieldException();
		}
		if(shield>MAX_SHIELD) {
			this.shield = MAX_SHIELD;
		} else {
			this.shield = shield;
		}
	}
	
	public void increaseShield(float shield) {
		if(shield<0) {
			throw new NegativeShieldException();
		}
		setShield(shield+this.shield);
	}

	@Override
	public void takeDamage(float damage) {
		if (damage < 0) {
			throw new NegativeDamageException();
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

	@Override
	public Drawable copy() {
		try {
			return (Warrior) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
