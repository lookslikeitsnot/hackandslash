package be.kiop.weapons;

public enum Weapons {
	Sword(Sword.class),
	Staff(Staff.class);
	
	private Class<?> weapon;
	Weapons(Class<?> weapon) {
		this.weapon = weapon;
	}
	
	public Class<?> getWeaponClass() {
		return weapon;
	}
}
