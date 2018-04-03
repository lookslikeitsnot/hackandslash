package be.kiop.UI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;

import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.enemies.skeletons.Skeleton;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.exceptions.OutOfBoardException;
import be.kiop.listeners.RepaintTimer;
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
	private Set<Enemy> enemies = new LinkedHashSet<>();
	private Set<Wall> walls = new LinkedHashSet<>();
	private final Set<Position> fixedHitBoxes;

	private static final Size exteriorWallSize = new Size(32, 32);

	static int testWidth = 992;
	static int testHeight = 1008;
	private static Size size = new Size(2 * exteriorWallSize.getWidth() + testWidth,
			2 * exteriorWallSize.getHeight() + testHeight);

	public Board() {
		hero = generateHero();
		
		walls = generateAllWalls();
		fixedHitBoxes = getFixedHitBoxes();
		
		enemies = generateEnemies(32);
		
		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(size, hero, walls, enemies, this);
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
		walls.add(new Wall(wall, new Position(size.getWidth() - wall.getSize().getWidth(), size.getHeight() - wall.getSize().getHeight())));
		return walls;
	}

	private static Set<Wall> generateInteriorWalls(Walls wall) {
		Set<Position> maze = generateMaze();
		return maze.stream().map(position -> new Wall(wall, position)).collect(Collectors.toSet());
	}

	private Set<Enemy> generateEnemies(int amount) {
		Set<Enemy> enemies = new LinkedHashSet<>();
		Skeletons skel = Skeletons.Skeleton_SOUTH_2;

		for (int i = 0; i < amount; i++) {
			Random random = new Random();

			int randomX = random.nextInt(size.getWidth() - 2 * exteriorWallSize.getWidth() - skel.getSize().getWidth())
					+ exteriorWallSize.getWidth();
			int randomY = random.nextInt(size.getHeight() - 2 * exteriorWallSize.getHeight() - skel.getSize().getHeight())
					+ exteriorWallSize.getHeight();
			
			int diffX = (skel.getSkin().getWidth()-skel.getHitBoxSize().getWidth())/2;
			int diffY = (skel.getSkin().getHeight()-skel.getHitBoxSize().getHeight())/2;

			Position enemmyPosition = new Position(randomX, randomY);
			Position enemmyTopLeftPosition = new Position(randomX+diffX, randomY+diffY);
			Position enemmyTopRightPosition = new Position(randomX+diffX+skel.getHitBoxSize().getWidth(), randomY+diffY);
			Position enemmyBottomLeftPosition = new Position(randomX+diffX, randomY+diffY+skel.getHitBoxSize().getHeight());
			Position enemmyBottomRightPosition = new Position(randomX+diffX+skel.getHitBoxSize().getWidth(), randomY+diffY+skel.getHitBoxSize().getHeight());
//			Enemy enemy = new Skeleton(skel, enemmyPosition, "Skek", 100, new Bone(), 5, 100, Set.of(new Sword()));
//			while (collision(enemy.getHitBox(2), getAllHitBoxes(-4))) {
//				randomX = random.nextInt(size.getWidth() - 2 * exteriorWallSize.getWidth()- skel.getSize().getWidth())
//						+ exteriorWallSize.getWidth();
//				randomY = random.nextInt(size.getHeight() - 2 * exteriorWallSize.getHeight() - skel.getSize().getHeight())
//						+ exteriorWallSize.getHeight();
//
//				enemmyPosition = new Position(randomX, randomY);
//				enemy = new Skeleton(skel, enemmyPosition, "Skek", 100, new Bone(), 5, 100, Set.of(new Sword()));
//			}
			while(!isInMap(enemmyTopLeftPosition) || !isInMap(enemmyTopRightPosition) || !isInMap(enemmyBottomRightPosition) || !isInMap(enemmyBottomLeftPosition)) {
				randomX = random.nextInt(size.getWidth() - 2 * exteriorWallSize.getWidth() - skel.getSize().getWidth())
						+ exteriorWallSize.getWidth();
				randomY = random.nextInt(size.getHeight() - 2 * exteriorWallSize.getHeight() - skel.getSize().getHeight())
						+ exteriorWallSize.getHeight();

				enemmyPosition = new Position(randomX, randomY);
				enemmyTopLeftPosition = new Position(randomX+diffX, randomY+diffY);
				enemmyTopRightPosition = new Position(randomX+diffX+skel.getHitBoxSize().getWidth(), randomY+diffY);
				enemmyBottomLeftPosition = new Position(randomX+diffX, randomY+diffY+skel.getHitBoxSize().getHeight());
				enemmyBottomRightPosition = new Position(randomX+diffX+skel.getHitBoxSize().getWidth(), randomY+diffY+skel.getHitBoxSize().getHeight());
			}

			enemies.add(new Skeleton(skel, enemmyPosition, "Skek", 100, new Bone(), 5, 100, Set.of(new Sword())));
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

		Set<Position> allHitBoxes = getAllHitBoxes(0);
		for(Enemy enemy: enemies) {
			enemy.move(allHitBoxes);
			if(collision(enemy.getHitBox(2), hero.getHitBox(2))) {
				hero.takeDamage(enemy.getWeapon().getDamage()*10);
				hero.setTakingDamage(true);
			} 
		}
		boardDrawing.repaint();
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

	public Set<Position> getAllHitBoxes(int range) {
		Set<Position> allHitBoxes = new LinkedHashSet<>(fixedHitBoxes);
		for (Enemy enemy : enemies) {
			allHitBoxes.addAll(enemy.getHitBox(range));
		}
		allHitBoxes.addAll(hero.getHitBox(range));

//		System.out.println("hitbox size: " + allHitBoxes.size());
		return allHitBoxes;
	}
	
	public Set<Position> getFixedHitBoxes() {
		Set<Position> fixedHitBoxes = new LinkedHashSet<>();
		
		for (Obstacle obstacle : getAllObstacles()) {
			for(Position pos: obstacle.getHitBox(0)) {
				if(pos.getX() >= exteriorWallSize.getWidth() && pos.getX() <= size.getWidth() - exteriorWallSize.getWidth()) {
					if(pos.getY() >= exteriorWallSize.getHeight() && pos.getY() <= size.getHeight() - exteriorWallSize.getHeight()) {
						if(fixedHitBoxes.contains(pos)) {
							fixedHitBoxes.remove(pos);
						} else {
							fixedHitBoxes.add(pos);
						}
						
					}
				}
			}
		}
		return fixedHitBoxes;
	}
	
	

	public Set<Obstacle> getAllObstacles() {
		Set<Obstacle> allObstacles = new LinkedHashSet<>(walls);
		return allObstacles;
	}
	
	private boolean isInMap(Position position) {
//		Enemy enemy = new Skeleton(Skeletons.Skeleton_Dog_EAST_2, position, "SSkek", 10, new Bone(), 1, 0, Set.of(new Bone()));
//		try {
//			IntStream.range(0, 10).forEach(time -> enemy.mazeMove(fixedHitBoxes));
//			
//		} catch (OutOfBoardException e) {
//			return false;
//		}
//		return true;
		Set<Position> lineBetween = new LinkedHashSet<>();
		for(int x = exteriorWallSize.getWidth()+1; x < position.getX();x++) {
			lineBetween.add(new Position(x, exteriorWallSize.getHeight()+1));
		}
		for(int y = exteriorWallSize.getHeight()+1; y < position.getY();y++) {
			lineBetween.add(new Position(position.getX(),y));
		}
//		System.out.println("line length: " + lineBetween.size());
		lineBetween.retainAll(fixedHitBoxes);
//		System.out.println("corresponding: " + lineBetween.size());
		return lineBetween.size()%2==0;
	}
	
	public boolean pathExistsBetween(Position pos1, Position pos2) {
		return true;
	}
}
