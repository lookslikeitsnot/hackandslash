package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.textures.Texture;
import be.kiop.textures.Weapons;
import be.kiop.valueobjects.Position;

public class Staff extends Weapon{
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(Weapons.values()).collect(Collectors.toSet());
	private float manaCost;
	public static final String DEFAULT_NAME= "Staff";

	public Staff(Weapons weapon, Position position, String name, float damage, float maxDamage, float range, float minRange, float maxRange,
			float attackSpeed, float maxAttackSpeed, float penetration, float manaCost) {
		super(name, maxDamage, minRange, maxRange, maxAttackSpeed);
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(weapon);
		super.setPosition(position);
		super.setDamage(damage);
		super.setRange(range);
		super.setAttackSpeed(attackSpeed);
		super.setPenetration(penetration);
		this.manaCost = manaCost;
	}

	public Staff() {
		super(DEFAULT_NAME, 5, 10, 10, 1);
		this.manaCost = 0;
	}
	
	public float getManaCost() {
		return manaCost;
	}
}
