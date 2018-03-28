package be.kiop.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import be.kiop.exceptions.SkinNotFoundException;
import be.kiop.valueobjects.Position;
import be.kiop.valueobjects.Size;

public enum Fires implements Texture, IdleAnimation, HitBoxTexture {
	Fire_1("fires", "Fire", new Position(0, 0), new Size(32, 55), new Size(32, 55), 1),
	Fire_2("fires", "Fire", new Position(32, 0), new Size(32, 55), new Size(32, 55), 2),
	Fire_3("fires", "Fire", new Position(64, 0), new Size(32, 55), new Size(32, 55), 3),

	Fire_Off_1("fires", "Fire_Off", new Position(0, 0), new Size(32, 55), new Size(32, 55), 1),
	Fire_Off_2("fires", "Fire_Off", new Position(32, 0), new Size(32, 55), new Size(32, 55), 2),
	Fire_Off_3("fires", "Fire_Off", new Position(64, 0), new Size(32, 55), new Size(32, 55), 3);

	private String name;
	private Size size;
	private Size hitBoxSize;
	private int animationFrame;
	private BufferedImage skin;

	Fires(String path, String name, Position position, Size size, Size hitBoxSize, int animationFrame) {
		this.name = name;
		this.size = size;
		this.hitBoxSize = hitBoxSize;
		this.animationFrame = animationFrame;
		try {
			BufferedImage sprites = ImageIO
					.read(Paths.get("src/main/resources/images/textures/fires/" + path + ".png").toFile());
			skin = sprites.getSubimage(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		} catch (IOException e) {
			throw new SkinNotFoundException();
		}
	}

	@Override
	public BufferedImage getSkin() {
		return skin;
	}

	@Override
	public Size getSize() {
		return size;
	}

	@Override
	public int getAnimationFrame() {
		return animationFrame;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Size getHitBoxSize() {
		return hitBoxSize;
	}

}
