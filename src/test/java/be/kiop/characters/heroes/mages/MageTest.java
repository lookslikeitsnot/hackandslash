package be.kiop.characters.heroes.mages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Animated;
import be.kiop.UI.Board;
import be.kiop.UI.Drawable;
import be.kiop.characters.GameCharacter;
import be.kiop.characters.enemies.skeletons.SkeletonTest;
import be.kiop.characters.heroes.Hero;
import be.kiop.events.HealthEvent;
import be.kiop.events.LifeEvent;
import be.kiop.events.TileEvent;
import be.kiop.exceptions.AlreadyAttackingException;
import be.kiop.exceptions.AlreadyMovingException;
import be.kiop.exceptions.AlreadyPeacefulException;
import be.kiop.exceptions.AlreadyStoppedException;
import be.kiop.exceptions.CharacterDiedException;
import be.kiop.exceptions.IllegalDirectionException;
import be.kiop.exceptions.IllegalFrameNumberException;
import be.kiop.exceptions.IllegalLevelException;
import be.kiop.exceptions.IllegalMovementFrameException;
import be.kiop.exceptions.IllegalNameException;
import be.kiop.exceptions.IllegalPositionException;
import be.kiop.exceptions.IllegalPositionsSetException;
import be.kiop.exceptions.IllegalTextureSetException;
import be.kiop.exceptions.IllegalTileException;
import be.kiop.exceptions.IllegalWeaponException;
import be.kiop.exceptions.IllegalWeaponSetException;
import be.kiop.exceptions.InvalidTextureException;
import be.kiop.exceptions.MaxLevelReachedException;
import be.kiop.exceptions.NegativeArmorException;
import be.kiop.exceptions.NegativeDamageException;
import be.kiop.exceptions.NegativeExperienceException;
import be.kiop.exceptions.NegativeHealthException;
import be.kiop.exceptions.NegativeManaException;
import be.kiop.exceptions.NegativePenetrationException;
import be.kiop.exceptions.NoMoveAnimationException;
import be.kiop.exceptions.NotEnoughManaException;
import be.kiop.exceptions.OutOfLivesException;
import be.kiop.exceptions.OutOfTileException;
import be.kiop.exceptions.TooLittleRangeException;
import be.kiop.listeners.HealthListener;
import be.kiop.listeners.LifeListener;
import be.kiop.listeners.TileListener;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.MageTextures;
import be.kiop.textures.WeaponTextures;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Fist;
import be.kiop.weapons.Staff;
import be.kiop.weapons.Staffs;
import be.kiop.weapons.Swords;
import be.kiop.weapons.Weapon;

public class MageTest {
	private Drawable drawable;
	private GameCharacter gc;
	private Hero hero;
	private Mage mage;
	private Weapon weapon;
	private Tile tile;

	private final static float MARGIN = 0.1F;

	private final static MageTextures DRAWABLE_TEXTURE = MageTextures.Mage_FEMALE_SOUTH_2;
	private final static MageTextures NEXT_VALID_TEXTURE = MageTextures.Mage_MALE_SOUTH_1;
	private final static MageTextures VALID_TEXTURE = MageTextures.Mage_MALE_SOUTH_2;
	private final static FloorTextures INVALID_TEXTURE = FloorTextures.Floor_Parquet_HORIZONTAL;
	private final static String GAMECHARACTER_NAME = "Mage";
	private final static float GAMECHARACTER_HEALTH = 100;
	private final static int GAMECHARACTER_LEVEL = 10;
	private final static int HERO_LIVES = 4;
	private final static float GAMECHARACTER_ARMOR = 50;
	private final static float HERO_MANA = 10;
	private final static Weapon VALID_WEAPON = Swords.Sword_1.getWeapon();

	// Needed to initialize board min and max tiles
	@SuppressWarnings("unused")
	private final static Board board = new Board(15, 15);

