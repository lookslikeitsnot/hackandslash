package be.kiop.characters.enemies.skeletons;

import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.items.Drop;
import be.kiop.textures.SkeletonTextures;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Bones;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public enum Skeletons {
	Skeleton_1(SkeletonTextures.Skeleton_SOUTH_2, Tile.ORIGIN, "Skeleton", 100, Bones.Bone_1.getWeapon(), 1, 0,
			Set.of(Swords.Sword_1.getWeapon())),
	Skeleton_Dog_1(SkeletonTextures.Skeleton_Dog_SOUTH_2, Tile.ORIGIN, "Skeleton Dog", 100, Bones.Bone_1.getWeapon(), 1,
			0, Set.of(Swords.Sword_1.getWeapon()));

	private final Skeleton skeleton;

	Skeletons(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			Set<Drop> droppables) {
		this.skeleton = new Skeleton(texture, tile, name, health, weapon, level, armor, droppables);
	}

	public GameCharacter getGameCharacter() {
		return skeleton;
	}
}
