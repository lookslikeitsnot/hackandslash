package be.kiop.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.textures.Warriors;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Board extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static Size size = new Size(640,640);
	
	private Map map;
	private HUD hud;
	
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
	Hero hero = new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
			HERO_EXPERIENCE, HERO_SHIELD);
	
	public Board() {
		setLayout(new BorderLayout());
		map = new Map(size, hero);
		hud = new HUD(hero, null);
//		map.setBorder(BorderFactory.createLineBorder(Color.red));
		add(hud, BorderLayout.NORTH);
		add(map, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

	}

	public static Size getSize(boolean i) {
		return Board.size;
	}

	public static void setSize(Size size) {
		if(size == null) {
			throw new IllegalArgumentException();
		}
		Board.size = size;
	}
	
	

}
