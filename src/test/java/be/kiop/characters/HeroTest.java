package be.kiop.characters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import be.kiop.exceptions.CharacterDiedException;
import be.kiop.weapons.Sword;
import be.kiop.weapons.SwordBuilder;

public class HeroTest {
	private Hero hero;
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
		hero = new Hero("Jean", 100, sword, 100);
	}
	
//	@Test(expected = Error.class)
//	public void cantBeInstanciated() {
//		new GameCharacter();
//	}
	
	@Test
	public void getHealth_allIsWell_heroHealth() {
		assertEquals(100, hero.getHealth(),0.1);
	}
	
	@Test(expected=CharacterDiedException.class)
	public void loseHealth_moreThanHeroLife_Exception() {
		hero.loseHealth(101);
	}
	
	@Test
	public void loseHealth_lessThanHeroLife_remainingHealth() {
		hero.loseHealth(99);
		assertEquals(1, hero.getHealth(), 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loseHealth_negativeAmount_Exception() {
		hero.loseHealth(-1);
	}
	
	@Test
	public void getWeapon_allIsWell_heroWeapon() {
		assertEquals(sword , hero.getWeapon());
	}
	
}
