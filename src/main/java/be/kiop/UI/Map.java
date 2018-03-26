package be.kiop.UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import be.kiop.characters.heroes.Hero;
import be.kiop.characters.heroes.warriors.Warrior;
import be.kiop.controllers.Keyboard;
import be.kiop.decorations.Floor;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Floors;
import be.kiop.textures.Texture;
import be.kiop.textures.Walls;
import be.kiop.textures.Warriors;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Sword;
import be.kiop.weapons.Weapon;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Drawable> textures;
	private List<Drawable> obstacles;
	private List<Drawable> ennemies;
	private Set<Position> hitBoxes;
	private Hero hero;
	private Size size;
	// public static final Dimension SKIN_DIMENSION = new Dimension(32, 32);

	public Map(Size size) {
		this.size = size;
		setPreferredSize(size.toDimension());
		textures = new ArrayList<>();
		obstacles = new ArrayList<>();
		ennemies = new ArrayList<>();
		hitBoxes = new LinkedHashSet<>();
		placeHero();
		placeWalls();
		placeFloor();
		new Keyboard(this, hero);
	}

	private void placeFloor() {
		try {
			placeTexture(Floors.Floor_Parquet_Hor, textures, Floor.class, false, false);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void placeWalls() {
		try {
			placeTexture(Walls.Wall_Small, obstacles, Wall.class, true, true);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}
	
	private void placeTexture (Texture texture, List<Drawable> list, Class<?> classe, boolean shouldBeDrawn, boolean solid) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = classe;
		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
		for (int x = 0; x < size.getWidth();  x += texture.getSize().getWidth()) {
			for (int y = 0; y < size.getHeight(); y += texture.getSize().getHeight()) {
				if ((x == 0 || y == 0 || x == size.getWidth() - texture.getSize().getWidth() || y == size.getHeight() - texture.getSize().getHeight()) == shouldBeDrawn) {
					list.add(((Drawable) ctor.newInstance(texture, new Position(x, y))));
					if(solid) {
						for(int textureX = 0; textureX < texture.getSize().getWidth(); textureX++) {
							for(int textureY = 0; textureY < texture.getSize().getHeight(); textureY++) {
								hitBoxes.add(new Position(textureX+x, textureY+y));
							}
						}
					}
				}
			}
		}
	}

	private List<Drawable> getAllDrawables() {
		List<Drawable> allDrawables = new ArrayList<>(obstacles);
		allDrawables.addAll(textures);
		allDrawables.addAll(ennemies);
		allDrawables.add(hero);
		return allDrawables;
	}

	@Override
	public void paintComponent(Graphics g) {
//		long startTime = System.nanoTime();
		int x;
		int y;
		BufferedImage skin;
		for (Drawable drawable : getAllDrawables()) {
			x = drawable.getPosition().getX();
			y = drawable.getPosition().getY();
//			long startTime = System.nanoTime();
			skin = drawable.getTexture().getSkin();
			
			g.drawImage(skin, x, y, null);
//			long endTime = System.nanoTime();
//			System.out.println("duration: " + (endTime - startTime)/1000000);
		}
//		long endTime = System.nanoTime();
//		System.out.println("duration: " + (endTime - startTime)/1000000);
	}

	private void placeHero() {
		Warriors HERO_SKIN = Warriors.Warrior_Young_2_1;
		String HERO_NAME = "Warrior";
		float HERO_HEALTH = 100;
		int HERO_LEVEL = 10;
		int HERO_LIVES = 5;
		float HERO_ARMOR = 50;
		float HERO_EXPERIENCE = 200;
		float HERO_SHIELD = 10;
		Weapon weapon = new Sword();
		Position position = new Position(32, 32);
		hero = new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
				HERO_EXPERIENCE, HERO_SHIELD);
		
	}
	
	public Set<Position> getHitBoxes(){
		return hitBoxes;
	}
	
//	public void moveCharacter(Directions direction, GameCharacter gc) {
//		switch (direction) {
//		case LEFT:
//			gc.moveLeft();
//			break;
//		case DOWN:
//			gc.moveDown();
//		case RIGHT:
//			gc.moveRight();
//			break;
//		case UP:
//			gc.moveUp();
//			break;
//		default:
//			break;
//		}
//	}
}
