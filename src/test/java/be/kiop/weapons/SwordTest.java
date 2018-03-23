package be.kiop.weapons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class SwordTest {
	private Sword weapon;
	private static final float WEAPON_DAMAGE = 8;
	private static final float WEAPON_MAX_DAMAGE = 14;
	private static final float WEAPON_RANGE = 5;
	private static final float WEAPON_MIN_RANGE = 3;
	private static final float WEAPON_MAX_RANGE = 7;
	private static final float WEAPON_ATTACK_SPEED = 2;
	private static final float WEAPON_MAX_ATTACK_SPEED = 8;
	private static final float WEAPON_PENETRATION = 30;
	private static final float WEAPON_CRIT_CHANCE = 50;
	private static final float WEAPON_MAX_CRIT_CHANCE = 80;
	private static final float MARGIN = 0.1F;

	@Before
	public void before() {
		SwordBuilder builder = new SwordBuilder();
		weapon = builder.withName(Sword.DEFAULT_NAME).withDamage(WEAPON_DAMAGE).withMaxDamage(WEAPON_MAX_DAMAGE)
				.withRange(WEAPON_RANGE).withMinRange(WEAPON_MIN_RANGE).withMaxRange(WEAPON_MAX_RANGE)
				.withAttackSpeed(WEAPON_ATTACK_SPEED).withMaxAttackSpeed(WEAPON_MAX_ATTACK_SPEED)
				.withPenetration(WEAPON_PENETRATION).withCritChance(WEAPON_CRIT_CHANCE).withMaxCritChance(WEAPON_MAX_CRIT_CHANCE).makeSword();
	}

	@Test
	public void getName_allIsWell_swordName() {
		assert (weapon.getName().equals(Sword.DEFAULT_NAME));
	}
	
//	@Test
//	public void setName_validName_swordName() {
//		String newName = "Weapon";
//		weapon.setName(newName);
//		assert(newName.equals(weapon.getName()));
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public void setName_blankName_exception() {
//		String newName = " ";
//		weapon.setName(newName);
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public void setName_nullName_exception() {
//		String newName = null;
//		weapon.setName(newName);
//	}

	@Test
	public void getDamage_allIsWell_swordDamage() {
		assertEquals(WEAPON_DAMAGE, weapon.getDamage(), MARGIN);
	}

	@Test
	public void getRange_allIsWell_swordRange() {
		assertEquals(WEAPON_RANGE, weapon.getRange(), MARGIN);
	}

	@Test
	public void getAttackSpeed_allIsWell_swordAttackSpeed() {
		assertEquals(WEAPON_ATTACK_SPEED, weapon.getAttackSpeed(), MARGIN);
	}

	@Test
	public void getCritChance_allIsWell_swordCritChance() {
		assertEquals(WEAPON_CRIT_CHANCE, weapon.getCritChance(), MARGIN);
	}

	@Test
	public void increaseCritChance_allIsWell_swordCritChanceIncreased() {
		weapon.increaseCritChance(1);
		assertEquals(WEAPON_CRIT_CHANCE+1, weapon.getCritChance(), MARGIN);
	}

	@Test
	public void increaseCritChance_moreThanMaxCritChance_swordMaxCritChance() {
		weapon.increaseCritChance(WEAPON_MAX_CRIT_CHANCE+1);
		assertEquals(WEAPON_MAX_CRIT_CHANCE, weapon.getCritChance(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseCritChance_negativeCritChance_exception() {
		weapon.increaseCritChance(-1);
	}

	@Test
	public void decreaseCritChance_allIsWell_swordCritChanceDecreased() {
		weapon.decreaseCritChance(WEAPON_CRIT_CHANCE-1);
		assertEquals(1, weapon.getCritChance(), MARGIN);
	}

	@Test
	public void decreaseCritChance_lessThanZeroCritChance_0() {
		weapon.decreaseCritChance(WEAPON_MAX_CRIT_CHANCE+1);
		assertEquals(0, weapon.getCritChance(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseCritChance_negativeCritChance_exception() {
		weapon.decreaseCritChance(-1);
	}

	@Test
	public void increaseDamage_allIsWell_swordDamageIncreased() {
		weapon.increaseDamage(1);
		assertEquals(WEAPON_DAMAGE + 1, weapon.getDamage(), MARGIN);
	}

	@Test
	public void increaseDamage_greaterThanMaxDamage_swordMaxDamage() {
		weapon.increaseDamage(WEAPON_MAX_DAMAGE+1);
		assertEquals(WEAPON_MAX_DAMAGE, weapon.getDamage(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseDamage_negativeDamage_exception() {
		weapon.increaseDamage(-1);
	}

	@Test
	public void decreaseDamage_allIsWell_swordDamageDecreased() {
		weapon.decreaseDamage(1);
		assertEquals(WEAPON_DAMAGE - 1, weapon.getDamage(), MARGIN);
	}

	@Test
	public void decreaseDamage_lessThan0_0() {
		weapon.decreaseDamage(WEAPON_MAX_DAMAGE+1);
		assertEquals(0, weapon.getDamage(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseDamage_negativeDamage_exception() {
		weapon.decreaseDamage(-1);
	}

	@Test
	public void increaseRange_allIsWell_swordRangeIncreased() {
		weapon.increaseRange(1);
		assertEquals(WEAPON_RANGE + 1, weapon.getRange(), MARGIN);
	}

	@Test
	public void increaseRange_greaterThanMaxRange_swordMaxRange() {
		weapon.increaseRange(WEAPON_MAX_RANGE + 1);
		assertEquals(WEAPON_MAX_RANGE, weapon.getRange(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseRange_negativeRange_exception() {
		weapon.increaseRange(-1);
	}

	@Test
	public void decreaseRange_allIsWell_swordRangeDecreased() {
		weapon.decreaseRange(1);
		assertEquals(WEAPON_RANGE - 1, weapon.getRange(), MARGIN);
	}

	@Test
	public void decreaseRange_lessThan0_swordMinRange() {
		weapon.decreaseRange(WEAPON_MAX_RANGE+1);
		assertEquals(WEAPON_MIN_RANGE, weapon.getRange(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseRange_negativeRange_exception() {
		weapon.decreaseRange(-1);
	}

	@Test
	public void increaseAttackSpeed_allIsWell_swordAttackSpeedIncreased() {
		weapon.increaseAttackSpeed(1);
		assertEquals(WEAPON_ATTACK_SPEED + 1, weapon.getAttackSpeed(), MARGIN);
	}

	@Test
	public void increaseAttackSpeed_greaterThanMaxAttackSpeed_swordMaxAttackSpeed() {
		weapon.increaseAttackSpeed(WEAPON_MAX_ATTACK_SPEED + 1);
		assertEquals(WEAPON_MAX_ATTACK_SPEED, weapon.getAttackSpeed(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseAttackSpeed_negativeAttackSpeed_exception() {
		weapon.increaseAttackSpeed(-1);
	}

	@Test
	public void decreaseAttackSpeed_allIsWell_swordAttackSpeedDecreased() {
		weapon.decreaseAttackSpeed(1);
		assertEquals(WEAPON_ATTACK_SPEED - 1, weapon.getAttackSpeed(), MARGIN);
	}

	@Test
	public void decreaseAttackSpeed_lessThan0_0() {
		weapon.decreaseAttackSpeed(WEAPON_ATTACK_SPEED + 1);
		assertEquals(0, weapon.getAttackSpeed(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseAttackSpeed_negativeRange_exception() {
		weapon.decreaseAttackSpeed(-1);
	}
	
	@Test
	public void getPenetration_nA_weaponPenetration() {
		assertEquals(WEAPON_PENETRATION, weapon.getPenetration(), MARGIN);
	}
	
	@Test
	public void setPenetration_lessThanZero_0() {
		weapon.setPenetration(-1);
		assertEquals(0, weapon.getPenetration(),MARGIN);
	}
	
	@Test
	public void setPenetration_moreThanMaxPen_weaponMaxPenetration() {
		weapon.setPenetration(Weapon.MAX_PENETRATION+1);
		assertEquals(Weapon.MAX_PENETRATION, weapon.getPenetration(), MARGIN);
	}
	
	@Test
	public void setPenetration_validPenetration_weaponPenetrationChanged() {
		weapon.setPenetration(1);
		assertEquals(1, weapon.getPenetration(), MARGIN);
	}
	
	@Test
	public void hashCode_sameWeaponName_sameHashCode() {
		assertEquals(new Sword().hashCode(), weapon.hashCode());
	}
	
	@Test
	public void equals_null_false() {
		assertFalse(weapon.equals(null));
	}
	
	@Test
	public void equals_sameWeaponName_true() {
		assert(weapon.equals(new Sword()));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equals_differentWeaponType_false() {
		assertFalse(weapon.equals(new Staff()));
	}
	
	@Test
	public void equals_differentObjectType_false() {
		assertFalse(weapon.equals(new Object()));
	}	
}
