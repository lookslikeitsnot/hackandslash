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
import be.kiop.events.TileEvent;
import be.kiop.exceptions.IllegalDropException;
import be.kiop.items.Drop;
import be.kiop.listeners.RepaintTimer;
import be.kiop.listeners.TileListener;
import be.kiop.maze.Maze;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.WallTextures;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.valueobjects.Tile;
import be.kiop.weapons.Staff;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Board extends JFrame implements TileListener{
	private static final long serialVersionUID = 1L;

	private BoardDrawing boardDrawing;
	private HUD hud;
	private BackpackPanel backpackPanel;

	private static int maxHorizontalTiles;
	private static int maxVerticalTiles;

	private final Set<Tile> allAvailableTiles;

	private Set<Tile> occupiedTiles = new LinkedHashSet<>();
	private Set<Tile> wallTiles = new LinkedHashSet<>();

	private Hero hero;
	private Enemy activeEnemy;
	private Set<Enemy> enemies = new LinkedHashSet<>();
	private Set<Wall> walls = new LinkedHashSet<>();
	private Set<Fire> fires = new LinkedHashSet<>();
	private List<Drop> drops = new ArrayList<>();

	public static final Size exteriorWallSize = new Size(32, 32);

	public static Size SIZE;

	//public final static Size TILE_SIZE = new Size(32, 48);
	public final static Size MAX_SIZE = new Size(4000, 4000);

	public Board(int horizontalTiles, int verticalTiles){
		maxHorizontalTiles = horizontalTiles;
		maxVerticalTiles = verticalTiles;

		wallTiles = Maze.generateMaze(horizontalTiles, verticalTiles);
		allAvailableTiles = findAllAvailableTiles(wallTiles);

		SIZE = Size.sum(Size.product(new Size(32, 48), maxHorizontalTiles, maxVerticalTiles),
				Size.product(exteriorWallSize, 2, 2));

		hero = generateHero((Hero) Warriors.Warrior_1_M.getGameCharacter());
		hero.addTileListener(this);

		walls = generateAllWalls(wallTiles);
//		fires = generateFirePits();

		enemies = generateEnemies(8, (Enemy) Skeletons.Skeleton_1.getGameCharacter());
//		enemies.addAll(generateEnemies(128, (Enemy) Skeletons.Skeleton_Dog_1.getGameCharacter()));

		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(SIZE, FloorTextures.Floor_Stone_Light_Grey_NONE,
				WallTextures.Wall_Mettalic_Dark, hero, walls, fires, enemies, drops, this, new Size(32, 48));
		hud = new HUD(hero, null);
		backpackPanel = new BackpackPanel(hero);
		
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		add(hud, BorderLayout.NORTH);
		add(boardDrawing, BorderLayout.CENTER);
		add(backpackPanel, BorderLayout.SOUTH);

		new RepaintTimer(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}

	public static int getMaxHorizontalTiles() {
		return maxHorizontalTiles;
	}

	public static int getMaxVerticalTiles() {
		return maxVerticalTiles;
	}

	private Set<Tile> findAllAvailableTiles(Set<Tile> unavailableTiles) {
		Set<Tile> allAvailableTiles = Maze.generateAllTiles(maxHorizontalTiles, maxVerticalTiles);
		allAvailableTiles.removeAll(unavailableTiles);
		return allAvailableTiles;
	}

	private Hero generateHero(Hero hero) {
		Tile tile = Tile.ORIGIN;
		hero.setTile(tile);
//		availableTiles.remove(tile);
		occupiedTiles.add(tile);
		return hero;
	}

	private Set<Wall> generateAllWalls(Set<Tile> unavailableTiles) {
		return generateInteriorWalls(WallTextures.Wall_Stone, unavailableTiles);
	}

	private Set<Wall> generateInteriorWalls(WallTextures wall, Set<Tile> unavailableTiles) {
		Set<Wall> walls = new LinkedHashSet<>();

		for (Tile tile : unavailableTiles) {
			walls.add(new Wall(wall, tile, false));
		}
		return walls;
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
		Enemy addedEnemy;

		List<Tile> availableTilesList = new ArrayList<>();
		availableTilesList.addAll(allAvailableTiles);
		availableTilesList.removeAll(occupiedTiles);

		Random random = new Random();
		Tile randomTile;

		for (int i = 0; i < amount; i++) {
			addedEnemy = (Enemy) enemy.clone();

			randomTile = availableTilesList.get(random.nextInt(availableTilesList.size()));

			addedEnemy.setTile(randomTile);
			addedEnemy.addTileListener(this);

			occupiedTiles.add(randomTile);
			enemies.add(addedEnemy);

		}
		return enemies;
	}

	@Override
	public void paint(Graphics g) {
		hud.repaint();
		backpackPanel.repaint();

		handleTheDeads();
		findActiveEnemy();
		moveEnemies();

		boardDrawing.repaint();
	}

	private void moveEnemies() {
		Set<Tile> availableTiles = new LinkedHashSet<>(allAvailableTiles);
		availableTiles.removeAll(occupiedTiles);
		for (Enemy enemy : enemies) {
			enemy.move(availableTiles);
		}
	}

	private void handleTheDeads() {
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = iterator.next();
			if (enemy.getHealth() == 0) {
				Drop drop = enemy.getDrop().get();
				
				if(drop instanceof Sword) {
					Sword sword = new Sword((Sword) drop);
					sword.setTile(enemy.getTile());
					drops.add(sword);
				}
				if(drop instanceof Staff) {
					Staff staff = new Staff((Staff) drop);
					staff.setTile(enemy.getTile());
					drops.add(staff);
				}
//				System.out.println("drops contains drop: " + drops.contains(drop));
//				if (drop != null) {
//					drop.setTile(enemy.getTile());
//					drops.add(drop);
//				}
//				System.out.println("drop length: " + drops.size());
				occupiedTiles.remove(enemy.getTile());
				iterator.remove();
			}
		}
	}

	public List<Drop> getDrops() {
		return drops;
	}
	
	public void removeDrop(Drop drop) {
		if(drop == null) {
			throw new IllegalDropException();
		}
		if(!drops.contains(drop)) {
			throw new IllegalDropException();
		}
		drops.remove(drop);
	}

	public Set<Position> getAllHitBoxes(int range, Tile tile) {
		Set<Position> allHitBoxes = new LinkedHashSet<>(); // fixedHitBoxes
		for (Enemy enemy : enemies) {
			allHitBoxes.addAll(enemy.getHitBox(range));
		}

		Set<Tile> adjacentTiles = tile.getAdjacentTiles();
		for (Tile adjacentTile : adjacentTiles) {
			if (!allAvailableTiles.contains(adjacentTile)) {
				allHitBoxes.addAll(adjacentTile.getTileHitBox(SIZE));
			}
		}

//		System.out.println("hitbox size: " + allHitBoxes.size());
		return allHitBoxes;
	}

	public Enemy getActiveEnemy() {
		return activeEnemy;
	}

	public Optional<Set<Enemy>> enemiesInRange() {
		Set<Enemy> enemiesInRange = new LinkedHashSet<>();
		for (Enemy enemy : enemies) {
			if (hero.inFrontOf(hero.getWeapon().getRange(), hero.getWeapon().getRange(), enemy.getTile(), allAvailableTiles)) {
				enemiesInRange.add(enemy);
			}
		}
		return Optional.ofNullable(enemiesInRange);
	}

	private void findActiveEnemy() {
//		for (Enemy enemy : enemies) {
//			if (enemy.inFrontOf(2, 2, hero.getTile(), availableTiles)) {
//				enemy.setActive(true);
//			}
//		}
	}

	@Override
	public void tileChanged(TileEvent event) {
		occupiedTiles.add(event.newTile);
		occupiedTiles.remove(event.oldTile);
	}
}
