package be.kiop.characters.ennemies;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.items.Droppable;
import be.kiop.items.Dropper;

public abstract class Ennemy extends GameCharacter implements Dropper{
	private Set<Droppable> droppables;
	
	@Override
	public Optional<Droppable> getDrop() {
		return droppables.stream().skip(new Random().nextInt(droppables.size())).findFirst();
	}

	@Override
	public void setDroppables(Set<Droppable> droppables) {
		if(droppables == null) {
			throw new IllegalArgumentException();
		}
		this.droppables = droppables;
	}
}
