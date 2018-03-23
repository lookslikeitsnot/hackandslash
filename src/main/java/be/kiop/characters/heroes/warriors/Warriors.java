package be.kiop.characters.heroes.warriors;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Warriors {
	Warrior("warrior.png"),
	ShinyHelmetWarrior("warrior-shiny-helmet.png");
	
	private Path path;
	
	Warriors(String path){
		this.path = Paths.get("src/main/resources/images/heroes/warriors/" + path);
	}
	
	public Path getPath() {
		return path;
	}
}