package be.kiop.obstacles.walls;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import be.kiop.textures.WallTextures;
import be.kiop.valueobjects.Tile;

public class WallTest {
	private Wall wall;
	
	private final static WallTextures OBSTACLE_PATH = WallTextures.Wall_Metallic;
	
	@Before
	public void before() {
//		position = new Position(board.getWidth()/2, board.getHeight()/2);
		
		wall = new Wall(OBSTACLE_PATH, new Tile(1,1), false);
	}
	
	@Test
	public void isDestructible_nA_false() {
		assertFalse(wall.isDestructible());
	}
	
//	@Test
//	public void setDestructible_true_obstacleIsDestructible() {
//		wall.setDestructible(true);
//		assertTrue(wall.isDestructible());
//	}
	
}
