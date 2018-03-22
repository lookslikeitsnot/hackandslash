package be.kiop.characters.heroes;

import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.exceptions.OutOfManaException;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Hero extends GameCharacter{
	private float mana;
	private int lives;
	private float experience;

	public Hero(String name, float health, Weapon weapon) {
		super(name, health, weapon);
		this.mana = 0;
		this.lives = 3;
		this.experience = 0;
	}
	
	public Hero(String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level) {
		super(name, health, weapon, availableWeapons, level);
		this.mana = 0;
		this.lives = 3;
		this.experience = 0;
	}
	
//	public Hero(String name, float health, Weapon weapon, int level, float mana) {
//		super(name, health, weapon, level);
//		this.mana = mana;
//		this.lives = 1;
//		this.experience = 0;
//	}
//	
//	public Hero(String name, float health, Weapon weapon, int level, float mana, int lives) {
//		super(name, health, weapon, level);
//		this.mana = mana;
//		this.lives = lives;
//		this.experience = 0;
//	}
//	
//	public Hero(String name, float health, Weapon weapon, int level, float mana, int lives, int experience) {
//		super(name, health, weapon, level);
//		this.mana = mana;
//		this.lives = lives;
//		this.experience = experience;
//	}

	public Hero(String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level, float mana, int lives, float experience) {
		super(name, health, weapon, availableWeapons, level);
		this.mana = mana;
		this.lives = lives;
		this.experience = experience;
	}

	public Hero(String name, float health, Weapon weapon, Set<Weapons> availableWeapons) {
		super(name, health, weapon, availableWeapons);
		this.mana = 0;
		this.lives = 3;
		this.experience = 0;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		if(mana <= 0) {
			this.mana = 0;
			throw new OutOfManaException();
		}
		this.mana = mana;
	}
	
	public void decrementLives() {
		lives--;
		if(lives <= 0) {
			lives = 0;
			throw new OutOfLivesException();
		}
	}

	public int getLives() {
		return lives;
	}

	public float getExperience() {
		return experience;
	}
	
	public void increaseExperience(float increment) {
		if(increment <= 0) {
			throw new IllegalArgumentException();
		}
		setExperience(this.experience + increment);
	}

	private void setExperience(float experience) {
		this.experience = experience;
		if(this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}
	
	private float getRequiredExpForNextLevel() {
		return this.level*100;
	}
}
