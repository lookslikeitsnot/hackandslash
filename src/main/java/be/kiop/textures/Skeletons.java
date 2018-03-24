package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Skeletons implements Texture{
	Skeleton_Large("skeleton", new Position(0,0), new Size(64, 64)), 
	Skeleton_Dog_Large("skeleton-dog", new Position(0,0), new Size(64, 64));
	
	private Path path;
	private Position position;
	private Size size;
	private BufferedImage skin;
	
	Skeletons(String path, Position position, Size size){
		this.path = Paths.get("src/main/resources/images/ennemies/skeletons/" + path + ".png");
		this.position = position;
		this.size = size;
		try {
			BufferedImage sprites = ImageIO.read(this.path.toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}
	
	public BufferedImage getSkin() {
//		try {
//		BufferedImage skin = ImageIO.read(path.toFile());
//		return skin.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
//	} catch (IOException e) {
//		throw new SkinNotFoundException();
//	}
	return skin;
	}
	
	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public Size getSize() {
		return size;
	}
}
