package be.kiop.characters;

import java.nio.file.Path;
import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public abstract class GameCharacter extends Drawable{
	private final String name;
	private float health;
	private Weapon weapon;
	private Set<Weapons> availableWeapons;
	protected int level;
	public final static int MAX_LEVEL = 100;
	public final static int MAX_ARMOR = 100;
	private float armor;

//	protected GameCharacter(String name, float health, Weapon weapon) {
//		this.name = name;
//		this.health = health;
//		this.availableWeapons = Set.of(Weapons.values());
//		setWeapon(weapon);
//		this.level = 1;
//		this.armor = 0;
//	}
//
//	protected GameCharacter(String name, float health, Weapon weapon, Set<Weapons> availableWeapons) {
//		this.name = name;
//		this.health = health;
//		this.availableWeapons = availableWeapons;
//		setWeapon(weapon);
//		this.level = 1;
//		this.armor = 0;
//	}
//
//	protected GameCharacter(String name, float health, Weapon weapon, int level, float armor) {
//		this.name = name;
//		this.health = health;
//		this.availableWeapons = Set.of(Weapons.values());
//		setWeapon(weapon);
//		this.level = level;
//		this.armor = armor;
//	}

	protected GameCharacter(Path skinPath, Position position, String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level,
			float armor) {
		super(skinPath, position);
		this.name = name;
		this.health = health;
		this.availableWeapons = availableWeapons;
		setWeapon(weapon);
		this.level = level;
		this.armor = armor;
	}

	public float getHealth() {
		return health;
	}

	private void decreaseHealth(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		setHealth(this.health - decrement);
	}

	private void increaseHealth(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setHealth(this.health + increment);
	}
	
	public void takeFlatDamage(float damage) {
		if (damage < 0) {
			throw new IllegalArgumentException();
		}
		decreaseHealth(damage);
	}
	
	public void takeDamage(float damage) {
		if (damage < 0) {
			throw new IllegalArgumentException();
		}		
		decreaseHealth(damage-armor*damage/100);
	}
	
	public void takeDamage(float damage, float penetration) {
		if (damage < 0 || penetration < 0) {
			throw new IllegalArgumentException();
		}
		float damageFactor = armor - penetration;
		if(damageFactor<0) {
			damageFactor = 0;
		}
		decreaseHealth(damage-damageFactor*damage/100);
	}
	
	public void heal(float health) {
		increaseHealth(health);
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

	public float getMaxHealth() {
		return this.level * 100;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void changeWeapon(Weapon weapon) {
		if (weapon == null) {
			throw new IllegalArgumentException();
		}
		setWeapon(weapon);
	}

	public void dropWeapon() {
		setWeapon(new Fist());
	}

	private void setWeapon(Weapon weapon) {
		if (!availableWeapons.stream().anyMatch(value -> value.getWeaponClass().equals(weapon.getClass()))
				&& !(weapon instanceof Fist)) {
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
		if (level >= MAX_LEVEL) {
			level = MAX_LEVEL;
			throw new MaxLevelReachedException();
		}
	}

	public void decreaseLevel() {
		this.level--;
		if (level <= 1) {
			level = 1;
			throw new MinLevelReachedException();
		}
	}

	public float getArmor() {
		return armor;
	}

	public void setArmor(float armor) {
		if (armor < 0) {
			this.armor = 0;
		} else if (armor > MAX_ARMOR) {
			this.armor = MAX_ARMOR;
		} else {
			this.armor = armor;
		}
	}
	
	public void attack(GameCharacter gameCharacter) {
		gameCharacter.takeDamage(this.weapon.getDamage(), this.weapon.getPenetration());
	}

}
