package be.kiop.characters.heroes;

import java.nio.file.Path;
import java.util.Set;

import be.kiop.exceptions.OutOfManaException;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Mage extends Hero{
	private float mana;
	public static final float MAX_MANA = 100;
	private static Set<Weapons> AVAILABLE_WEAPONS = Set.of(Weapons.Staff);

	public Mage(Path skinPath, String name, float health, Weapon weapon, int level, float armor, int lives, float experience, float mana) {
		super(skinPath, name, health, weapon, AVAILABLE_WEAPONS, level, armor, lives, experience);
		this.mana = mana;
	}

//	public Mage(String name, float health, Weapon weapon, int level, float armor) {
//		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor);
//		this.mana = 100;
//	}
//
//	public Mage(String name, float health, Weapon weapon) {
//		super(name, health, weapon);
//		this.mana = 100;
//	}
//	
//	public Mage() {
//		super("Mage", 100, new Staff());
//		this.mana = 100;
//	}
//	
	public void decreaseMana(float decrement) {
		if(decrement < 0) {
			throw new IllegalArgumentException();
		}
		mana = decrement>mana ? 0 : mana-decrement;
		if(mana == 0) {
			throw new OutOfManaException();
		}
	}
	
	public void increaseMana(float increment) {
		if(increment < 0) {
			throw new IllegalArgumentException();
		}
		mana = increment+mana>MAX_MANA ? MAX_MANA : mana+increment;
	}
	
	public float getMana() {
		return mana;
	}
}
