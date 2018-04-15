package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public class Bone extends Weapon {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WeaponTextures.values())
			.collect(Collectors.toSet());

	public Bone(Texture texture, Tile tile, String name, float damage, float maxDamage,
			int range, int minRange, int maxRange, float attackSpeed, float maxAttackSpeed, float penetration) {
		super(AVAILABLE_TEXTURES, texture, tile, name, damage, maxDamage, range, minRange, maxRange, attackSpeed, maxAttackSpeed,
				penetration);
	}
}
