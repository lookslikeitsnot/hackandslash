package be.kiop.characters;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kiop.UI.Animated;
import be.kiop.UI.Drawable;
import be.kiop.events.HealthEvent;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.listeners.HealthListener;
import be.kiop.textures.HitBoxTexture;
import be.kiop.textures.MoveAnimation;
import be.kiop.textures.Texture;
import be.kiop.textures.TextureBuilder;
import be.kiop.textures.WeaponTextures;
import be.kiop.utils.StringUtils;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Genders;
import be.kiop.valueobjects.HitBox;
import be.kiop.valueobjects.NextDirection;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;

public abstract class GameCharacter extends Drawable implements Animated, HitBox {
	private String name;
	private float health;
	private Weapon weapon;
	private Set<WeaponTextures> availableWeapons;
	private int level;
	public final static int MAX_LEVEL = 100;
	public final static int MAX_ARMOR = 100;
	private float armor;
	public final static int SPEED = 2;
	private boolean moving;
	private int movementFrame = 1;
	private Directions direction = Directions.SOUTH;
	private boolean attacking;
	private boolean takingDamage;
	private Tile tile;

	public void reset() {
		attacking = false;
		takingDamage = false;
	}

	private final Set<HealthListener> healthListeners = new HashSet<>();

	public void addHealthListener(HealthListener listener) {
		synchronized (healthListeners) {
			healthListeners.add(listener);
		}
	}

	public void removeHealthListener(HealthListener listener) {
		synchronized (healthListeners) {
			healthListeners.remove(listener);
		}
	}

