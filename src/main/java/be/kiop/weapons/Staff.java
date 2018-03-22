package be.kiop.weapons;

public class Staff extends Weapon{

	public Staff(String name, float damage, float maxDamage, float range, float minRange, float maxRange,
			float attackSpeed, float maxAttackSpeed, float penetration) {
		super(name, damage, maxDamage, range, minRange, maxRange, attackSpeed, maxAttackSpeed, penetration);
	}

	public Staff(String name, float damage, float range, float attackSpeed) {
		super(name, damage, range, attackSpeed);
	}

	public Staff() {
		super("Staff", 5, 10, 1);
	}
}
