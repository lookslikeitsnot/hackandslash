package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;

public enum Swords {
	Sword_1(WeaponTextures.Sword, Position.ORIGIN, "Staff", 10, 20, 100, 100, 200, 1, 2, 0, 0, 0),
	Sword_5(WeaponTextures.Sword, Position.ORIGIN, "Staff", 50, 100, 100, 100, 200, 1, 2, 0, 0, 0);

	private Sword sword;

	Swords(WeaponTextures weapon, Position position, String name, float damage, float maxDamage, int range,
			int minRange, int maxRange, float attackSpeed, float maxAttackSpeed, float penetration, float critChance,
			float maxCritChance) {
		this.sword = new Sword(weapon, position, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration, critChance, maxCritChance);
	}

	public Weapon getWeapon() {
		return sword;
	}
}
