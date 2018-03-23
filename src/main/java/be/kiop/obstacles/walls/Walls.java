package be.kiop.obstacles.walls;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Walls {
	Wall("wall");
	
	private Path path;
	
	Walls(String path){
		this.path = Paths.get("src/main/resources/images/obstacles/walls/" + path + ".png");
	}
	
	public Path getPath() {
		return path;
	}
}