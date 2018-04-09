package be.kiop.characters;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kiop.UI.Animated;
import be.kiop.UI.Board;
import be.kiop.UI.Drawable;
import be.kiop.events.HealthEvent;
import be.kiop.exceptions.IllegalLevelException;
import be.kiop.exceptions.IllegalNameException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.IllegalWeaponSetException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.NegativeHealthException;
import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.listeners.HealthListener;
import be.kiop.textures.HitBoxTexture;
import be.kiop.textures.MoveAnimation;
import be.kiop.textures.Texture;
import be.kiop.textures.TextureBuilder;
import be.kiop.textures.WeaponTextures;
import be.kiop.utils.SetUtils;
import be.kiop.utils.StringUtils;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Genders;
import be.kiop.valueobjects.HitBox;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Weapon;

public abstract class GameCharacter extends Drawable implements Animated, HitBox {
	public final static int MAX_LEVEL = 100;
	public final static int MAX_ARMOR = 100;
	public final static int SPEED = 2;

	private final String name;
	private final Set<WeaponTextures> availableWeapons;
	private final Set<HealthListener> healthListeners = new HashSet<>();

	private float health;
	private Weapon weapon;
	private int level;
	private float armor;
	private boolean moving;
	private int movementFrame;
	private Directions direction;
	private boolean attacking;
	private boolean takingDamage;

	public GameCharacter(Set<Texture> availableTextures, Texture texture, Tile tile, String name,
			Set<WeaponTextures> availableWeapons, float health, Weapon weapon, int level, float armor) {
		super(availableTextures, texture, tile);
		if (!StringUtils.isValidString(name)) {
			throw new IllegalNameException();
		}
		this.name = name;
		if (!SetUtils.isValidSet(availableWeapons)) {
			throw new IllegalWeaponSetException();
		}
		this.availableWeapons = availableWeapons;
		setLevel(level);
		setHealth(health);
		setWeapon(weapon);
		setArmor(armor);
		setMoving(false);
		setMovementFrame(1);
		setDirection(Directions.SOUTH);
		setAttacking(false);
		setTakingDamage(false);
	}

	public String getName() {
		return name;
	}

	public float getHealth() {
		synchronized (healthListeners) {
			return health;
		}
	}

