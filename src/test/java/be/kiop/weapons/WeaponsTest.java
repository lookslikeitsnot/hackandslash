package be.kiop.weapons;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class WeaponsTest {
	@Test
	public void allValues_nA_exist() {
		assertNotNull(Weapons.values());
		assertNotNull(Weapons.valueOf("Bone"));
	}
}
