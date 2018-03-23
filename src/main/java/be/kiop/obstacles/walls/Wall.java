package be.kiop.obstacles.walls;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.obstacles.Obstacle;
import be.kiop.valueobjects.Position;

public class Wall extends Obstacle{
	private final static Set<Path> AVAILABLE_SKIN_PATHS = Arrays.stream(Walls.values())
			.map(wall -> wall.getPath()).collect(Collectors.toSet());
	public Wall(Path skinPath, Position position) {
		super.setAvailableSkinPaths(AVAILABLE_SKIN_PATHS);
		super.setSkinPath(skinPath);
		super.setPosition(position);
		setDestructible(false);
	}
}
