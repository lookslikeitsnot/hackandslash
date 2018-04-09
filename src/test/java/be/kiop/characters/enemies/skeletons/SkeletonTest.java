package be.kiop.characters.enemies.skeletons;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Drawable;
import be.kiop.characters.GameCharacter;
import be.kiop.exceptions.IllegalFrameNumberException;
import be.kiop.exceptions.IllegalLevelException;
import be.kiop.exceptions.IllegalNameException;
import be.kiop.exceptions.IllegalPositionException;
import be.kiop.exceptions.IllegalTextureSetException;
import be.kiop.exceptions.IllegalTileException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.IllegalWeaponSetException;
import be.kiop.exceptions.InvalidTextureException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.NegativeArmorException;
import be.kiop.exceptions.NegativeHealthException;
import be.kiop.exceptions.OutOfTileException;
import be.kiop.items.Drop;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.SkeletonTextures;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public class SkeletonTest {
	private Skeleton enemy;
	private Weapon weapon;
	private Tile tile;

	private final static float MARGIN = 0.1F;

	private final static SkeletonTextures DRAWABLE_TEXTURE = SkeletonTextures.Skeleton_SOUTH_2;
	private final static SkeletonTextures VALID_TEXTURE = SkeletonTextures.Skeleton_Dog_EAST_2;
	private final static FloorTextures INVALID_TEXTURE = FloorTextures.Floor_Parquet_HORIZONTAL;
	private final static Weapon VALID_WEAPON = Swords.Sword_1.getWeapon();
	private final static String GAMECHARACTER_NAME = "Skeleton";
	private final static float GAMECHARACTER_HEALTH = 100;
	private final static int GAMECHARACTER_LEVEL = 10;
	private final static float GAMECHARACTER_ARMOR = 50;
	private final static Set<Drop> ENEMY_DROPPABLES = Set.of(Swords.Sword_1.getWeapon());

	@Before
	public void before() {
		tile = new Tile(1, 1);
		weapon = new Bone(WeaponTextures.Bone, tile, "Little Bone", 50, 75, 40, 30, 100, 5, 10, 20);
		enemy = new Skeleton(DRAWABLE_TEXTURE, tile, GAMECHARACTER_NAME, GAMECHARACTER_HEALTH, weapon,
				GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR, ENEMY_DROPPABLES);
	}

	/* DRAWABLE TEST */
	@Test(expected = IllegalTextureSetException.class)
	public void Drawable_nullAsTextureSet_exception() {
		new Drawable(null, DRAWABLE_TEXTURE, tile) {
		};
	}

	@Test(expected = IllegalTextureSetException.class)
	public void Drawable_emptySetAsTextureSet_exception() {
		new Drawable(Set.of(), DRAWABLE_TEXTURE, tile) {
		};
	}

	@Test
	public void setTexture_validTexture_textureSet() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setTexture(VALID_TEXTURE);
		assertEquals(VALID_TEXTURE, drawable.getTexture());
	}

	@Test(expected = InvalidTextureException.class)
	public void setTexture_nullAsTexture_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setTexture(null);
	}

	@Test(expected = InvalidTextureException.class)
	public void setTexture_textureNotInAvailableTexturesAsTexture_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setTexture(INVALID_TEXTURE);
	}

	@Test
	public void setTile_validTile_tileSet() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		Tile validTile = Tile.ORIGIN;
		drawable.setTile(validTile);
		assertEquals(validTile, drawable.getTile());
	}

	@Test(expected = IllegalTileException.class)
	public void setTile_nullAsTile_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setTile(null);
	}

	@Test
	public void setPositionOfTextureCenterInTile_validPosition_positionSet() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(Position.ORIGIN);
		assertEquals(Position.ORIGIN, drawable.getPositionOfTextureCenterInTile());
	}

	@Test
	public void setPositionOfTextureCenterInTile_maxPosition_positionSet() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		Position maxPos = new Position(tile.getSize().getWidth(), tile.getSize().getHeight());
		drawable.setPositionOfTextureCenterInTile(maxPos);
		assertEquals(maxPos, drawable.getPositionOfTextureCenterInTile());
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_nullAsPosition_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(null);
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_lessThan0xPosition_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(new Position(-1, 0));
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_lessThan0yPosition_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(new Position(0, -1));
	}

	@Test(expected = OutOfTileException.class)
	public void setPositionOfTextureCenterInTile_moreThanTileWidthPosition_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(new Position(tile.getSize().getWidth() + 1, 0));
	}

	@Test(expected = OutOfTileException.class)
	public void setPositionOfTextureCenterInTile_moreThanTileHeight_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.setPositionOfTextureCenterInTile(new Position(0, tile.getSize().getHeight() + 1));
	}

	@Test
	public void getAssociatedFrameNumber_validFrameNumber_associatedFrameNumber() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		assertEquals(drawable.getAssociatedFrameNumber(1), 2);
	}

	@Test(expected = IllegalFrameNumberException.class)
	public void getAssociatedFrameNumber_0AsFrameNumber_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.getAssociatedFrameNumber(0);
	}

	@Test(expected = IllegalFrameNumberException.class)
	public void getAssociatedFrameNumber_moreThanAnimationLengthAsFrameNumber_exception() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable.getAssociatedFrameNumber(Drawable.ANIMATION_LENGTH + 1);
	}

	@Test
	public void clone_nA_cloned() {
		Drawable drawable = new Drawable(Set.of(DRAWABLE_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		try {
			assertEquals(drawable, drawable.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/* GAMECHARACTER TEST */
	@Test(expected = IllegalNameException.class)
	public void GameCharacter_nullAsName_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, null,
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
	}

	@Test(expected = IllegalNameException.class)
	public void GameCharacter_emptyName_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "", Set.of((WeaponTextures) weapon.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
	}

	@Test(expected = IllegalNameException.class)
	public void GameCharacter_blankName_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, " ", Set.of((WeaponTextures) weapon.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
	}

	@Test(expected = IllegalWeaponSetException.class)
	public void GameCharacter_nullAsAvailableWeapons_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID", null, GAMECHARACTER_HEALTH, weapon,
				GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
	}

	@Test(expected = IllegalWeaponSetException.class)
	public void GameCharacter_emptyAvailableWeapons_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID", Set.of(), GAMECHARACTER_HEALTH, weapon,
				GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
	}

	@Test
	public void increaseLevel_validTimes_levelIncreased() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.increaseLevel();
		assertEquals(GAMECHARACTER_LEVEL + 1, gc.getLevel());
	}

	@Test(expected = MaxLevelReachedException.class)
	public void increaseLevel_toReachMoreThanMaxLevelTimes_exception() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(i -> gc.increaseLevel());
	}

	@Test
	public void decreaseLevel_validTimes_levelIncreased() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.decreaseLevel();
		assertEquals(GAMECHARACTER_LEVEL - 1, gc.getLevel());
	}

	@Test(expected = IllegalLevelException.class)
	public void decreaseLevel_toReachLessThan0Times_exception() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(i -> gc.decreaseLevel());
	}
	
	@Test(expected = NegativeHealthException.class)
	public void setHealth_lessThan0_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), -1, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
	}

	@Test
	public void heal_validAmount_healthIncreased() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.heal(1);
		assertEquals(GAMECHARACTER_HEALTH + 1, gc.getHealth(), MARGIN);
	}

	@Test
	public void heal_0_sameHealth() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.heal(0);
		assertEquals(GAMECHARACTER_HEALTH, gc.getHealth(), MARGIN);
	}

	@Test(expected = NegativeHealthException.class)
	public void heal_negativeAmount_exception() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.heal(-1);
	}

	@Test
	public void heal_moreThanMaxHealthAmount_maxHealth() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.heal(gc.getMaxHealth() + 1);
		assertEquals(gc.getMaxHealth(), gc.getHealth(), MARGIN);
	}

	@Test
	public void attack_validAmount_healthDecreased() {
		GameCharacter gc1 = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		GameCharacter gc2 = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc2.attack(gc1);
		assertEquals(
				GAMECHARACTER_HEALTH - (weapon.getDamage() * (100 - gc1.getArmor() + weapon.getPenetration()) / 100),
				gc1.getHealth(), MARGIN);
	}
	
	@Test
	public void changeWeapon_validWeapon_weaponChanged() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.changeWeapon(VALID_WEAPON);
		assertEquals(VALID_WEAPON, gc.getWeapon());
	}
	
	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_nullAsWeapon_exception() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.changeWeapon(null);
	}
	
	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_weaponNotInWeaponSetAsWeapon_exception() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.changeWeapon(VALID_WEAPON);
	}
	
	@Test
	public void changeWeapon_fistAsWeapon_weaponChanged() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.changeWeapon(new Fist());
		assertEquals(new Fist(), gc.getWeapon());
	}
	
	@Test
	public void dropWeapon_nA_weaponIsFist() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		gc.dropWeapon();
		assertEquals(new Fist(), gc.getWeapon());
	}
	
	@Test
	public void setArmor_validArmor_armorSet() {
		float armor = 5.0F;
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				armor) {
		};
		assertEquals(armor, gc.getArmor(), MARGIN);
	}
	
	@Test(expected = NegativeArmorException.class)
	public void setArmor_negativeArmor_exception() {
		float armor = -5.0F;
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				armor) {
		};
	}
	
	@Test
	public void setArmor_moreThanMaxArmor_maxArmor() {
		float armor = GameCharacter.MAX_ARMOR+1;
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				armor) {
		};
		assertEquals(GameCharacter.MAX_ARMOR, gc.getArmor(), MARGIN);
	}

