package be.kiop.characters.heroes;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kiop.characters.GameCharacter;
import be.kiop.events.LifeEvent;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.LessThanCurrentExperienceException;
import be.kiop.exceptions.LostALifeException;
import be.kiop.exceptions.NegativeExperienceException;
import be.kiop.exceptions.NegativeLifeException;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.listeners.LifeListener;
import be.kiop.textures.HitBoxTexture;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public abstract class Hero extends GameCharacter {
	private final Set<LifeListener> lifeListeners = new HashSet<>();
	
	private int lives;
	private float experience;
	
	public static final int MAX_LIVES = 5;
	
	public Hero(Set<Texture> availableTextures, Texture texture, Tile tile, String name,
			Set<WeaponTextures> availableWeapons, float health, Weapon weapon, int level, float armor, int lives) {
		super(availableTextures, texture, tile, name, availableWeapons, health, weapon, level, armor);
		setLives(lives);
		setExperience(0);
	}
	
	public int getLives() {
		synchronized (lifeListeners) {
			return lives;
		}
	}

	private void setLives(int lives) {
		LifeEvent lifeEvent;
		synchronized (lifeListeners) {
			lifeEvent = new LifeEvent(this.lives, lives);
			this.lives = lives;
			if (this.lives < 1) {
				this.lives = 0;
				throw new OutOfLivesException();
			} else if (this.lives > MAX_LIVES) {
				this.lives = MAX_LIVES;
			}
		}
		if (lifeEvent.oldLives != lifeEvent.newLives) {
			broadcast(lifeEvent);
		}
	}
	
	public float getExperience() {
		return experience;
	}
	
	private void setExperience(float experience) {
		if (experience < 0) {
			throw new NegativeExperienceException();
		} else if(experience < this.experience) {
			throw new LessThanCurrentExperienceException();
		}
		this.experience = experience;
		while (this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}
	
	public void decreaseLives() {
		setLives(getLives()-1);
	}

	public void increaseLives(int lives) {
		if(lives < 0) {
			throw new NegativeLifeException();
		}
		setLives(getLives()+lives);
	}

	public void increaseExperience(float increment) {
		setExperience(this.experience + increment);
	}
	
	private float getRequiredExpForNextLevel() {
		return getLevel() * 100;
	}

	@Override
	protected void setHealth(float health) {
		super.setHealth(health);
		if(getHealth() == 0) {
			if (lives > 1) {
				setLives(lives - 1);
				setHealth(getMaxHealth());
				throw new LostALifeException();
			} else {
				throw new CharacterDiedException();

			}
		}
	}

	public void addLifeListener(LifeListener listener) {
		synchronized (lifeListeners) {
			lifeListeners.add(listener);
		}
	}

	public void removeLifeListener(LifeListener listener) {
		synchronized (lifeListeners) {
			lifeListeners.remove(listener);
		}
	}
	
	private void broadcast(LifeEvent lifeEvent) {
		Set<LifeListener> snapshot;
		synchronized (lifeListeners) {
			snapshot = new HashSet<>(lifeListeners);
		}
		for (LifeListener listener : snapshot) {
			listener.lifeChanged(lifeEvent);
		}
	}
	
	@Override
	public void setNextTexture() {
		super.setNextTexture();
		setMoving(false);
	}
	
	public void move(Directions direction, Set<Position> unavailablePositions) {
		this.setMoving(true);
		if (this.getDirection() != direction) {
			setMovementFrame(1);
		}
		setDirection(direction);

		switch (direction) {
		case SOUTH:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.SOUTH, unavailablePositions)) {
					moveSOUTH();
				} else {
					break;
				}
			}
			break;
		case WEST:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.WEST, unavailablePositions)) {
					moveWEST();
				} else {
					break;
				}
			}
			break;
		case EAST:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.EAST, unavailablePositions)) {
					moveEAST();
				} else {
					break;
				}
			}
			break;
		case NORTH:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.NORTH, unavailablePositions)) {
					moveNORTH();
				} else {
					break;
				}
			}
			break;
		}
	}
	
	private boolean canMove(Directions direction, Set<Position> unavailablePositions) {
		int textureCenterX = getAbsolutePosition().getX() + getTexture().getSize().getWidth() / 2;
		int textureCenterY = getAbsolutePosition().getY() + getTexture().getSize().getHeight() / 2;
		int minHitBoxX = textureCenterX - ((HitBoxTexture) getTexture()).getHitBoxSize().getWidth() / 2;
		int minHitBoxY = textureCenterY - ((HitBoxTexture) getTexture()).getHitBoxSize().getHeight() / 2;
		int maxHitBoxX = textureCenterX + ((HitBoxTexture) getTexture()).getHitBoxSize().getWidth() / 2;
		int maxHitBoxY = textureCenterY + ((HitBoxTexture) getTexture()).getHitBoxSize().getHeight() / 2;
		Set<Position> toCheck = new LinkedHashSet<>();
		switch (direction) {
		case SOUTH:
			toCheck = IntStream.range(minHitBoxX, maxHitBoxX).mapToObj(posX -> new Position(posX, maxHitBoxY + 1))
					.collect(Collectors.toSet());
			break;
		case WEST:
			toCheck = IntStream.range(minHitBoxY, maxHitBoxY).mapToObj(posY -> new Position(minHitBoxX - 1, posY))
					.collect(Collectors.toSet());
			break;
		case EAST:
			toCheck = IntStream.range(minHitBoxY, maxHitBoxY).mapToObj(posY -> new Position(maxHitBoxX + 1, posY))
					.collect(Collectors.toSet());
			break;
		case NORTH:
			toCheck = IntStream.range(minHitBoxX, maxHitBoxX).mapToObj(posX -> new Position(posX, minHitBoxY - 1))
					.collect(Collectors.toSet());
			break;
		}
		return (Collections.disjoint(unavailablePositions, toCheck));
	}
}
