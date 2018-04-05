package be.kiop.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import be.kiop.characters.GameCharacter;
import be.kiop.characters.enemies.Enemy;
import be.kiop.characters.heroes.Hero;
import be.kiop.controllers.Keyboard;
import be.kiop.decorations.Floor;
import be.kiop.items.Drop;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.Floors;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public class BoardDrawing extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Set<Floor> floors;

	private Hero hero;
	private Set<Enemy> enemies;
	private List<Drop> drops;
	private Set<Wall> walls;
	private Set<Fire> fires;

	private Size size;

	public BoardDrawing(Size size, Floors floor, Hero hero, Set<Wall> walls, Set<Fire> fires, Set<Enemy> enemies, List<Drop> drops,
			Board board) {
		this.size = size;
		this.enemies = enemies;
		this.hero = hero;
		this.drops = drops;
		this.walls = walls;
		this.fires = fires;

		floors = generateFloor(floor);
		
		new Keyboard(this, hero, board);

		setPreferredSize(size.toDimension());
	}

	private Set<Floor> generateFloor(Floors floor) {
		Set<Floor> floors = new LinkedHashSet<>();
		for (int x = 0; x < size.getWidth(); x += floor.getSize().getWidth()) {
			for (int y = 0; y < size.getHeight(); y += floor.getSize().getHeight()) {
				if ((x == 0 || y == 0 || x == size.getWidth() - floor.getSize().getWidth()
						|| y == size.getHeight() - floor.getSize().getHeight()) == false) {
					floors.add(new Floor(floor, new Position(x, y)));
				}
			}
		}
		return floors;
	}

//	private void placeFixedTexture(Texture texture, Set<Drawable> list, Class<?> clazz, boolean shouldBeDrawn,
//			boolean solid) throws NoSuchMethodException, SecurityException, InstantiationException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
//		for (int x = 0; x < size.getWidth(); x += texture.getSize().getWidth()) {
//			for (int y = 0; y < size.getHeight(); y += texture.getSize().getHeight()) {
//				if ((x == 0 || y == 0 || x == size.getWidth() - texture.getSize().getWidth()
//						|| y == size.getHeight() - texture.getSize().getHeight()) == shouldBeDrawn) {
//					list.add(((Drawable) ctor.newInstance(texture, new Position(x, y))));
//				}
//			}
//		}
//	}
//
//	private void placeTexture(Texture texture, List<Drawable> list, Class<?> clazz, boolean solid,
//			Set<Position> positions) throws NoSuchMethodException, SecurityException, InstantiationException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Constructor<?> ctor = clazz.getConstructor(Texture.class, Position.class);
//		for (Position position : positions) {
//			list.add(((Drawable) ctor.newInstance(texture, position)));
//		}
//	}