//	@Test(expected = IllegalArgumentException.class)
//	public void setName_null_IllegalArgument() {
//		new Skeleton(DRAWABLE_TEXTURE, tile, null, GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR, ENEMY_DROPPABLES);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void setName_invalid_IllegalArgument() {
//		new Skeleton(DRAWABLE_TEXTURE, tile, " ", GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR, ENEMY_DROPPABLES);
//	}
//
//	@Test
//	public void getTexture_nA_gameCharacterSkin() {
//		assertEquals(DRAWABLE_TEXTURE, enemy.getTexture());
//	}
//
//	@Test
//	public void getName_nA_heroName() {
//		assertTrue(enemy.getName().equals(GAMECHARACTER_NAME));
//	}
//
//	@Test
//	public void getHealth_nA_heroHealth() {
//		assertEquals(GAMECHARACTER_HEALTH, enemy.getHealth(), MARGIN);
//	}
//
//	@Test
//	public void heal_moreThanHeroLife_maxHealth() {
//		enemy.heal(enemy.getMaxHealth() + 1);
//		assertEquals(enemy.getMaxHealth(), enemy.getHealth(), MARGIN);
//	}
//
//	@Test
//	public void heal_lessThanHeroLife_heroHealth() {
//		enemy.heal(1);
//		assertEquals(GAMECHARACTER_HEALTH + 1, enemy.getHealth(), MARGIN);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void heal_negativeAmount_Exception() {
//		enemy.heal(-1);
//	}
//
//	@Test
//	public void getWeapon_nA_heroWeapon() {
//		assertEquals(weapon, enemy.getWeapon());
//	}
//
//	@Test
//	public void changeWeapon_validWeapon_heroWeapon() {
//		enemy.changeWeapon(new Fist());
//		assertEquals(new Fist(), enemy.getWeapon());
//	}
//
//	@Test(expected = IllegalWeaponException.class)
//	public void changeWeapon_invalidWeapon_exception() {
//		enemy.changeWeapon(Swords.Sword_1.getWeapon());
//	}
//
//	@Test(expected = IllegalWeaponException.class)
//	public void changeWeapon_nullWeapon_exception() {
//		enemy.changeWeapon(null);
//	}
//
//	@Test
//	public void dropWeapon_nA_fist() {
//		enemy.dropWeapon();
//		assertEquals(new Fist(), enemy.getWeapon());
//	}
//
//	@Test
//	public void getLevel_nA_heroLevel() {
//		assertEquals(GAMECHARACTER_LEVEL, enemy.getLevel());
//	}
//
//	@Test
//	public void increaseLevel_nA_heroLevelIncreased() {
//		enemy.increaseLevel();
//		assertEquals(GAMECHARACTER_LEVEL + 1, enemy.getLevel());
//	}
//
//	@Test(expected = MaxLevelReachedException.class)
//	public void increaseLevel_moreThanMaxLevel_exception() {
//		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> enemy.increaseLevel());
//	}
//
//	@Test
//	public void decreaseLevel_nA_heroLevelDecreased() {
//		enemy.decreaseLevel();
//		assertEquals(GAMECHARACTER_LEVEL - 1, enemy.getLevel());
//	}
//
//	@Test(expected = MinLevelReachedException.class)
//	public void decreaseLevel_lessThanMinLevel_exception() {
//		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(val -> enemy.decreaseLevel());
//	}
//
//	@Test
//	public void setArmor_moreThanMaxArmor_maxArmor() {
//		enemy.setArmor(GameCharacter.MAX_ARMOR + 1);
//		assertEquals(GameCharacter.MAX_ARMOR, enemy.getArmor(), MARGIN);
//	}
//
//	@Test
//	public void getArmor_nA_heroArmor() {
//		assertEquals(GAMECHARACTER_ARMOR, enemy.getArmor(), MARGIN);
//	}
//
//	@Test
//	public void setArmor_lessThanZero_0() {
//		enemy.setArmor(-1);
//		assertEquals(0, enemy.getArmor(), MARGIN);
//	}
//
//	@Test
//	public void setArmor_allIsWell_heroArmorIncreased() {
//		enemy.setArmor(GAMECHARACTER_ARMOR + 1);
//		assertEquals(GAMECHARACTER_ARMOR + 1, enemy.getArmor(), MARGIN);
//	}
//
//	@Test
//	public void attack_enemy_enemyTakesDamage() {
//		GameCharacter gc = new Skeleton(SkeletonTextures.Skeleton_NORTH_2, tile, "Skeleton", GAMECHARACTER_HEALTH,
//				Bones.Bone_1.getWeapon(), 1, 0, Set.of(Swords.Sword_1.getWeapon()));
//		enemy.attack(gc);
//		assertEquals(GAMECHARACTER_HEALTH - weapon.getDamage(), gc.getHealth(), MARGIN);
//	}
//
////	@Test(expected = IllegalArgumentException.class)
////	public void setAvailableWeapons_null_IllegalArgument() {
////		enemy.setAvailableWeapons(null);
////	}
////
////	@Test(expected = IllegalArgumentException.class)
////	public void setAvailableWeapons_emptySet_IllegalArgument() {
////		enemy.setAvailableWeapons(new LinkedHashSet<>());
////	}
//
////	@Test
////	public void move_validMoveEast_GameCharacterMoved() {
////		Position pos = new Position(enemy.getAbsoluteCenterPosition().getX(), enemy.getAbsoluteCenterPosition().getY());
////		enemy.move(Directions.EAST, new LinkedHashSet<>());
////		assertEquals(pos.getX() + GameCharacter.SPEED, enemy.getAbsoluteCenterPosition().getX());
////		assertEquals(pos.getY(), enemy.getAbsoluteCenterPosition().getY());
////	}
////
////	@Test
////	public void move_validMoveSouth_GameCharacterMoved() {
////		Position pos = new Position(enemy.getAbsoluteCenterPosition().getX(), enemy.getAbsoluteCenterPosition().getY());
////		enemy.move(Directions.SOUTH, new LinkedHashSet<>());
////		assertEquals(pos.getX(), enemy.getAbsoluteCenterPosition().getX());
////		assertEquals(pos.getY() +GameCharacter.SPEED, enemy.getAbsoluteCenterPosition().getY());
////	}
////
////	@Test
////	public void move_validMoveWest_GameCharacterMoved() {
////		Position pos = new Position(enemy.getAbsoluteCenterPosition().getX(), enemy.getAbsoluteCenterPosition().getY());
////		enemy.move(Directions.WEST, new LinkedHashSet<>());
////		assertEquals(pos.getX() - GameCharacter.SPEED, enemy.getAbsoluteCenterPosition().getX());
////		assertEquals(pos.getY(), enemy.getAbsoluteCenterPosition().getY());
////	}
////
////	@Test
////	public void move_validMoveNorth_GameCharacterMoved() {
////		Position pos = new Position(enemy.getAbsoluteCenterPosition().getX(), enemy.getAbsoluteCenterPosition().getY());
////		enemy.move(Directions.NORTH, new LinkedHashSet<>());
////		assertEquals(pos.getX(), enemy.getAbsoluteCenterPosition().getX());
////		assertEquals(pos.getY() - GameCharacter.SPEED, enemy.getAbsoluteCenterPosition().getY());
////	}
////
////	@Test
////	public void move_cannotMoveNorth_GameCharacterNotMoved() {
////		int deltaX = ((enemy.getTexture().getSize().getWidth()
////				- ((HitBoxTexture) enemy.getTexture()).getHitBoxSize().getWidth()) / 2);
////		int deltaY = ((enemy.getTexture().getSize().getHeight()
////				- ((HitBoxTexture) enemy.getTexture()).getHitBoxSize().getHeight()) / 2);
////		Position pos = new Position(enemy.getAbsoluteCenterPosition().getX(), enemy.getAbsoluteCenterPosition().getY());
////		Position posNorth = Position.sum(pos, new Position(deltaX, deltaY));
//////		Position posNorth = new Position(position.getX() + deltaX, position.getY() + deltaY - 1);
////		enemy.move(Directions.NORTH, Set.of(posNorth));
////		assertEquals(pos.getX(), enemy.getAbsoluteCenterPosition().getX());
////		assertEquals(pos.getY(), enemy.getAbsoluteCenterPosition().getY());
////	}
////
////	@Test
////	public void setNextTexture_nA_GameCharacterHasNextTexture() {
////		enemy.move(Directions.SOUTH, new LinkedHashSet<>());
////		enemy.setNextTexture();
////		assertEquals(ENEMY_NEXT_TEXTURE, enemy.getTexture());
////	}
//
//	@Test
//	public void getDrop_nA_optionalOfSword() {
//		Optional<Drop> optionalWeapon = Optional.of(Swords.Sword_1.getWeapon());
//		assertEquals(optionalWeapon, enemy.getDrop());
//	}
//
////	@Test(expected = IllegalArgumentException.class)
////	public void setDroppables_null_IllegalArgument() {
////		enemy.setDroppables(null);
////	}
}
