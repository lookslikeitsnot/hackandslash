package be.kiop.characters.heroes;

import java.util.Set;

import be.kiop.weapons.Staff;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Warrior extends Hero{
	private static Set<Weapons> AVAILABLE_WEAPONS = Set.of(Weapons.Sword);

	public Warrior(String name, float health, Weapon weapon, int level, float mana, int lives, int experience) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, mana, lives, experience);
	}

	public Warrior(String name, float health, Weapon weapon, int level) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level);
	}

	public Warrior(String name, float health, Weapon weapon) {
		super(name, health, weapon);
	}
	
	public Warrior() {
		super("Warrior", 200, new Sword());
	}
}
