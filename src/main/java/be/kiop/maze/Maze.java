package be.kiop.maze;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.exceptions.IllegalTileAmountException;
import be.kiop.valueobjects.Tile;

public class Maze {
	public static  Set<Tile> generateMaze(int horizontalTiles, int verticalTiles){
		if(horizontalTiles < 0 || verticalTiles < 0) {
			throw new IllegalTileAmountException();
		}
		if(horizontalTiles%2 != 1 || verticalTiles%2 != 1) {
			throw new IllegalTileAmountException();
		}
		
		int tries = horizontalTiles*verticalTiles*4;
		Map<Tile, Boolean> tested = generateAllTilesMap(horizontalTiles, verticalTiles);

		int x = 0;
		int y = 0;

		Random random = new Random();

		for (int i = 0; i < tries; i++) {
			tested.put(new Tile(x, y), true);
			int randInt = random.nextInt(4);
			switch (randInt) {
			case 0:
				if (x + 2  >= horizontalTiles) {
					break;
				} else if (tested.get(new Tile(x + 2, y))) {
					x += 2;
					break;
				} else {
					tested.put(new Tile(x + 1, y), true);
					x += 2 ;
					break;
				}

			case 1:
				if (y + 2 >= verticalTiles) {
					break;
				} else if (tested.get(new Tile(x, y + 2))) {
					y += 2;
					break;
				} else {
					tested.put(new Tile(x, y + 1), true);
					y += 2;
					break;
				}
			case 2:
				if (x - 2 < 0) {
					break;
				} else if (tested.get(new Tile(x - 2, y))) {
					x -= 2;
					break;
				} else {
					tested.put(new Tile(x - 1, y), true);
					x -= 2;
					break;
				}

			case 3:
				if (y - 2  < 0) {
					break;
				} else if (tested.get(new Tile(x, y - 2))) {
					y -= 2 ;
					break;
				} else {
					tested.put(new Tile(x, y - 1), true);
					y -= 2 ;
					break;
				}
			}
		}
		return tested.entrySet().stream().filter(test -> test.getValue()==false).map(Map.Entry::getKey).collect(Collectors.toSet());
	}
	
	public static Set<Tile> generateAllTiles(int horizontalTiles, int verticalTiles) {
		if(horizontalTiles < 0 || verticalTiles < 0) {
			throw new IllegalTileAmountException();
		}
		Set<Tile> allTiles = new LinkedHashSet<>();
		for(int x = 0; x < horizontalTiles; x++) {
			for(int y = 0; y < verticalTiles; y++) {
				allTiles.add(new Tile(x, y));
			}
		}
		return allTiles;
	}

	private static Map<Tile, Boolean> generateAllTilesMap(int horizontalTiles, int verticalTiles){
		if(horizontalTiles < 0 || verticalTiles < 0) {
			throw new IllegalTileAmountException();
		}
		Map<Tile, Boolean> tilesMap = new LinkedHashMap<>();
		generateAllTiles(horizontalTiles, verticalTiles).forEach(tile -> tilesMap.put(tile, false));
		return tilesMap;
	}
}
