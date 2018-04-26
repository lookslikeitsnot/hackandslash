package be.kiop.maze;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.valueobjects.Directions;
import be.kiop.valueobjects.Tile;

public class AStar {
	public static int counter = 0;

	public static Directions mazeSolvingDirections(Tile start, Tile goal, Set<Tile> availableTiles)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Set<Tile> closedTiles = new LinkedHashSet<>();
		Set<Tile> openTiles = new LinkedHashSet<>();
		openTiles.add(start);
		Map<Tile, Tile> cameFrom = new LinkedHashMap<>();
//		Integer[] integers = new Integer[Board.ACTIVATION_DISTANCE*Board.ACTIVATION_DISTANCE];
//		Arrays.fill(integers, Double.POSITIVE_INFINITY);
//		List<Integer> gScore = Arrays.asList(integers);
		Map<Tile, Integer> gScore = new LinkedHashMap<>();
//		Map<Tile, Integer> gScore = getScoreMap(start);
		gScore.put(start, 0);
		Map<Tile, Integer> fScore = new LinkedHashMap<>();
//		Map<Tile, Integer> fScore = getScoreMap(start);
		fScore.put(start, heuristicCostEstimate(start, goal));

		Tile current;
		Set<Tile> adjacentTiles;
		int tentativeGScore;
		
//		System.out.println("function call number " + counter++);

		while (!openTiles.isEmpty()) {
//			System.out.println("is this infinite ?");
			System.out.println("opentiles size: " + openTiles.size());
			current = lowestFScoreTile(openTiles, fScore);
			if (current == goal) {
				return optimalDirection(cameFrom, start);
			}
			openTiles.remove(current);
			closedTiles.add(current);

			adjacentTiles = current.getAvailableAdjacentTiles(availableTiles).values().stream().collect(Collectors.toSet());

			for (Tile neighbor : adjacentTiles) {
//				System.out.println("testing neighbour");
				if (closedTiles.contains(neighbor)) {
//					System.out.println("contained!");
					continue;
				}
				if (!openTiles.contains(neighbor)) {
//					System.out.println("doesn't contain");
					openTiles.add(neighbor);
				}

				tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE);
				if (tentativeGScore >= gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
					continue;
				}

				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentativeGScore);
				fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, goal));
			}
		}
		return null;
	}

	private static Directions optimalDirection(Map<Tile, Tile> cameFrom, Tile start) {
		System.out.println("start: " + start);
		Tile firstMove = cameFrom.get(start);
		System.out.println("firstmove: " + firstMove);
		int startX = start.getHorizontalPosition();
		int startY = start.getVerticalPosition();
		int finalX = firstMove.getHorizontalPosition();
		int finalY = firstMove.getVerticalPosition();
		Directions optimalDirection = null;
		if (startX < finalX) {
			optimalDirection = Directions.WEST;
		}
		if (startY < finalY) {
			optimalDirection = Directions.SOUTH;
		}
		if (startX > finalX) {
			optimalDirection = Directions.EAST;
		}
		if (startY > finalY) {
			optimalDirection = Directions.NORTH;
		}
		System.out.println("optimal direction: " + optimalDirection);
		return optimalDirection;
	}

	private static Tile lowestFScoreTile(Set<Tile> tiles, Map<Tile, Integer> fScore) {
		Tile lowestScoreTile = tiles.stream().findFirst().get();
		for (Tile tile : tiles) {
			if (fScore.getOrDefault(tile, Integer.MAX_VALUE) < fScore.getOrDefault(lowestScoreTile,
					Integer.MAX_VALUE)) {
				lowestScoreTile = tile;
			}
		}
		return lowestScoreTile;
	}

//	private static Map<Tile, Integer> getScoreMap(Tile tile) {
//		Map<Tile, Integer> scoreMap = new LinkedHashMap<>();
//		Set<Tile> adjacentTiles = tile.getAdjacentTiles();
//		for (Tile adjacentTile : adjacentTiles) {
//			scoreMap.put(adjacentTile, Integer.MAX_VALUE);
//		}
//		return scoreMap;
//	}

	private static int heuristicCostEstimate(Tile t1, Tile t2) {
		return tileManhattanDistance(t1, t2);
	}

	public static int tileManhattanDistance(Tile t1, Tile t2) {
		return Math.abs(t1.getHorizontalPosition() - t2.getHorizontalPosition())
				+ Math.abs(t1.getVerticalPosition() - t2.getVerticalPosition());
	}
}
