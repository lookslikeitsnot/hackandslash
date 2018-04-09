package be.kiop.obstacles.walls;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.obstacles.Obstacle;
import be.kiop.textures.Texture;
import be.kiop.textures.WallTextures;
import be.kiop.valueobjects.Tile;

public class Wall extends Obstacle{
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(WallTextures.values()).collect(Collectors.toSet());
	
	
public Wall(Texture texture, Tile tile, boolean destructible) {
		super(AVAILABLE_TEXTURES, texture, tile, destructible);
	}
}
