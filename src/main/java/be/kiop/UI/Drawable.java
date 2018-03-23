package be.kiop.UI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import be.kiop.exceptions.SkinNotFoundException;

public abstract class Drawable {
	public final static Path VALID_SKIN = Paths.get("src/main/resources/images/test.png");
	
	private Path skinPath;

	public Drawable(Path skinPath) {
		setSkinPath(skinPath);
	}

	public Path getSkinPath() {
		return skinPath;
	}
	
	public void setSkinPath(Path skinPath) {
		if(skinPath == null || !Files.exists(skinPath)) {
			throw new SkinNotFoundException();
		}
		this.skinPath = skinPath;
	}
}
