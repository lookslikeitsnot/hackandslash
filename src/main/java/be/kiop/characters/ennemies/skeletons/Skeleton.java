package be.kiop.characters.ennemies.skeletons;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.items.Droppable;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Skeleton extends Ennemy {
	private final static Set<Path> AVAILABLE_SKIN_PATHS = Arrays.stream(Skeletons.values())
			.map(skeleton -> skeleton.getPath()).collect(Collectors.toSet());
	private final static Set<Weapons> availableWeapons = Set.of(Weapons.Bone);

	public Skeleton(Path skinPath, Position position, String name, float health, Weapon weapon, int level, float armor,
			Set<Droppable> droppables) {
		super.setAvailableSkinPaths(AVAILABLE_SKIN_PATHS);
		super.setSkinPath(skinPath);
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
