package be.kiop.characters.enemies.skeletons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.characters.enemies.Enemy;
import be.kiop.items.Drop;
import be.kiop.textures.SkeletonTextures;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public class Skeleton extends Enemy {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(SkeletonTextures.values())
			.collect(Collectors.toSet());
	private final static Set<WeaponTextures> AVAILABLE_WEAPONS = Set.of(WeaponTextures.Bone);

	public Skeleton(Texture texture, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			Set<Drop> droppables) {
		super(AVAILABLE_TEXTURES, texture, tile, name, AVAILABLE_WEAPONS, health, weapon, level, armor, droppables);
	}
}
