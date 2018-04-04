package be.kiop.UI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.enemies.skeletons.Skeleton;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.listeners.RepaintTimer;
import be.kiop.obstacles.Obstacle;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Fires;
import be.kiop.textures.Skeletons;
import be.kiop.textures.Walls;
import be.kiop.textures.Warriors;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Board extends JFrame {
	private static final long serialVersionUID = 1L;

	private BoardDrawing boardDrawing;
	private HUD hud;

	private Hero hero;
	private Enemy activeEnemy;
	private Set<Enemy> enemies = new LinkedHashSet<>();
	private Set<Wall> walls = new LinkedHashSet<>();
	private Set<Fire> fires = new LinkedHashSet<>();
	private final Set<Position> fixedHitBoxes;

	private static final Size exteriorWallSize = new Size(32, 32);

	static int testWidth = 992;
	static int testHeight = 1008;
	private static Size size = new Size(2 * exteriorWallSize.getWidth() + testWidth,
			2 * exteriorWallSize.getHeight() + testHeight);

	private final static Size corridorSize = new Size(32, 48);

	public Board() {
		hero = generateHero();

		walls = generateAllWalls();
		fixedHitBoxes = calculateFixedHitBoxes();
		fires = generateFirePits();

		enemies = generateEnemies(16);

		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(size, hero, walls, fires, enemies, this);
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

	private static Hero generateHero() {
		Warriors HERO_SKIN = Warriors.Warrior_MALE_SOUTH_1;
		String HERO_NAME = "Warrior";
		float HERO_HEALTH = 1000;
		int HERO_LEVEL = 10;
		int HERO_LIVES = 5;
		float HERO_ARMOR = 50;
		float HERO_EXPERIENCE = 200;
		float HERO_SHIELD = 10;
		Weapon weapon = new Sword();
		Position position = new Position(32, 32);
		return new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
				HERO_EXPERIENCE, HERO_SHIELD);
	}

	private static Set<Wall> generateAllWalls() {
		Set<Wall> allWalls = new LinkedHashSet<>();
		allWalls.addAll(generateInteriorWalls(Walls.Wall_Stone));
		allWalls.addAll(generateExteriorWalls(Walls.Wall_Mettalic_Dark));
		return allWalls;
	}

	private static Set<Wall> generateExteriorWalls(Walls wall) {
		Set<Wall> walls = new LinkedHashSet<>();
		for (int x = 0; x <= size.getWidth(); x += wall.getSize().getWidth()) {
			for (int y = 0; y <= size.getHeight(); y += wall.getSize().getHeight()) {
				if (x >= size.getWidth() - wall.getSize().getWidth()
						&& y < size.getHeight() - wall.getSize().getHeight()) {
					walls.add(new Wall(wall, new Position(size.getWidth() - wall.getSize().getWidth(), y)));
				}

				else if (y >= size.getHeight() - wall.getSize().getHeight()
						&& x < size.getWidth() - wall.getSize().getWidth()) {
					walls.add(new Wall(wall, new Position(x, size.getHeight() - wall.getSize().getHeight())));
				} else if (y >= size.getHeight() - wall.getSize().getHeight()
						&& x >= size.getWidth() - wall.getSize().getWidth()) {

				} else if (x == 0 || y == 0) {
					walls.add(new Wall(wall, new Position(x, y)));
				}
			}
		}
		walls.add(new Wall(wall, new Position(size.getWidth() - wall.getSize().getWidth(),
				size.getHeight() - wall.getSize().getHeight())));
		return walls;
	}

	private static Set<Wall> generateInteriorWalls(Walls wall) {
		Set<Position> maze = generateMaze();
		return maze.stream().map(position -> new Wall(wall, position)).collect(Collectors.toSet());
	}
	
	private static Set<Fire> generateFirePits() {
		Fires fire = Fires.Fire_2;
		Set<Position> positions = Set.of(
				new Position(0,0), 
				new Position(0, size.getHeight() - fire.getSize().getHeight()), 
				new Position(size.getWidth() - fire.getSize().getWidth(), 0),
				new Position(size.getWidth() - fire.getSize().getWidth(), size.getHeight() - fire.getSize().getHeight()));
		return positions.stream().map(pos-> new Fire(fire, pos)).collect(Collectors.toSet());
	}

	private Set<Enemy> generateEnemies(int amount) {
		Set<Enemy> enemies = new LinkedHashSet<>();
		Skeletons skel = Skeletons.Skeleton_SOUTH_2;

		for (int i = 0; i < amount; i++) {
			Random random = new Random();
			int offsetX = (skel.getSkin().getWidth() - corridorSize.getWidth()) / 2;
			int offsetY = (skel.getSkin().getHeight() - corridorSize.getHeight()) / 2;

			int randX = random.nextInt((size.getWidth() - 2 * exteriorWallSize.getWidth()) / corridorSize.getWidth());
			int randY = random
					.nextInt((size.getHeight() - 2 * exteriorWallSize.getHeight()) / corridorSize.getHeight());

			int posX = randX * corridorSize.getWidth() + exteriorWallSize.getWidth();
			int posY = randY * corridorSize.getHeight() + exteriorWallSize.getHeight();

			while (fixedHitBoxes.contains(new Position(posX, posY))) {
				randX = random.nextInt((size.getWidth() - 2 * exteriorWallSize.getWidth()) / corridorSize.getWidth());
				randY = random
						.nextInt((size.getHeight() - 2 * exteriorWallSize.getHeight()) / corridorSize.getHeight());

				posX = randX * corridorSize.getWidth() + exteriorWallSize.getWidth();
				posY = randY * corridorSize.getHeight() + exteriorWallSize.getHeight();
			}

			enemies.add(new Skeleton(skel, new Position(posX - offsetX, posY - offsetY), "Skek", 100, new Bone(), 5,
					100, Set.of(new Sword())));
		}
		return enemies;
	}

	public static Size getSize(boolean i) {
		return Board.size;
	}

	public static void setSize(Size size) {
		if (size == null) {
			throw new IllegalArgumentException();
		}
		Board.size = size;
	}

	@Override
	public void paint(Graphics g) {
		hud.repaint();

		findActiveEnemy();
		moveEnemies();

		boardDrawing.repaint();
	}

	private void moveEnemies() {
		Set<Position> allHitBoxes = getAllHitBoxes(0);
		for (Enemy enemy : enemies) {
			enemy.move(allHitBoxes);
			if (enemy.isActive()) {
//				System.out.println("I can see you");
				if (collision(enemy.getHitBox(2), hero.getHitBox(2))) {
					hero.takeDamage(enemy.getWeapon().getDamage() * 10);
				}
				enemy.setActive(false);
			}

		}
	}

	private static Set<Position> generateMaze() {
		int tries = 4000;

		Map<Position, Boolean> tested = new LinkedHashMap<>();

		final int exteriorWidth = exteriorWallSize.getWidth();
		final int exteriorHeight = exteriorWallSize.getHeight();

		for (int x = exteriorWidth; x < size.getWidth() - exteriorWidth; x += corridorSize.getWidth()) {
			for (int y = exteriorHeight; y < size.getHeight() - exteriorWidth; y += corridorSize.getHeight()) {
				tested.put(new Position(x, y), false);
			}
		}

		int x = exteriorWidth;
		int y = exteriorHeight;

		Random random = new Random();

		for (int i = 0; i < tries; i++) {
			tested.put(new Position(x, y), true);
			int randInt = random.nextInt(4);
			switch (randInt) {
			case 0:
				if (x + 2 * corridorSize.getWidth() >= size.getWidth() - exteriorWidth) {
					break;
				} else if (tested.get(new Position(x + 2 * corridorSize.getWidth(), y)) == true) {
					x += 2 * corridorSize.getWidth();
					break;
				} else {
					tested.put(new Position(x + corridorSize.getWidth(), y), true);
					x += 2 * corridorSize.getWidth();
					break;
				}

			case 1:
				if (y + 2 * corridorSize.getHeight() >= size.getHeight() - exteriorHeight) {
					break;
				} else if (tested.get(new Position(x, y + 2 * corridorSize.getHeight())) == true) {
					y += 2 * corridorSize.getHeight();
					break;
				} else {
					tested.put(new Position(x, y + corridorSize.getHeight()), true);
					y += 2 * corridorSize.getHeight();
					break;
				}
			case 2:
				if (x - 2 * corridorSize.getWidth() < exteriorWidth) {
					break;
				} else if (tested.get(new Position(x - 2 * corridorSize.getWidth(), y)) == true) {
					x -= 2 * corridorSize.getWidth();
					break;
				} else {
					tested.put(new Position(x - corridorSize.getWidth(), y), true);
					x -= 2 * corridorSize.getWidth();
					break;
				}

			case 3:
				if (y - 2 * corridorSize.getHeight() < exteriorHeight) {
					break;
				} else if (tested.get(new Position(x, y - 2 * corridorSize.getHeight())) == true) {
					y -= 2 * corridorSize.getHeight();
					break;
				} else {
					tested.put(new Position(x, y - corridorSize.getHeight()), true);
					y -= 2 * corridorSize.getHeight();
					break;
				}
			}
		}

		return tested.entrySet().stream().filter(a -> a.getValue() == false).map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	private boolean collision(Set<Position> hitBox1, Set<Position> hitBox2) {
		return !Collections.disjoint(hitBox1, hitBox2);
	}

	public Set<Position> getAllHitBoxes(int range) {
		Set<Position> allHitBoxes = new LinkedHashSet<>(fixedHitBoxes);
		for (Enemy enemy : enemies) {
			allHitBoxes.addAll(enemy.getHitBox(range));
		}
		allHitBoxes.addAll(hero.getHitBox(range));

//		System.out.println("hitbox size: " + allHitBoxes.size());
		return allHitBoxes;
	}

	private Set<Position> calculateFixedHitBoxes() {
		Set<Position> fixedHitBoxes = new LinkedHashSet<>();

		for (Obstacle obstacle : getAllObstacles()) {
			for (Position pos : obstacle.getHitBox(0)) {
				if (pos.getX() >= exteriorWallSize.getWidth() - 1
						&& pos.getX() <= size.getWidth() - exteriorWallSize.getWidth()) {
					if (pos.getY() >= exteriorWallSize.getHeight() - 1
							&& pos.getY() <= size.getHeight() - exteriorWallSize.getHeight()) {

						if (!(fixedHitBoxes.contains(pos.right()) || fixedHitBoxes.contains(pos.down())
								|| fixedHitBoxes.contains(pos.left()) || fixedHitBoxes.contains(pos.up()))) {
							fixedHitBoxes.add(pos);
						}
					}
				}
			}
		}
//		System.out.println("hitbox size= " + fixedHitBoxes.size());
		return fixedHitBoxes;
	}

	public Set<Position> getFixedHitBoxes() {
		return fixedHitBoxes;
	}

	public Set<Obstacle> getAllObstacles() {
		Set<Obstacle> allObstacles = new LinkedHashSet<>(walls);
		return allObstacles;
	}

	public Enemy getActiveEnemy() {
		return activeEnemy;
	}

	public Optional<Enemy> enemyInRange() {
//		System.out.println("range: " + hero.getWeapon().getRange());
		for (Enemy enemy : enemies) {
			if (hero.inFrontOf(hero.getWeapon().getRange(), hero.getWeapon().getRange(), enemy.getCenter(),
					fixedHitBoxes)) {
//				System.out.println("ennemy in range");
				return Optional.ofNullable(enemy);
			}
		}
		return Optional.empty();
	}

	private void findActiveEnemy() {
		for (Enemy enemy : enemies) {
			if (enemy.inFrontOf(64, 96, hero.getCenter(), fixedHitBoxes)) {
				enemy.setActive(true);
			}
		}
	}
}
