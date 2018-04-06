package be.kiop.weapons;

import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;

public enum Staffs {
	Staff_1(WeaponTextures.Staff, Position.ORIGIN, "Staff", 10, 20, 100, 100, 200, 1, 2, 0, 0),
	Staff_5(WeaponTextures.Staff, Position.ORIGIN, "Staff", 50, 100, 100, 100, 200, 1, 2, 0, 0);
	
	private Staff staff;
	
	Staffs(WeaponTextures weapon, Position position, String name, float damage, float maxDamage, int range, int minRange, int maxRange,
			float attackSpeed, float maxAttackSpeed, float penetration, float manaCost){
		this.staff = new Staff(weapon, position, name, damage, maxDamage, range, minRange, maxRange,
				attackSpeed, maxAttackSpeed, penetration, manaCost);
	}
	
	public Weapon getWeapon() {
		return staff;
	}
}
