package be.kiop.characters.enemies;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.IllegalDropSetException;
import be.kiop.items.Drop;
import be.kiop.items.Dropper;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.utils.SetUtils;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Weapon;

public abstract class Enemy extends GameCharacter implements Dropper {
	private final int id;
	private final Set<Drop> droppables;

	private boolean active;

	private static int counter = 0;

	public Enemy(Set<Texture> availableTextures, Texture texture, Tile tile, String name,
			Set<WeaponTextures> availableWeapons, float health, Weapon weapon, int level, float armor,
			Set<Drop> droppables) {
		super(availableTextures, texture, tile, name, availableWeapons, health, weapon, level, armor);
		this.id = counter++;
		if (!SetUtils.isValidSet(droppables)) {
			throw new IllegalDropSetException();
		}
		this.droppables = droppables;
		this.active = false;
	}
	
	public int getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public Optional<Drop> getDrop() {
		return droppables.stream().skip(new Random().nextInt(droppables.size())).findFirst();
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
}
