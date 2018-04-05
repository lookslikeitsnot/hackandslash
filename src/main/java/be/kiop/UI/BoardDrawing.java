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
import be.kiop.valueobjects.Directions;
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

	public BoardDrawing(Size size, Floors floor, Hero hero, Set<Wall> walls, Set<Fire> fires, Set<Enemy> enemies,
			List<Drop> drops, Board board) {
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

	@Override
	public void paintComponent(Graphics g) {
//		long startTime = System.nanoTime();
		drawFloors(g);
		drawWalls(g);
		drawFires(g);
		drawDrops(g);
		drawEnemies(g);
		drawHero(g);
	}

	private void drawFloors(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		for (Floor floor : floors) {
			x = floor.getPosition().getX();
			y = floor.getPosition().getY();

			skin = floor.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawWalls(Graphics g) {
		int x;
		int y;

		BufferedImage skin;

		for (Wall wall : walls) {
			x = wall.getPosition().getX();
			y = wall.getPosition().getY();

			skin = wall.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawFires(Graphics g) {
		int x;
		int y;

		BufferedImage skin;

		for (Fire fire : fires) {
			fire.setNextTexture();
			x = fire.getPosition().getX();
			y = fire.getPosition().getY();

			skin = fire.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawDrops(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		for (Drop drop : drops) {
			x = drop.getPosition().getX();
			y = drop.getPosition().getY();

			skin = drop.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}

	}

	private void drawEnemies(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		BufferedImage weaponSkin;

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
			
			if(enemy.getDirection() == Directions.WEST ) {
				g.drawImage(skin, x, y, null);

				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage(weaponSkin, weaponImageTransform(enemy), null);
			} else {
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage(weaponSkin, weaponImageTransform(enemy), null);
				
				g.drawImage(skin, x, y, null);
			}

			

			enemy.reset();
		}
	}

	private void drawHero(Graphics g) {
		hero.setNextTexture();
		int x = hero.getPosition().getX();
		int y = hero.getPosition().getY();

		BufferedImage skin = hero.getTexture().getSkin();
		BufferedImage weaponSkin = hero.getWeapon().getTexture().getSkin();

		if (hero.isTakingDamage()) {
			skin = colorFilter(skin, Color.red);
			weaponSkin = colorFilter(weaponSkin, Color.red);
		}

		if(hero.getDirection() == Directions.WEST ) {
			g.drawImage(skin, x, y, null);

			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(weaponSkin, weaponImageTransform(hero), null);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(weaponSkin, weaponImageTransform(hero), null);
			
			g.drawImage(skin, x, y, null);
		}

		hero.reset();
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
			at.translate(-12, 2);
			at.rotate(Math.PI / 4);
			if (gc.isAttacking()) {
				at.rotate(Math.PI);
			}
			break;
		case SOUTH:
			at.translate(12, 5);
			at.rotate(Math.PI * 5 / 4);
			if (gc.isAttacking()) {
				at.rotate(Math.PI);
			} else {
				//at.scale(0.5, 0.5);
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
