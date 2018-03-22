package be.kiop.characters;

import java.util.Set;

import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public abstract class GameCharacter {
	private final String name;
	private float health;
	private Weapon weapon;
	private Set<Weapons> availableWeapons;
	protected int level;
	private final static int MAX_LEVEL = 100;

	protected GameCharacter(String name, float health, Weapon weapon) {
		this.name = name;
		this.health = health;
		this.availableWeapons = Set.of(Weapons.values());
		setWeapon(weapon);
		this.level = 1;
	}
	
	protected GameCharacter(String name, float health, Weapon weapon, Set<Weapons> availableWeapons) {
		this.name = name;
		this.health = health;
		this.availableWeapons = availableWeapons;
		setWeapon(weapon);
		this.level = 1;
	}

	protected GameCharacter(String name, float health, Weapon weapon, int level) {
		this.name = name;
		this.health = health;
		this.availableWeapons = Set.of(Weapons.values());
		setWeapon(weapon);
		this.level = level;
	}

	protected GameCharacter(String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level) {
		this.name = name;
		this.health = health;
		this.availableWeapons = availableWeapons;
		setWeapon(weapon);
		this.level = level;
	}

	public float getHealth() {
		return health;
	}

	public void decreaseHealth(float decrement) {
		if (decrement <= 0) {
			throw new IllegalArgumentException();
		}
		setHealth(this.health - decrement);
	}
	
	public void increaseHealth(float increment) {
		if (increment <= 0) {
			throw new IllegalArgumentException();
		}
		setHealth(this.health + increment);
	}
	
	private void setHealth(float health) {
		this.health = health;
		if (this.health <= 0) {
			this.health = 0;
			throw new CharacterDiedException();
		} else if (this.health >= getMaxHealth()) {
			this.health = getMaxHealth();
		}
	}
	
	private float getMaxHealth() {
		return this.level*100;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		if(!availableWeapons.stream().anyMatch(value->value.getWeaponClass().equals(weapon.getClass()))) {
			throw new IllegalWeaponException();
		}
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void increaseLevel() {
		this.level++;
		if(level >= MAX_LEVEL) {
			level = MAX_LEVEL;
			throw new MaxLevelReachedException();
		}
	}
	
	public void decreaseLevel() {
		this.level--;
		if(level <= 1) {
			level = 1;
			throw new MinLevelReachedException();
		}
	}
}
