package be.kiop.characters.heroes;

import java.util.Set;

import be.kiop.weapons.Staff;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Mage extends Hero{
	private float mana;
	private static Set<Weapons> AVAILABLE_WEAPONS = Set.of(Weapons.Staff);

	public Mage(String name, float health, Weapon weapon, int level, float armor, int lives, int experience, float mana) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor, lives, experience);
		this.mana = mana;
	}

	public Mage(String name, float health, Weapon weapon, int level, float armor) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor);
		this.mana = 100;
	}

	public Mage(String name, float health, Weapon weapon) {
		super(name, health, weapon);
		this.mana = 100;
	}
	
	public Mage() {
		super("Mage", 100, new Staff());
		this.mana = 100;
	}
	
	public float getMana() {
		return mana;
	}
}
