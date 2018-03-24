package be.kiop.obstacles.walls;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.obstacles.Obstacle;
import be.kiop.textures.Texture;
import be.kiop.textures.Walls;
import be.kiop.valueobjects.Position;

public class Wall extends Obstacle{
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(Walls.values()).collect(Collectors.toSet());
	public Wall(Walls wall, Position position) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(wall);
		super.setPosition(position);
		setDestructible(false);
	}
}
