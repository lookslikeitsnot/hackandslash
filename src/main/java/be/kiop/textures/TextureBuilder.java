package be.kiop.textures;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextureBuilder {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Texture getTexture(Class<Enum> clazz, String... args) {
		String enumName = Arrays.stream(args).collect(Collectors.joining("_"));
		return (Texture) Enum.valueOf(clazz, enumName);
	}
}
