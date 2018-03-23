package be.kiop.weapons;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.valueobjects.Position;

public class Sword extends Weapon {
	private final static Set<Path> AVAILABLE_SKIN_PATHS = Arrays.stream(Weapons.values())
			.map(weapon -> weapon.getPath()).collect(Collectors.toSet());
	private float critChance;
	private final float maxCritChance;
	public static final String DEFAULT_NAME = "Sword";

	public Sword(Path skinPath, Position position, String name, float damage, float maxDamage, float range,
			float minRange, float maxRange, float attackSpeed, float maxAttackSpeed, float penetration,
			float critChance, float maxCritChance) {
		super(name, maxDamage, minRange, maxRange, maxAttackSpeed);
		super.setAvailableSkinPaths(AVAILABLE_SKIN_PATHS);
		super.setSkinPath(skinPath);
		super.setPosition(position);
		super.setDamage(damage);
		super.setRange(range);
		super.setAttackSpeed(attackSpeed);
		super.setPenetration(penetration);
		this.maxCritChance = maxCritChance;
		setCritChance(critChance);
	}

	public Sword() {
		super(DEFAULT_NAME, 4, 2, 2, 1);
		this.critChance = 0;
		this.maxCritChance = 0;
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
