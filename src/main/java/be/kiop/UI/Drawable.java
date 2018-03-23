package be.kiop.UI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;

public abstract class Drawable {
	public final static Path VALID_SKIN = Paths.get("src/main/resources/images/test.png");
	
	private Path skinPath;
	private Set<Path> availableSkinPaths;
	private Position position;

//	public Drawable(Path skinPath, Position position) {
//		setSkinPath(skinPath);
//		this.position = position;
//	}

	public Position getPosition() {
		return position;
	}

	public Path getSkinPath() {
		return skinPath;
	}
	
	public void setSkinPath(Path skinPath) {
		if(skinPath == null || !Files.exists(skinPath)) {
			throw new SkinNotFoundException();
		} else if (!availableSkinPaths.contains(skinPath)) {
			throw new SkinNotFoundException();
		}
		this.skinPath = skinPath;
	}
	
	public void moveLeft() {
		position.setX(position.getX()-1);
	}
	
	public void moveRight() {
		position.setX(position.getX()+1);
	}
	
	public void moveUp() {
		position.setY(position.getY()-1);
	}
	
	public void moveDown() {
		position.setY(position.getY()+1);
	}
	
	public void teleport(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void setPosition(Position position) {
		if(position == null) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	public Set<Path> getAvailableSkinPaths() {
		return availableSkinPaths;
	}

	public void setAvailableSkinPaths(Set<Path> availableSkinPaths) {
		if(availableSkinPaths == null || availableSkinPaths.isEmpty())
		{
		throw new IllegalArgumentException();
		}
		this.availableSkinPaths = availableSkinPaths;
	}
	
	
}
