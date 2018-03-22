package be.kiop.weapons;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StaffTest {
	private Staff staff;

	@Before
	public void before() {
		staff = new Staff("Staff", 8, 14, 5, 3, 7, 2, 8, 10);
	}

	@Test
	public void getName_allIsWell_staffName() {
		assert (staff.getName().equals("Staff"));
	}

	@Test
	public void getDamage_allIsWell_staffDamage() {
		assertEquals(8, staff.getDamage(), 0.1);
	}

	@Test
	public void getRange_allIsWell_staffRange() {
		assertEquals(5, staff.getRange(), 0.1);
	}

	@Test
	public void getAttackSpeed_allIsWell_staffAttackSpeed() {
		assertEquals(2, staff.getAttackSpeed(), 0.1);
	}

	@Test
	public void increaseDamage_allIsWell_staffDamageIncreased() {
		staff.increaseDamage(1);
		assertEquals(9, staff.getDamage(), 0.1);
	}

	@Test
	public void increaseDamage_greaterThanMaxDamage_staffMaxDamage() {
		staff.increaseDamage(10);
		assertEquals(14, staff.getDamage(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseDamage_negativeDamage_exception() {
		staff.increaseDamage(-1);
	}

	@Test
	public void decreaseDamage_allIsWell_staffDamageDecreased() {
		staff.decreaseDamage(1);
		assertEquals(7, staff.getDamage(), 0.1);
	}

	@Test
	public void decreaseDamage_lessThan0_0() {
		staff.decreaseDamage(20);
		assertEquals(0, staff.getDamage(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseDamage_negativeDamage_exception() {
		staff.decreaseDamage(-1);
	}

	@Test
	public void increaseRange_allIsWell_staffRangeIncreased() {
		staff.increaseRange(1);
		assertEquals(6, staff.getRange(), 0.1);
	}

	@Test
	public void increaseRange_greaterThanMaxRange_staffMaxRange() {
		staff.increaseRange(10);
		assertEquals(7, staff.getRange(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseRange_negativeRange_exception() {
		staff.increaseRange(-1);
	}

	@Test
	public void decreaseRange_allIsWell_staffRangeDecreased() {
		staff.decreaseRange(1);
		assertEquals(4, staff.getRange(), 0.1);
	}

	@Test
	public void decreaseRange_lessThan0_staffMinRange() {
		staff.decreaseRange(10);
		assertEquals(3, staff.getRange(), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseRange_negativeRange_exception() {
		staff.decreaseRange(-1);
	}
	
	@Test
	public void increaseAttackSpeed_allIsWell_staffAttackSpeedIncreased() {
		staff.increaseAttackSpeed(1);
		assertEquals(3, staff.getAttackSpeed(), 0.1);
	}
	
	@Test
	public void increaseAttackSpeed_greaterThanMaxAttackSpeed_staffMaxAttackSpeed() {
		staff.increaseAttackSpeed(10);
		assertEquals(8, staff.getAttackSpeed(), 0.1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void increaseAttackSpeed_negativeAttackSpeed_exception() {
		staff.increaseAttackSpeed(-1);
	}
	
	@Test
	public void decreaseAttackSpeed_allIsWell_staffAttackSpeedDecreased() {
		staff.decreaseAttackSpeed(1);
		assertEquals(1, staff.getAttackSpeed(), 0.1);
	}
	
	@Test
	public void decreaseAttackSpeed_lessThan0_0() {
		staff.decreaseAttackSpeed(10);
		assertEquals(0, staff.getAttackSpeed(), 0.1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void decreaseAttackSpeed_negativeRange_exception() {
		staff.decreaseAttackSpeed(-1);
	}
}
