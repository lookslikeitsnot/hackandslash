package be.kiop.characters;

import be.kiop.exceptions.CharacterDiedException;
import be.kiop.weapons.Weapon;

public abstract class GameCharacter {
	private final String name;
	private float health;
	private Weapon weapon;

	GameCharacter(String name, float health, Weapon weapon) {
		this.name = name;
		this.health = health;
		this.weapon = weapon;
	}

	public float getHealth() {
		return health;
	}

	public void loseHealth(float lostHealth) {
		if (lostHealth <= 0) {
			throw new IllegalArgumentException();
		}
		this.health -= lostHealth;
		if (health <= 0) {
			this.health = 0;
			throw new CharacterDiedException();
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}
}
