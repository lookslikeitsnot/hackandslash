package be.kiop.weapons;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SwordTest {
	private Sword sword;
	
	@Before
	public void before() {
		SwordBuilder builder = new SwordBuilder();
		sword = builder.withName("épée longue")
						.withDamage(8)
						.withMaxDamage(14)
						.withRange(5)
						.withMinRange(5)
						.withMaxRange(7)
						.withAttackSpeed(2)
						.withMaxAttackSpeed(8)
						.withCritChance(50)
						.withMaxCritChance(100)
						.makeSword();
	}
	
	@Test
	public void getName_allIsWell_swordName() {
		assert(sword.getName().equals("épée longue"));
	}
	
	@Test
	public void getDamage_allIsWell_swordDamage() {
		assertEquals(8, sword.getDamage(), 0.1);
	}
	
	@Test
	public void getRange_allIsWell_swordRange() {
		assertEquals(5, sword.getRange(), 0.1);
	}
	
	@Test
	public void getAttackSpeed_allIsWell_swordAttackSpeed() {
		assertEquals(2, sword.getAttackSpeed(), 0.1);
	}
	
	@Test
	public void getCritChance_allIsWell_swordCritChance() {
		assertEquals(50, sword.getCritChance(), 0.1);
	}
	
	@Test
	public void increaseCritChance() {
		
	}
}
