package be.kiop.characters.ennemies.skeletons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.items.Droppable;
import be.kiop.textures.Skeletons;
import be.kiop.textures.Texture;
import be.kiop.textures.Weapons;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;

public class Skeleton extends Ennemy {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(Skeletons.values()).collect(Collectors.toSet());
	private final static Set<Weapons> availableWeapons = Set.of(Weapons.Bone);

	public Skeleton(Skeletons skeleton, Position position, String name, float health, Weapon weapon, int level, float armor,
			Set<Droppable> droppables) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(skeleton);
		super.setPosition(position);
		super.setName(name);
		super.setLevel(level);
		super.setHealth(health);
		super.setAvailableWeapons(availableWeapons);
		super.setWeapon(weapon);
		super.setArmor(armor);
		super.setDroppables(droppables);
	}
}
