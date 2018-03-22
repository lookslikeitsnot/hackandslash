package be.kiop.weapons;

public abstract class Weapon {
	private final String name;
	
	private float damage;
	private final float maxDamage;
	
	private float range;
	private final float minRange;
	private final float maxRange;
	
	
	private float attackSpeed;
	private final float maxAttackSpeed;
	
	Weapon(String name, float damage, float maxDamage, float range, 
			float minRange, float maxRange, float attackSpeed, float maxAttackSpeed) {
		this.name = name;
		this.damage = damage;
		this.maxDamage = maxDamage;
		this.range = range;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.attackSpeed = attackSpeed;
		this.maxAttackSpeed = maxAttackSpeed;
	}

	public Weapon(String name, float damage, float range, float attackSpeed) {
		this.name = name;
		this.damage = damage;
		this.maxDamage = damage;
		this.range = range;
		this.minRange = range;
		this.maxRange = range;
		this.attackSpeed = attackSpeed;
		this.maxAttackSpeed = attackSpeed;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}
	
	public void increaseDamage(float increment) {
		if(increment < 0) {
			throw new IllegalArgumentException();
		}
		setDamage(this.damage+increment);
	}
	
	public void decreaseDamage(float decrement) {
		if(decrement < 0) {
			throw new IllegalArgumentException();
		}
		setDamage(this.damage-decrement);
	}

	private void setDamage(float damage) {
		if(damage < 0) {
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
		if(increment < 0) {
			throw new IllegalArgumentException();
		}
		setRange(this.range+increment);
	}
	
	public void decreaseRange(float decrement) {
		if(decrement < 0) {
			throw new IllegalArgumentException();
		}
		setRange(this.range-decrement);
	}

	private void setRange(float range) {
		if(range<this.minRange){
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
		if(increment < 0) {
			throw new IllegalArgumentException();
		}
		setAttackSpeed(this.attackSpeed+increment);
	}
	public void decreaseAttackSpeed(float decrement) {
		if(decrement < 0) {
			throw new IllegalArgumentException();
		}
		setAttackSpeed(this.attackSpeed-decrement);
	}
	private void setAttackSpeed(float attackSpeed) {
		if(attackSpeed < 0) {
			this.attackSpeed = 0;
		} else if (attackSpeed > this.maxAttackSpeed) {
			this.attackSpeed = this.maxAttackSpeed;
		} else {
			this.attackSpeed = attackSpeed;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
