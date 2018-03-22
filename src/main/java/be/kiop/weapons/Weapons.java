package be.kiop.weapons;

public enum Weapons {
	Bone(Bone.class), Fist(Fist.class), Staff(Staff.class), Sword(Sword.class);

	private Class<?> weapon;

	Weapons(Class<?> weapon) {
		this.weapon = weapon;
	}

	public Class<?> getWeaponClass() {
		return weapon;
	}
}
