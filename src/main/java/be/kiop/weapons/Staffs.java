package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public enum Staffs {
	Staff_1(WeaponTextures.Staff, Tile.ORIGIN, "Staff", 10, 20, 2, 1, 4, 1, 2, 0, 0),
	Staff_5(WeaponTextures.Staff, Tile.ORIGIN, "Staff", 50, 100, 3, 1, 4, 1, 2, 0, 0);
	
	private final Staff staff;
	
	Staffs(WeaponTextures weapon, Tile tile, String name, float damage, float maxDamage, int range, int minRange, int maxRange,
			float attackSpeed, float maxAttackSpeed, float penetration, float manaCost){
		this.staff = new Staff(weapon, tile, name, damage, maxDamage, range, minRange, maxRange,
				attackSpeed, maxAttackSpeed, penetration, manaCost);
	}
	
	public Weapon getWeapon() {
		return staff;
	}
}
