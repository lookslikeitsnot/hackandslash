package be.kiop.characters;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import be.kiop.UI.Animated;
import be.kiop.UI.Drawable;
import be.kiop.events.HealthEvent;
import be.kiop.exceptions.AlreadyAttackingException;
import be.kiop.exceptions.AlreadyMovingException;
import be.kiop.exceptions.AlreadyPeacefulException;
import be.kiop.exceptions.AlreadyStoppedException;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalDirectionException;
import be.kiop.exceptions.IllegalFrameNumberException;
import be.kiop.exceptions.IllegalLevelException;
import be.kiop.exceptions.IllegalMovementFrameException;
import be.kiop.exceptions.IllegalNameException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.IllegalWeaponSetException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.NegativeArmorException;
import be.kiop.exceptions.NegativeDamageException;
import be.kiop.exceptions.NegativeHealthException;
import be.kiop.exceptions.NegativePenetrationException;
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
	private Directions direction;
	private boolean attacking;
	private boolean takingDamage;

	private boolean moving;
	private int movementFrame;

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
		setDirection(Directions.SOUTH);
		setAttacking(false);
		setTakingDamage(false);

		setMoving(false);
		setMovementFrame(1);
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	private void setLevel(int level) {
		if (level < 0) {
			throw new IllegalLevelException();
		}
		if (level > MAX_LEVEL) {
			this.level = MAX_LEVEL;
			throw new MaxLevelReachedException();
		}
		this.level = level;
	}

	public void increaseLevel() {
		setLevel(this.level + 1);
	}

	public void decreaseLevel() {
		setLevel(this.level - 1);
	}

	public float getHealth() {
		synchronized (healthListeners) {
			return health;
		}
	}

	public float getMaxHealth() {
		return this.level * 100;
	}

	protected void setHealth(float health) {
		HealthEvent event;
		synchronized (healthListeners) {
			event = new HealthEvent(this.health, health);
			this.health = health;
			if (this.health < 0) {
				this.health = 0;
				throw new CharacterDiedException();
			} else if (this.health > getMaxHealth()) {
				this.health = getMaxHealth();
			} else {
				this.health = health;
			}
		}
		if (event.oldHealth != event.newHealth) {
			broadcast(event);
		}
	}

	private void increaseHealth(float increment) {
		if (increment < 0) {
			throw new NegativeHealthException();
		}
		setHealth(this.health + increment);
	}

	public void heal(float health) {
		if (health < 0) {
			throw new NegativeHealthException();
		}
		increaseHealth(health);
	}

	private void decreaseHealth(float decrement) {
		if (decrement < 0) {
			throw new NegativeHealthException();
		}
		if (decrement > 0) {
			setTakingDamage(true);
		}
		setHealth(this.health - decrement);
	}

	private float getDamageFactor(float penetration) {
		if (penetration < 0) {
			throw new NegativePenetrationException();
		}
		float damageFactor = armor - penetration;
		if (damageFactor < 0) {
			damageFactor = 0;
		}
		return (1 - damageFactor / 100);
	}

	protected void takeDamage(float damage, float penetration) {
		if (damage < 0) {
			throw new NegativeDamageException();
		}
		if (penetration < 0) {
			throw new NegativePenetrationException();
		}
		decreaseHealth(damage * getDamageFactor(penetration));
	}

	public void attack(GameCharacter gc) {
		startAttacking();
		if (gc != null) {
			gc.takeDamage(this.weapon.getDamage(), this.weapon.getPenetration());
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	private void setWeapon(Weapon weapon) {
		if (weapon == null || (!availableWeapons.contains(weapon.getTexture()) && !(weapon instanceof Fist))) {
			throw new IllegalWeaponException();
		}
		this.weapon = weapon;
	}

	public void changeWeapon(Weapon weapon) {
		setWeapon(weapon);
	}

	public void dropWeapon() {
		setWeapon(new Fist());
	}

	public float getArmor() {
		return armor;
	}

	private void setArmor(float armor) {
		if (armor < 0) {
			this.armor = 0;
			throw new NegativeArmorException();
		} else if (armor > MAX_ARMOR) {
			this.armor = MAX_ARMOR;
		} else {
			this.armor = armor;
		}
	}

	public Directions getDirection() {
		return direction;
	}

	protected void setDirection(Directions direction) {
		if (direction == null) {
			throw new IllegalDirectionException();
		}
		this.direction = direction;
	}

	public boolean isAttacking() {
		return attacking;
	}

	private void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public void startAttacking() {
		if (attacking) {
			throw new AlreadyAttackingException();
		}
		setAttacking(true);
	}

	public void stopAttacking() {
		if (!attacking) {
			throw new AlreadyPeacefulException();
		}
		setAttacking(false);
	}

	public boolean isTakingDamage() {
		return takingDamage;
	}

	private void setTakingDamage(boolean takingDamage) {
		this.takingDamage = takingDamage;
	}

	public void reset() {
		try {
			stopAttacking();
		} catch (AlreadyPeacefulException e) {
//			System.out.println("peaceful");
		}
		setTakingDamage(false);
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

	protected void moveEAST() {
		if (getPositionOfTextureCenterInTile().getX() >= getTile().getSize().getWidth()) {
			setTile(getTile().getEASTwardTile());
			getPositionOfTextureCenterInTile().setX(0);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().east());
	}

	protected void moveSOUTH() {
		if (getPositionOfTextureCenterInTile().getY() >= getTile().getSize().getHeight()) {
			setTile(getTile().getSOUTHwardTile());
			getPositionOfTextureCenterInTile().setY(0);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().south());
	}

	protected void moveWEST() {
		if (getPositionOfTextureCenterInTile().getX() <= 0) {
			setTile(getTile().getWESTwardTile());
			getPositionOfTextureCenterInTile().setX(getTile().getSize().getWidth() - 1);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().west());
	}

	protected void moveNORTH() {
		if (getPositionOfTextureCenterInTile().getY() <= 0) {
			setTile(getTile().getNORTHwardTile());
			getPositionOfTextureCenterInTile().setY(getTile().getSize().getHeight() - 1);
		}
		setPositionOfTextureCenterInTile(getPositionOfTextureCenterInTile().north());
	}

	

	public boolean inFrontOf(int rangeX, int rangeY, Tile potentiallyInFront, Set<Tile> availableTiles) {
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
			break;
		case SOUTH:
			if (potX != thisX || potY < thisY) {
				return false;
			}
			break;
		case WEST:
			if (potY != thisY || potX > thisX) {
				return false;
			}
			break;
		case NORTH:
			if (potX != thisX || potY > thisY) {
				return false;
			}
			break;
		}

		Set<Tile> tilesBetween = new LinkedHashSet<>();
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				tilesBetween.add(new Tile(x, y));
			}
		}
		if (!SetUtils.isValidSet(tilesBetween)) {
			return false;
		}

		Set<Tile> commons = new LinkedHashSet<>(tilesBetween);
		commons.retainAll(availableTiles);
		return availableTiles.containsAll(tilesBetween);
	}

	public boolean isMoving() {
		return moving;
	}

	private void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void startMoving() {
		if (moving) {
			throw new AlreadyMovingException();
		}
		setMoving(true);
	}

	public void stopMoving() {
		if (!moving) {
			throw new AlreadyStoppedException();
		}
		setMoving(false);
	}

	public int getMovementFrame() {
		return movementFrame;
	}

	private void setMovementFrame(int movementFrame) {
		if (movementFrame < 1) {
			throw new IllegalMovementFrameException();
		}
		if (movementFrame > ANIMATION_LENGTH) {
			this.movementFrame = 1;
		} else {
			this.movementFrame = movementFrame;
		}
	}

	protected void resetMovementFrame() {
		setMovementFrame(1);
	}

	private int getAssociatedFrameNumber(int frameCounter) {
		if (frameCounter < 1 || frameCounter > ANIMATION_LENGTH) {
			throw new IllegalFrameNumberException();
		}

		switch (frameCounter) {
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		default:
			return 1;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setNextTexture() {
		if (moving) {
			Genders gender = null;
			Texture oldTexture = getTexture();
			String textureString = oldTexture.getName();
			Class<Enum> textureClass = null;
			textureClass = (Class<Enum>) getTexture().getClass();

			if (!(oldTexture instanceof MoveAnimation)) {
				throw new NoMoveAnimationException();
			}
			if (oldTexture instanceof CharacterGender) {
				gender = ((CharacterGender) oldTexture).getGender();
			}
			String genderString = gender == null ? null : gender.name();
			String directionString = direction.name();

			setMovementFrame(movementFrame + 1);
			int associatedFrame = getAssociatedFrameNumber(movementFrame);

			setTexture(TextureBuilder.getTexture(textureClass, textureString, genderString, directionString,
					Integer.toString(associatedFrame)));
		}
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
}
