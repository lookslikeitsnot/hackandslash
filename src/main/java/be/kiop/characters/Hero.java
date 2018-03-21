package be.kiop.characters;

import be.kiop.exceptions.OutOfLivesException;
import be.kiop.exceptions.OutOfManaException;
import be.kiop.weapons.Weapon;

public class Hero extends GameCharacter{
	private float mana;
	private int lives;

	public Hero(String name, float health, Weapon weapon, float mana) {
		super(name, health, weapon);
		this.mana = mana;
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
}
