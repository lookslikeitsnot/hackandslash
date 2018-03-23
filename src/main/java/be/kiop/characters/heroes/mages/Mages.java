package be.kiop.characters.heroes.mages;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Mages {
	BlueMage("blue-mage.png"), 
	RedMage("red-mage.png"),
	WhiteMage("white-mage.png");
	
	private Path path;
	
	Mages(String path){
		this.path = Paths.get("src/main/resources/images/heroes/mages/" + path);
	}
	
	public Path getPath() {
		return path;
	}
}