package be.kiop.characters.ennemies.skeletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.OutOfBoardException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.items.Droppable;
import be.kiop.textures.Floors;
import be.kiop.textures.Skeletons;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class SkeletonTest {
	private Skeleton ennemy;
	private Weapon weapon;
	private Position position;

	private final static float MARGIN = 0.1F;

	private final static Skeletons ENNEMY_TEXTURE = Skeletons.Skeleton_NORTH_2;
	private final static Skeletons VALID_TEXTURE = Skeletons.Skeleton_Dog_EAST_2;
	private final static Floors INVALID_TEXTURE = Floors.Floor_Parquet_HORIZONTAL;
	private final static String ENNEMY_NAME = "Skeleton";
	private final static float ENNEMY_HEALTH = 100;
	private final static int ENNEMY_LEVEL = 10;
	private final static float ENNEMY_ARMOR = 50;
	private final static Set<Droppable> ENNEMY_DROPPABLES = Set.of(new Sword());

	@Before
	public void before() {
		weapon = new Bone();
		position = new Position(Board.getSize(true).getWidth()/2, Board.getSize(true).getHeight()/2);
		ennemy = new Skeleton(ENNEMY_TEXTURE, position, ENNEMY_NAME, ENNEMY_HEALTH, weapon, ENNEMY_LEVEL, ENNEMY_ARMOR,
				ENNEMY_DROPPABLES);
	}

	@Test
	public void getTexture_nA_gameCharacterSkin() {
		assertEquals(ENNEMY_TEXTURE, ennemy.getTexture());
	}

	@Test
	public void setTexture_valid_gameCharacterSkinChanged() {
		ennemy.setTexture(VALID_TEXTURE);
		assertEquals(VALID_TEXTURE, ennemy.getTexture());
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_unvalid_skinNotFoundException() {
		ennemy.setTexture(INVALID_TEXTURE);
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_null_skinNotFoundException() {
		Texture newTexture = null;
		ennemy.setTexture(newTexture);
	}

	@Test
	public void moveLeft_nA_gameCharacterPositionXMinus1() {
		ennemy.moveLeft();
		assertEquals(Board.getSize(true).getWidth() / 2 - GameCharacter.SPEED, ennemy.getPosition().getX());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveLeft_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> ennemy.moveLeft());
	}

	@Test
	public void moveRight_nA_gameCharacterPositionXPlus1() {
		ennemy.moveRight();
		assertEquals(Board.getSize(true).getWidth()/2 + GameCharacter.SPEED, ennemy.getPosition().getX());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveRight_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> ennemy.moveRight());
	}

	@Test
	public void moveUp_nA_gameCharacterPositionYMinus1() {
		ennemy.moveUp();
		assertEquals(Board.getSize(true).getHeight()/2 - GameCharacter.SPEED, ennemy.getPosition().getY());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveUp_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getHeight()/2 + 1).forEach(iteration -> ennemy.moveUp());
	}

	@Test
	public void moveDown_nA_gameCharacterPositionYPlus1() {
		ennemy.moveDown();
		assertEquals(Board.getSize(true).getHeight() / 2 + GameCharacter.SPEED, ennemy.getPosition().getY());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveDown_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> ennemy.moveDown());
	}

	@Test
	public void teleport_validPosition_gameCharacterPositionChanged() {
		ennemy.teleport(1, 1);
		assertEquals(new Position(1, 1), ennemy.getPosition());
	}

	@Test(expected = OutOfBoardException.class)
	public void teleport_invalidPosition_OutOfBoardException() {
		ennemy.teleport(-1, -1);
	}

	@Test
	public void getName_nA_heroName() {
		assertTrue(ennemy.getName().equals(ENNEMY_NAME));
	}

	@Test
	public void getHealth_nA_heroHealth() {
		assertEquals(ENNEMY_HEALTH, ennemy.getHealth(), MARGIN);
	}

	@Test(expected = CharacterDiedException.class)
	public void takeFlatDamage_moreThanHeroHealth_Exception() {
		ennemy.takeFlatDamage(ENNEMY_HEALTH + 1);
	}

	@Test
	public void takeFlatDamage_lessThanHeroHealth_remainingHealth() {
		ennemy.takeFlatDamage(ENNEMY_HEALTH - 1);
		assertEquals(1, ennemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeFlatDamage_negativeAmount_Exception() {
		ennemy.takeFlatDamage(-1);
	}

	@Test(expected = CharacterDiedException.class)
	public void takeDamage_moreThanHeroHealth_Exception() {
		ennemy.takeDamage(ENNEMY_HEALTH * 100 / ENNEMY_ARMOR + ENNEMY_HEALTH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeAmount_Exception() {
		ennemy.takeDamage(-1);
	}

	@Test(expected = CharacterDiedException.class)
	public void takeDamage_moreThanHeroLifeAndPenetration_Exception() {
		ennemy.takeDamage(ENNEMY_HEALTH, ENNEMY_ARMOR + 1);
	}

	@Test
	public void takeDamage_lessThanHeroLifeAndPenetration_remainingHealth() {
		ennemy.takeDamage(ENNEMY_HEALTH - 1, ENNEMY_ARMOR);
		assertEquals(1, ennemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeDamageAndPositivePenetration_Exception() {
		ennemy.takeDamage(-1, ENNEMY_HEALTH / 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_positiveDamageAndNegativePenetration_Exception() {
		ennemy.takeDamage(1, -ENNEMY_HEALTH / 2);
	}

	@Test
	public void heal_moreThanHeroLife_maxHealth() {
		ennemy.heal(ennemy.getMaxHealth() + 1);
		assertEquals(ennemy.getMaxHealth(), ennemy.getHealth(), MARGIN);
	}

	@Test
	public void heal_lessThanHeroLife_heroHealth() {
		ennemy.heal(1);
		assertEquals(ENNEMY_HEALTH + 1, ennemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void heal_negativeAmount_Exception() {
		ennemy.heal(-1);
	}

	@Test
	public void getWeapon_nA_heroWeapon() {
		assertEquals(weapon, ennemy.getWeapon());
	}

	@Test
	public void changeWeapon_validWeapon_heroWeapon() {
		ennemy.changeWeapon(new Fist());
		assertEquals(new Fist(), ennemy.getWeapon());
	}

	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_invalidWeapon_exception() {
		ennemy.changeWeapon(new Sword());
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeWeapon_nullWeapon_exception() {
		ennemy.changeWeapon(null);
	}

	@Test
	public void dropWeapon_nA_fist() {
		ennemy.dropWeapon();
		assertEquals(new Fist(), ennemy.getWeapon());
	}

	@Test
	public void getLevel_nA_heroLevel() {
		assertEquals(ENNEMY_LEVEL, ennemy.getLevel());
	}

	@Test
	public void increaseLevel_nA_heroLevelIncreased() {
		ennemy.increaseLevel();
		assertEquals(ENNEMY_LEVEL + 1, ennemy.getLevel());
	}

	@Test(expected = MaxLevelReachedException.class)
	public void increaseLevel_moreThanMaxLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> ennemy.increaseLevel());
	}

	@Test
	public void decreaseLevel_nA_heroLevelDecreased() {
		ennemy.decreaseLevel();
		assertEquals(ENNEMY_LEVEL - 1, ennemy.getLevel());
	}

	@Test(expected = MinLevelReachedException.class)
	public void decreaseLevel_lessThanMinLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> ennemy.decreaseLevel());
	}
	
	@Test
	public void setArmor_moreThanMaxArmor_maxArmor() {
		ennemy.setArmor(GameCharacter.MAX_ARMOR + 1);
		assertEquals(GameCharacter.MAX_ARMOR, ennemy.getArmor(), MARGIN);
	}

	@Test
	public void getArmor_nA_heroArmor() {
		assertEquals(ENNEMY_ARMOR, ennemy.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_lessThanZero_0() {
		ennemy.setArmor(-1);
		assertEquals(0, ennemy.getArmor(), MARGIN);
	}
	
	@Test
	public void setArmor_allIsWell_heroArmorIncreased() {
		ennemy.setArmor(ENNEMY_ARMOR + 1);
		assertEquals(ENNEMY_ARMOR + 1, ennemy.getArmor(), MARGIN);
	}

	@Test
	public void attack_ennemy_enemyTakesDamage() {
		GameCharacter gc = new Skeleton(Skeletons.Skeleton_NORTH_2, position, "Skeleton", ENNEMY_HEALTH, new Bone(), 1, 0, Set.of(new Sword()));
		ennemy.attack(gc);
		assertEquals(ENNEMY_HEALTH - weapon.getDamage(), gc.getHealth(), MARGIN);
	}

	@Test
	public void getDrop_nA_optionalOfSword() {
		Optional<Droppable> optionalWeapon = Optional.of(new Sword());
		assertEquals(optionalWeapon, ennemy.getDrop());
	}

}
