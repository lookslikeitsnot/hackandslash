package be.kiop.characters.heroes.warriors;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Warriors {
	Warrior("warrior"), ShinyHelmetWarrior("warrior-shiny-helmet");

	private Path path;

	Warriors(String path) {
		this.path = Paths.get("src/main/resources/images/heroes/warriors/" + path + ".png");
	}

	public Path getPath() {
		return path;
	}
}