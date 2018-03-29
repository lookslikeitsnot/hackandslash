package be.kiop.UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import be.kiop.characters.ennemies.Ennemy;
import be.kiop.characters.ennemies.skeletons.Skeleton;
import be.kiop.characters.heroes.Hero;
import be.kiop.controllers.Keyboard;
import be.kiop.decorations.Floor;
import be.kiop.listeners.RepaintTimer;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Fires;
import be.kiop.textures.Floors;
import be.kiop.textures.Skeletons;
import be.kiop.textures.Texture;
import be.kiop.textures.Walls;
import be.kiop.valueobjects.HitBox;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;
import be.kiop.weapons.Bone;
import be.kiop.weapons.Sword;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Drawable> textures;
	private List<Drawable> obstacles;
	private List<Drawable> ennemies;
	private Set<Position> fixedHitBoxes;
	private Set<Position> dynamicHitBoxes;
	private Hero hero;
	private Size size;

	public Map(Size size, Hero hero) {
		this.size = size;
		setPreferredSize(size.toDimension());
		textures = new ArrayList<>();
		obstacles = new ArrayList<>();
		ennemies = new ArrayList<>();
		dynamicHitBoxes = new LinkedHashSet<>();
		this.hero = hero;
//		placeHero();
		placeEnnemies();
		placeWalls();
		placeFloor();
		placeFirePits();
		setFixedHitBoxes();
		new Keyboard(this, hero);
		new RepaintTimer(this);
	}

	private void placeEnnemies() {
		Set<Position> positions = Set.of(new Position(64, 64), new Position(544, 64), new Position(64, 512),
				new Position(544, 512));
		positions.stream().forEach(position -> ennemies.add(new Skeleton(Skeletons.Skeleton_SOUTH_2, position, "Skek",
				100, new Bone(), 5, 100, Set.of(new Sword()))));
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
		Set<Position> positions = Set.of(new Position(32, 32), new Position(576, 32), new Position(32, 544),
				new Position(576, 544));
		try {
			placeTexture(Fires.Fire_1, obstacles, Fire.class, true, positions);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
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
				}
			}
		}
	}

	private void placeTexture(Texture texture, List<Drawable> list, Class<?> clazz, boolean solid,
			Set<Position> positions) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
		for (Position position : positions) {
			list.add(((Drawable) ctor.newInstance(texture, position)));
		}
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
			if (drawable instanceof Animation) {
				((Animation) drawable).setNextTexture();
			}
			if(drawable instanceof Ennemy) {
				((Ennemy) drawable).move(getHitBoxes());
			}
			skin = drawable.getTexture().getSkin();
			if(collision()) {
//				System.out.println("aie");
				hero.takeDamage(0.01F);
			}

			g.drawImage(skin, x, y, null);
//			long endTime = System.nanoTime();
//			System.out.println("duration: " + (endTime - startTime)/1000000);
		}
//		long endTime = System.nanoTime();
//		System.out.println("duration: " + (endTime - startTime)/1000000);
	}

//	private void placeHero() {
//		Warriors HERO_SKIN = Warriors.Warrior_MALE_SOUTH_1;
//		String HERO_NAME = "Warrior";
//		float HERO_HEALTH = 100;
//		int HERO_LEVEL = 10;
//		int HERO_LIVES = 5;
//		float HERO_ARMOR = 50;
//		float HERO_EXPERIENCE = 200;
//		float HERO_SHIELD = 10;
//		Weapon weapon = new Sword();
//		Position position = new Position(300, 300);
//		hero = new Warrior(HERO_SKIN, position, HERO_NAME, HERO_HEALTH, weapon, HERO_LEVEL, HERO_ARMOR, HERO_LIVES,
//				HERO_EXPERIENCE, HERO_SHIELD);
//
//	}

	private void setFixedHitBoxes() {
		fixedHitBoxes = new LinkedHashSet<>();
		for (Drawable obstacle : obstacles) {
			if (obstacle instanceof HitBox) {
				fixedHitBoxes.addAll(((HitBox) obstacle).getHitBox(0));
			}
		}
	}

	public Set<Position> getHitBoxes() {
		Set<Position> allHitBoxes = new LinkedHashSet<>(fixedHitBoxes);
		dynamicHitBoxes.clear();
		for (Drawable ennemy : ennemies) {
			if (ennemy instanceof Ennemy) {
				dynamicHitBoxes.addAll(((Ennemy) ennemy).getHitBox(0));
			}
		}
		
		dynamicHitBoxes.addAll(hero.getHitBox(0));
		allHitBoxes.addAll(dynamicHitBoxes);
		
//		System.out.println(allHitBoxes.size());
		return allHitBoxes;
	}
	
	public Set<Position> getDamageDealingHitBoxes(){
		Set<Position> damageDealingHitBoxes = new LinkedHashSet<>();
		for (Drawable ennemy : ennemies) {
			if (ennemy instanceof Ennemy) {
				damageDealingHitBoxes.addAll(((Ennemy) ennemy).getHitBox(2));
			}
		}
		return damageDealingHitBoxes;
	}
	
	public boolean collision() {
		return !Collections.disjoint(getDamageDealingHitBoxes(), hero.getHitBox(2));
	}
}
