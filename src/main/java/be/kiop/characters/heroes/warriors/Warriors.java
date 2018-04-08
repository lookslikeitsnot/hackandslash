package be.kiop.characters.heroes.warriors;

import be.kiop.characters.GameCharacter;
import be.kiop.textures.Texture;
import be.kiop.textures.WarriorTextures;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public enum Warriors {
	Warrior_1_F(WarriorTextures.Warrior_FEMALE_SOUTH_2, Tile.ORIGIN, "Female Warrior", 100, Swords.Sword_1.getWeapon(),
			1, 0, 3, 100),
	Warriore_1_M(WarriorTextures.Warrior_MALE_SOUTH_2, Tile.ORIGIN, "Male Warrior", 100, Swords.Sword_1.getWeapon(), 1,
			0, 3, 100),
	Warrior_5_F(WarriorTextures.Warrior_FEMALE_SOUTH_2, Tile.ORIGIN, "Female Warrior", 500, Swords.Sword_5.getWeapon(),
			5, 0, 3, 100),
	Warrior_5_M(WarriorTextures.Warrior_MALE_SOUTH_2, Tile.ORIGIN, "Male Warrior", 500, Swords.Sword_5.getWeapon(), 5,
			0, 3, 100);

	private Warrior warrior;

	Warriors(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor, int lives,
			float shield) {
		this.warrior = new Warrior(texture, tile, name, health, weapon, level, armor, lives, shield);
	}

	public GameCharacter getGameCharacter() {
		return warrior;
	}
}
