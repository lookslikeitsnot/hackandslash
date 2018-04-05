package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.textures.Texture;
import be.kiop.textures.Weapons;
import be.kiop.valueobjects.Position;

public class Bone extends Weapon{
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(Weapons.values()).collect(Collectors.toSet());
	
	public Bone(Weapons weapon, Position position, String name, float damage, float maxDamage, int range, int minRange,
			int maxRange, float attackSpeed, float maxAttackSpeed, float penetration) {
		super(name, maxDamage, minRange, maxRange, maxAttackSpeed);
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(weapon);
		super.setPosition(position);
		super.setDamage(damage);
		super.setRange(range);
		super.setAttackSpeed(attackSpeed);
		super.setPenetration(penetration);
	}
	
//	public Bone() {
//		super("Bone", 10, 5, 5, 2);
//		setTexture(Weapons.Bone);
//	}
}
