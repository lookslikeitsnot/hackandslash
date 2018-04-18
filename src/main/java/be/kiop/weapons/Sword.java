package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.exceptions.IllegalMaximumException;
import be.kiop.exceptions.NegativeCritChanceException;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public class Sword extends Weapon {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WeaponTextures.values())
			.collect(Collectors.toSet());
	private final float maxCritChance;

	private float critChance;

	public Sword(Texture texture, Tile tile, String name, float damage, float maxDamage, int range, int minRange,
			int maxRange, float attackSpeed, float maxAttackSpeed, float penetration, float critChance,
			float maxCritChance) {
		super(AVAILABLE_TEXTURES, texture, tile, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration);
		if (maxCritChance < critChance) {
			throw new IllegalMaximumException();
		}
		this.maxCritChance = maxCritChance;
		setCritChance(critChance);
	}
	
	public Sword(Sword sword) {
		super(sword);
		this.critChance = sword.critChance;
		this.maxCritChance = sword.maxCritChance;
	}

	public float getCritChance() {
		return critChance;
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

	public void increaseCritChance(float increment) {
		if (increment < 0) {
			throw new NegativeCritChanceException();
		}
		setCritChance(this.critChance + increment);
	}

	public void decreaseCritChance(float decrement) {
		if (decrement < 0) {
			throw new NegativeCritChanceException();
		}
		setCritChance(this.critChance - decrement);
	}
}
