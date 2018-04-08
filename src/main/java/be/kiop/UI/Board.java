package be.kiop.UI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.enemies.skeletons.Skeletons;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warriors;
import be.kiop.items.Drop;
import be.kiop.listeners.RepaintTimer;
import be.kiop.maze.Maze;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.WallTextures;
import be.kiop.utils.SetUtils;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.valueobjects.Tile;

public class Board extends JFrame {
	private static final long serialVersionUID = 1L;

	private BoardDrawing boardDrawing;
	private HUD hud;

	private final int horizontalTiles;
	private final int verticalTiles;

	private Set<Tile> occupiedTiles = new LinkedHashSet<>();
	private Set<Tile> availableTiles = new LinkedHashSet<>();

	private Hero hero;
	private Enemy activeEnemy;
	private Set<Enemy> enemies = new LinkedHashSet<>();
	private Set<Wall> walls = new LinkedHashSet<>();
	private Set<Fire> fires = new LinkedHashSet<>();
	private List<Drop> drops = new ArrayList<>();
//	private final Set<Position> fixedHitBoxes;

	public static final Size exteriorWallSize = new Size(32, 32);

//	static int testWidth = 1888;
//	static int testHeight = 1008;
//	private static Size size = new Size(2 * exteriorWallSize.getWidth() + testWidth,
//			2 * exteriorWallSize.getHeight() + testHeight);
	private final Size size;

	public final static Size TILE_SIZE = new Size(32, 48);
	public final static Size MAX_SIZE = new Size(4000, 4000);

