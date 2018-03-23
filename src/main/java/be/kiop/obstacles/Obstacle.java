package be.kiop.obstacles;

import be.kiop.UI.Drawable;

public class Obstacle extends Drawable{
	private boolean destructible;

//	public Obstacle(Path skinPath, Position position, boolean destructible) {
//		super.setAvailableSkinPaths(AVAILABLE_SKIN_PATHS);
//		super.setSkinPath(skinPath);
//		super.setPosition(position);
//		this.destructible = destructible;
//	}

	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}
}
