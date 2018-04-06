package be.kiop.characters.enemies;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.items.Drop;
import be.kiop.items.Dropper;

public abstract class Enemy extends GameCharacter implements Dropper{
	private int id;
	private Set<Drop> droppables;
	private boolean active;
	
	public static int counter = 0;

	@Override
	public Optional<Drop> getDrop() {
		return droppables.stream().skip(new Random().nextInt(droppables.size())).findFirst();
	}

	@Override
	public void setDroppables(Set<Drop> droppables) {
		if(droppables == null) {
			throw new IllegalArgumentException();
		}
		this.droppables = droppables;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
//		System.out.println("testing");
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Enemy))
			return false;
		Enemy other = (Enemy) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
