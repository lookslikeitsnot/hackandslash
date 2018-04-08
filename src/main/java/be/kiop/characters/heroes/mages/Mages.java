package be.kiop.characters.heroes.mages;

import be.kiop.characters.GameCharacter;
import be.kiop.textures.MageTextures;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Staffs;
import be.kiop.weapons.Weapon;

public enum Mages {
	Mage_1_F(MageTextures.Mage_FEMALE_SOUTH_2, Tile.ORIGIN, "Female Mage", 100, Staffs.Staff_1.getWeapon(), 1, 0, 3,
			100),
	Mage_1_M(MageTextures.Mage_MALE_SOUTH_2, Tile.ORIGIN, "Male Mage", 100, Staffs.Staff_1.getWeapon(), 1, 0, 3, 100),
	Mage_5_F(MageTextures.Mage_FEMALE_SOUTH_2, Tile.ORIGIN, "Female Mage", 500, Staffs.Staff_5.getWeapon(), 5, 0, 3,
			100),
	Mage_5_M(MageTextures.Mage_MALE_SOUTH_2, Tile.ORIGIN, "Male Mage", 500, Staffs.Staff_5.getWeapon(), 5, 0, 3, 100);

	private Mage mage;

	Mages(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor, int lives,
			float mana) {
		this.mage = new Mage(texture, tile, name, health, weapon, level, armor, lives, mana);
	}

	public GameCharacter getGameCharacter() {
		return mage;
	}
}
