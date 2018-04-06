package be.kiop.weapons;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.textures.Texture;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;

public class Fist extends Weapon {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WeaponTextures.values()).collect(Collectors.toSet());
	public Fist() {
		super("Fist", 1, 1, 1, 4);
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(WeaponTextures.Fist);
		super.setPosition(Position.ORIGIN);
		super.setDamage(1);
		super.setRange(10);
		super.setAttackSpeed(1);
		super.setPenetration(0);
	}

	@Override
	public Drawable copy() {
		try {
			return (Fist) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
