package be.kiop.characters.heroes;

import java.util.Set;

import be.kiop.weapons.Staff;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Mage extends Hero{
	private static Set<Weapons> AVAILABLE_WEAPONS = Set.of(Weapons.Staff);

	public Mage(String name, float health, Weapon weapon, int level, float mana, int lives, int experience) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, mana, lives, experience);
	}

//	public Mage(String name, float health, Weapon weapon, int level, float mana, int lives) {
//		super(name, health, weapon , AVAILABLE_WEAPONS, level, mana, lives);
//	}
//
//	public Mage(String name, float health, Weapon weapon, int level, float mana) {
//		super(name, health, weapon, AVAILABLE_WEAPONS, level, mana);
//	}

	public Mage(String name, float health, Weapon weapon, int level) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level);
	}

	public Mage(String name, float health, Weapon weapon) {
		super(name, health, weapon);
	}
	
	public Mage() {
		super("Mage", 100, new Staff());
	}
	
}
