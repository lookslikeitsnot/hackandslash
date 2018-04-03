package be.kiop.UI;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Walls;
import be.kiop.textures.Warriors;
import be.kiop.valueobjects.HitBox;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Board extends JFrame {
	private static final long serialVersionUID = 1L;

	private static Size size = new Size(672, 672);

	private BoardDrawing boardDrawing;
	private HUD hud;

	private Hero hero;
	private Set<Ennemy> ennemies;
	private Set<Wall> walls;

	public Board() {
		hero = generateHero();
		walls = generateWalls(Walls.Wall_Mettalic_Dark);
		setLayout(new BorderLayout());
		boardDrawing = new BoardDrawing(size, hero, walls);
		hud = new HUD(hero, null);
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		add(hud, BorderLayout.NORTH);
		add(boardDrawing, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

	}

	private Hero generateHero() {
		Warriors HERO_SKIN = Warriors.Warrior_MALE_SOUTH_1;
		String HERO_NAME = "Warrior";
		float HERO_HEALTH = 1000;
		int HERO_LEVEL = 10;
		int HERO_LIVES = 5;
		float HERO_ARMOR = 50;
		float HERO_EXPERIENCE = 200;
		float HERO_SHIELD = 10;
		Weapon weapon = new Sword();
		Position position = new Position(300, 300);
		return new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
				HERO_EXPERIENCE, HERO_SHIELD);
	}
	
	private Set<Position> getHitBox(List<HitBox> objects, int range){
		return objects.stream().map(object -> object.getHitBox(range)).collect(HashSet::new, Set::addAll, Set::addAll);
	}

	private Set<Wall> generateWalls(Walls wall) {
		Set<Wall> walls = new LinkedHashSet<>();
		for (int x = 0; x < size.getWidth(); x += wall.getSize().getWidth()) {
			for (int y = 0; y < size.getHeight(); y += wall.getSize().getHeight()) {
				if (x == 0 || y == 0 || x == size.getWidth() - wall.getSize().getWidth()
						|| y == size.getHeight() - wall.getSize().getHeight()) {
					walls.add(new Wall(wall, new Position(x, y)));
				}
			}
		}
		walls.addAll(generateMaze(wall));
		return walls;
	}
	
	private Set<Wall> generateMaze(Walls wall){	
		return maze().stream().map(position->new Wall(wall, position)).collect(Collectors.toSet());
	}

	private List<Ennemy> generateEnnemies(int amount) {
		return new ArrayList<>();
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

	
	private Set<Position> maze() {
		Map<Position, Boolean> tested = new LinkedHashMap<>();
		for (int x = 0; x<12; x++) {
			for(int y = 0; y<12;y++) {
				tested.put(new Position(x*48+32, y*48+32), false);
			}
		}
		int x = 32;
		int y = 32;
		
		Random random = new Random();
		
		for( int i = 0; i < 1000; i++) {
//			System.out.println("now testing " + new Position(x, y));
			tested.put(new Position(x, y), true);
			int randInt = random.nextInt(4);
//			System.out.println(randInt);
			switch(randInt) {
			case 0:
				if(x + 2*48 >= 576+32) {
					break;
				} else if (tested.get(new Position(x+96, y)) == true){
					x+=96;
					break;
				} else {
					tested.put(new Position(x+48, y), true);
					x+=96;
					break;
				}
				
			case 1:
				if(y + 2*48 >= 576+32) {
					break;
				} else if (tested.get(new Position(x, y+96)) == true){
					y+=96;
					break;
				} else {
					tested.put(new Position(x, y+48), true);
					y+=96;
					break;
				}
			case 2:
				if(x - 2*48 <32) {
					break;
				} else if (tested.get(new Position(x-96, y)) == true){
					x-=96;
					break;
				} else {
					tested.put(new Position(x-48, y), true);
					x-=96;
					break;
				}
				
			case 3:
				if(y - 2*48 <32) {
					break;
				} else if (tested.get(new Position(x, y-96)) == true){
					y-=96;
					break;
				} else {
					tested.put(new Position(x, y-48), true);
					y-=96;
					break;
				}
				
			}
		}
		
		return tested.entrySet().stream().filter(a -> a.getValue()==false).map(Map.Entry::getKey).collect(Collectors.toSet());
	}
}
