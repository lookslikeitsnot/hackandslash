package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;

public enum Bones {
	Bone_1(WeaponTextures.Bone, Position.ORIGIN, "Bone", 10, 10, 30, 25, 35, 5, 6, 10),
	Bone_5(WeaponTextures.Bone, Position.ORIGIN, "Bone", 50, 50, 30, 25, 35, 5, 6, 10);

	private Bone bone;

	Bones(WeaponTextures weapon, Position position, String name, float damage, float maxDamage, int range, int minRange,
			int maxRange, float attackSpeed, float maxAttackSpeed, float penetration) {
		bone = new Bone(weapon, position, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration);
	}

	public Weapon getWeapon() {
		return bone;
	}
}
