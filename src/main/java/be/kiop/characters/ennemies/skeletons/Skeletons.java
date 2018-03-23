package be.kiop.characters.ennemies.skeletons;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Skeletons {
	Skeleton("skeleton.png"), 
	SkeletonDog("skeleton-dog.png");
	
	private Path path;
	
	Skeletons(String path){
		this.path = Paths.get("src/main/resources/images/ennemies/skeletons/" + path);
	}
	
	public Path getPath() {
		return path;
	}
}