	public Board(int horizontalTiles, int verticalTiles) {
		this.horizontalTiles = horizontalTiles;
		this.verticalTiles = verticalTiles;

		Set<Tile> unavailableTiles = new Maze(horizontalTiles, verticalTiles).generateMaze();
		availableTiles = findAvailableTiles(horizontalTiles, verticalTiles, unavailableTiles);

		this.size = Size.sum(Size.product(TILE_SIZE, horizontalTiles, verticalTiles),
				Size.product(exteriorWallSize, 2, 2));

		hero = generateHero((Hero) Warriors.Warrior_1_F.getGameCharacter());

		walls = generateAllWalls(unavailableTiles);
//		fixedHitBoxes = calculateFixedHitBoxes();
//		fires = generateFirePits();

		enemies = generateEnemies(4, (Enemy) Skeletons.Skeleton_1.getGameCharacter());
		enemies.addAll(generateEnemies(4, (Enemy) Skeletons.Skeleton_Dog_1.getGameCharacter()));

		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(size, FloorTextures.Floor_Stone_Light_Grey_NONE,
				WallTextures.Wall_Mettalic_Dark, hero, walls, fires, enemies, drops, this);
		hud = new HUD(hero, null);
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		add(hud, BorderLayout.NORTH);
		add(boardDrawing, BorderLayout.CENTER);

		new RepaintTimer(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}

	private static Set<Tile> findAvailableTiles(int horizontalTiles, int verticalTiles, Set<Tile> unavailableTiles) {
		Set<Tile> availableTiles = new LinkedHashSet<>();
		for (int i = 0; i < horizontalTiles; i++) {
			for (int j = 0; j < verticalTiles; j++) {
				availableTiles.add(new Tile(i, j));
			}
		}
		availableTiles.removeAll(unavailableTiles);
		return availableTiles;
	}

	private static Hero generateHero(Hero hero) {
//		WarriorTextures HERO_SKIN = WarriorTextures.Warrior_MALE_SOUTH_1;
//		String HERO_NAME = "Warrior";
//		float HERO_HEALTH = 1000;
//		int HERO_LEVEL = 10;
//		int HERO_LIVES = 5;
//		float HERO_ARMOR = 50;
//		float HERO_EXPERIENCE = 200;
//		float HERO_SHIELD = 10;
//		Weapon weapon = new Sword(WeaponTextures.Sword, new Position(0, 0), "Heavy Sword", 50, 75, 80, 70, 100, 5, 10, 20, 50,
//				70);
//		Position position = new Position(32, 32);
		Tile tile = Tile.ORIGIN;
//		hero.setPosition(position);
		hero.setTile(tile);
		return hero;
//		return new Warrior(HERO_SKIN, position, tile, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
//				HERO_EXPERIENCE, HERO_SHIELD);
	}

	private Set<Wall> generateAllWalls(Set<Tile> unavailableTiles) {
//		Set<Wall> allWalls = new LinkedHashSet<>();
//		allWalls.addAll(generateInteriorWalls(WallTextures.Wall_Stone, unavailableTiles));
//		allWalls.addAll(generateExteriorWalls(WallTextures.Wall_Metallic));
		return generateInteriorWalls(WallTextures.Wall_Stone, unavailableTiles);
	}

//	private Set<Wall> generateExteriorWalls(WallTextures wall) {
//		Set<Wall> walls = new LinkedHashSet<>();
//		for (int x = 0; x <= size.getWidth(); x += wall.getSize().getWidth()) {
//			for (int y = 0; y <= size.getHeight(); y += wall.getSize().getHeight()) {
//				if (x >= size.getWidth() - wall.getSize().getWidth()
//						&& y < size.getHeight() - wall.getSize().getHeight()) {
//					walls.add(new Wall(wall, new Position(size.getWidth() - wall.getSize().getWidth(), y)));
//				} else if (y >= size.getHeight() - wall.getSize().getHeight()
//						&& x < size.getWidth() - wall.getSize().getWidth()) {
//					walls.add(new Wall(wall, new Position(x, size.getHeight() - wall.getSize().getHeight())));
//				} else if (y >= size.getHeight() - wall.getSize().getHeight()
//						&& x >= size.getWidth() - wall.getSize().getWidth()) {
//
//				} else if (x == 0 || y == 0) {
//					walls.add(new Wall(wall, new Position(x, y)));
//				}
//			}
//		}
//		walls.add(new Wall(wall, new Position(size.getWidth() - wall.getSize().getWidth(),
//				size.getHeight() - wall.getSize().getHeight())));
//		return walls;
//	}

	private Set<Wall> generateInteriorWalls(WallTextures wall, Set<Tile> unavailableTiles) {
//		Set<Position> maze = generateMaze();
		Set<Wall> walls = new LinkedHashSet<>();
//		Position tilePosition;

		for (Tile tile : unavailableTiles) {
//			tilePosition = new Position(tile.getHorizontalPosition() * TILE_SIZE.getWidth(),
//					tile.getVerticalPosition() * TILE_SIZE.getHeight());
//			tilePosition.add(exteriorWallSize.getWidth(), exteriorWallSize.getHeight());
			walls.add(new Wall(wall, tile, false));
		}
		return walls;
//		return maze.stream().map(position -> new Wall(wall, position)).collect(Collectors.toSet());
	}

//	private Set<Fire> generateFirePits() {
//		FireTextures fire = FireTextures.Fire_2;
//		Set<Position> positions = Set.of(new Position(0, 0),
//				new Position(0, size.getHeight() - fire.getSize().getHeight()),
//				new Position(size.getWidth() - fire.getSize().getWidth(), 0), new Position(
//						size.getWidth() - fire.getSize().getWidth(), size.getHeight() - fire.getSize().getHeight()));
//		return positions.stream().map(pos -> new Fire(fire, pos)).collect(Collectors.toSet());
//	}

	private Set<Enemy> generateEnemies(int amount, Enemy enemy) {
		Set<Enemy> enemies = new LinkedHashSet<>();
//		Skeletons skel = Skeletons.Skeleton_SOUTH_2;
		Enemy addedEnemy;

		List<Tile> availableTilesList = new ArrayList<>();
		availableTilesList.addAll(availableTiles);

		Random random = new Random();
		Tile randomTile;

		for (int i = 0; i < amount; i++) {
			addedEnemy = (Enemy) enemy.copy();
//			System.out.println("added enemy: " + addedEnemy);
//			int textureWidth = enemy.getTexture().getSkin().getWidth();
//			int textureHeight = enemy.getTexture().getSkin().getHeight();

//			int widthDiff = textureWidth > TILE_SIZE.getWidth()
//					? TILE_SIZE.getWidth() - enemy.getTexture().getSkin().getWidth()
//					: enemy.getTexture().getSkin().getWidth() - TILE_SIZE.getWidth();
//			int heightDiff = textureHeight > TILE_SIZE.getHeight()
//					? TILE_SIZE.getHeight() - enemy.getTexture().getSkin().getHeight()
//					: enemy.getTexture().getSkin().getHeight() - TILE_SIZE.getHeight();

//			int offsetX = (widthDiff) / 2 + exteriorWallSize.getWidth();
//			int offsetY = (heightDiff) / 2 + exteriorWallSize.getHeight();

			randomTile = availableTilesList.get(random.nextInt(availableTilesList.size()));

			addedEnemy.setTile(randomTile);
//			addedEnemy.setPosition(Position.getAssociatedPosition(randomTile, TILE_SIZE, offsetX, offsetY));

			availableTiles.remove(randomTile);
			occupiedTiles.add(randomTile);
//			System.out.println("random tile: " + randomTile);
//			System.out.println("offset x: " + offsetX);
//			System.out.println("offset y: " + offsetY);

			enemies.add(addedEnemy);

//		for (int i = 0; i < amount; i++) {
//			Random random = new Random();
//			int offsetX = (skel.getSkin().getWidth() - corridorSize.getWidth()) / 2;
//			int offsetY = (skel.getSkin().getHeight() - corridorSize.getHeight()) / 2;
//
//			int randX = random.nextInt((size.getWidth() - 2 * exteriorWallSize.getWidth()) / corridorSize.getWidth());
//			int randY = random
//					.nextInt((size.getHeight() - 2 * exteriorWallSize.getHeight()) / corridorSize.getHeight());
//
//			int posX = randX * corridorSize.getWidth() + exteriorWallSize.getWidth();
//			int posY = randY * corridorSize.getHeight() + exteriorWallSize.getHeight();
//
//			while (fixedHitBoxes.contains(new Position(posX, posY))) {
//				randX = random.nextInt((size.getWidth() - 2 * exteriorWallSize.getWidth()) / corridorSize.getWidth());
//				randY = random
//						.nextInt((size.getHeight() - 2 * exteriorWallSize.getHeight()) / corridorSize.getHeight());
//
//				posX = randX * corridorSize.getWidth() + exteriorWallSize.getWidth();
//				posY = randY * corridorSize.getHeight() + exteriorWallSize.getHeight();
//			}

//			enemies.add(new Skeleton(skel, new Position(posX - offsetX, posY - offsetY), "Skek", 100,
//					new Bone(Weapons.Bone,
//							new Position(posX + skel.getSize().getWidth() / 2, posY + skel.getSize().getHeight() / 2),
//							"Little Bone", 5, 10, 40, 30, 100, 5, 10, 20),
//					5, 50, Set.of(new Sword(Weapons.Sword, new Position(48, 48), "Heavy Sword", 50, 75, 40, 30, 100, 5,
//							10, 20, 50, 70))));
		}
		return enemies;
	}

	public Size getSize(boolean i) {
		return size;
	}

	@Override
	public void paint(Graphics g) {
		hud.repaint();

		handleTheDeads();
		findActiveEnemy();
		moveEnemies();
		//getAllHitBoxes(0);

		boardDrawing.repaint();
	}

	private void moveEnemies() {
//		Set<Position> allHitBoxes = getAllHitBoxes(0);
//		for (Enemy enemy : enemies) {
//			enemy.move(allHitBoxes);
//			if (enemy.isActive()) {
////				System.out.println("I can see you");
//				if (collision(enemy.getHitBox(2), hero.getHitBox(2))) {
//					hero.takeDamage(enemy.getWeapon().getDamage() * 10);
//				}
//				enemy.setActive(false);
//			}
//
//		}
		for (Enemy enemy : enemies) {
			availableTiles = enemy.move(availableTiles);
		}
	}

	private void handleTheDeads() {
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = iterator.next();
			if (enemy.getHealth() == 0) {
				Drop drop = enemy.getDrop().get();
				if (drop != null) {
					drop.setTile(enemy.getTile());
					drops.add(drop);
				}
				iterator.remove();
			}
		}
	}

//	private boolean collision(Set<Position> hitBox1, Set<Position> hitBox2) {
//		return !Collections.disjoint(hitBox1, hitBox2);
//	}

