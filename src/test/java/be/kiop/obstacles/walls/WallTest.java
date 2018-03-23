package be.kiop.obstacles.walls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.valueobjects.Position;

public class WallTest {
	private Wall wall;
	private Position position;
	private final static Path OBSTACLE_PATH = Walls.Wall.getPath();
	
	@Before
	public void before() {
		position = new Position(Board.getWidth()/2, Board.getHeight()/2);
		wall = new Wall(OBSTACLE_PATH, position);
	}
	
	@Test
	public void isDestructible_nA_false() {
		assertFalse(wall.isDestructible());
	}
	
	@Test
	public void setDestructible_true_obstacleIsDestructible() {
		wall.setDestructible(true);
		assertTrue(wall.isDestructible());
	}
	
}
