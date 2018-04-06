package be.kiop.characters.heroes.warriors;

import be.kiop.characters.GameCharacter;
import be.kiop.textures.WarriorTextures;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public enum Warriors {
	Warrior_1_F(WarriorTextures.Warrior_FEMALE_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Female Warrior", 100, Swords.Sword_1.getWeapon(), 1, 0, 3,0,100),
	Warriore_1_M(WarriorTextures.Warrior_MALE_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Male Warrior", 100, Swords.Sword_1.getWeapon(), 1, 0, 3,0,100),
	Warrior_5_F(WarriorTextures.Warrior_FEMALE_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Female Warrior", 500, Swords.Sword_5.getWeapon(), 5, 0, 3,0,100),
	Warrior_5_M(WarriorTextures.Warrior_MALE_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Male Warrior", 500, Swords.Sword_5.getWeapon(), 5, 0, 3,0,100);
	
	
	private Warrior warrior;
	
	Warriors(WarriorTextures warrior, Position position, Tile tile, String name, float health, Weapon weapon, int level, float armor, int lives, float experience,
			float shield){
		this.warrior = new Warrior(warrior, position, tile, name, health, weapon, level, armor, lives, experience, shield);
	}
	
	public GameCharacter getGameCharacter() {
		return warrior;
	}
}
