package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public enum Bones {
	Bone_1(WeaponTextures.Bone, Tile.ORIGIN, "Bone", 10, 10, 1, 1, 2, 5, 6, 10),
	Bone_5(WeaponTextures.Bone, Tile.ORIGIN, "Bone", 50, 50, 2, 1, 2, 5, 6, 10);

	private final Bone bone;

	Bones(WeaponTextures weapon, Tile tile, String name, float damage, float maxDamage, int range, int minRange,
			int maxRange, float attackSpeed, float maxAttackSpeed, float penetration) {
		bone = new Bone(weapon, tile, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration);
	}

	public Weapon getWeapon() {
		return bone;
	}
}
