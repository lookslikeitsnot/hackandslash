package be.kiop.characters.enemies.skeletons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.MinLevelReachedException;
import be.kiop.exceptions.OutOfBoardException;
import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.items.Drop;
import be.kiop.textures.Floors;
import be.kiop.textures.HitBoxTexture;
import be.kiop.textures.Skeletons;
import be.kiop.textures.Texture;
import be.kiop.textures.Weapons;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class SkeletonTest {
	private Skeleton enemy;
	private Weapon weapon;
	private Position position;

	private final static float MARGIN = 0.1F;

	private final static Skeletons ENNEMY_TEXTURE = Skeletons.Skeleton_SOUTH_2;
	private final static Skeletons ENNEMY_NEXT_TEXTURE = Skeletons.Skeleton_SOUTH_1;
	private final static Skeletons VALID_TEXTURE = Skeletons.Skeleton_Dog_EAST_2;
	private final static Floors INVALID_TEXTURE = Floors.Floor_Parquet_HORIZONTAL;
	private final static String ENNEMY_NAME = "Skeleton";
	private final static float ENNEMY_HEALTH = 100;
	private final static int ENNEMY_LEVEL = 10;
	private final static float ENNEMY_ARMOR = 50;
	private final static Set<Drop> ENNEMY_DROPPABLES = Set.of(new Sword(Weapons.Sword, new Position(48,48), "Heavy Sword", 50, 75, 40, 30, 100, 5, 10, 20, 50, 70));

	@Before
	public void before() {
		position = new Position(Board.getSize(true).getWidth() / 2, Board.getSize(true).getHeight() / 2);
		weapon = new Bone(Weapons.Bone, position, "Little Bone", 50, 75, 40, 30, 100, 5, 10, 20);
		enemy = new Skeleton(ENNEMY_TEXTURE, position, ENNEMY_NAME, ENNEMY_HEALTH, weapon, ENNEMY_LEVEL, ENNEMY_ARMOR,
				ENNEMY_DROPPABLES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null_IllegalArgument() {
		new Skeleton(ENNEMY_TEXTURE, position, null, ENNEMY_HEALTH, weapon, ENNEMY_LEVEL, ENNEMY_ARMOR,
				ENNEMY_DROPPABLES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_invalid_IllegalArgument() {
		new Skeleton(ENNEMY_TEXTURE, position, " ", ENNEMY_HEALTH, weapon, ENNEMY_LEVEL, ENNEMY_ARMOR,
				ENNEMY_DROPPABLES);
	}

	@Test
	public void getTexture_nA_gameCharacterSkin() {
		assertEquals(ENNEMY_TEXTURE, enemy.getTexture());
	}

	@Test
	public void setTexture_valid_gameCharacterSkinChanged() {
		enemy.setTexture(VALID_TEXTURE);
		assertEquals(VALID_TEXTURE, enemy.getTexture());
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_unvalid_skinNotFoundException() {
		enemy.setTexture(INVALID_TEXTURE);
	}

	@Test(expected = SkinNotFoundException.class)
	public void setTexture_null_skinNotFoundException() {
		Texture newTexture = null;
		enemy.setTexture(newTexture);
	}

	@Test
	public void moveLeft_nA_gameCharacterPositionXMinus1() {
		enemy.moveLeft();
		assertEquals(Board.getSize(true).getWidth() / 2 - 1, enemy.getPosition().getX());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveLeft_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> enemy.moveLeft());
	}

	@Test
	public void moveRight_nA_gameCharacterPositionXPlus1() {
		enemy.moveRight();
		assertEquals(Board.getSize(true).getWidth() / 2 + 1, enemy.getPosition().getX());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveRight_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> enemy.moveRight());
	}

	@Test
	public void moveUp_nA_gameCharacterPositionYMinus1() {
		enemy.moveUp();
		assertEquals(Board.getSize(true).getHeight() / 2 -1, enemy.getPosition().getY());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveUp_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getHeight() / 2 + 1).forEach(iteration -> enemy.moveUp());
	}

	@Test
	public void moveDown_nA_gameCharacterPositionYPlus1() {
		enemy.moveDown();
		assertEquals(Board.getSize(true).getHeight() / 2 + 1, enemy.getPosition().getY());
	}

	@Test(expected = OutOfBoardException.class)
	public void moveDown_untilOOB_OutOfBoardException() {
		IntStream.range(0, Board.getSize(true).getWidth() + 1).forEach(iteration -> enemy.moveDown());
	}

	@Test
	public void teleport_validPosition_gameCharacterPositionChanged() {
		enemy.teleport(1, 1);
		assertEquals(new Position(1, 1), enemy.getPosition());
	}

	@Test(expected = OutOfBoardException.class)
	public void teleport_invalidPosition_OutOfBoardException() {
		enemy.teleport(-1, -1);
	}

	@Test
	public void getName_nA_heroName() {
		assertTrue(enemy.getName().equals(ENNEMY_NAME));
	}

	@Test
	public void getHealth_nA_heroHealth() {
		assertEquals(ENNEMY_HEALTH, enemy.getHealth(), MARGIN);
	}

	@Test
	public void takeFlatDamage_moreThanHeroHealth_0Health() {
		enemy.takeFlatDamage(ENNEMY_HEALTH + 1);
		assertEquals(0, enemy.getHealth(), MARGIN);
	}

	@Test
	public void takeFlatDamage_lessThanHeroHealth_remainingHealth() {
		enemy.takeFlatDamage(ENNEMY_HEALTH - 1);
		assertEquals(1, enemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeFlatDamage_negativeAmount_Exception() {
		enemy.takeFlatDamage(-1);
	}

	@Test
	public void takeDamage_moreThanHeroHealth_0Health() {
		enemy.takeDamage(ENNEMY_HEALTH * 100 / ENNEMY_ARMOR + ENNEMY_HEALTH);
		assertEquals(0, enemy.getHealth(),MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeAmount_Exception() {
		enemy.takeDamage(-1);
	}

	@Test
	public void takeDamage_moreThanHeroLifeAndPenetration_0Health() {
		enemy.takeDamage(ENNEMY_HEALTH, ENNEMY_ARMOR + 1);
		assertEquals(0, enemy.getHealth(),MARGIN);
	}

	@Test
	public void takeDamage_lessThanHeroLifeAndPenetration_remainingHealth() {
		enemy.takeDamage(ENNEMY_HEALTH - 1, ENNEMY_ARMOR);
		assertEquals(1, enemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_negativeDamageAndPositivePenetration_Exception() {
		enemy.takeDamage(-1, ENNEMY_HEALTH / 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void takeDamage_positiveDamageAndNegativePenetration_Exception() {
		enemy.takeDamage(1, -ENNEMY_HEALTH / 2);
	}

	@Test
	public void heal_moreThanHeroLife_maxHealth() {
		enemy.heal(enemy.getMaxHealth() + 1);
		assertEquals(enemy.getMaxHealth(), enemy.getHealth(), MARGIN);
	}

	@Test
	public void heal_lessThanHeroLife_heroHealth() {
		enemy.heal(1);
		assertEquals(ENNEMY_HEALTH + 1, enemy.getHealth(), MARGIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void heal_negativeAmount_Exception() {
		enemy.heal(-1);
	}

	@Test
	public void getWeapon_nA_heroWeapon() {
		assertEquals(weapon, enemy.getWeapon());
	}

	@Test
	public void changeWeapon_validWeapon_heroWeapon() {
		enemy.changeWeapon(new Fist());
		assertEquals(new Fist(), enemy.getWeapon());
	}

	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_invalidWeapon_exception() {
		enemy.changeWeapon(new Sword(Weapons.Sword, new Position(48,48), "Heavy Sword", 50, 75, 40, 30, 100, 5, 10, 20, 50, 70));
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeWeapon_nullWeapon_exception() {
		enemy.changeWeapon(null);
	}

	@Test
	public void dropWeapon_nA_fist() {
		enemy.dropWeapon();
		assertEquals(new Fist(), enemy.getWeapon());
	}

	@Test
	public void getLevel_nA_heroLevel() {
		assertEquals(ENNEMY_LEVEL, enemy.getLevel());
	}

	@Test
	public void increaseLevel_nA_heroLevelIncreased() {
		enemy.increaseLevel();
		assertEquals(ENNEMY_LEVEL + 1, enemy.getLevel());
	}

	@Test(expected = MaxLevelReachedException.class)
	public void increaseLevel_moreThanMaxLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> enemy.increaseLevel());
	}

	@Test
	public void decreaseLevel_nA_heroLevelDecreased() {
		enemy.decreaseLevel();
		assertEquals(ENNEMY_LEVEL - 1, enemy.getLevel());
	}

	@Test(expected = MinLevelReachedException.class)
	public void decreaseLevel_lessThanMinLevel_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> enemy.decreaseLevel());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLevel_lessThan0_IllegalArgument() {
		enemy.setLevel(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLevel_moreThanMaxLevel_IllegalArgument() {
		enemy.setLevel(GameCharacter.MAX_LEVEL + 1);
	}

	@Test
	public void setArmor_moreThanMaxArmor_maxArmor() {
		enemy.setArmor(GameCharacter.MAX_ARMOR + 1);
		assertEquals(GameCharacter.MAX_ARMOR, enemy.getArmor(), MARGIN);
	}

	@Test
	public void getArmor_nA_heroArmor() {
		assertEquals(ENNEMY_ARMOR, enemy.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_lessThanZero_0() {
		enemy.setArmor(-1);
		assertEquals(0, enemy.getArmor(), MARGIN);
	}

	@Test
	public void setArmor_allIsWell_heroArmorIncreased() {
		enemy.setArmor(ENNEMY_ARMOR + 1);
		assertEquals(ENNEMY_ARMOR + 1, enemy.getArmor(), MARGIN);
	}

	@Test
	public void attack_enemy_enemyTakesDamage() {
		GameCharacter gc = new Skeleton(Skeletons.Skeleton_NORTH_2, position, "Skeleton", ENNEMY_HEALTH,
				new Bone(Weapons.Bone, position, "Little Bone", 50, 75, 40, 30, 100, 5, 10, 20), 1,
				0, Set.of(new Sword(Weapons.Sword, new Position(48,48), "Heavy Sword", 50, 75, 40, 30, 100, 5, 10, 20, 50, 70)));
		enemy.attack(gc);
		assertEquals(ENNEMY_HEALTH - weapon.getDamage(), gc.getHealth(), MARGIN);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setAvailableWeapons_null_IllegalArgument() {
		enemy.setAvailableWeapons(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setAvailableWeapons_emptySet_IllegalArgument() {
		enemy.setAvailableWeapons(new LinkedHashSet<>());
	}
	
	@Test
	public void move_validMoveEast_GameCharacterMoved() {
		Position pos = new Position(position.getX(), position.getY());
		enemy.move(Directions.EAST, new LinkedHashSet<>());
		assertEquals(pos.getX()+GameCharacter.SPEED, enemy.getPosition().getX());
		assertEquals(pos.getY(), enemy.getPosition().getY());
	}
	@Test
	public void move_validMoveSouth_GameCharacterMoved() {
		Position pos = new Position(position.getX(), position.getY());
		enemy.move(Directions.SOUTH, new LinkedHashSet<>());
		assertEquals(pos.getX(), enemy.getPosition().getX());
		assertEquals(pos.getY()+GameCharacter.SPEED, enemy.getPosition().getY());
	}
	@Test
	public void move_validMoveWest_GameCharacterMoved() {
		Position pos = new Position(position.getX(), position.getY());
		enemy.move(Directions.WEST, new LinkedHashSet<>());
		assertEquals(pos.getX()-GameCharacter.SPEED, enemy.getPosition().getX());
		assertEquals(pos.getY(), enemy.getPosition().getY());
	}
	@Test
	public void move_validMoveNorth_GameCharacterMoved() {
		Position pos = new Position(position.getX(), position.getY());
		enemy.move(Directions.NORTH, new LinkedHashSet<>());
		assertEquals(pos.getX(), enemy.getPosition().getX());
		assertEquals(pos.getY()-GameCharacter.SPEED, enemy.getPosition().getY());
	}
	
	@Test
	public void move_cannotMoveNorth_GameCharacterNotMoved() {
		int deltaX = ((enemy.getTexture().getSize().getWidth() - ((HitBoxTexture) enemy.getTexture()).getHitBoxSize().getWidth())/2);
		int deltaY = ((enemy.getTexture().getSize().getHeight() - ((HitBoxTexture) enemy.getTexture()).getHitBoxSize().getHeight())/2);
		Position pos = new Position(position.getX(), position.getY());
		Position posNorth = new Position(position.getX()+deltaX, position.getY() + deltaY - 1);
		enemy.move(Directions.NORTH, Set.of(posNorth));
		assertEquals(pos.getX(), enemy.getPosition().getX());
		assertEquals(pos.getY(), enemy.getPosition().getY());
	}
	
	@Test
	public void setNextTexture_nA_GameCharacterHasNextTexture() {
		enemy.move(Directions.SOUTH, new LinkedHashSet<>());
		enemy.setNextTexture();
		assertEquals(ENNEMY_NEXT_TEXTURE, enemy.getTexture());
	}

	@Test
	public void getDrop_nA_optionalOfSword() {
		Optional<Drop> optionalWeapon = Optional.of(new Sword(Weapons.Sword, new Position(48,48), "Heavy Sword", 50, 75, 40, 30, 100, 5, 10, 20, 50, 70));
		assertEquals(optionalWeapon, enemy.getDrop());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setDroppables_null_IllegalArgument() {
		enemy.setDroppables(null);
	}
}
