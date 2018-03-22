package be.kiop.characters.heroes;

import java.util.Set;

import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Warrior extends Hero{
	private float shield;
	private static Set<Weapons> AVAILABLE_WEAPONS = Set.of(Weapons.Sword);

	public Warrior(String name, float health, Weapon weapon, int level, float armor, int lives, int experience, float shield) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor, lives, experience);
		this.shield = shield;
	}

	public Warrior(String name, float health, Weapon weapon, int level, float armor) {
		super(name, health, weapon, AVAILABLE_WEAPONS, level, armor);
		this.shield = 0;
	}

	public Warrior(String name, float health, Weapon weapon) {
		super(name, health, weapon);
		this.shield = 0;
	}
	
	public Warrior() {
		super("Warrior", 200, new Sword());
		this.shield = 0;
	}
	
	
}