//	private List<Drawable> getAllDrawables() {
//		List<Drawable> allDrawables = new ArrayList<>();
//		allDrawables.addAll(ennemies);
//		allDrawables.addAll(obstacles);
//		allDrawables.add(hero);
//		return allDrawables;
//	}

	@Override
	public void paintComponent(Graphics g) {
//		long startTime = System.nanoTime();
		int x;
		int y;

		BufferedImage skin;
		BufferedImage weaponSkin;
		
		//drawing floor
		for (Floor floor:floors) {
			x = floor.getPosition().getX();
			y = floor.getPosition().getY();

			skin = floor.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}

		// drawing all walls
		for (Wall wall : walls) {
			x = wall.getPosition().getX();
			y = wall.getPosition().getY();

			skin = wall.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}

		// drawing all firepits
		for (Fire fire : fires) {
			fire.setNextTexture();
			x = fire.getPosition().getX();
			y = fire.getPosition().getY();

			skin = fire.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}

		for (Drop drop : drops) {
			x = drop.getPosition().getX();
			y = drop.getPosition().getY();

			skin = drop.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}

		for (Enemy enemy : enemies) {
			enemy.setNextTexture();
			x = enemy.getPosition().getX();
			y = enemy.getPosition().getY();

			skin = enemy.getTexture().getSkin();
			weaponSkin = enemy.getWeapon().getTexture().getSkin();

			if (enemy.isTakingDamage()) {
				skin = colorFilter(skin, Color.red);
				weaponSkin = colorFilter(weaponSkin, Color.red);
			}

			g.drawImage(skin, x, y, null);

			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(weaponSkin, weaponImageTransform(enemy), null);

			enemy.reset();
		}

		hero.setNextTexture();
		x = hero.getPosition().getX();
		y = hero.getPosition().getY();

		skin = hero.getTexture().getSkin();
		weaponSkin = hero.getWeapon().getTexture().getSkin();

		if (hero.isTakingDamage()) {
			skin = colorFilter(skin, Color.red);
			weaponSkin = colorFilter(weaponSkin, Color.red);
		}

		g.drawImage(skin, x, y, null);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(weaponSkin, weaponImageTransform(hero), null);

		hero.reset();

//		for (Drawable drawable : getAllDrawables()) {
//			x = drawable.getPosition().getX();
//			y = drawable.getPosition().getY();
//
//			weaponImage = null;
////			long startTime = System.nanoTime();
//			if (drawable instanceof Animation) {
//				((Animation) drawable).setNextTexture();
//			}
//			if (!(drawable instanceof GameCharacter)) {
//				skin = drawable.getTexture().getSkin();
//				g.drawImage(skin, x, y, null);
//			} else {
//				GameCharacter gc = (GameCharacter) drawable;
//				weaponImage = gc.getWeapon().getTexture().getSkin();
//				if (gc.isTakingDamage()) {
//					skin = colorFilter(drawable.getTexture().getSkin(), Color.red);
//					weaponImage = colorFilter(weaponImage, Color.red);
////					((GameCharacter) drawable).reset();
//				} else {
//					skin = drawable.getTexture().getSkin();
//				}
//				g.drawImage(skin, x, y, null);
//				Graphics2D g2d = (Graphics2D) g;
//				g2d.drawImage(weaponImage,weaponImageTransform(gc) , null);
//				gc.reset();
//				//drawable.getCenter().getX(), drawable.getCenter().getY()+5, 24, 24
//			}
//
//			
//			/*
//			 * HITBOX OF CHARACTERS if (drawable instanceof GameCharacter) {
//			 * g.setColor(Color.RED);
//			 * 
//			 * int hitBoxWidth = ((HitBoxTexture)
//			 * drawable.getTexture()).getHitBoxSize().getWidth(); int hitBoxHeight =
//			 * ((HitBoxTexture) drawable.getTexture()).getHitBoxSize().getHeight();
//			 * 
//			 * int offSetX = (drawable.getTexture().getSize().getWidth() - hitBoxWidth) / 2;
//			 * int offSetY = (drawable.getTexture().getSize().getHeight() - hitBoxHeight) /
//			 * 2;
//			 * 
//			 * g.drawRect(x + offSetX, y + offSetY, hitBoxWidth, hitBoxHeight); }
//			 */
//
////			long endTime = System.nanoTime();
////			System.out.println("duration: " + (endTime - startTime)/1000000);
//		}

		/*
		 * HITBOX OF OBSTACLES g.setColor(Color.green); for (Position position :
		 * board.getFixedHitBoxes()) { g.drawLine(position.getX(), position.getY(),
		 * position.getX(), position.getY()); }
		 */
//		long endTime = System.nanoTime();
//		System.out.println("duration: " + (endTime - startTime)/1000000);
	}

	private static AffineTransform weaponImageTransform(GameCharacter gc) {
		AffineTransform at = new AffineTransform();
		// at.translate(24, 24);
		at.translate(gc.getCenter().getX(), gc.getCenter().getY());
		at.scale(0.8, 0.8);
		switch (gc.getDirection()) {
		case EAST:
			at.translate(2, 5);
			at.rotate(Math.PI / 2);
			if (gc.isAttacking()) {
				at.rotate(Math.PI * 5 / 4);
			}
			break;
		case NORTH:
			at.translate(12, 2);
			at.rotate(Math.PI / 4);
			if (gc.isAttacking()) {
				at.rotate(Math.PI);
			}
			break;
		case SOUTH:
			at.translate(-10, 5);
			at.rotate(Math.PI * 5 / 4);
			if (gc.isAttacking()) {
				at.rotate(Math.PI);
			} else {
				at.scale(0, 0);
			}

			break;
		case WEST:
			at.translate(-2, 5);
			if (gc.isAttacking()) {
				at.rotate(Math.PI * 3 / 4);
			}
			break;
		default:
			break;

		}

		return at;
	}

	private static BufferedImage colorFilter(BufferedImage skin, Color color) {
		int w = skin.getWidth();
		int h = skin.getHeight();
		BufferedImage filteredSkin = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = filteredSkin.createGraphics();
		g.drawImage(skin, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5F));
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return filteredSkin;
	}
}
