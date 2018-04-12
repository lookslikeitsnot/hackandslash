package be.kiop.characters.heroes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kiop.UI.Board;
import be.kiop.characters.GameCharacter;
import be.kiop.events.LifeEvent;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalDirectionException;
import be.kiop.exceptions.IllegalPositionException;
import be.kiop.exceptions.IllegalPositionsSetException;
import be.kiop.exceptions.InvalidTextureException;
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

	public void loseALife() {
		setLives(lives - 1);
	}

	public void increaseLives(int lives) {
		if (lives < 0) {
			throw new NegativeLifeException();
		}
		setLives(this.lives + lives);
	}

	public float getExperience() {
		return experience;
	}

	private void setExperience(float experience) {
		if (experience < 0) {
			throw new NegativeExperienceException();
		} else if (experience < this.experience) {
			throw new LessThanCurrentExperienceException();
		}
		this.experience = experience;
		while (this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}

	public void increaseExperience(float increment) {
		if (experience < 0) {
			throw new NegativeExperienceException();
		}
		setExperience(this.experience + increment);
	}

	private float getRequiredExpForNextLevel() {
		return getLevel() * 100;
	}

	@Override
	protected void setHealth(float health) {
		super.setHealth(health);
		if (getHealth() == 0) {
			if (lives > 1) {
				loseALife();
				setHealth(getMaxHealth());
			} else {
				throw new CharacterDiedException();
			}
		}
	}

	public Set<LifeListener> getLifeListeners() {
		return lifeListeners;
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
		stopMoving();
	}

	public void move(Directions direction, Set<Position> unavailablePositions) {
		if(direction == null) {
			throw new IllegalDirectionException();
		}
		if(unavailablePositions == null) {
			throw new IllegalPositionsSetException();
		}
		if (!isMoving()) {
			startMoving();
		}

		if (this.getDirection() != direction) {
			resetMovementFrame();
		}
		setDirection(direction);
		IntStream.range(0, SPEED).forEach(i -> {
			if (canMove(direction, unavailablePositions)) {
				try {
					Method moveMethod = GameCharacter.class.getDeclaredMethod("move" + getDirection().name());
					moveMethod.setAccessible(true);
					moveMethod.invoke(this);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new UnsupportedOperationException();
				}
			}
		});
	}

	private boolean canMove(Directions direction, Set<Position> unavailablePositions) {
		if(direction == null) {
			throw new IllegalDirectionException();
		}
		if(unavailablePositions == null) {
			throw new IllegalPositionsSetException();
		}
		Texture texture = getTexture();
		if (!(texture instanceof HitBoxTexture)) {
			throw new InvalidTextureException();
		}
		HitBoxTexture hitBoxTexture = (HitBoxTexture) texture;

		int hitBoxWidth = hitBoxTexture.getHitBoxSize().getWidth();
		int hitBoxHeight = hitBoxTexture.getHitBoxSize().getHeight();

		int textureCenterX = getAbsoluteCenterPosition().getX();
		int textureCenterY = getAbsoluteCenterPosition().getY();
		int minHitBoxX = textureCenterX - hitBoxWidth / 2;
		int minHitBoxY = textureCenterY - hitBoxHeight / 2;
		int maxHitBoxX = textureCenterX + hitBoxWidth / 2;
		int maxHitBoxY = textureCenterY + hitBoxHeight / 2;

		Set<Position> toCheck = new LinkedHashSet<>();
		switch (direction) {
		case SOUTH:
			if (maxHitBoxY >= Board.MAX_SIZE.getHeight() - Board.exteriorWallSize.getHeight()) {
				return false;
			}
			toCheck = IntStream.range(minHitBoxX, maxHitBoxX).mapToObj(posX -> new Position(posX, maxHitBoxY + 1))
					.collect(Collectors.toSet());
			break;
		case WEST:
			if (minHitBoxX < 0 + Board.exteriorWallSize.getWidth()) {
				return false;
			}
			toCheck = IntStream.range(minHitBoxY, maxHitBoxY).mapToObj(posY -> new Position(minHitBoxX - 1, posY))
					.collect(Collectors.toSet());
			break;
		case EAST:
			if (maxHitBoxX >= Board.MAX_SIZE.getWidth() - Board.exteriorWallSize.getWidth()) {
				return false;
			}
			toCheck = IntStream.range(minHitBoxY, maxHitBoxY).mapToObj(posY -> new Position(maxHitBoxX + 1, posY))
					.collect(Collectors.toSet());
			break;
		case NORTH:
			if (minHitBoxY < 0 + Board.exteriorWallSize.getHeight()) {
				return false;
			}
			toCheck = IntStream.range(minHitBoxX, maxHitBoxX).mapToObj(posX -> new Position(posX, minHitBoxY - 1))
					.collect(Collectors.toSet());
			break;
		}
		return (Collections.disjoint(unavailablePositions, toCheck));
	}
}
