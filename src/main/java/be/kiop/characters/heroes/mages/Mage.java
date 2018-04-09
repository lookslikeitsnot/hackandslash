package be.kiop.characters.heroes.mages;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.NegativeManaException;
import be.kiop.exceptions.OutOfManaException;
import be.kiop.textures.MageTextures;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public class Mage extends Hero {
	public static final float MAX_MANA = 100;
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(MageTextures.values())
			.collect(Collectors.toSet());
	private final static Set<WeaponTextures> AVAILABLE_WEAPONS = Set.of(WeaponTextures.Staff);

	private float mana;

	public Mage(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor, int lives,
			float mana) {
		super(AVAILABLE_TEXTURES, texture, tile, name, AVAILABLE_WEAPONS, health, weapon, level, armor, lives);
		setMana(mana);
	}

	public float getMana() {
		return mana;
	}

	private void setMana(float mana) {
		if (mana < 0) {
			throw new NegativeManaException();
		} else if (mana == 0) {
			throw new OutOfManaException();
		}
		this.mana = mana>MAX_MANA?MAX_MANA:mana;
	}

	public void decreaseMana(float decrement) {
		if (decrement < 0) {
			throw new NegativeManaException();
		}
		setMana(mana - decrement);
	}

	public void increaseMana(float increment) {
		if (increment < 0) {
			throw new NegativeManaException();
		}
		setMana(mana + increment);
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
