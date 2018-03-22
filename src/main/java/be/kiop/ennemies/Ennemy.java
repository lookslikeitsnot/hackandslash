package be.kiop.ennemies;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.items.Droppable;
import be.kiop.weapons.Weapon;
import be.kiop.weapons.Weapons;

public abstract class Ennemy extends GameCharacter{
	Set<Droppable> droppables;
	
	public Ennemy(String name, float health, Weapon weapon, Set<Weapons> availableWeapons, int level, float armor, Set<Droppable> droppables) {
		super(name, health, weapon, availableWeapons, level, armor);
		this.droppables = droppables;
	}
	
	public Optional<Droppable> getDrop() {
		return droppables.stream().skip(new Random().nextInt(droppables.size())).findFirst();
	}
}
