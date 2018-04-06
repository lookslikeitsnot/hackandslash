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
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public class Skeleton extends Enemy {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(SkeletonTextures.values()).collect(Collectors.toSet());
	private final static Set<WeaponTextures> availableWeapons = Set.of(WeaponTextures.Bone);

	public Skeleton(SkeletonTextures skeletonTexture, Position position, Tile tile, String name, float health, Weapon weapon, int level, float armor,
			Set<Drop> droppables) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(skeletonTexture);
		super.setPosition(position);
		super.setTile(tile);
		super.setName(name);
		super.setLevel(level);
		super.setHealth(health);
		super.setAvailableWeapons(availableWeapons);
		super.setWeapon(weapon);
		super.setArmor(armor);
		super.setDroppables(droppables);
		super.setId(Enemy.counter++);
	}

	@Override
	public Drawable copy() {
		try {
			return (Skeleton) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
