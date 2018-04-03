package be.kiop.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import be.kiop.characters.GameCharacter;
import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.controllers.Keyboard;
import be.kiop.decorations.Floor;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Floors;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public class BoardDrawing extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Drawable> textures;
	private List<Drawable> obstacles;
	private Set<Wall> walls;
	private Set<Enemy> ennemies;
//	private Set<Position> fixedHitBoxes;
//	private Set<Position> dynamicHitBoxes;
	private Hero hero;
	private Size size;

	public BoardDrawing(Size size, Hero hero, Set<Wall> walls, Set<Enemy> ennemies, Board board) {	//, Set<Position> fixedHitBoxes
		this.size = size;
		this.walls = walls;
		setPreferredSize(size.toDimension());
		textures = new ArrayList<>();
		obstacles = new ArrayList<>();
		this.ennemies = ennemies;
//		dynamicHitBoxes = new LinkedHashSet<>();
		this.hero = hero;
//		this.fixedHitBoxes = fixedHitBoxes;
//		placeHero();
//		placeEnnemies();
		placeWalls();
		placeFloor();
//		placeFirePits();
		new Keyboard(this, hero, board);
		
	}

//	private void placeEnnemies() {
//		Set<Position> positions = Set.of(new Position(64, 64), new Position(544, 64), new Position(64, 512),
//				new Position(544, 512));
//		positions.stream().forEach(position -> ennemies.add(new Skeleton(Skeletons.Skeleton_SOUTH_2, position, "Skek",
//				100, new Bone(), 5, 100, Set.of(new Sword()))));
//	}
//
	private void placeFloor() {
		try {
			placeFixedTexture(Floors.Floor_Parquet_HORIZONTAL, textures, Floor.class, false, false);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void placeWalls() {
		obstacles.addAll(walls);
//		try {
//			placeFixedTexture(Walls.Wall_Metallic, obstacles, Wall.class, true, true);
//		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
//				| IllegalArgumentException | InvocationTargetException e) {
//			e.printStackTrace();
//		}

	}

//	private void placeFirePits() {
//		Set<Position> positions = Set.of(new Position(32, 32), new Position(576, 32), new Position(32, 544),
//				new Position(576, 544));
//		try {
//			placeTexture(Fires.Fire_1, obstacles, Fire.class, true, positions);
//		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
//				| IllegalArgumentException | InvocationTargetException e) {
//			e.printStackTrace();
//		}
//	}

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
//
//	private void placeTexture(Texture texture, List<Drawable> list, Class<?> clazz, boolean solid,
//			Set<Position> positions) throws NoSuchMethodException, SecurityException, InstantiationException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
//		for (Position position : positions) {
//			list.add(((Drawable) ctor.newInstance(texture, position)));
//		}
//	}

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
			
			if (drawable instanceof GameCharacter) {
				if(((GameCharacter) drawable).isTakingDamage()) {
					skin = colorFilter(drawable.getTexture().getSkin(), Color.red);
				}
				else {
					skin = drawable.getTexture().getSkin();
				}
			}
			else {
				skin = drawable.getTexture().getSkin();
			}

			g.drawImage(skin, x, y, null);
//			long endTime = System.nanoTime();
//			System.out.println("duration: " + (endTime - startTime)/1000000);
		}
//		long endTime = System.nanoTime();
//		System.out.println("duration: " + (endTime - startTime)/1000000);
		hero.setTakingDamage(false);
	}

//	private void setFixedHitBoxes() {
//		fixedHitBoxes = new LinkedHashSet<>();
//		for (Drawable obstacle : obstacles) {
//			if (obstacle instanceof HitBox) {
//				fixedHitBoxes.addAll(((HitBox) obstacle).getHitBox(0));
//			}
//		}
//	}

//	public Set<Position> getHitBoxes() {
//		Set<Position> allHitBoxes = new LinkedHashSet<>(fixedHitBoxes);
//		dynamicHitBoxes.clear();
//		for (Drawable ennemy : ennemies) {
//			if (ennemy instanceof Enemy) {
//				dynamicHitBoxes.addAll(((Enemy) ennemy).getHitBox(0));
//			}
//		}
//		
//		dynamicHitBoxes.addAll(hero.getHitBox(0));
//		allHitBoxes.addAll(dynamicHitBoxes);
//		
//		return allHitBoxes;
//	}
	
	public boolean collision(Set<Position> hitBox1,  Set<Position> hitBox2) {
		return !Collections.disjoint(hitBox1, hitBox2);
	}
	
	private static BufferedImage colorFilter(BufferedImage skin, Color color)
    {
        int w = skin.getWidth();
        int h = skin.getHeight();
        BufferedImage filteredSkin = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = filteredSkin.createGraphics();
        g.drawImage(skin, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0,0,w,h);
        g.dispose();
        return filteredSkin;
    }
}
