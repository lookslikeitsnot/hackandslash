package be.kiop.characters;

import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.textures.Texture;
import be.kiop.textures.Weapons;
import be.kiop.utils.StringUtils;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;

public abstract class GameCharacter extends Drawable {
	private String name;
	private float health;
	private Weapon weapon;
	private Set<Weapons> availableWeapons;
	private int level;
	public final static int MAX_LEVEL = 100;
	public final static int MAX_ARMOR = 100;
	private float armor;
	public final static int SPEED = 2;
	private Directions direction = Directions.UP;

	protected void setName(String name) {
		if (!StringUtils.isValidString(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
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
		decreaseHealth(damage);
	}

	public void takeDamage(float damage) {
		decreaseHealth(damage - armor * damage / 100);
	}

	public void takeDamage(float damage, float penetration) {
		if (penetration < 0) {
			throw new IllegalArgumentException();
		}
		float damageFactor = armor - penetration;
		if (damageFactor < 0) {
			damageFactor = 0;
		}
		decreaseHealth(damage - damageFactor * damage / 100);
	}

	public void heal(float health) {
		increaseHealth(health);
	}

	public void setHealth(float health) {
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

	public void setWeapon(Weapon weapon) {
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

	public void setLevel(int level) {
		if (level < 0 || level > MAX_LEVEL) {
			throw new IllegalArgumentException();
		}
		this.level = level;
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

	public Set<Weapons> getAvailableWeapons() {
		return availableWeapons;
	}

	public void setAvailableWeapons(Set<Weapons> availableWeapons) {
		if (availableWeapons == null || availableWeapons.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.availableWeapons = availableWeapons;
	}

	public void moveLeft() {
		getPosition().setX(getPosition().getX() - SPEED);
	}

	public void moveRight() {
		getPosition().setX(getPosition().getX() + SPEED);
	}

	public void moveUp() {
		getPosition().setY(getPosition().getY() - SPEED);
	}

	public void moveDown() {
		getPosition().setY(getPosition().getY() + SPEED);
	}

	public void teleport(int x, int y) {
		getPosition().setX(x);
		getPosition().setY(y);
	}

//	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public void move(Directions direction, Set<Position> unavailablePositions) {
//		String textureName = getTextureAbsoluteName();
//		Class<Enum> textureClass = null;
//		try {
//			textureClass = (Class<Enum>) Class.forName(getTexture().getClass().getName());
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		setNextTexture(direction);
		switch (direction) {
		case DOWN:
//			setTexture((Texture) Enum.valueOf(textureClass, textureName + "_2-2"));
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.DOWN, unavailablePositions)) {
					moveDown();
				} else {
					break;
				}
			}
			break;
		case LEFT:
//			setTexture((Texture) Enum.valueOf(textureClass, textureName + "_1-2"));
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.LEFT, unavailablePositions)) {
					moveLeft();
				} else {
					break;
				}
			}
			break;
		case RIGHT:
//			setTexture((Texture) Enum.valueOf(textureClass, textureName + "_3-2"));
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.RIGHT, unavailablePositions)) {
					moveRight();
				} else {
					break;
				}
			}
			break;
		case UP:
//			setTexture((Texture) Enum.valueOf(textureClass, textureName + "_4-2"));
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.UP, unavailablePositions)) {
					moveUp();
				} else {
					break;
				}
			}
			break;
		}
	}

	private boolean canMove(Directions direction, Set<Position> unavailablePositions) {
		switch (direction) {
		case DOWN:
			if (!unavailablePositions.contains(new Position(getPosition().getX(),
					getPosition().getY() + getTexture().getSize().getHeight() + 1))) {
				return true;
			}
			break;
		case LEFT:
			if (!unavailablePositions.contains(new Position(getPosition().getX() - 1, getPosition().getY()))) {
				return true;
			}
			break;
		case RIGHT:
			if (!unavailablePositions.contains(
					new Position(getPosition().getX() + getTexture().getSize().getWidth() + 1, getPosition().getY()))) {
				return true;
			}
			break;
		case UP:
			if (!unavailablePositions.contains(new Position(getPosition().getX(), getPosition().getY() - 1))) {
				return true;
			}
			break;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setNextTexture(Directions direction) {
		String textureName = getTextureAbsoluteName();
		Class<Enum> textureClass = null;
		try {
			textureClass = (Class<Enum>) Class.forName(getTexture().getClass().getName());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (direction == this.direction) {
			int animationDirection = getAnimationDirection();
			int animationFrame = getAnimationFrame();

			animationFrame++;
			if (animationFrame > 6) {
				animationFrame = 2;
			}
			setTexture((Texture) Enum.valueOf(textureClass,
					textureName + "_" + animationDirection + "_" + animationFrame));
		} else {
			this.direction = direction;
			switch(direction) {
			case DOWN:
				setTexture((Texture) Enum.valueOf(textureClass, textureName + "_2_2"));
				break;
			case LEFT:
				setTexture((Texture) Enum.valueOf(textureClass, textureName + "_1_2"));
				break;
			case RIGHT:
				setTexture((Texture) Enum.valueOf(textureClass, textureName + "_3_2"));
				break;
			case UP:
				setTexture((Texture) Enum.valueOf(textureClass, textureName + "_4_2"));
				break;
			
			}
		}
	}
}
