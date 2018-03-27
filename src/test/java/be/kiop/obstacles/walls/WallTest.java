package be.kiop.obstacles.walls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import be.kiop.UI.Board;
import be.kiop.textures.Walls;
import be.kiop.valueobjects.Position;

public class WallTest {
	private Wall wall;
	private Position position;
	private Board board;
	
	private final static Walls OBSTACLE_PATH = Walls.Wall_Metallic;
	
	@Before
	public void before() {
		board = new Board();
		position = new Position(board.getWidth()/2, board.getHeight()/2);
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
