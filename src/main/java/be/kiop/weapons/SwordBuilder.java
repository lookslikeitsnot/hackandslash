package be.kiop.weapons;

public class SwordBuilder {
	private String name;
	
	private float damage;
	private float maxDamage;
	
	private float range;
	private float minRange;
	private float maxRange;
	
	private float attackSpeed;
	private float maxAttackSpeed;
	
	private float critChance;
	private float maxCritChance;

	
	public SwordBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public SwordBuilder withDamage(float damage) {
		this.damage = damage;
		return this;
	}
	
	public SwordBuilder withMaxDamage(float maxDamage) {
		this.maxDamage = maxDamage;
		return this;
	}
	
	public SwordBuilder withRange(float range) {
		this.range = range;
		return this;
	}
	public SwordBuilder withMinRange(float minRange) {
		this.minRange = minRange;
		return this;
	}
	public SwordBuilder withMaxRange(float maxRange) {
		this.maxRange = maxRange;
		return this;
	}
	public SwordBuilder withCritChance(float critChance) {
		this.critChance = critChance;
		return this;
	}
	public SwordBuilder withMaxCritChance(float maxCritChance) {
		this.maxCritChance = maxCritChance;
		return this;
	}

	public SwordBuilder withAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
		return this;
	}
	public SwordBuilder withMaxAttackSpeed(float maxAttackSpeed) {
		this.maxAttackSpeed = maxAttackSpeed;
		return this;
	}
	
	
	public Sword makeSword() {
		return new Sword(name, damage, maxDamage, range, minRange, maxRange, attackSpeed, maxAttackSpeed, critChance, maxCritChance);
	}
}
