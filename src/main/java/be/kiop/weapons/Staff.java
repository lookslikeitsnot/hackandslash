package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.exceptions.NegativeManaCostException;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Tile;

public class Staff extends Weapon{
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WeaponTextures.values()).collect(Collectors.toSet());
	private final float manaCost;

	
	
	public Staff(Texture texture, Tile tile, String name, float damage, float maxDamage,
			int range, int minRange, int maxRange, float attackSpeed, float maxAttackSpeed, float penetration,
			float manaCost) {
		super(AVAILABLE_TEXTURES, texture, tile, name, damage, maxDamage, range, minRange, maxRange, attackSpeed,
				maxAttackSpeed, penetration);
		if(manaCost < 0) {
			throw new NegativeManaCostException();
		}
		this.manaCost = manaCost;
	}

	public float getManaCost() {
		return manaCost;
	}

	@Override
	public Drawable copy() {
		try {
			return (Staff) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
