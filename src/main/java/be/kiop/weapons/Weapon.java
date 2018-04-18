package be.kiop.weapons;

import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.exceptions.IllegalMaximumException;
import be.kiop.exceptions.IllegalNameException;
import be.kiop.exceptions.NegativeAttackSpeedException;
import be.kiop.exceptions.NegativeDamageException;
import be.kiop.exceptions.NegativeRangeException;
import be.kiop.items.Drop;
import be.kiop.textures.Texture;
import be.kiop.utils.StringUtils;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;

public abstract class Weapon extends Drawable implements Drop {
	private final String name;

	private float damage;
	private final float maxDamage;

	private int range;
	private final int minRange;
	private final int maxRange;

	private float attackSpeed;
	private final float maxAttackSpeed;

	private float penetration;
	public static final float MAX_PENETRATION = 100.0F;
	
	public Weapon(Weapon otherWeapon) {
		super(otherWeapon.getAvailableTextures(), otherWeapon.getTexture(), otherWeapon.getTile());
		this.name = otherWeapon.name;
		this.damage = otherWeapon.damage;
		this.maxDamage = otherWeapon.maxDamage;

		this.range = otherWeapon.range;
		this.minRange = otherWeapon.minRange;
		this.maxRange = otherWeapon.maxRange;

		this.attackSpeed = otherWeapon.attackSpeed;
		this.maxAttackSpeed = otherWeapon.maxAttackSpeed;

		this.penetration = otherWeapon.penetration;
	}

	protected Weapon(Set<Texture> availableTextures, Texture texture, Tile tile, String name, float damage,
			float maxDamage, int range, int minRange, int maxRange, float attackSpeed, float maxAttackSpeed,
			float penetration) {
		super(availableTextures, texture, tile);
		if (!StringUtils.isValidString(name)) {
			throw new IllegalNameException();
		}
		this.name = name;
		if (maxDamage < damage) {
			throw new IllegalMaximumException();
		}
		this.maxDamage = maxDamage;
		if(minRange < 0) {
			throw new NegativeRangeException();
		}
		this.minRange = minRange;
		if (maxRange < range) {
			throw new IllegalMaximumException();
		}
		this.maxRange = maxRange;
		if (maxAttackSpeed < attackSpeed) {
			throw new IllegalMaximumException();
		}
		this.maxAttackSpeed = maxAttackSpeed;

		setDamage(damage);
		setRange(range);
		setAttackSpeed(attackSpeed);
		setPenetration(penetration);
	}

	public Weapon(Set<Texture> availableTextures, Texture texture, Tile tile, String name, float damage, int range,
			float attackSpeed, float penetration) {
		super(availableTextures, texture, tile);
		this.name = name;
		this.damage = damage;
		this.maxDamage = damage;
		this.range = range;
		this.minRange = range;
		this.maxRange = range;
		this.attackSpeed = attackSpeed;
		this.maxAttackSpeed = attackSpeed;
		this.penetration = penetration;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}

	private void setDamage(float damage) {
		if (damage < 0) {
			this.damage = 0;
		} else if (damage > this.maxDamage) {
			this.damage = this.maxDamage;
		} else {
			this.damage = damage;
		}
	}

	public int getRange() {
		return range;
	}

	private void setRange(int range) {
		if (range < this.minRange) {
			this.range = this.minRange;
		} else if (range > this.maxRange) {
			this.range = this.maxRange;
		} else {
			this.range = range;
		}
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	private void setAttackSpeed(float attackSpeed) {
		if (attackSpeed < 0) {
			this.attackSpeed = 0;
		} else if (attackSpeed > this.maxAttackSpeed) {
			this.attackSpeed = this.maxAttackSpeed;
		} else {
			this.attackSpeed = attackSpeed;
		}
	}

	public float getPenetration() {
		return penetration;
	}

	public void setPenetration(float penetration) {
		if (penetration < 0) {
			this.penetration = 0;
		} else if (penetration > MAX_PENETRATION) {
			this.penetration = MAX_PENETRATION;
		} else {
			this.penetration = penetration;
		}
	}

	public void increaseDamage(float increment) {
		if (increment < 0) {
			throw new NegativeDamageException();
		}
		setDamage(this.damage + increment);
	}

	public void decreaseDamage(float decrement) {
		if (decrement < 0) {
			throw new NegativeDamageException();
		}
		setDamage(this.damage - decrement);
	}

	public void increaseRange(int increment) {
		if (increment < 0) {
			throw new NegativeRangeException();
		}
		setRange(this.range + increment);
	}

	public void decreaseRange(int decrement) {
		if (decrement < 0) {
			throw new NegativeRangeException();
		}
		setRange(this.range - decrement);
	}

	public void increaseAttackSpeed(float increment) {
		if (increment < 0) {
			throw new NegativeAttackSpeedException();
		}
		setAttackSpeed(this.attackSpeed + increment);
	}

	public void decreaseAttackSpeed(float decrement) {
		if (decrement < 0) {
			throw new NegativeAttackSpeedException();
		}
		setAttackSpeed(this.attackSpeed - decrement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Weapon))
			return false;
		Weapon other = (Weapon) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Position getTextureCenter() {
		int textureWidth = getTexture().getSize().getWidth();
		int textureHeight = getTexture().getSize().getHeight();
		return new Position(textureWidth / 2, textureHeight / 2);
	}
}
