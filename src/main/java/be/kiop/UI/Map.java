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
import be.kiop.listeners.RepaintTimer;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Fires;
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
		placeFirePits();
		new Keyboard(this, hero);

		new RepaintTimer(this);
	}

	private void placeFloor() {
		try {
			placeFixedTexture(Floors.Floor_Parquet_HORIZONTAL, textures, Floor.class, false, false);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void placeWalls() {
		try {
			placeFixedTexture(Walls.Wall_Metallic, obstacles, Wall.class, true, true);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private void placeFirePits() {
		Set<Position> positions = Set.of(new Position(32, 32), new Position(576, 32), new Position(32, 544), new Position(576, 544));
		try {
			placeTexture(Fires.Fire_1, obstacles, Fire.class, true, positions);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		obstacles.add(new Fire(Fires.Fire_1, new Position(32, 32)));
	}

	private void placeFixedTexture(Texture texture, List<Drawable> list, Class<?> clazz, boolean shouldBeDrawn,
			boolean solid) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
		for (int x = 0; x < size.getWidth(); x += texture.getSize().getWidth()) {
			for (int y = 0; y < size.getHeight(); y += texture.getSize().getHeight()) {
				if ((x == 0 || y == 0 || x == size.getWidth() - texture.getSize().getWidth()
						|| y == size.getHeight() - texture.getSize().getHeight()) == shouldBeDrawn) {
					list.add(((Drawable) ctor.newInstance(texture, new Position(x, y))));
					if (solid) {
						for (int textureX = 0; textureX < texture.getSize().getWidth(); textureX++) {
							for (int textureY = 0; textureY < texture.getSize().getHeight(); textureY++) {
								hitBoxes.add(new Position(textureX + x, textureY + y));
							}
						}
					}
				}
			}
		}
	}

	private void placeTexture(Texture texture, List<Drawable> list, Class<?> clazz, boolean solid,
			Set<Position> positions) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
		int amount = 0;
		for (Position position : positions) {
			list.add(((Drawable) ctor.newInstance(texture, position)));
			if (solid) {
				for (int textureX = 0; textureX < texture.getSize().getWidth(); textureX++) {
					for (int textureY = 0; textureY < texture.getSize().getHeight(); textureY++) {
						hitBoxes.add(new Position(textureX + position.getX(), textureY + position.getY()));
						amount++;
					}
				}
			}

		}
		System.out.println("amount: " + amount);
	}

	private List<Drawable> getAllDrawables() {
		List<Drawable> allDrawables = new ArrayList<>();
		allDrawables.addAll(textures);
		allDrawables.addAll(ennemies);
		allDrawables.addAll(obstacles);
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
			if (drawable instanceof Animated) {
				((Animated) drawable).setNextTexture();
			}
			skin = drawable.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
//			long endTime = System.nanoTime();
//			System.out.println("duration: " + (endTime - startTime)/1000000);
		}
//		long endTime = System.nanoTime();
//		System.out.println("duration: " + (endTime - startTime)/1000000);
	}

	private void placeHero() {
		Warriors HERO_SKIN = Warriors.Warrior_MALE_SOUTH_1;
		String HERO_NAME = "Warrior";
		float HERO_HEALTH = 100;
		int HERO_LEVEL = 10;
		int HERO_LIVES = 5;
		float HERO_ARMOR = 50;
		float HERO_EXPERIENCE = 200;
		float HERO_SHIELD = 10;
		Weapon weapon = new Sword();
		Position position = new Position(300, 300);
		hero = new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
				HERO_EXPERIENCE, HERO_SHIELD);

	}

	public Set<Position> getHitBoxes() {
		return hitBoxes;
	}
}