	public Set<Position> getAllHitBoxes(int range) {
		Set<Position> allHitBoxes = new LinkedHashSet<>(); // fixedHitBoxes
		for (Enemy enemy : enemies) {
			allHitBoxes.addAll(enemy.getHitBox(range));
		}
		allHitBoxes.addAll(hero.getHitBox(range));

//		System.out.println("hitbox size: " + allHitBoxes.size());
		return allHitBoxes;
	}

//	private Set<Position> calculateFixedHitBoxes() {
//		Set<Position> fixedHitBoxes = new LinkedHashSet<>();
//
//		for (Obstacle obstacle : getAllObstacles()) {
//			for (Position pos : obstacle.getHitBox(0)) {
//				if (pos.getX() >= exteriorWallSize.getWidth() - 1
//						&& pos.getX() <= size.getWidth() - exteriorWallSize.getWidth()) {
//					if (pos.getY() >= exteriorWallSize.getHeight() - 1
//							&& pos.getY() <= size.getHeight() - exteriorWallSize.getHeight()) {
//
//						if (!(fixedHitBoxes.contains(pos.right()) || fixedHitBoxes.contains(pos.down())
//								|| fixedHitBoxes.contains(pos.left()) || fixedHitBoxes.contains(pos.up()))) {
//							fixedHitBoxes.add(pos);
//						}
//					}
//				}
//			}
//		}
//		System.out.println("hitbox size= " + fixedHitBoxes.size());
//		return fixedHitBoxes;
//	}
//
//	public Set<Position> getFixedHitBoxes() {
//		return fixedHitBoxes;
//	}

//	public Set<Obstacle> getAllObstacles() {
//		Set<Obstacle> allObstacles = new LinkedHashSet<>(walls);
//		return allObstacles;
//	}

	public Enemy getActiveEnemy() {
		return activeEnemy;
	}

	public Optional<Enemy> enemyInRange() {
//		System.out.println("range: " + hero.getWeapon().getRange());
		for (Enemy enemy : enemies) {
			if (hero.inFrontOf(1, 1, enemy.getTile(), SetUtils.merge(availableTiles, occupiedTiles))) {
//				System.out.println("ennemy in range");
				return Optional.ofNullable(enemy);
			}
		}
		return Optional.empty();
	}

	private void findActiveEnemy() {
		for (Enemy enemy : enemies) {
			if (enemy.inFrontOf(2, 2, hero.getTile(), availableTiles)) {
				enemy.setActive(true);
			}
		}
	}

	public int getHorizontalTiles() {
		return horizontalTiles;
	}

	public int getVerticalTiles() {
		return verticalTiles;
	}
}
