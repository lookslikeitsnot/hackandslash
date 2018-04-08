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
import be.kiop.items.Drop;
import be.kiop.obstacles.fires.Fire;
import be.kiop.obstacles.walls.Wall;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.WallTextures;
import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public class BoardDrawing extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Set<Position> floorPositions;
	private final Set<Position> exteriorWallsPositions;

	private FloorTextures floorTexture;
	private WallTextures exteriorWallsTexture;

	private Hero hero;
	private Set<Enemy> enemies;
	private List<Drop> drops;
	private Set<Wall> walls;
	private Set<Fire> fires;

	private Size size;

	private int horizontalTiles;
	private int verticalTiles;

	public BoardDrawing(Size size, FloorTextures floorTexture, WallTextures exteriorWallsTexture, Hero hero,
			Set<Wall> walls, Set<Fire> fires, Set<Enemy> enemies, List<Drop> drops, Board board) {
		this.size = size;
		this.enemies = enemies;
		this.hero = hero;
		this.drops = drops;
		this.walls = walls;
		this.fires = fires;
		this.horizontalTiles = board.getHorizontalTiles();
		this.verticalTiles = board.getVerticalTiles();
		this.floorTexture = floorTexture;
		this.exteriorWallsTexture = exteriorWallsTexture;

		floorPositions = generateFloorPositions(floorTexture);
		exteriorWallsPositions = generateExteriorWallsPositions(exteriorWallsTexture);

		new Keyboard(this, hero, board);

		setPreferredSize(size.toDimension());
	}

	private Set<Position> generateExteriorWallsPositions(WallTextures exteriorWallsTexture) {

		int width = exteriorWallsTexture.getSize().getWidth();
		int height = exteriorWallsTexture.getSize().getHeight();

		int maxX = size.getWidth();
		int maxY = size.getHeight();

		Set<Position> exteriorWallsPositions = new LinkedHashSet<>();
		for (int x = maxX; x > width; x -= width) {
			for (int y = maxY; y > height; y -= height) {
				if (x == maxX || y == maxY) {
					exteriorWallsPositions.add(new Position(x - width, y - height));
				}
			}
		}
		for (int x = 0; x < maxX; x += width) {
			for (int y = 0; y < maxY; y += height) {
				if (x == 0 || y == 0) {
					exteriorWallsPositions.add(new Position(x, y));
				}
			}
		}
		return exteriorWallsPositions;
	}

	private Set<Position> generateFloorPositions(FloorTextures floorTexture) {
		int minX = Board.exteriorWallSize.getWidth();
		int minY = Board.exteriorWallSize.getHeight();
		int maxX = Board.TILE_SIZE.getWidth() * horizontalTiles + Board.exteriorWallSize.getWidth();
		int maxY = Board.TILE_SIZE.getHeight() * verticalTiles + Board.exteriorWallSize.getHeight();
		int stepX = floorTexture.getSize().getWidth();
		int stepY = floorTexture.getSize().getHeight();

		Set<Position> floors = new LinkedHashSet<>();
		for (int x = minX; x < maxX; x += stepX) {
			for (int y = minY; y < maxY; y += stepY) {
				if (x >= minX && y >= minY && x <= maxX && y <= maxY) {
					floors.add(new Position(x, y));
				}
			}
		}
		return floors;
	}

	@Override
	public void paintComponent(Graphics g) {
		drawFloors(g);

		drawWalls(g);
		drawFires(g);
		drawDrops(g);
		drawEnemies(g);
		drawHero(g);
		drawExteriorWalls(g);
	}

	private void drawFloors(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		for (Position floorPosition : floorPositions) {
			x = floorPosition.getX();
			y = floorPosition.getY();

			skin = this.floorTexture.getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawExteriorWalls(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		for (Position exteriorWallPosition : exteriorWallsPositions) {
			x = exteriorWallPosition.getX();
			y = exteriorWallPosition.getY();

			skin = this.exteriorWallsTexture.getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawWalls(Graphics g) {
		int x;
		int y;

		BufferedImage skin;

		for (Wall wall : walls) {
			x = wall.getAbsolutePosition().getX();
			y = wall.getAbsolutePosition().getY();

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
			x = fire.getAbsolutePosition().getX();
			y = fire.getAbsolutePosition().getY();

			skin = fire.getTexture().getSkin();

			g.drawImage(skin, x, y, null);
		}
	}

	private void drawDrops(Graphics g) {
		int x;
		int y;

		BufferedImage skin;
		for (Drop drop : drops) {
			x = drop.getAbsolutePosition().getX();
			y = drop.getAbsolutePosition().getY();

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
			x = enemy.getAbsolutePosition().getX();
			y = enemy.getAbsolutePosition().getY();

			skin = enemy.getTexture().getSkin();
			weaponSkin = enemy.getWeapon().getTexture().getSkin();

			if (enemy.isTakingDamage()) {
				skin = colorFilter(skin, Color.red);
				weaponSkin = colorFilter(weaponSkin, Color.red);
			}

			if (enemy.getDirection() == Directions.WEST) {
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
		int x = hero.getAbsolutePosition().getX();
		int y = hero.getAbsolutePosition().getY();

		BufferedImage skin = hero.getTexture().getSkin();
		BufferedImage weaponSkin = hero.getWeapon().getTexture().getSkin();

		if (hero.isTakingDamage()) {
			skin = colorFilter(skin, Color.red);
			weaponSkin = colorFilter(weaponSkin, Color.red);
		}

		if (hero.getDirection() == Directions.WEST) {
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
		at.translate(gc.getAbsoluteCenterPosition().getX(), gc.getAbsoluteCenterPosition().getY());
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
			} else {
				at.scale(0.8, 0.8);
			}
			break;
		case SOUTH:
			at.translate(12, 5);
			at.rotate(Math.PI * 5 / 4);
			if (gc.isAttacking()) {
				at.rotate(Math.PI);
			} else {
				at.scale(0.8, 0.8);
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
