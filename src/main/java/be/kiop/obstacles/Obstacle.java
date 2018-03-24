package be.kiop.obstacles;

import be.kiop.UI.Drawable;

public abstract class Obstacle extends Drawable{
	private boolean destructible;


	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}
}
