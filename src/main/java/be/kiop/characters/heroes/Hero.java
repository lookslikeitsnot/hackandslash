package be.kiop.characters.heroes;

import java.nio.file.Path;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public abstract class Hero extends GameCharacter{
	private int lives;
	private float experience;

//	public Hero(String name, float health, Weapon weapon) {
//		super(name, health, weapon);
//		this.lives = 3;
//		this.experience = 0;
//	}
//	
//	public Hero(String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level, float armor) {
//		super(name, health, weapon, availableWeapons, level, armor);
//		this.lives = 3;
//		this.experience = 0;
//	}

	public Hero(Path skinPath, String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level, float armor, int lives, float experience) {
		super(skinPath, name, health, weapon, availableWeapons, level, armor);
		this.lives = lives;
		this.experience = experience;
	}

//	public Hero(String name, float health, Weapon weapon, Set<Weapons> availableWeapons) {
//		super(name, health, weapon, availableWeapons);
//		this.lives = 3;
//		this.experience = 0;
//	}

	public void decreaseLives() {
		lives--;
		if(lives <= 0) {
			lives = 0;
			throw new OutOfLivesException();
		}
	}
	
	public void increaseLives() {
		lives++;
	}

	public int getLives() {
		return lives;
	}

	public float getExperience() {
		return experience;
	}
	
	public void increaseExperience(float increment) {
		if(increment < 0) {
			throw new IllegalArgumentException();
		}
		setExperience(this.experience + increment);
	}

	private void setExperience(float experience) {
		this.experience = experience;
		while(this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}
	
	private float getRequiredExpForNextLevel() {
		return this.level*100;
	}
}
