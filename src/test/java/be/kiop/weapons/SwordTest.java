package be.kiop.weapons;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SwordTest {
	private Sword sword;

	@Before
	public void before() {
		SwordBuilder builder = new SwordBuilder();
		sword = builder.withName("épée longue").withDamage(8).withMaxDamage(14).withRange(5).withMinRange(3)
				.withMaxRange(7).withAttackSpeed(2).withMaxAttackSpeed(8).withCritChance(50).withMaxCritChance(100)
				.makeSword();
	}

	@Test
	public void getName_allIsWell_swordName() {
		assert (sword.getName().equals("épée longue"));
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
	public void increaseCritChance_allIsWell_swordCritChanceIncreased() {
		sword.increaseCritChance(1);
		assertEquals(51, sword.getCritChance(), 0.1);
	}

	@Test
	public void increaseCritChance_moreThanMaxCritChance_swordMaxCritChance() {
		sword.increaseCritChance(100);
		assertEquals(100, sword.getCritChance(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseCritChance_negativeCritChance_exception() {
		sword.increaseCritChance(-1);
	}

	@Test
	public void decreaseCritChance_allIsWell_swordCritChanceDecreased() {
		sword.decreaseCritChance(100);
		assertEquals(0, sword.getCritChance(), 0.1);
	}

	@Test
	public void decreaseCritChance_lessThanZeroCritChance_0() {
		sword.decreaseCritChance(100);
		assertEquals(0, sword.getCritChance(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseCritChance_negativeCritChance_exception() {
		sword.decreaseCritChance(-1);
	}

	@Test
	public void increaseDamage_allIsWell_swordDamageIncreased() {
		sword.increaseDamage(1);
		assertEquals(9, sword.getDamage(), 0.1);
	}

	@Test
	public void increaseDamage_greaterThanMaxDamage_swordMaxDamage() {
		sword.increaseDamage(10);
		assertEquals(14, sword.getDamage(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseDamage_negativeDamage_exception() {
		sword.increaseDamage(-1);
	}

	@Test
	public void decreaseDamage_allIsWell_swordDamageDecreased() {
		sword.decreaseDamage(1);
		assertEquals(7, sword.getDamage(), 0.1);
	}

	@Test
	public void decreaseDamage_lessThan0_0() {
		sword.decreaseDamage(20);
		assertEquals(0, sword.getDamage(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseDamage_negativeDamage_exception() {
		sword.decreaseDamage(-1);
	}

	@Test
	public void increaseRange_allIsWell_swordRangeIncreased() {
		sword.increaseRange(1);
		assertEquals(6, sword.getRange(), 0.1);
	}

	@Test
	public void increaseRange_greaterThanMaxRange_swordMaxRange() {
		sword.increaseRange(10);
		assertEquals(7, sword.getRange(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseRange_negativeRange_exception() {
		sword.increaseRange(-1);
	}

	@Test
	public void decreaseRange_allIsWell_swordRangeDecreased() {
		sword.decreaseRange(1);
		assertEquals(4, sword.getRange(), 0.1);
	}

	@Test
	public void decreaseRange_lessThan0_swordMinRange() {
		sword.decreaseRange(10);
		assertEquals(3, sword.getRange(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseRange_negativeRange_exception() {
		sword.decreaseRange(-1);
	}
	
	@Test
	public void increaseAttackSpeed_allIsWell_swordAttackSpeedIncreased() {
		sword.increaseAttackSpeed(1);
		assertEquals(3, sword.getAttackSpeed(), 0.1);
	}
	
	@Test
	public void increaseAttackSpeed_greaterThanMaxAttackSpeed_swordMaxAttackSpeed() {
		sword.increaseAttackSpeed(10);
		assertEquals(8, sword.getAttackSpeed(), 0.1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void increaseAttackSpeed_negativeAttackSpeed_exception() {
		sword.increaseAttackSpeed(-1);
	}
	
	@Test
	public void decreaseAttackSpeed_allIsWell_swordAttackSpeedDecreased() {
		sword.decreaseAttackSpeed(1);
		assertEquals(1, sword.getAttackSpeed(), 0.1);
	}
	
	@Test
	public void decreaseAttackSpeed_lessThan0_0() {
		sword.decreaseAttackSpeed(10);
		assertEquals(0, sword.getAttackSpeed(), 0.1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void decreaseAttackSpeed_negativeRange_exception() {
		sword.decreaseAttackSpeed(-1);
	}
}
