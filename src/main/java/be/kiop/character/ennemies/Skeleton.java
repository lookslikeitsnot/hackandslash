package be.kiop.character.ennemies;

import java.nio.file.Path;
import java.util.Set;

import be.kiop.items.Droppable;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public class Skeleton extends Ennemy{
	public final static Set<Weapons> availableWeapons = Set.of(Weapons.Bone);

	public Skeleton(Path skinPath, Position position, String name, float health, Weapon weapon, int level, float armor, Set<Droppable> droppables) {
		super(skinPath, position, name, health, weapon, availableWeapons, level, armor, droppables);
	}
	
}
