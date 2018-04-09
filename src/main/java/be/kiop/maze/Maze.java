package be.kiop.maze;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.valueobjects.Tile;

public class Maze {
	private final int horizontalTiles;
	private final int vertictalTiles;

	private static final int TRIES = 150000;
	
	public Maze(int horizontalTiles, int verticalTiles) {
		this.horizontalTiles = horizontalTiles;
		this.vertictalTiles = verticalTiles;
		
	}
	
	public Set<Tile> generateMaze(){
		Map<Tile, Boolean> tested = new LinkedHashMap<>();

		for (int x = 0; x < horizontalTiles; x++) {
			for (int y = 0; y < vertictalTiles; y++) {
				tested.put(new Tile(x, y), false);
			}
		}

		int x = 0;
		int y = 0;


		Random random = new Random();

		for (int i = 0; i < TRIES; i++) {
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
				if (y + 2 >= vertictalTiles) {
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
}