	@Before
	public void before() {
		tile = new Tile(Board.getMaxHorizontalTiles() / 2, Board.getMaxHorizontalTiles() / 2);
		weapon = (Staff) Staffs.Staff_1.getWeapon();
		drawable = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, GAMECHARACTER_NAME,
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR) {
		};
		hero = new Hero(Set.of(VALID_TEXTURE, NEXT_VALID_TEXTURE), VALID_TEXTURE, tile, GAMECHARACTER_NAME,
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR, HERO_LIVES) {
		};
		mage = new Mage(DRAWABLE_TEXTURE, tile, GAMECHARACTER_NAME, GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR, HERO_LIVES, HERO_MANA);

	}

	/* START MAGE TESTS */
	/* START HERO TESTS */
	/* START GAMECHARACTER TESTS */
	/* START DRAWABLE TESTS */
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
		drawable.setTexture(VALID_TEXTURE);
		assertEquals(VALID_TEXTURE, drawable.getTexture());
	}

	@Test(expected = InvalidTextureException.class)
	public void setTexture_nullAsTexture_exception() {
		drawable.setTexture(null);
	}

	@Test(expected = InvalidTextureException.class)
	public void setTexture_textureNotInAvailableTexturesAsTexture_exception() {
		drawable.setTexture(INVALID_TEXTURE);
	}

	@Test
	public void setTile_validTile_tileSet() {
		Tile validTile = Tile.ORIGIN;
		drawable.setTile(validTile);
		assertEquals(validTile, drawable.getTile());
	}

	@Test(expected = IllegalTileException.class)
	public void setTile_nullAsTile_exception() {
		drawable.setTile(null);
	}

	@Test
	public void setPositionOfTextureCenterInTile_validPosition_positionSet() {
		drawable.setPositionOfTextureCenterInTile(Position.ORIGIN);
		assertEquals(Position.ORIGIN, drawable.getPositionOfTextureCenterInTile());
	}

	@Test
	public void setPositionOfTextureCenterInTile_maxPosition_positionSet() {
		Position maxPos = new Position(tile.getSize().getWidth(), tile.getSize().getHeight());
		drawable.setPositionOfTextureCenterInTile(maxPos);
		assertEquals(maxPos, drawable.getPositionOfTextureCenterInTile());
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_nullAsPosition_exception() {
		drawable.setPositionOfTextureCenterInTile(null);
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_lessThan0xPosition_exception() {
		drawable.setPositionOfTextureCenterInTile(new Position(-1, 0));
	}

	@Test(expected = IllegalPositionException.class)
	public void setPositionOfTextureCenterInTile_lessThan0yPosition_exception() {
		drawable.setPositionOfTextureCenterInTile(new Position(0, -1));
	}

	@Test(expected = OutOfTileException.class)
	public void setPositionOfTextureCenterInTile_moreThanTileWidthPosition_exception() {
		drawable.setPositionOfTextureCenterInTile(new Position(tile.getSize().getWidth() + 1, 0));
	}

	@Test(expected = OutOfTileException.class)
	public void setPositionOfTextureCenterInTile_moreThanTileHeight_exception() {
		drawable.setPositionOfTextureCenterInTile(new Position(0, tile.getSize().getHeight() + 1));
	}

	@Test
	public void addTileListener_validListener_tileListenerAdded() {
		TileListener tL = new TileListener() {
			@Override
			public void tileChanged(TileEvent event) {
			}
		};
		drawable.addTileListener(tL);
		assertTrue(drawable.getTileListeners().contains(tL));
	}

	@Test
	public void removeTileListener_validListener_tileListenerRemoved() {
		TileListener tL = new TileListener() {
			@Override
			public void tileChanged(TileEvent event) {
			}
		};
		drawable.addTileListener(tL);
		assertTrue(drawable.getTileListeners().contains(tL));
		drawable.removeTileListener(tL);
		assertTrue(drawable.getTileListeners().isEmpty());
	}

	@Test
	public void broadcast_validTileEvent_tileEventBroadcasted() {
		TileEvent tE = new TileEvent(tile, Tile.ORIGIN);
		TileListener tL = new TileListener() {

			@Override
			public void tileChanged(TileEvent event) {
				assertEquals(Tile.ORIGIN, event.newTile);
			}
		};
		drawable.addTileListener(tL);
		try {
			Method broadcast = Drawable.class.getDeclaredMethod("broadcast", TileEvent.class);
			broadcast.setAccessible(true);
			broadcast.invoke(drawable, tE);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void equals_sameDrawable_true() {
		assertEquals(drawable, drawable);
	}

	@Test
	public void equals_sameDrawableParameters_true() {
		assertEquals(drawable, new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		});
	}

	@Test
	public void equals_nullAsDrawable_false() {
		assertNotEquals(drawable, null);
	}

	@Test
	public void equals_notInstanceOfDrawable_false() {
		assertNotEquals(drawable, new SkeletonTest());
	}

	@Test
	public void equals_differentPositionOfTextureCenterInTile_false() {
		Drawable drawable2 = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable2.setPositionOfTextureCenterInTile(Position.ORIGIN);
		assertNotEquals(drawable, drawable2);
	}

	@Test
	public void equals_differentTexture_false() {
		Drawable drawable2 = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable2.setTexture(VALID_TEXTURE);
		assertNotEquals(drawable, drawable2);
	}

	@Test
	public void equals_differentTiles_false() {
		Drawable drawable2 = new Drawable(Set.of(DRAWABLE_TEXTURE, VALID_TEXTURE), DRAWABLE_TEXTURE, tile) {
		};
		drawable2.setTile(Tile.ORIGIN);
		assertNotEquals(drawable, drawable2);
	}

	@Test
	public void clone_nA_cloned() {
		try {
			assertEquals(drawable, drawable.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/* END DRAWABLE TESTS */

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
		gc.increaseLevel();
		assertEquals(GAMECHARACTER_LEVEL + 1, gc.getLevel());
	}

	@Test(expected = MaxLevelReachedException.class)
	public void increaseLevel_toReachMoreThanMaxLevelTimes_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(i -> gc.increaseLevel());
	}

	@Test
	public void decreaseLevel_validTimes_levelIncreased() {
		gc.decreaseLevel();
		assertEquals(GAMECHARACTER_LEVEL - 1, gc.getLevel());
	}

	@Test(expected = IllegalLevelException.class)
	public void decreaseLevel_toReachLessThan0Times_exception() {
		IntStream.range(0, GameCharacter.MAX_LEVEL + 1).forEach(i -> gc.decreaseLevel());
	}

	@Test(expected = CharacterDiedException.class)
	public void setHealth_lessThan0_exception() {
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture()), -1, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
	}

	@Test
	public void increaseHealth_validAmount_healthIncreased() {
		try {
			Method increaseHealth = GameCharacter.class.getDeclaredMethod("increaseHealth", float.class);
			increaseHealth.setAccessible(true);
			increaseHealth.invoke(gc, 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH + 1, gc.getHealth(), MARGIN);
	}

	@Test
	public void increaseHealth_by0_healthUnchanged() {
		try {
			Method increaseHealth = GameCharacter.class.getDeclaredMethod("increaseHealth", float.class);
			increaseHealth.setAccessible(true);
			increaseHealth.invoke(gc, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH, gc.getHealth(), MARGIN);
	}

	@Test(expected = NegativeHealthException.class)
	public void increaseHealth_negativeAmount_exception() throws Throwable {
		try {
			Method increaseHealth = GameCharacter.class.getDeclaredMethod("increaseHealth", float.class);
			increaseHealth.setAccessible(true);
			increaseHealth.invoke(gc, -1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void heal_validAmount_healthIncreased() {
		gc.heal(1);
		assertEquals(GAMECHARACTER_HEALTH + 1, gc.getHealth(), MARGIN);
	}

	@Test
	public void heal_0_sameHealth() {
		gc.heal(0);
		assertEquals(GAMECHARACTER_HEALTH, gc.getHealth(), MARGIN);
	}

	@Test(expected = NegativeHealthException.class)
	public void heal_negativeAmount_exception() {
		gc.heal(-1);
	}

	@Test
	public void heal_moreThanMaxHealthAmount_maxHealth() {
		gc.heal(gc.getMaxHealth() + 1);
		assertEquals(gc.getMaxHealth(), gc.getHealth(), MARGIN);
	}

	@Test
	public void decreaseHealth_validAmount_healthDecreasedAndTakingDamage() {
		try {
			Method decreaseHealth = GameCharacter.class.getDeclaredMethod("decreaseHealth", float.class);
			decreaseHealth.setAccessible(true);
			decreaseHealth.invoke(gc, 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH - 1, gc.getHealth(), MARGIN);
		assertTrue(gc.isTakingDamage());
	}

	@Test
	public void decreaseHealth_by0_healthUnchangedAndNotTakingDamage() {
		try {
			Method decreaseHealth = GameCharacter.class.getDeclaredMethod("decreaseHealth", float.class);
			decreaseHealth.setAccessible(true);
			decreaseHealth.invoke(gc, 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH - 1, gc.getHealth(), MARGIN);
		assertTrue(gc.isTakingDamage());
	}

	@Test(expected = NegativeHealthException.class)
	public void decreaseHealth_lessThan0_exception() throws Throwable {
		try {
			Method decreaseHealth = GameCharacter.class.getDeclaredMethod("decreaseHealth", float.class);
			decreaseHealth.setAccessible(true);
			decreaseHealth.invoke(gc, -1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void getDamageFactor_lessPenetrationThanArmorAmount_validFactor() {
		try {
			Method getDamageFactor = GameCharacter.class.getDeclaredMethod("getDamageFactor", float.class);
			getDamageFactor.setAccessible(true);
			assertEquals(1 - (GAMECHARACTER_ARMOR - 10) / 100, getDamageFactor.invoke(gc, 10));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getDamageFactor_0PenetrationAmount_between0And1Factor() {
		try {
			Method getDamageFactor = GameCharacter.class.getDeclaredMethod("getDamageFactor", float.class);
			getDamageFactor.setAccessible(true);
			assertEquals(1 - (GAMECHARACTER_ARMOR) / 100, getDamageFactor.invoke(gc, 0));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = NegativePenetrationException.class)
	public void getDamageFactor_negativePenetrationAmount_exception() throws Throwable {
		try {
			Method getDamageFactor = GameCharacter.class.getDeclaredMethod("getDamageFactor", float.class);
			getDamageFactor.setAccessible(true);
			getDamageFactor.invoke(gc, -1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void getDamageFactor_morePenetrationThanArmorAmount_1AsFactor() {
		try {
			Method getDamageFactor = GameCharacter.class.getDeclaredMethod("getDamageFactor", float.class);
			getDamageFactor.setAccessible(true);
			assertEquals(1F, (float) getDamageFactor.invoke(gc, GAMECHARACTER_ARMOR + 1), MARGIN);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void takeDamage_validAmount_healthDecreasedAndTakingDamage() {
		try {
			Method takeDamage = GameCharacter.class.getDeclaredMethod("takeDamage", float.class, float.class);
			takeDamage.setAccessible(true);
			takeDamage.invoke(gc, 1, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH - 1 * (GAMECHARACTER_ARMOR / 100), gc.getHealth(), MARGIN);
		assertTrue(gc.isTakingDamage());
	}

	@Test
	public void takeDamage_0Damage_healthUnchangedAndNotTakingDamage() {
		try {
			Method takeDamage = GameCharacter.class.getDeclaredMethod("takeDamage", float.class, float.class);
			takeDamage.setAccessible(true);
			takeDamage.invoke(gc, 0, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(GAMECHARACTER_HEALTH, gc.getHealth(), MARGIN);
		assertFalse(gc.isTakingDamage());
	}

	@Test(expected = NegativeDamageException.class)
	public void takeDamage_lessThan0Damage_exception() throws Throwable {
		try {
			Method takeDamage = GameCharacter.class.getDeclaredMethod("takeDamage", float.class, float.class);
			takeDamage.setAccessible(true);
			takeDamage.invoke(gc, -1, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test(expected = NegativePenetrationException.class)
	public void takeDamage_lessThan0Penetration_exception() throws Throwable {
		try {
			Method takeDamage = GameCharacter.class.getDeclaredMethod("takeDamage", float.class, float.class);
			takeDamage.setAccessible(true);
			takeDamage.invoke(gc, 1, -1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void attack_validAmount_healthDecreased() throws CloneNotSupportedException {
		GameCharacter gc1 = (GameCharacter) gc.clone();
		GameCharacter gc2 = (GameCharacter) gc.clone();
		gc2.attack(gc1);
		assertEquals(
				GAMECHARACTER_HEALTH - (weapon.getDamage() * (100 - gc1.getArmor() + weapon.getPenetration()) / 100),
				gc1.getHealth(), MARGIN);
	}

	@Test
	public void changeWeapon_validWeapon_weaponChanged() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
		gc.changeWeapon(VALID_WEAPON);
		assertEquals(VALID_WEAPON, gc.getWeapon());
	}

	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_nullAsWeapon_exception() {
		gc.changeWeapon(null);
	}

	@Test(expected = IllegalWeaponException.class)
	public void changeWeapon_weaponNotInWeaponSetAsWeapon_exception() {
		gc.changeWeapon(VALID_WEAPON);
	}

	@Test
	public void changeWeapon_fistAsWeapon_weaponChanged() {
		gc.changeWeapon(new Fist());
		assertEquals(new Fist(), gc.getWeapon());
	}

	@Test
	public void dropWeapon_nA_weaponIsFist() {
		gc.dropWeapon();
		assertEquals(new Fist(), gc.getWeapon());
	}

	@Test
	public void setArmor_validArmor_armorSet() {
		float armor = 5.0F;
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, armor) {
		};
		assertEquals(armor, gc.getArmor(), MARGIN);
	}

	@Test(expected = NegativeArmorException.class)
	public void setArmor_negativeArmor_exception() {
		float armor = -5.0F;
		new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, armor) {
		};
	}

	@Test
	public void setArmor_moreThanMaxArmor_maxArmor() {
		float armor = GameCharacter.MAX_ARMOR + 1;
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, armor) {
		};
		assertEquals(GameCharacter.MAX_ARMOR, gc.getArmor(), MARGIN);
	}

	@Test
	public void setDirection_validDirection_directionChanged() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
			assertEquals(Directions.NORTH, gc.getDirection());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalDirectionException.class)
	public void setDirection_nullAsDirection_exception() throws Throwable {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, (Directions) null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void startAttacking_notYetAttacking_startsAttacking() {
		gc.startAttacking();
		assertTrue(gc.isAttacking());
	}

	@Test
	public void stopAttacking_alreadyAttacking_stopsAttacking() {
		gc.startAttacking();
		assertTrue(gc.isAttacking());
		gc.stopAttacking();
		assertFalse(gc.isAttacking());
	}

	@Test(expected = AlreadyAttackingException.class)
	public void startAttacking_alreadyAttacking_exception() {
		gc.startAttacking();
		gc.startAttacking();
	}

	@Test(expected = AlreadyPeacefulException.class)
	public void stopAttacking_notYetAttacking_stopsAttacking() {
		gc.stopAttacking();
	}

	@Test
	public void reset_alreadyAttacking_stopsAttacking() {
		gc.startAttacking();
		assertTrue(gc.isAttacking());
		gc.reset();
		assertFalse(gc.isAttacking());
	}

	@Test
	public void addHealthListener_validListener_healthListenerAdded() {
		HealthListener hL = new HealthListener() {
			@Override
			public void healthChanged(HealthEvent event) {
			}
		};
		gc.addHealthListener(hL);
		assertTrue(gc.getHealthListeners().contains(hL));
	}

	@Test
	public void removeHealthListener_validListener_healthListenerRemoved() {
		HealthListener hL = new HealthListener() {
			@Override
			public void healthChanged(HealthEvent event) {
			}
		};
		gc.addHealthListener(hL);
		assertTrue(gc.getHealthListeners().contains(hL));
		gc.removeHealthListener(hL);
		assertTrue(gc.getHealthListeners().isEmpty());
	}

	@Test
	public void broadcast_validHealthEvent_healthEventBroadcasted() {
		int test = 0;
		HealthEvent hE = new HealthEvent(test, 1);
		HealthListener hL = new HealthListener() {
			@Override
			public void healthChanged(HealthEvent event) {
				assertEquals(1, event.newHealth, MARGIN);
			}
		};
		gc.addHealthListener(hL);
		try {
			Method broadcast = GameCharacter.class.getDeclaredMethod("broadcast", HealthEvent.class);
			broadcast.setAccessible(true);
			broadcast.invoke(gc, hE);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveEAST_by1_positionOfCenterChangedAndTileIsTheSame() {
		try {
			Method moveEAST = GameCharacter.class.getDeclaredMethod("moveEAST");
			moveEAST.setAccessible(true);
			moveEAST.invoke(gc);
			assertEquals(tile.getSize().getWidth() / 2 + 1, gc.getPositionOfTextureCenterInTile().getX());
			assertEquals(tile, gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveEAST_byATilePlus1_positionOfCenterChangedAndTileChanged() {
		try {
			Method moveEAST = GameCharacter.class.getDeclaredMethod("moveEAST");
			moveEAST.setAccessible(true);
			IntStream.range(0, tile.getSize().getWidth() + 1).forEach(i -> {
				try {
					moveEAST.invoke(gc);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
			assertEquals(tile.getSize().getWidth() / 2 + 1, gc.getPositionOfTextureCenterInTile().getX());
			assertEquals(tile.getEASTwardTile(), gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveSOUTH_by1_positionOfCenterChangedAndTileIsTheSame() {
		try {
			Method moveSOUTH = GameCharacter.class.getDeclaredMethod("moveSOUTH");
			moveSOUTH.setAccessible(true);
			moveSOUTH.invoke(gc);
			assertEquals(tile.getSize().getHeight() / 2 + 1, gc.getPositionOfTextureCenterInTile().getY());
			assertEquals(tile, gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveSOUTH_byATilePlus1_positionOfCenterChangedAndTileChanged() {
		try {
			Method moveSOUTH = GameCharacter.class.getDeclaredMethod("moveSOUTH");
			moveSOUTH.setAccessible(true);
			IntStream.range(0, tile.getSize().getHeight() + 1).forEach(i -> {
				try {
					moveSOUTH.invoke(gc);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
			assertEquals(tile.getSize().getHeight() / 2 + 1, gc.getPositionOfTextureCenterInTile().getY());
			assertEquals(tile.getSOUTHwardTile(), gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveWEST_by1_positionOfCenterChangedAndTileIsTheSame() {
		try {
			Method moveWEST = GameCharacter.class.getDeclaredMethod("moveWEST");
			moveWEST.setAccessible(true);
			moveWEST.invoke(gc);
			assertEquals(tile.getSize().getWidth() / 2 - 1, gc.getPositionOfTextureCenterInTile().getX());
			assertEquals(tile, gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveWEST_byATilePlus1_positionOfCenterChangedAndTileChanged() {
		try {
			Method moveWEST = GameCharacter.class.getDeclaredMethod("moveWEST");
			moveWEST.setAccessible(true);
			IntStream.range(0, tile.getSize().getWidth() + 1).forEach(i -> {
				try {
					moveWEST.invoke(gc);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
			assertEquals(tile.getSize().getWidth() / 2 - 1, gc.getPositionOfTextureCenterInTile().getX());
			assertEquals(tile.getWESTwardTile(), gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveNORTH_by1_positionOfCenterChangedAndTileIsTheSame() {
		try {
			Method moveNORTH = GameCharacter.class.getDeclaredMethod("moveNORTH");
			moveNORTH.setAccessible(true);
			moveNORTH.invoke(gc);
			assertEquals(tile.getSize().getHeight() / 2 - 1, gc.getPositionOfTextureCenterInTile().getY());
			assertEquals(tile, gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void moveNORTH_byATilePlus1_positionOfCenterChangedAndTileChanged() {
		try {
			Method moveNORTH = GameCharacter.class.getDeclaredMethod("moveNORTH");
			moveNORTH.setAccessible(true);
			IntStream.range(0, tile.getSize().getHeight() + 1).forEach(i -> {
				try {
					moveNORTH.invoke(gc);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
			assertEquals(tile.getSize().getHeight() / 2 - 1, gc.getPositionOfTextureCenterInTile().getY());
			assertEquals(tile.getNORTHwardTile(), gc.getTile());
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void inFrontOf_goingEastAndinRangeAndInFrontAndAllAvailableTiles_true() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertTrue(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndinRangeAndInFrontAndAllAvailableTiles_true() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertTrue(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndinRangeAndInFrontAndAllAvailableTiles_true() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertTrue(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndinRangeAndInFrontAndAllAvailableTiles_true() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertTrue(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingEastAndOutOfRangeAndInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Tile OutOfRangeInFront = inFront.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront, OutOfRangeInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, OutOfRangeInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndOutOfRangeAndInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Tile OutOfRangeInFront = inFront.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront, OutOfRangeInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, OutOfRangeInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndOutOfRangeAndInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Tile OutOfRangeInFront = inFront.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront, OutOfRangeInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, OutOfRangeInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndOutOfRangeAndInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Tile OutOfRangeInFront = inFront.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront, OutOfRangeInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, OutOfRangeInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingEastAndInRangeAndInFrontAndMissing1AvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Tile twiceInFront = inFront.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile, twiceInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, twiceInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndInRangeAndInFrontAndMissing1AvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Tile twiceInFront = inFront.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, twiceInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, twiceInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndInRangeAndInFrontAndMissing1AvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Tile twiceInFront = inFront.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile, twiceInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, twiceInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndInRangeAndInFrontAndMissing1AvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Tile twiceInFront = inFront.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, twiceInFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, twiceInFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingEastAndinRangeAndNotInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndinRangeAndNotInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndinRangeAndNotInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndinRangeAndNotInFrontAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingEastAndinRangeAndInFrontAndNotAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndinRangeAndInFrontAndNotAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndinRangeAndInFrontAndNotAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndinRangeAndInFrontAndNotAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingEastAndinRangeAndBehindAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.EAST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getWESTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingSouthAndinRangeAndBehindAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.SOUTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getNORTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingWestAndinRangeAndBehindAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.WEST);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getEASTwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	@Test
	public void inFrontOf_goingNorthAndinRangeAndBehindAndAllAvailableTiles_false() {
		try {
			Method setDirection = GameCharacter.class.getDeclaredMethod("setDirection", Directions.class);
			setDirection.setAccessible(true);
			setDirection.invoke(gc, Directions.NORTH);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		int rangeX = 1;
		int rangeY = 1;

		Tile inFront = tile.getSOUTHwardTile();
		Set<Tile> availableTiles = Set.of(tile, inFront);
		assertFalse(gc.inFrontOf(rangeX, rangeY, inFront, availableTiles));
	}

	/* ANIMATED TESTS */
	@Test
	public void startMoving_fromNonMovingState_moving() {
		gc.startMoving();
		assertTrue(gc.isMoving());
	}

	@Test
	public void stopMoving_fromMovingState_notMoving() {
		gc.startMoving();
		assertTrue(gc.isMoving());
		gc.stopMoving();
		assertFalse(gc.isMoving());
	}

	@Test(expected = AlreadyMovingException.class)
	public void startMoving_fromMovingState_exception() {
		gc.startMoving();
		gc.startMoving();
	}

	@Test(expected = AlreadyStoppedException.class)
	public void stopMoving_fromNonMovingState_exception() {
		gc.startMoving();
		gc.stopMoving();
		gc.stopMoving();
	}

	@Test
	public void setMovementFrame_frameBetween1AndAnimationLength_frameSet() {
		try {
			Method setMovementFrame = GameCharacter.class.getDeclaredMethod("setMovementFrame", int.class);
			setMovementFrame.setAccessible(true);
			setMovementFrame.invoke(gc, 2);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(2, gc.getMovementFrame());
	}

	@Test(expected = IllegalMovementFrameException.class)
	public void setMovementFrame_lessThan1_exception() throws Throwable {
		try {
			Method setMovementFrame = GameCharacter.class.getDeclaredMethod("setMovementFrame", int.class);
			setMovementFrame.setAccessible(true);
			setMovementFrame.invoke(gc, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void setMovementFrame_moreThanAnimationLength_firstFrame() {
		try {
			Method setMovementFrame = GameCharacter.class.getDeclaredMethod("setMovementFrame", int.class);
			setMovementFrame.setAccessible(true);
			setMovementFrame.invoke(gc, Animated.ANIMATION_LENGTH + 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(1, gc.getMovementFrame());
	}

	@Test
	public void resetMovementFrame_nA_firstFrame() {
		try {
			Method setMovementFrame = GameCharacter.class.getDeclaredMethod("setMovementFrame", int.class);
			setMovementFrame.setAccessible(true);
			setMovementFrame.invoke(gc, 2);
			assertEquals(2, gc.getMovementFrame());

			Method resetMovementFrame = GameCharacter.class.getDeclaredMethod("resetMovementFrame");
			resetMovementFrame.setAccessible(true);
			resetMovementFrame.invoke(gc);
			assertEquals(1, gc.getMovementFrame());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAssociatedFrameNumber_validFrameNumber_associatedFrameNumber() {
		try {
			Method getAssociatedFrameNumber = GameCharacter.class.getDeclaredMethod("getAssociatedFrameNumber",
					int.class);
			getAssociatedFrameNumber.setAccessible(true);
			assertEquals(1, getAssociatedFrameNumber.invoke(gc, 2));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalFrameNumberException.class)
	public void getAssociatedFrameNumber_0AsFrameNumber_exception() throws Throwable {
		try {
			Method getAssociatedFrameNumber = GameCharacter.class.getDeclaredMethod("getAssociatedFrameNumber",
					int.class);
			getAssociatedFrameNumber.setAccessible(true);
			getAssociatedFrameNumber.invoke(gc, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test(expected = IllegalFrameNumberException.class)
	public void getAssociatedFrameNumber_moreThanAnimationLengthAsFrameNumber_exception() throws Throwable {
		try {
			Method getAssociatedFrameNumber = GameCharacter.class.getDeclaredMethod("getAssociatedFrameNumber",
					int.class);
			getAssociatedFrameNumber.setAccessible(true);
			getAssociatedFrameNumber.invoke(gc, Animated.ANIMATION_LENGTH + 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void setNextTexture_onNonMovingTexture_noNextTexture() {
		gc.setNextTexture();
		assertEquals(VALID_TEXTURE, gc.getTexture());
	}

	@Test
	public void setNextTexture_onMovingTexture_nextTexture() {
		GameCharacter gc = new GameCharacter(Set.of(VALID_TEXTURE, NEXT_VALID_TEXTURE), VALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
		gc.startMoving();
		gc.setNextTexture();
		assertEquals(NEXT_VALID_TEXTURE, gc.getTexture());
	}

	@Test(expected = NoMoveAnimationException.class)
	public void setNextTexture_onMovingTextureWithoutMoveAnimation_exception() {
		GameCharacter gc = new GameCharacter(Set.of(INVALID_TEXTURE), INVALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
		gc.startMoving();
		gc.setNextTexture();
	}

	/* END ANIMATED TESTS */

	/* HITBOX TESTS */

	@Test
	public void getHitBox_0AsRange_hitBoxPositions() {
		int range = 0;

		Set<Position> positions = new LinkedHashSet<>();

		int textureCenterX = gc.getAbsoluteCenterPosition().getX();
		int textureCenterY = gc.getAbsoluteCenterPosition().getY();
		int minHitBoxX = textureCenterX - VALID_TEXTURE.getHitBoxSize().getWidth() / 2 - range;
		int minHitBoxY = textureCenterY - VALID_TEXTURE.getHitBoxSize().getHeight() / 2 - range;
		int maxHitBoxX = textureCenterX + VALID_TEXTURE.getHitBoxSize().getWidth() / 2 + range;
		int maxHitBoxY = textureCenterY + VALID_TEXTURE.getHitBoxSize().getHeight() / 2 + range;

		for (int x = minHitBoxX; x <= maxHitBoxX; x++) {
			for (int y = minHitBoxY; y <= maxHitBoxY; y++) {
				if (x == minHitBoxX || y == minHitBoxY || x == maxHitBoxX || y == maxHitBoxY)
					positions.add(new Position(x, y));
			}
		}
		assertEquals(positions, gc.getHitBox(0));
	}

	@Test(expected = InvalidTextureException.class)
	public void getHitBox_0AsRangeNoHitboxTexture_exception() {
		GameCharacter gc = new GameCharacter(Set.of(INVALID_TEXTURE), INVALID_TEXTURE, tile, "VALID",
				Set.of((WeaponTextures) weapon.getTexture(), (WeaponTextures) VALID_WEAPON.getTexture()),
				GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL, GAMECHARACTER_ARMOR) {
		};
		gc.getHitBox(0);
	}

	@Test(expected = TooLittleRangeException.class)
	public void getHitBox_lessThanHAlfHitboxSizeAsRange_exception() {
		int min = Math.min(VALID_TEXTURE.getHitBoxSize().getWidth() / 2, VALID_TEXTURE.getHitBoxSize().getHeight() / 2);
		gc.getHitBox(-min);
	}

	/* END HITBOX TESTS */
	/* END GAMECHARACTER TESTS */

	@Test
	public void loseALife_once_livesDecreased() {
		hero.loseALife();
		assertEquals(HERO_LIVES - 1, hero.getLives());
	}

	@Test(expected = OutOfLivesException.class)
	public void loseALife_livesTimes_exception() {
		IntStream.range(0, HERO_LIVES).forEach(i -> hero.loseALife());
	}

	@Test
	public void increaseLives_by1_livesIncreased() {
		hero.increaseLives(1);
		assertEquals(HERO_LIVES + 1, hero.getLives());
	}

	@Test
	public void increaseLives_by0_livesUnchanged() {
		hero.increaseLives(0);
		assertEquals(HERO_LIVES, hero.getLives());
	}

	@Test
	public void increaseLives_byMoreThanMaxLives_maxLives() {
		hero.increaseLives(Hero.MAX_LIVES + 1);
		assertEquals(Hero.MAX_LIVES, hero.getLives());
	}

	@Test
	public void getExperience_nA_heroStartsWith0Experience() {
		assertEquals(0, hero.getExperience(), MARGIN);
	}

	@Test
	public void increaseExperience_byPositiveAmountSmallerThanRequiredForNextLevel_experienceIncreased() {
		hero.increaseExperience(1);
		assertEquals(1, hero.getExperience(), MARGIN);
	}

	@Test
	public void increaseExperience_byPositiveAmountRequiredForNextLevel_levelIncreased() {
		hero.increaseExperience(hero.getLevel() * 100);
		assertEquals(0, hero.getExperience(), MARGIN);
		assertEquals(GAMECHARACTER_LEVEL + 1, hero.getLevel());
	}

	@Test
	public void increaseExperience_byPositiveAmountRequiredForNextLevelPlus1_experienceIncreased() {
		hero.increaseExperience(hero.getLevel() * 100 + 1);
		assertEquals(1, hero.getExperience(), MARGIN);
		assertEquals(GAMECHARACTER_LEVEL + 1, hero.getLevel());
	}

	@Test
	public void increaseExperience_byPositiveAmountRequiredForTwoNextLevel_levelIncreased() {
		hero.increaseExperience(hero.getLevel() * 100);
		assertEquals(0, hero.getExperience(), MARGIN);
		assertEquals(GAMECHARACTER_LEVEL + 1, hero.getLevel());
		hero.increaseExperience(hero.getLevel() * 100);
		assertEquals(0, hero.getExperience(), MARGIN);
		assertEquals(GAMECHARACTER_LEVEL + 2, hero.getLevel());
	}

	@Test(expected = NegativeExperienceException.class)
	public void increaseExperience_byNegativeAmount_exception() {
		hero.increaseExperience(-1);
	}

	@Test
	public void setHealth_validAmountBetween1AndMaxHealth_healthSet() {
		try {
			Method setHealth = Hero.class.getDeclaredMethod("setHealth", float.class);
			setHealth.setAccessible(true);
			setHealth.invoke(hero, 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(1, hero.getHealth(), MARGIN);
	}

	@Test
	public void setHealth_0AsHealthAndMoreThan1Life_lostALifeAndRegainedAllHisHealth() throws Throwable {
		try {
			Method setHealth = Hero.class.getDeclaredMethod("setHealth", float.class);
			setHealth.setAccessible(true);
			setHealth.invoke(hero, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
		assertEquals(hero.getMaxHealth(), hero.getHealth(), MARGIN);
		assertEquals(HERO_LIVES - 1, hero.getLives());
	}

	@Test(expected = CharacterDiedException.class)
	public void setHealth_AsHealthAnd1LifeRemaining_exception() throws Throwable {
		IntStream.range(0, HERO_LIVES - 1).forEach(i -> hero.loseALife());
		try {
			Method setHealth = Hero.class.getDeclaredMethod("setHealth", float.class);
			setHealth.setAccessible(true);
			setHealth.invoke(hero, 0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	public void addLifeListener_toValidLifeListener_listenerAdded() {
		LifeListener lL = new LifeListener() {

			@Override
			public void lifeChanged(LifeEvent event) {
			}
		};
		hero.addLifeListener(lL);
		assertTrue(hero.getLifeListeners().contains(lL));
	}

	@Test
	public void removeLifeListener_validLifeListener_listenerremoved() {
		LifeListener lL = new LifeListener() {

			@Override
			public void lifeChanged(LifeEvent event) {
			}
		};
		hero.addLifeListener(lL);
		assertTrue(hero.getLifeListeners().contains(lL));
		hero.removeLifeListener(lL);
		assertTrue(hero.getLifeListeners().isEmpty());
	}

	@Test
	public void broadcast_validLifeEvent_lifeEventBroadcasted() {
		LifeEvent lE = new LifeEvent(HERO_LIVES, 1);
		LifeListener lL = new LifeListener() {
			@Override
			public void lifeChanged(LifeEvent event) {
				assertEquals(1, event.newLives);
			}
		};
		hero.addLifeListener(lL);
		try {
			Method broadcast = Hero.class.getDeclaredMethod("broadcast", LifeEvent.class);
			broadcast.setAccessible(true);
			broadcast.invoke(hero, lE);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void setNextTexture_nA_stoppedMoving() {
		hero.startMoving();
		hero.setNextTexture();
		assertFalse(hero.isMoving());
	}

	@Test
	public void move_EASTDirectionAndNoUnavailablePositions_startedMoving() {
		hero.move(Directions.EAST, Set.of());
		assertTrue(hero.isMoving());
	}

	@Test
	public void move_SOUTHDirectionAndNoUnavailablePositions_startedMoving() {
		hero.move(Directions.SOUTH, Set.of());
		assertTrue(hero.isMoving());
	}

	@Test
	public void move_WESTDirectionAndNoUnavailablePositions_startedMoving() {
		hero.move(Directions.WEST, Set.of());
		assertTrue(hero.isMoving());
	}

	@Test
	public void move_NORTHDirectionAndNoUnavailablePositions_startedMoving() {
		hero.move(Directions.NORTH, Set.of());
		assertTrue(hero.isMoving());
	}

	@Test(expected = IllegalDirectionException.class)
	public void move_nullAsDirectionAndNoUnavailablePositions_exception() {
		hero.move((Directions) null, Set.of());
	}

	@Test(expected = IllegalPositionsSetException.class)
	public void move_validDirectionAndNullAsUnavailablePositions_exception() {
		hero.move(Directions.SOUTH, null);
	}

	@Test(expected = InvalidTextureException.class)
	public void move_validDirectionAndNoUnavailablePositionsAndTextureIsNotHitBoxTexture_exception() {
		hero = new Hero(Set.of(VALID_TEXTURE, INVALID_TEXTURE), VALID_TEXTURE, tile, GAMECHARACTER_NAME,
				Set.of((WeaponTextures) weapon.getTexture()), GAMECHARACTER_HEALTH, weapon, GAMECHARACTER_LEVEL,
				GAMECHARACTER_ARMOR, HERO_LIVES) {
		};
		hero.setTexture(INVALID_TEXTURE);
		hero.move(Directions.SOUTH, Set.of());
	}

	@Test
	public void move_EASTDirectionAndNoUnavailablePositionsUntilReachingExteriorWallsPlus1_stopsMovingAtWall() {
		IntStream.range(0, Board.SIZE.getWidth()).forEach(i -> hero.move(Directions.EAST, Set.of()));
		int hitBoxWidth = ((MageTextures) hero.getTexture()).getHitBoxSize().getWidth();

		assertEquals(Board.SIZE.getWidth() - Board.exteriorWallSize.getWidth() - hitBoxWidth / 2,
				hero.getAbsoluteCenterPosition().getX());
	}

	@Test
	public void move_SOUTHDirectionAndNoUnavailablePositionsUntilReachingExteriorWallsPlus1_stopsMovingAtWall() {
		IntStream.range(0, Board.SIZE.getHeight() + 1).forEach(i -> hero.move(Directions.SOUTH, Set.of()));
		int hitBoxHeight = ((MageTextures) hero.getTexture()).getHitBoxSize().getHeight();

		assertEquals(Board.SIZE.getHeight() - Board.exteriorWallSize.getHeight() - hitBoxHeight / 2,
				hero.getAbsoluteCenterPosition().getY());
	}

	@Test
	public void move_WESTDirectionAndNoUnavailablePositionsUntilReachingExteriorWallsPlus1_stopsMovingAtWall() {
		IntStream.range(0, Board.SIZE.getWidth()).forEach(i -> hero.move(Directions.WEST, Set.of()));
		int hitBoxWidth = ((MageTextures) hero.getTexture()).getHitBoxSize().getWidth();
		assertEquals(Board.exteriorWallSize.getWidth() - 1 + hitBoxWidth / 2, hero.getAbsoluteCenterPosition().getX());
	}

	@Test
	public void move_NORTHDirectionAndNoUnavailablePositionsUntilReachingExteriorWallsPlus1_stopsMovingAtWall() {
		IntStream.range(0, Board.SIZE.getHeight() + 1).forEach(i -> hero.move(Directions.NORTH, Set.of()));
		int hitBoxHeight = ((MageTextures) hero.getTexture()).getHitBoxSize().getHeight();
		assertEquals(Board.exteriorWallSize.getHeight() - 1 + hitBoxHeight / 2,
				hero.getAbsoluteCenterPosition().getY());
	}

	/* END HERO TESTS */
	
	@Test
	public void decreaseMana_byAmountBetween1andCurrenMana_manaDecreased() {
		mage.decreaseMana(1);
		assertEquals(HERO_MANA-1, mage.getMana(), MARGIN);
	}
	
	@Test(expected = NegativeManaException.class)
	public void decreaseMana_byNegativeAmount_exception() {
		mage.decreaseMana(-1);
	}
	
	@Test(expected = NotEnoughManaException.class)
	public void decreaseMana_byMoreThanCurrenManaAmount_exception() {
		mage.decreaseMana(HERO_MANA+1);
	}
	
	@Test
	public void increaseMana_byAmountBetweenCurrenManaAndMaxMana_manaIncreased() {
		mage.increaseMana(1);
		assertEquals(HERO_MANA+1, mage.getMana(), MARGIN);
	}

	@Test(expected = NegativeManaException.class)
	public void increaseMana_byNegativeAmount_exception() {
		mage.increaseMana(-1);
	}
	
	@Test
	public void increaseMana_byMoreThanMaxMana_maxMana() {
		mage.increaseMana(Mage.MAX_MANA+1);
		assertEquals(Mage.MAX_MANA, mage.getMana(), MARGIN);
	}
	/* END MAGE TESTS */
}
