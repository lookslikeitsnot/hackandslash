package be.kiop.characters.heroes.mages;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.OutOfManaException;
import be.kiop.textures.MageTextures;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public class Mage extends Hero {
	private float mana;
	public static final float MAX_MANA = 100;
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(MageTextures.values()).collect(Collectors.toSet());
	private static Set<WeaponTextures> availableWeapons = Set.of(WeaponTextures.Staff);

	public Mage(MageTextures mage, Position position, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			int lives, float experience, float mana) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(mage);
		super.setPosition(position);
		super.setTile(tile);
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

	@Override
	public Drawable copy() {
		try {
			return (Mage) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