	protected void setName(String name) {
		if (!StringUtils.isValidString(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	public float getHealth() {
		synchronized (healthListeners) {
			return health;
		}
	}

	private void decreaseHealth(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		takingDamage = true;
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
		HealthEvent event;
		synchronized (healthListeners) {
			event = new HealthEvent(this.health, health);
			this.health = health;
			if (this.health <= 0) {
				this.health = 0;
//				throw new CharacterDiedException();
			} else if (this.health >= getMaxHealth()) {
				this.health = getMaxHealth();
			}

		}
		if (event.oldHealth != event.newHealth) {
			broadcast(event);
		}
	}

	private void broadcast(HealthEvent healthEvent) {
		Set<HealthListener> snapshot;
		synchronized (healthListeners) {
			snapshot = new HashSet<>(healthListeners);
		}
		for (HealthListener listener : snapshot) {
			listener.healthChanged(healthEvent);
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

	public void attack(GameCharacter gc) {
//		System.out.println("attacking");
		attacking = true;
		if (gc != null) {
			gc.takeDamage(this.weapon.getDamage(), this.weapon.getPenetration());
		}

	}

//	public Set<Weapons> getAvailableWeapons() {
//		return availableWeapons;
//	}

	public void setAvailableWeapons(Set<WeaponTextures> availableWeapons) {
		if (availableWeapons == null || availableWeapons.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.availableWeapons = availableWeapons;
	}

	public void moveLeft() {
		getPosition().setX(getPosition().getX() - 1);
	}

	public void moveRight() {
		getPosition().setX(getPosition().getX() + 1);
	}

	public void moveUp() {
		getPosition().setY(getPosition().getY() - 1);
	}

	public void moveDown() {
		getPosition().setY(getPosition().getY() + 1);
	}

	public void teleport(int x, int y) {
		getPosition().setX(x);
		getPosition().setY(y);
	}

	public void mazeMove(Set<Position> unavailablePositions) {
//		System.out.println("dir1: " + direction.name());
		Directions nextDirection = NextDirection.valueOf(direction.name()).getNextDirection();
		if (canMove(nextDirection, unavailablePositions)) {
			direction = nextDirection;
			move(unavailablePositions);
		} else if (canMove(direction, unavailablePositions)) {
			move(unavailablePositions);
		} else {
			nextDirection = NextDirection.valueOf(nextDirection.name()).getNextDirection();
			if (canMove(nextDirection, unavailablePositions)) {
				direction = nextDirection;
				move(unavailablePositions);
			} else {
				direction = NextDirection.valueOf(nextDirection.name()).getNextDirection();
				move(unavailablePositions);
			}
		}
//		System.out.println("dir2: " + direction.name());
	}

	public void move(Set<Position> unavailablePositions) {
		Random r = new Random();
		int tried = 0;
		while (!canMove(direction, unavailablePositions) && tried < 20) {
			tried++;
			this.direction = Directions.values()[r.nextInt(Directions.values().length)];
		}
		move(direction, unavailablePositions);

	}

	public void move(Directions direction, Set<Position> unavailablePositions) {
		this.moving = true;
		if (this.direction != direction) {
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
		int textureCenterX = getPosition().getX() + getTexture().getSize().getWidth() / 2;
		int textureCenterY = getPosition().getY() + getTexture().getSize().getHeight() / 2;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setNextTexture() {
		if (moving) {
			Genders gender = null;
			Texture oldTexture = getTexture();
			String textureString = oldTexture.getName();
			Class<Enum> textureClass = null;
			try {
				textureClass = (Class<Enum>) Class.forName(getTexture().getClass().getName());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (!(oldTexture instanceof MoveAnimation)) {
				throw new NoMoveAnimationException();
			}
			if (oldTexture instanceof CharacterGender) {
				gender = ((CharacterGender) oldTexture).getGender();
			}
			String genderString = gender == null ? null : gender.name();
			String directionString = direction.name();

			movementFrame++;
			movementFrame = movementFrame > Drawable.ANIMATION_LENGTH ? 1 : movementFrame;
			int associatedFrame = getAssociatedFrameNumber(movementFrame);

			setTexture(TextureBuilder.getTexture(textureClass, textureString, genderString, directionString,
					Integer.toString(associatedFrame)));
		}
		this.moving = false;
	}

	@Override
	public Set<Position> getHitBox(int range) {
		Set<Position> positions = new LinkedHashSet<>();
		int textureCenterX = getPosition().getX() + getTexture().getSize().getWidth() / 2;
		int textureCenterY = getPosition().getY() + getTexture().getSize().getHeight() / 2;
		int minHitBoxX = textureCenterX - ((HitBoxTexture) getTexture()).getHitBoxSize().getWidth() / 2 - range;
		int minHitBoxY = textureCenterY - ((HitBoxTexture) getTexture()).getHitBoxSize().getHeight() / 2 - range;
		int maxHitBoxX = textureCenterX + ((HitBoxTexture) getTexture()).getHitBoxSize().getWidth() / 2 + range;
		int maxHitBoxY = textureCenterY + ((HitBoxTexture) getTexture()).getHitBoxSize().getHeight() / 2 + range;

		for (int x = minHitBoxX; x <= maxHitBoxX; x++) {
			for (int y = minHitBoxY; y <= maxHitBoxY; y++) {
				if (x == minHitBoxX || y == minHitBoxY || x == maxHitBoxX || y == maxHitBoxY)
					positions.add(new Position(x, y));
			}
		}
		return positions;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isTakingDamage() {
		return takingDamage;
	}

//	public void setTakingDamage(boolean takingDamage) {
//		this.takingDamage = takingDamage;
//	}

	public boolean inFrontOf(int rangeX, int rangeY, Position potentiallyInFront, Set<Position> unavailablePositions) {
		int potX = potentiallyInFront.getX();
		int potY = potentiallyInFront.getY();

		Position center = getCenter();
		int centerX = center.getX();
		int centerY = center.getY();

		int minX = potX > centerX ? centerX : potX;
		int minY = potY > centerY ? centerY : potY;
		int maxX = potX < centerX ? centerX : potX;
		int maxY = potY < centerY ? centerY : potY;

		if (maxX - minX > rangeX || maxY - minY > rangeY) {
			return false;
		}

		Set<Position> line = new LinkedHashSet<>();

		Size hitBoxSize = ((HitBoxTexture) getTexture()).getHitBoxSize();

		switch (direction) {
		case EAST:
			if (potX < centerX || maxY-minY>24 ) {
				return false;
			}
			minX += hitBoxSize.getWidth();
			break;
		case SOUTH:
			if (potY < centerY || maxX-minX>24) {
				return false;
			}
			minY += hitBoxSize.getHeight();
			break;
		case WEST:
			if (potX > centerX || maxY-minY>16) {
				return false;
			}
			break;
		case NORTH:
			if (potY > centerY || maxX-minX>16) {
				return false;
			}
			break;
		default:
			break;

		}
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				line.add(new Position(x, y));
			}
		}
		if (line.isEmpty()) {
			return false;
		}
		return Collections.disjoint(line, unavailablePositions);
	}

	public Directions getDirection() {
		return direction;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
