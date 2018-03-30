package be.kiop.weapons;

import be.kiop.UI.Drawable;
import be.kiop.items.Droppable;

public abstract class Weapon extends Drawable implements Droppable {
	private final String name;

	private float damage;
	private final float maxDamage;

	private float range;
	private final float minRange;
	private final float maxRange;

	private float attackSpeed;
	private final float maxAttackSpeed;

	private float penetration;
	public static final float MAX_PENETRATION = 100.0F;

	// protected Weapon(String name, float damage, float maxDamage, float range,
	// float minRange, float maxRange, float attackSpeed, float maxAttackSpeed,
	// float penetration) {
	// setName(name);
	// this.damage = damage;
	// this.maxDamage = maxDamage;
	// this.range = range;
	// this.minRange = minRange;
	// this.maxRange = maxRange;
	// this.attackSpeed = attackSpeed;
	// this.maxAttackSpeed = maxAttackSpeed;
	// this.penetration = penetration;
	// }
	//
	// protected Weapon(String name, float damage, float range, float attackSpeed) {
	// setName(name);
	// this.damage = damage;
	// this.maxDamage = damage;
	// this.range = range;
	// this.minRange = range;
	// this.maxRange = range;
	// this.attackSpeed = attackSpeed;
	// this.maxAttackSpeed = attackSpeed;
	// this.penetration = 0;
	// }

	public Weapon(String name, float maxDamage, float minRange, float maxRange, float maxAttackSpeed) {
		this.name = name;
		this.maxDamage = maxDamage;
		this.damage = maxDamage;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.range = minRange;
		this.maxAttackSpeed = maxAttackSpeed;
		this.attackSpeed = maxAttackSpeed;
		this.penetration = 0;
	}

//	public void setName(String name) {
//		if (name == null || name.trim().isEmpty()) {
//			throw new IllegalArgumentException();
//		}
//		this.name = name;
//	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}

	public void increaseDamage(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setDamage(this.damage + increment);
	}

	public void decreaseDamage(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		setDamage(this.damage - decrement);
	}

	public void setDamage(float damage) {
		if (damage < 0) {
			this.damage = 0;
		} else if (damage > this.maxDamage) {
			this.damage = this.maxDamage;
		} else {
			this.damage = damage;
		}
	}

	public float getRange() {
		return range;
	}

	public void increaseRange(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setRange(this.range + increment);
	}

	public void decreaseRange(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		setRange(this.range - decrement);
	}

	public void setRange(float range) {
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

	public void increaseAttackSpeed(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setAttackSpeed(this.attackSpeed + increment);
	}

	public void decreaseAttackSpeed(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		setAttackSpeed(this.attackSpeed - decrement);
	}

	public void setAttackSpeed(float attackSpeed) {
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
}
