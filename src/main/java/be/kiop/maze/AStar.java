package be.kiop.maze;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Tile;

public class AStar {

	public Directions mazeSolvingDirections(Tile start, Tile goal, Set<Tile> availableTiles) {
		Set<Tile> closedTiles = new LinkedHashSet<>();
		Set<Tile> openTiles = new LinkedHashSet<>();
		openTiles.add(start);
		Map<Tile, Integer> cameFrom = new LinkedHashMap<>();
//		Integer[] integers = new Integer[Board.ACTIVATION_DISTANCE*Board.ACTIVATION_DISTANCE];
//		Arrays.fill(integers, Double.POSITIVE_INFINITY);
//		List<Integer> gScore = Arrays.asList(integers);
		Map<Tile, Integer> gScore = getScoreMap(start);
		gScore.put(start, 0);
		Map<Tile, Integer> fScore = getScoreMap(start);
		fScore.put(start, heuristicCostEstimate(start, goal));
	}

	private Map<Tile, Integer> getScoreMap(Tile tile) {
		Map<Tile, Integer> scoreMap = new LinkedHashMap<>();
		Set<Tile> adjacentTiles = tile.getAdjacentTiles();
		for (Tile adjacentTile : adjacentTiles) {
			scoreMap.put(adjacentTile, (int) Double.POSITIVE_INFINITY);
		}
		return scoreMap;
	}

	private int heuristicCostEstimate(Tile t1, Tile t2) {
		return tileManhattanDistance(t1, t2);
	}

	public static int tileManhattanDistance(Tile t1, Tile t2) {
		return Math.abs(t1.getHorizontalPosition() - t2.getHorizontalPosition())
				+ Math.abs(t1.getVerticalPosition() - t2.getVerticalPosition());
	}
}
