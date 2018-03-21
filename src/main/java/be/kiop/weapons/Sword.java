package be.kiop.weapons;

public class Sword extends Weapon {
	private float critChance;
	private final float maxCritChance;

	public Sword(String name, float damage, float maxDamage, float range, float minRange, float maxRange,
			float attackSpeed, float maxAttackSpeed, float critChance, float maxCritChance) {
		super(name, damage, maxDamage, range, minRange, maxRange, attackSpeed, maxAttackSpeed);
		this.critChance = critChance;
		this.maxCritChance = maxCritChance;
	}

	public float getCritChance() {
		return critChance;
	}

	public void increaseCritChance(float increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}
		setCritChance(this.critChance + increment);
	}

	public void decreaseCritChance(float decrement) {
		if (decrement < 0) {
			throw new IllegalArgumentException();
		}
		setCritChance(this.critChance - decrement);
	}

	private void setCritChance(float critChance) {
		if (critChance < 0) {
			this.critChance = 0;
		} else if (critChance > maxCritChance) {
			this.critChance = this.maxCritChance;
		} else {
			this.critChance = critChance;
		}
	}
}
