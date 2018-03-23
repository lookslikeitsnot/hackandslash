package be.kiop.characters.heroes;

import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.LostALifeException;
import be.kiop.exceptions.OutOfLivesException;

public abstract class Hero extends GameCharacter{
	private int lives;
	private float experience;
	public static final int MAX_LIVES = 100;

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

//	public Hero(Path skinPath, Position position, String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level, float armor, int lives, float experience) {
//		super(skinPath, position, name, health, weapon, availableWeapons, level, armor);
//		this.lives = lives;
//		this.experience = experience;
//	}

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

	public void setLives(int lives) {
		if(lives < 1 || lives > MAX_LIVES) {
			throw new IllegalArgumentException();
		}
		this.lives = lives;
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

	public void setExperience(float experience) {
		if(experience < 0) {
			throw new IllegalArgumentException();
		}
		this.experience = experience;
		while(this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}
	
	private float getRequiredExpForNextLevel() {
		return getLevel()*100;
	}
	
	@Override
	public void setHealth(float health) {
		try {
			super.setHealth(health);
		} catch (CharacterDiedException ex) {
			if(lives > 2) {
				lives--;
				setHealth(getMaxHealth());
				throw new LostALifeException();
			}
			else {
				throw ex;
			}
		}
	}
}
