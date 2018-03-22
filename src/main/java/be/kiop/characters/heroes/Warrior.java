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
	
	public float getShield() {
		return shield;
	}
	
	@Override
	public void takeDamage(float damage) {
		if(shield>0) {
			float remainingShield = shield;
			remainingShield -= damage;
			if(remainingShield < 0) {
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
