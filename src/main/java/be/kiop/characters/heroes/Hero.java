package be.kiop.characters.heroes;

import java.util.HashSet;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.events.LifeEvent;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.LostALifeException;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.listeners.LifeListener;

public abstract class Hero extends GameCharacter {
	private int lives;
	private float experience;
	public static final int MAX_LIVES = 5;
	private final Set<LifeListener> lifeListeners = new HashSet<>();

	public void decreaseLives() {
		lives--;
		if (lives <= 0) {
			lives = 0;
			throw new OutOfLivesException();
		}
	}

	public void increaseLives() {
		lives++;
	}

	public int getLives() {
		synchronized (lifeListeners) {
			return lives;
		}
	}

	public void setLives(int lives) {
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

	private void broadcast(LifeEvent lifeEvent) {
		Set<LifeListener> snapshot;
		synchronized (lifeListeners) {
			snapshot = new HashSet<>(lifeListeners);
		}
		for (LifeListener listener : snapshot) {
			listener.lifeChanged(lifeEvent);
		}
	}

	public float getExperience() {
		return experience;
	}

	public void increaseExperience(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setExperience(this.experience + increment);
	}

	public void setExperience(float experience) {
		if (experience < 0) {
			throw new IllegalArgumentException();
		}
		this.experience = experience;
		while (this.experience >= getRequiredExpForNextLevel()) {
			this.experience -= getRequiredExpForNextLevel();
			increaseLevel();
		}
	}

	private float getRequiredExpForNextLevel() {
		return getLevel() * 100;
	}

	@Override
	public void setHealth(float health) {
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
}
