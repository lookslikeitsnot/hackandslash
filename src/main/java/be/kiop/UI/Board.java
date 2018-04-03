package be.kiop.UI;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.ennemies.skeletons.Skeleton;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.obstacles.Obstacle;
import be.kiop.obstacles.walls.Wall;
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
	private Set<Ennemy> ennemies = new LinkedHashSet<>();
	private Set<Wall> walls = new LinkedHashSet<>();

	private static final Size exteriorWallSize = new Size(32, 32);

	static int testWidth = 992;
	static int testHeight = 1008;
	private static Size size = new Size(2 * exteriorWallSize.getWidth() + testWidth,
			2 * exteriorWallSize.getHeight() + testHeight);

	public Board() {
		hero = generateHero();
		walls = generateAllWalls();
		ennemies = generateEnnemies(4);
		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(size, hero, walls, ennemies);
		hud = new HUD(hero, null);
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		add(hud, BorderLayout.NORTH);
		add(boardDrawing, BorderLayout.CENTER);

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
		return walls;
	}

	private static Set<Wall> generateInteriorWalls(Walls wall) {
		Set<Position> maze = generateMaze();
		return maze.stream().map(position -> new Wall(wall, position)).collect(Collectors.toSet());
	}

	private Set<Ennemy> generateEnnemies(int amount) {
		Set<Ennemy> ennemies = new LinkedHashSet<>();
		Skeletons skel = Skeletons.Skeleton_SOUTH_2;

		for (int i = 0; i < amount; i++) {
			Random random = new Random();

			int randomX = random.nextInt(size.getWidth() - 2 * exteriorWallSize.getWidth() - skel.getSize().getWidth())
					+ exteriorWallSize.getWidth();
			int randomY = random.nextInt(size.getHeight() - 2 * exteriorWallSize.getHeight() - skel.getSize().getHeight())
					+ exteriorWallSize.getHeight();

			Position ennemmyPosition = new Position(randomX, randomY);
			Ennemy ennemy = new Skeleton(skel, ennemmyPosition, "Skek", 100, new Bone(), 5, 100, Set.of(new Sword()));
			while (collision(ennemy.getHitBox(0), getAllHitBoxes())) {
				randomX = random.nextInt(size.getWidth() - 2 * exteriorWallSize.getWidth()- skel.getSize().getWidth())
						+ exteriorWallSize.getWidth();
				randomY = random.nextInt(size.getHeight() - 2 * exteriorWallSize.getHeight() - skel.getSize().getHeight())
						+ exteriorWallSize.getHeight();

				ennemmyPosition = new Position(randomX, randomY);
				ennemy = new Skeleton(skel, ennemmyPosition, "Skek", 100, new Bone(), 5, 100, Set.of(new Sword()));
			}
			ennemies.add(ennemy);
		}
		return ennemies;
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

	private static Set<Position> generateMaze() {
		int tries = 10000;
		Size corridorSize = new Size(32, 48);

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

	public boolean collision(Set<Position> hitBox1, Set<Position> hitBox2) {
		return !Collections.disjoint(hitBox1, hitBox2);
	}

	public Set<Position> getAllHitBoxes() {
		Set<Position> allHitBoxes = new LinkedHashSet<>();
		for (Ennemy ennemy : ennemies) {
			allHitBoxes.addAll(ennemy.getHitBox(0));
		}
		for (Obstacle obstacle : getAllObstacles()) {
			allHitBoxes.addAll(obstacle.getHitBox(0));
		}

		allHitBoxes.addAll(hero.getHitBox(0));

		return allHitBoxes;
	}

	public Set<Obstacle> getAllObstacles() {
		Set<Obstacle> allObstacles = new LinkedHashSet<>(walls);
		return allObstacles;
	}
}
