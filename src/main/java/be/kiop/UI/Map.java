package be.kiop.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.decorations.Floor;
import be.kiop.obstacles.Obstacle;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Floors;
import be.kiop.textures.Walls;
import be.kiop.textures.Warriors;
import be.kiop.valueobjects.Position;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Map extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private List<Drawable> textures;
	private List<Obstacle> obstacles;
	private List<Ennemy> ennemies;
	private Hero hero;
	public static final Dimension SKIN_DIMENSION = new Dimension(32, 32);
	
	public Map(int horizontalTiles, int verticalTiles) {
		setPreferredSize(new Dimension(640,640));
		textures = new ArrayList<>();
		obstacles = new ArrayList<>();
		ennemies = new ArrayList<>();
		placeHero();
		placeWalls(horizontalTiles, verticalTiles);
		placeFloor(horizontalTiles, verticalTiles);
	}
	
	private void placeFloor(int horizontalTiles, int verticalTiles) {
		for(int x = 0; x < horizontalTiles; x++) {
			for(int y = 0; y < verticalTiles; y++) {
				if(x != 0 && y != 0 && x != horizontalTiles-1 && y != verticalTiles-1) {
					textures.add(new Floor(Floors.Floor_Metallic_Small, new Position(x*(int)SKIN_DIMENSION.getWidth(), y*(int)SKIN_DIMENSION.getHeight())));
				}
			}
		}
	}
	
	private void placeWalls(int horizontalTiles, int verticalTiles) {
		for(int x = 0; x < horizontalTiles; x++) {
			for(int y = 0; y < verticalTiles; y++) {
				if(x == 0 || y == 0 || x == horizontalTiles-1 || y == verticalTiles-1) {
					obstacles.add(new Wall(Walls.Wall_Large, new Position(x*(int)SKIN_DIMENSION.getWidth(), y*(int)SKIN_DIMENSION.getHeight())));
				}
			}
		}
		
	}
	
	private List<Drawable> getAllDrawables(){
		List<Drawable> allDrawables = new ArrayList<>(obstacles);
		allDrawables.addAll(textures);
		allDrawables.addAll(ennemies);
		allDrawables.add(hero);
		return allDrawables;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int x;
		int y;
		BufferedImage skin;
		for(Drawable drawable: getAllDrawables()) {
			x = drawable.getPosition().getX();
			y = drawable.getPosition().getY();
			skin = drawable.getTexture().getSkin();
			g.drawImage(skin, x, y, null);
		}
	}
	
	private void placeHero() {
		Warriors HERO_SKIN = Warriors.Warrior_Large;
		String HERO_NAME = "Warrior";
		float HERO_HEALTH = 100;
		int HERO_LEVEL = 10;
		int HERO_LIVES = 5;
		float HERO_ARMOR = 50;
		float HERO_EXPERIENCE = 200;
		float HERO_SHIELD = 10;
		Weapon weapon = new Sword();
		Position position = new Position(32, 32);
		hero = new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES, HERO_EXPERIENCE,
				HERO_SHIELD);
	}
}
