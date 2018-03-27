package be.kiop.characters;

import java.util.Set;

import be.kiop.UI.Animated;
import be.kiop.UI.Drawable;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.textures.MoveAnimation;
import be.kiop.textures.Texture;
import be.kiop.textures.TextureBuilder;
import be.kiop.textures.Weapons;
import be.kiop.utils.StringUtils;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Genders;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;

public abstract class GameCharacter extends Drawable implements Animated {
	private String name;
	private float health;
	private Weapon weapon;
	private Set<Weapons> availableWeapons;
	private int level;
	public final static int MAX_LEVEL = 100;
	public final static int MAX_ARMOR = 100;
	private float armor;
	public final static int SPEED = 1;
	private boolean moving;
	private int movementFrame;
	private Directions direction = Directions.NORTH;

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

	public void move(Directions direction, Set<Position> unavailablePositions) {
		this.moving = true;
		if(this.direction != direction) {
			movementFrame = 1;
		}
		this.direction = direction;
		switch (direction) {
		case SOUTH:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.SOUTH, unavailablePositions)) {
					moveDown();
				} else {
					break;
				}
			}
			break;
		case WEST:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.WEST, unavailablePositions)) {
					moveLeft();
				} else {
					break;
				}
			}
			break;
		case EAST:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.EAST, unavailablePositions)) {
					moveRight();
				} else {
					break;
				}
			}
			break;
		case NORTH:
			for (int i = 0; i < SPEED; i++) {
				if (canMove(Directions.NORTH, unavailablePositions)) {
					moveUp();
				} else {
					break;
				}
			}
			break;
		}
	}

	private boolean canMove(Directions direction, Set<Position> unavailablePositions) {
		Set<Position> toCheck;
		switch (direction) {
		case SOUTH:
//			toCheck = 
			if (!unavailablePositions.contains(new Position(getPosition().getX(),
					getPosition().getY() + getTexture().getSize().getHeight() + 1))) {
				return true;
			}
			break;
		case WEST:
			if (!unavailablePositions.contains(new Position(getPosition().getX() - 1, getPosition().getY()))) {
				return true;
			}
			break;
		case EAST:
			if (!unavailablePositions.contains(
					new Position(getPosition().getX() + getTexture().getSize().getWidth() + 1, getPosition().getY()))) {
				return true;
			}
			break;
		case NORTH:
			if (!unavailablePositions.contains(new Position(getPosition().getX(), getPosition().getY() - 1))) {
				return true;
			}
			break;
		}
		return false;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setNextTexture() {
		if(moving) {
			Genders gender = null;
			Texture oldTexture = getTexture();
			String textureString = oldTexture.getName();
			Class<Enum> textureClass = null;
			try {
				textureClass = (Class<Enum>) Class.forName(getTexture().getClass().getName());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if(!(oldTexture instanceof MoveAnimation)) {
				throw new NoMoveAnimationException();
			}
			if(oldTexture instanceof CharacterGender) {
				gender = ((CharacterGender) oldTexture).getGender();
			}
			String genderString = gender==null ? "" : gender.name();
			String directionString = direction.name();
			
			movementFrame++;
			movementFrame = movementFrame>Drawable.ANIMATION_LENGTH ? 1 : movementFrame; 
			int associatedFrame = getAssociatedFrameNumber(movementFrame);
			
			setTexture(TextureBuilder.getTexture(textureClass, textureString, genderString, directionString, Integer.toString(associatedFrame)));
		}
		this.moving = false;
	}
}
