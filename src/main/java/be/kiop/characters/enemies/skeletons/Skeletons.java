package be.kiop.characters.enemies.skeletons;

import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.items.Drop;
import be.kiop.textures.SkeletonTextures;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Bones;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public enum Skeletons {
	Skeleton_1(SkeletonTextures.Skeleton_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Skeleton", 100, Bones.Bone_1.getWeapon(), 1, 0, Set.of(Swords.Sword_1.getWeapon())),
	Skeleton_Dog_1(SkeletonTextures.Skeleton_Dog_SOUTH_2, Position.ORIGIN, Tile.ORIGIN, "Skeleton Dog", 100, Bones.Bone_1.getWeapon(), 1, 0, Set.of(Swords.Sword_1.getWeapon()));
	
	private Skeleton skeleton;
	
	Skeletons(SkeletonTextures skeletonTexture, Position position, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			Set<Drop> droppables){
		this.skeleton = new Skeleton(skeletonTexture, position, tile, name, health, weapon, level, armor, droppables);
	}
	
	public GameCharacter getGameCharacter() {
		return skeleton;
	}
}
