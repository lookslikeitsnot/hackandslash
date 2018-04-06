package be.kiop.weapons;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import be.kiop.textures.WeaponTextures;

public class WeaponsTest {
	@Test
	public void allValues_nA_exist() {
		assertNotNull(WeaponTextures.values());
		assertNotNull(WeaponTextures.valueOf("Bone"));
	}
}
