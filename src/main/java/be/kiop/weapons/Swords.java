package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public enum Swords {
	Sword_1(WeaponTextures.Sword, Tile.ORIGIN, "Staff", 10, 20, 2, 1, 3, 1, 2, 0, 0, 0),
	Sword_5(WeaponTextures.Sword, Tile.ORIGIN, "Staff", 50, 100, 3, 1, 3, 1, 2, 0, 0, 0);

	private final Sword sword;

	Swords(WeaponTextures weapon, Tile tile, String name, float damage, float maxDamage, int range,
			int minRange, int maxRange, float attackSpeed, float maxAttackSpeed, float penetration, float critChance,
			float maxCritChance) {
		this.sword = new Sword(weapon, tile, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration, critChance, maxCritChance);
	}

	public Weapon getWeapon() {
		return sword;
	}
}
