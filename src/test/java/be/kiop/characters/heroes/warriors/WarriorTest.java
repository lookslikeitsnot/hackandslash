package be.kiop.characters.heroes.warriors;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.characters.GameCharacter;
import be.kiop.characters.enemies.skeletons.Skeletons;
import be.kiop.characters.heroes.Hero;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.LostALifeException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.HitBoxTexture;
import be.kiop.textures.Texture;
import be.kiop.textures.WarriorTextures;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Staffs;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public class WarriorTest {
	private Warrior hero;
	private Weapon weapon;
	private Tile tile;

	private final static float MARGIN = 0.1F;

	private final static WarriorTextures HERO_TEXTURE = WarriorTextures.Warrior_FEMALE_SOUTH_2;
	private final static WarriorTextures HERO_NEXT_TEXTURE = WarriorTextures.Warrior_FEMALE_SOUTH_1;
	private final static WarriorTextures VALID_TEXTURE = WarriorTextures.Warrior_MALE_SOUTH_2;
	private final static FloorTextures INVALID_TEXTURE = FloorTextures.Floor_Parquet_HORIZONTAL;
	private final static String HERO_NAME = "Warrior";
	private final static float HERO_HEALTH = 100;
	private final static int HERO_LEVEL = 10;
	private final static int HERO_LIVES = 4;
	private final static float HERO_ARMOR = 50;
	private final static float HERO_SHIELD = 10;

	@Before
	public void before() {
		weapon = Swords.Sword_1.getWeapon();
		tile = new Tile(1, 1);
		hero = new Warrior(HERO_TEXTURE, tile, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
				HERO_SHIELD);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null_IllegalArgument() {
		new Warrior(HERO_TEXTURE, tile, null, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES, HERO_SHIELD);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_invalid_IllegalArgument() {
		new Warrior(HERO_TEXTURE, tile, " ", HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES, HERO_SHIELD);
	}

	@Test
	public void getTexture_nA_gameCharacterTexture() {
		assertEquals(HERO_TEXTURE, hero.getTexture());
	}

	@Test
	public void setTexture_valid_gameCharacterTextureChanged() {
		hero.setTexture(VALID_TEXTURE);
		assertEquals(VALID_TEXTURE, hero.getTexture());
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_unvalid_skinNotFoundException() {
		hero.setTexture(INVALID_TEXTURE);
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_null_skinNotFoundException() {
		Texture newTexture = null;
		hero.setTexture(newTexture);
	}

//	@Test
//	public void moveLeft_nA_gameCharacterPositionXMinus1() {
//		hero.moveWEST();
//		assertEquals(Board.MAX_SIZE.getWidth() / 2 - 1, hero.getAbsolutePosition().getX());
//	}
//
//	@Test(expected = OutOfBoardException.class)
//	public void moveLeft_untilOOB_OutOfBoardException() {
//		IntStream.range(0, Board.MAX_SIZE.getWidth() + 1).forEach(iteration -> hero.moveWEST());
//	}
//
//	@Test
//	public void moveRight_nA_gameCharacterPositionXPlus1() {
//		hero.moveEAST();
//		assertEquals(Board.MAX_SIZE.getWidth() / 2 + 1, hero.getAbsolutePosition().getX());
//	}
//
//	@Test(expected = OutOfBoardException.class)
//	public void moveRight_untilOOB_OutOfBoardException() {
//		IntStream.range(0, Board.MAX_SIZE.getWidth() + 1).forEach(iteration -> hero.moveEAST());
//	}
//
//	@Test
//	public void moveUp_nA_gameCharacterPositionYMinus1() {
//		hero.moveNORTH();
//		assertEquals(Board.MAX_SIZE.getHeight() / 2 - 1, hero.getAbsolutePosition().getY());
//	}
//
//	@Test(expected = OutOfBoardException.class)
//	public void moveUp_untilOOB_OutOfBoardException() {
//		IntStream.range(0, Board.MAX_SIZE.getHeight() + 1).forEach(iteration -> hero.moveNORTH());
//	}
//
//	@Test
//	public void moveDown_nA_gameCharacterPositionYPlus1() {
//		hero.moveSOUTH();
//		assertEquals(Board.MAX_SIZE.getHeight() / 2 + 1, hero.getAbsolutePosition().getY());
//	}
//
//	@Test(expected = OutOfBoardException.class)
//	public void moveDown_untilOOB_OutOfBoardException() {
//		IntStream.range(0, Board.MAX_SIZE.getWidth() + 1).forEach(iteration -> hero.moveSOUTH());
//	}

//	@Test
//	public void teleport_validPosition_gameCharacterPositionChanged() {
//		hero.teleport(1, 1);
//		assertEquals(new Position(1, 1), hero.getAbsolutePosition());
//	}
//
//	@Test(expected = OutOfBoardException.class)
//	public void teleport_invalidPosition_OutOfBoardException() {
//		hero.teleport(-1, -1);
//	}

	@Test
	public void getName_nA_heroName() {
		assert (hero.getName().equals(HERO_NAME));
	}

	@Test
	public void getHealth_nA_heroHealth() {
		assertEquals(HERO_HEALTH, hero.getHealth(), MARGIN);
	}

	@Test(expected = LostALifeException.class)
	public void takeFlatDamage_moreThanHeroHealth_Exception() {
		hero.takeFlatDamage(HERO_HEALTH + 1);
	}

	@Test
	public void takeFlatDamage_lessThanHeroHealth_remainingHealth() {
		hero.takeFlatDamage(HERO_HEALTH - 1);
		assertEquals(1, hero.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeFlatDamage_negativeAmount_Exception() {
		hero.takeFlatDamage(-1);
	}

	@Test(expected = LostALifeException.class)
	public void takeDamage_moreThanHeroHealth_Exception() {
		hero.takeDamage(HERO_HEALTH * 100 / HERO_ARMOR + HERO_HEALTH);
	}

	@Test
	public void takeDamage_lessThanHeroShield_remainingHealth() {
		hero.takeDamage(HERO_SHIELD);
		assertEquals(HERO_HEALTH, hero.getHealth(), MARGIN);
	}

	@Test
	public void takeDamage_lessThanHeroHealth_remainingHealth() {
		hero.takeDamage(HERO_SHIELD + HERO_HEALTH);
		assertEquals(HERO_HEALTH * HERO_ARMOR / 100, hero.getHealth(), MARGIN);

		// HERO_HEALTH-(HERO_HEALTH*(1-HERO_ARMOR/100))
		// HERO_HEALTH(1-(1-HERO_ARMOR/100))
		// HERO_HEALTH(HERO_ARMOR/100)
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeAmount_Exception() {
		hero.takeDamage(-1);
	}

	@Test(expected = LostALifeException.class)
	public void takeDamage_moreThanHeroLifeAndPenetration_Exception() {
		hero.takeDamage(HERO_HEALTH, HERO_ARMOR + 1);
	}

	@Test
	public void takeDamage_lessThanHeroLifeAndPenetration_remainingHealth() {
		hero.takeDamage(HERO_HEALTH - 1, HERO_ARMOR);
		assertEquals(1, hero.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeDamageAndPositivePenetration_Exception() {
		hero.takeDamage(-1, HERO_HEALTH / 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_positiveDamageAndNegativePenetration_Exception() {
		hero.takeDamage(1, -HERO_HEALTH / 2);
	}

	@Test
	public void heal_moreThanHeroLife_maxHealth() {
		hero.heal(hero.getMaxHealth() + 1);
		assertEquals(hero.getMaxHealth(), hero.getHealth(), MARGIN);
	}

	@Test
	public void heal_lessThanHeroLife_heroHealth() {
		hero.heal(1);
		assertEquals(HERO_HEALTH + 1, hero.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void heal_negativeAmount_Exception() {
		hero.heal(-1);
	}

	@Test
	public void getWeapon_nA_heroWeapon() {
		assertEquals(weapon, hero.getWeapon());
	}

	@Test
	public void changeWeapon_validWeapon_heroWeapon() {
		hero.changeWeapon(new Fist());
		assertEquals(new Fist(), hero.getWeapon());
	}

	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_invalidWeapon_exception() {
		hero.changeWeapon(Staffs.Staff_1.getWeapon());
	}

	@Test(expected = IllegalWeaponException.class)
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
		assertEquals(HERO_LEVEL, hero.getLevel());
	}

	@Test
	public void increaseLevel_nA_heroLevelIncreased() {
		hero.increaseLevel();
		assertEquals(HERO_LEVEL + 1, hero.getLevel());
	}

	@Test(expected = MaxLevelReachedException.class)
	public void increaseLevel_moreThanMaxLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> hero.increaseLevel());
	}

	@Test
	public void decreaseLevel_nA_heroLevelDecreased() {
		hero.decreaseLevel();
		assertEquals(HERO_LEVEL - 1, hero.getLevel());
	}

	@Test(expected = MinLevelReachedException.class)
	public void decreaseLevel_lessThanMinLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> hero.decreaseLevel());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLevel_lessThan0_IllegalArgument() {
		hero.setLevel(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLevel_moreThanMaxLevel_IllegalArgument() {
		hero.setLevel(GameCharacter.MAX_LEVEL + 1);
	}

	@Test
	public void getLives_nA_heroLives() {
		assertEquals(HERO_LIVES, hero.getLives());
	}

	@Test
	public void increaseLives_1_heroLivesIncremented() {
		hero.increaseLives(1);
		assertEquals(HERO_LIVES + 1, hero.getLives());
	}

	@Test
	public void decreaseLives_nA_heroLivesDecreased() {
		hero.decreaseLives();
		assertEquals(HERO_LIVES - 1, hero.getLives());
	}

	@Test(expected = OutOfLivesException.class)
	public void decreaseLives_nA_exception() {
		IntStream.range(0, HERO_LIVES + 1).forEach(val -> hero.decreaseLives());
	}

	@Test
	public void getExperience_nA_heroExperience() {
		assertEquals(0, hero.getExperience(), MARGIN);
	}

	@Test
	public void increaseExperience_notEnoughForNewLevel_heroExperienceIncreased() {
		hero.increaseExperience(HERO_LEVEL * 100 - 1);
		assertEquals(HERO_LEVEL * 100 - 1, hero.getExperience(), MARGIN);
	}

	@Test
	public void increaseExperience_enoughForTwoNewLevels_heroExperienceIncreased() {
		hero.increaseExperience((2 * HERO_LEVEL + 1) * 100);
		assertEquals(0, hero.getExperience(), MARGIN);
		assertEquals(HERO_LEVEL + 2, hero.getLevel());
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseExperience_negativeAmount_exception() {
		hero.increaseExperience(-1);
	}

	@Test
	public void getShield_nA_heroShield() {
		assertEquals(HERO_SHIELD, hero.getShield(), MARGIN);
	}

	@Test
	public void getArmor_nA_heroArmor() {
		assertEquals(HERO_ARMOR, hero.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_lessThanZero_0() {
		hero.setArmor(-1);
		assertEquals(0, hero.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_moreThanMaxArmor_maxArmor() {
		hero.setArmor(GameCharacter.MAX_ARMOR + 1);
		assertEquals(GameCharacter.MAX_ARMOR, hero.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_allIsWell_heroArmorIncreased() {
		hero.setArmor(HERO_ARMOR + 1);
		assertEquals(HERO_ARMOR + 1, hero.getArmor(), MARGIN);
	}

	@Test
	public void attack_enemy_enemyTakesDamage() {
		GameCharacter gc = Skeletons.Skeleton_1.getGameCharacter();
		float gcHealth = gc.getHealth();
		hero.attack(gc);
		assertEquals(gcHealth - weapon.getDamage(), gc.getHealth(), MARGIN);
	}

//	@Test(expected = IllegalArgumentException.class)
//	public void setAvailableWeapons_null_IllegalArgument() {
//		hero.setAvailableWeapons(null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setAvailableWeapons_emptySet_IllegalArgument() {
//		hero.setAvailableWeapons(new LinkedHashSet<>());
//	}

	@Test
	public void move_validMoveEast_GameCharacterMoved() {
		Position pos = new Position(hero.getAbsoluteCenterPosition().getX(), hero.getAbsoluteCenterPosition().getY());
		hero.move(Directions.EAST, new LinkedHashSet<>());
		assertEquals(pos.getX() + GameCharacter.SPEED, hero.getAbsoluteCenterPosition().getX());
		assertEquals(pos.getY(), hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void move_validMoveSouth_GameCharacterMoved() {
		Position pos = new Position(hero.getAbsoluteCenterPosition().getX(), hero.getAbsoluteCenterPosition().getY());
		hero.move(Directions.SOUTH, new LinkedHashSet<>());
		assertEquals(pos.getX(), hero.getAbsoluteCenterPosition().getX());
		assertEquals(pos.getY() + GameCharacter.SPEED, hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void move_validMoveWest_GameCharacterMoved() {
		Position pos = new Position(hero.getAbsoluteCenterPosition().getX(), hero.getAbsoluteCenterPosition().getY());
		hero.move(Directions.WEST, new LinkedHashSet<>());
		assertEquals(pos.getX() - GameCharacter.SPEED, hero.getAbsoluteCenterPosition().getX());
		assertEquals(pos.getY(), hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void move_validMoveNorth_GameCharacterMoved() {
		Position pos = new Position(hero.getAbsoluteCenterPosition().getX(), hero.getAbsoluteCenterPosition().getY());
		hero.move(Directions.NORTH, new LinkedHashSet<>());
		assertEquals(pos.getX(), hero.getAbsoluteCenterPosition().getX());
		assertEquals(pos.getY() - GameCharacter.SPEED, hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void move_cannotMoveNorth_GameCharacterNotMoved() {
		int deltaY = ((HitBoxTexture) hero.getTexture()).getHitBoxSize().getHeight() / 2;
		Position pos = new Position(hero.getAbsoluteCenterPosition().getX(), hero.getAbsoluteCenterPosition().getY());
		Position posNorth = new Position(pos.getX(), pos.getY() -deltaY - 1);
		hero.move(Directions.NORTH, Set.of(posNorth));
		assertEquals(pos.getX(), hero.getAbsoluteCenterPosition().getX());
		assertEquals(pos.getY(), hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void setNextTexture_nA_GameCharacterHasNextTexture() {
		hero.move(Directions.SOUTH, new LinkedHashSet<>());
		hero.setNextTexture();
		assertEquals(HERO_NEXT_TEXTURE, hero.getTexture());
	}

	@Test(expected = OutOfLivesException.class)
	public void decreaseLives_lessThanOne_IllegalArgument() {
		IntStream.range(0, HERO_LIVES).forEach(i -> hero.decreaseLives());
	}

	@Test
	public void setLives_moreThanMax_MaxLives() {
		hero.increaseLives(Hero.MAX_LIVES + 1);
		assertEquals(Hero.MAX_LIVES, hero.getLives());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setExperience_lessThanZero_IllegalArgument() {
		hero.increaseExperience(-1);
	}

	@Test(expected = CharacterDiedException.class)
	public void setHealth_0andNoLivesLeft_CharacterDied() {
		IntStream.range(0, HERO_LIVES-1).forEach(i -> hero.decreaseLives());
		hero.setHealth(0);
	}
}
