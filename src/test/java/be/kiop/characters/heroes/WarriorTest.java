package be.kiop.characters.heroes;

import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Staff;
import be.kiop.weapons.Sword;

public class WarriorTest {
	private Hero hero;
	private Sword sword;
	private final static String HERO_NAME = "Warrior";
	
	@Before
	public void before() {
		sword = new Sword();
		hero = new Warrior(HERO_NAME, 100, sword, 4, 50, 5, 200, 10);
	}
	
	@Test
	public void getName_nA_heroName() {
		assert(hero.getName().equals(HERO_NAME));
	}
	
	@Test
	public void getHealth_nA_heroHealth() {
		assertEquals(100, hero.getHealth(),0.1);
	}
	
	@Test(expected=CharacterDiedException.class)
	public void takeFlatDamage_moreThanHeroLife_Exception() {
		hero.takeFlatDamage(101);
	}
	
	@Test
	public void takeFlatDamage_lessThanHeroLife_remainingHealth() {
		hero.takeFlatDamage(50);
		assertEquals(50, hero.getHealth(), 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void takeFlatDamage_negativeAmount_Exception() {
		hero.takeFlatDamage(-1);
	}
		
	@Test(expected=CharacterDiedException.class)
	public void takeDamage_moreThanHeroLife_Exception() {
		hero.takeDamage(1000);
	}
	
	@Test
	public void takeDamage_lessThanHeroLife_remainingHealth() {
		hero.takeDamage(50);
		assertEquals(75, hero.getHealth(), 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void takeDamage_negativeAmount_Exception() {
		hero.takeDamage(-1);
	}
	
	@Test(expected=CharacterDiedException.class)
	public void takeDamage_moreThanHeroLifeAndPenetration_Exception() {
		hero.takeDamage(101, 50);
	}
	
	@Test
	public void takeDamage_lessThanHeroLifeAndPenetration_remainingHealth() {
		hero.takeDamage(50, 50);
		assertEquals(50, hero.getHealth(), 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void takeDamage_negativeAmountAndPenetration_Exception() {
		hero.takeDamage(-1, 50);
	}
	
	@Test
	public void heal_moreThanHeroLife_maxHealth() {
		hero.heal(1000);
		assertEquals(hero.getMaxHealth(), hero.getHealth(), 0.1);
	}
	
	@Test
	public void heal_lessThanHeroLife_heroHealth() {
		hero.heal(50);
		assertEquals(150, hero.getHealth(), 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void heal_negativeAmount_Exception() {
		hero.heal(-1);
	}
	
	@Test
	public void getWeapon_nA_heroWeapon() {
		assertEquals(sword , hero.getWeapon());
	}
	
	@Test
	public void changeWeapon_validWeapon_heroWeapon() {
		hero.changeWeapon(new Fist());
		assertEquals(new Fist(), hero.getWeapon());
	}
	
	@Test(expected=IllegalWeaponException.class)
	public void changeWeapon_invalidWeapon_exception() {
		hero.changeWeapon(new Staff());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void changeWeapon_nullWeapon_exception() {
		hero.changeWeapon(null);
	}
	
	@Test
	public void dropWeapon_nA_fist() {
		hero.dropWeapon();
		assertEquals(new Fist(), hero.getWeapon());
	}
	
	@Test
	public void getLevel_nA_heroLevel() {
		assertEquals(4, hero.getLevel());
	}
	
	@Test
	public void increaseLevel_nA_heroLevelIncreased() {
		hero.increaseLevel();
		assertEquals(5, hero.getLevel());
	}
	
	@Test(expected=MaxLevelReachedException.class)
	public void increaseLevel_moreThanMaxLevel_exception() {
		IntStream.range(0, 100).forEach(val -> hero.increaseLevel());
	}
	
	@Test
	public void decreaseLevel_nA_heroLevelDecreased() {
		hero.decreaseLevel();
		assertEquals(3, hero.getLevel());
	}
	
	@Test(expected=MinLevelReachedException.class)
	public void decreaseLevel_lessThanMinLevel_exception() {
		IntStream.range(0, 100).forEach(val -> hero.decreaseLevel());
	}
	
	@Test
	public void getMana_nA_heroMana() {
		
	}
}