	public void setHealth(float health) {
		HealthEvent event;
		synchronized (healthListeners) {
			event = new HealthEvent(this.health, health);
			this.health = health;
			if (this.health <= 0) {
				this.health = 0;
			} else if (this.health >= getMaxHealth()) {
				this.health = getMaxHealth();
			} else {
				this.health = health;
			}

		}
		if (event.oldHealth != event.newHealth) {
			broadcast(event);
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		if (!availableWeapons.stream().anyMatch(value -> value.getWeaponClass().equals(weapon.getClass()))
				&& !(weapon instanceof Fist)) {
			throw new IllegalWeaponException();
		}
		this.weapon = weapon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if (level < 0 || level > MAX_LEVEL) {
			throw new IllegalLevelException();
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

	public boolean isMoving() {
		return moving;
	}

	protected void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getMovementFrame() {
		return movementFrame;
	}

	public void setMovementFrame(int movementFrame) {
		this.movementFrame = movementFrame;
	}

	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
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

	public void setTakingDamage(boolean takingDamage) {
		this.takingDamage = takingDamage;
	}

	public void reset() {
		attacking = false;
		takingDamage = false;
	}

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

	private void broadcast(HealthEvent healthEvent) {
		Set<HealthListener> snapshot;
		synchronized (healthListeners) {
			snapshot = new HashSet<>(healthListeners);
		}
		for (HealthListener listener : snapshot) {
			listener.healthChanged(healthEvent);
		}
	}

	private void decreaseHealth(float decrement) {
		if (decrement < 0) {
			throw new NegativeHealthException();
		}
		takingDamage = true;
		setHealth(this.health - decrement);
	}

	private void increaseHealth(float increment) {
		if (increment < 0) {
			throw new NegativeHealthException();
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

	public float getMaxHealth() {
		return this.level * 100;
	}

	public void changeWeapon(Weapon weapon) {
		if (weapon == null) {
			throw new IllegalWeaponException();
		}
		setWeapon(weapon);
	}

	public void dropWeapon() {
		setWeapon(new Fist());
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

	public void attack(GameCharacter gc) {
		attacking = true;
		if (gc != null) {
			gc.takeDamage(this.weapon.getDamage(), this.weapon.getPenetration());
		}
	}

	public void moveEAST() {
		if (getPositionOfTextureCenterInTile().getX() >= Board.TILE_SIZE.getWidth()) {
			setTile(getTile().getEASTwardTile());
			getPositionOfTextureCenterInTile().setX(0);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().east());
	}

	public void moveSOUTH() {
		if (getPositionOfTextureCenterInTile().getY() >= Board.TILE_SIZE.getHeight()) {
			setTile(getTile().getSOUTHwardTile());
			getPositionOfTextureCenterInTile().setY(0);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().south());
	}

	public void moveWEST() {
		if (getPositionOfTextureCenterInTile().getX() <= 0) {
			setTile(getTile().getWESTwardTile());
			getPositionOfTextureCenterInTile().setX(Board.TILE_SIZE.getWidth() - 1);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().west());
	}

	public void moveNORTH() {
		if (getPositionOfTextureCenterInTile().getY() <= 0) {
			setTile(getTile().getNORTHwardTile());
			getPositionOfTextureCenterInTile().setY(Board.TILE_SIZE.getHeight() - 1);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().north());
	}

//	public void teleport(int x, int y) {
//		getPosition().setX(x);
//		getPosition().setY(y);
//	}

//	public void mazeMove(Set<Position> unavailablePositions) {
////		System.out.println("dir1: " + direction.name());
//		Directions nextDirection = NextDirection.valueOf(direction.name()).getNextDirection();
//		if (canMove(nextDirection, unavailablePositions)) {
//			direction = nextDirection;
//			move(unavailablePositions);
//		} else if (canMove(direction, unavailablePositions)) {
//			move(unavailablePositions);
//		} else {
//			nextDirection = NextDirection.valueOf(nextDirection.name()).getNextDirection();
//			if (canMove(nextDirection, unavailablePositions)) {
//				direction = nextDirection;
//				move(unavailablePositions);
//			} else {
//				direction = NextDirection.valueOf(nextDirection.name()).getNextDirection();
//				move(unavailablePositions);
//			}
//		}
////		System.out.println("dir2: " + direction.name());
//	}

//	public void move(Set<Position> unavailablePositions) {
//		Random r = new Random();
//		int tried = 0;
//		while (!canMove(direction, unavailablePositions) && tried < 20) {
//			tried++;
//			this.direction = Directions.values()[r.nextInt(Directions.values().length)];
//		}
//		move(direction, unavailablePositions);
//
//	}

//	private void move() {
//		if (!moving) {
//			throw new UnsupportedOperationException();
//		}
//		try {
//			Method moveMethod = this.getClass().getMethod("move" + direction.name());
//			try {
//				moveMethod.invoke(this);
//			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//				throw new UnsupportedOperationException();
//			}
//		} catch (NoSuchMethodException | SecurityException e) {
//			throw new UnsupportedOperationException();
//		}
////		System.out.println("position of current center:" + getPositionOfTextureCenterInTile());
////		System.out.println("position of center:" + Board.TILE_SIZE.getCenter());
//		if (getPositionOfTextureCenterInTile().equals(Board.TILE_SIZE.getCenter())) {
//			moving = false;
//		}
//	}

//	public Set<Tile> move(Set<Tile> availableTiles) {
//		if (moving) {
//			move();
//		} else {
//			movementFrame = 1;
//			Map<Directions, Tile> possibleTiles = null;
//			try {
//				possibleTiles = getTile().getAvailableAdjacentTiles(availableTiles);
//			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
//					| InvocationTargetException e) {
//			}
//			if (MapUtils.isValidMap(possibleTiles)) {
//				List<Directions> keys = new ArrayList<>(possibleTiles.keySet());
//				Directions direction = keys.get(random.nextInt(keys.size()));
//				this.moving = true;
//				this.direction = direction;
//				availableTiles.add(getTile());
//				availableTiles.remove(possibleTiles.get(direction));
//			}
//		}
//		return availableTiles;
//	}

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
//		this.moving = false;
	}

	@Override
	public Set<Position> getHitBox(int range) {
		Set<Position> positions = new LinkedHashSet<>();
		int textureCenterX = getAbsolutePosition().getX() + getTexture().getSize().getWidth() / 2;
		int textureCenterY = getAbsolutePosition().getY() + getTexture().getSize().getHeight() / 2;
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

//	public void setTakingDamage(boolean takingDamage) {
//		this.takingDamage = takingDamage;
//	}

	public boolean inFrontOf(int rangeX, int rangeY, Tile potentiallyInFront, Set<Tile> availableTiles) {
//		System.out.println("available tiles size: " + availableTiles.size());
		int potX = potentiallyInFront.getHorizontalPosition();
		int potY = potentiallyInFront.getVerticalPosition();

		int thisX = getTile().getHorizontalPosition();
		int thisY = getTile().getVerticalPosition();

		int minX = potX > thisX ? thisX : potX;
		int minY = potY > thisY ? thisY : potY;
		int maxX = potX < thisX ? thisX : potX;
		int maxY = potY < thisY ? thisY : potY;
		
		if (maxX - minX > rangeX || maxY - minY > rangeY) {
			return false;
		}

		switch (direction) {
		case EAST:
			if (potY != thisY || potX < thisX) {
				return false;
			}
//			maxX += rangeX;
			break;
		case SOUTH:
			if (potX != thisX || potY < thisY) {
				return false;
			}
//			maxY += rangeY;
			break;
		case WEST:
			if (potY != thisY || potX > thisX) {
				return false;
			}
//			minX -= rangeX;
			break;
		case NORTH:
			if (potX != thisX || potY > thisY) {
				return false;
			}
//			minY -= rangeY;
			break;
		}
//		System.out.println("minX tile: " + minX);
//		System.out.println("minY tile: " + minY);
//		System.out.println("maxX tile: " + maxX);
//		System.out.println("maxY tile: " + maxY);
		Set<Tile> tilesBetween = new LinkedHashSet<>();
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				tilesBetween.add(new Tile(x, y));
			}
		}
		if(!SetUtils.isValidSet(tilesBetween)) {
//			System.out.println("empty set of infront tiles");
			return false;
		}
//		System.out.println("available contains all infront: " + availableTiles.containsAll(tilesBetween));
//		System.out.println(tilesBetween);
		Set<Tile> commons = new LinkedHashSet<>(tilesBetween);
		commons.retainAll(availableTiles);
//		System.out.println("valid tiles: " + commons);
		return availableTiles.containsAll(tilesBetween);

//		Position center = getAbsoluteCenterPosition();
//		int centerX = center.getX();
//		int centerY = center.getY();
//
//		int minX = potX > centerX ? centerX : potX;
//		int minY = potY > centerY ? centerY : potY;
//		int maxX = potX < centerX ? centerX : potX;
//		int maxY = potY < centerY ? centerY : potY;
//
//		if (maxX - minX > rangeX || maxY - minY > rangeY) {
//			return false;
//		}
//
//		Set<Position> line = new LinkedHashSet<>();
//
//		Size hitBoxSize = ((HitBoxTexture) getTexture()).getHitBoxSize();
//
//		switch (direction) {
//		case EAST:
//			if (potX < centerX || maxY-minY>24 ) {
//				return false;
//			}
//			minX += hitBoxSize.getWidth();
//			break;
//		case SOUTH:
//			if (potY < centerY || maxX-minX>24) {
//				return false;
//			}
//			minY += hitBoxSize.getHeight();
//			break;
//		case WEST:
//			if (potX > centerX || maxY-minY>16) {
//				return false;
//			}
//			break;
//		case NORTH:
//			if (potY > centerY || maxX-minX>16) {
//				return false;
//			}
//			break;
//		default:
//			break;
//
//		}
//		for (int x = minX; x < maxX; x++) {
//			for (int y = minY; y < maxY; y++) {
//				line.add(new Position(x, y));
//			}
//		}
//		if (line.isEmpty()) {
//			return false;
//		}
//		return Collections.disjoint(line, unavailablePositions);
	}

}
