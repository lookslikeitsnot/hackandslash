package be.kiop.maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import be.kiop.exceptions.IllegalTileAmountException;

public class MazeTest {
	@Test
	public void generateMaze_greaterThan1AndOddTileAmount_mazeGenerated() {
		assertFalse(Maze.generateMaze(5, 5).isEmpty());
	}

	@Test(expected = IllegalTileAmountException.class)
	public void generateMaze_negativeHorizontalTileAmount_exception() {
		assertFalse(Maze.generateMaze(-1, 5).isEmpty());
	}

	@Test(expected = IllegalTileAmountException.class)
	public void generateMaze_negativeVerticalTileAmount_exception() {
		assertFalse(Maze.generateMaze(5, -1).isEmpty());
	}
	
	@Test(expected = IllegalTileAmountException.class)
	public void generateMaze_evenHorizontalTileAmount_exception() {
		assertFalse(Maze.generateMaze(4, 5).isEmpty());
	}
	
	@Test(expected = IllegalTileAmountException.class)
	public void generateMaze_evenVerticalTileAmount_exception() {
		assertFalse(Maze.generateMaze(5, 4).isEmpty());
	}
	
	@Test
	public void generateAllTiles_greaterThan1AndOddTileAmount_mazeGenerated() {
		assertEquals(Maze.generateAllTiles(5, 5).size(), 25);
	}

	@Test(expected = IllegalTileAmountException.class)
	public void generateAllTiles_negativeHorizontalTileAmount_exception() {
		assertFalse(Maze.generateAllTiles(-1, 5).isEmpty());
	}

	@Test(expected = IllegalTileAmountException.class)
	public void generateAllTiles_negativeVerticalTileAmount_exception() {
		assertFalse(Maze.generateAllTiles(5, -1).isEmpty());
	}
}
