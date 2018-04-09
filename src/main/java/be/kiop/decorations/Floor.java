package be.kiop.decorations;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Drawable;
import be.kiop.textures.FloorTextures;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Tile;

public class Floor extends Drawable {
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(FloorTextures.values())
			.collect(Collectors.toSet());

	public Floor(Texture texture, Tile tile) {
		super(AVAILABLE_TEXTURES, texture, tile);
	}
}
