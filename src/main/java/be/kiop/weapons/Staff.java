package be.kiop.weapons;

public class Staff extends Weapon{
	private float manaCost;
	public static final String DEFAULT_NAME= "Staff";

	public Staff(String name, float damage, float maxDamage, float range, float minRange, float maxRange,
			float attackSpeed, float maxAttackSpeed, float penetration, float manaCost) {
		super(name, damage, maxDamage, range, minRange, maxRange, attackSpeed, maxAttackSpeed, penetration);
		this.manaCost = manaCost;
	}

//	public Staff(String name, float damage, float range, float attackSpeed) {
//		super(name, damage, range, attackSpeed);
//	}

	public Staff() {
		super(DEFAULT_NAME, 5, 10, 1);
		this.manaCost = 0;
	}
	
	public float getManaCost() {
		return manaCost;
	}
}
