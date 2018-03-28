package be.kiop.obstacles;

import java.util.LinkedHashSet;
import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.valueobjects.HitBox;
import be.kiop.valueobjects.Position;

public abstract class Obstacle extends Drawable implements HitBox {
	private boolean destructible;


	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	@Override
	public Set<Position> getHitBox(int range){
		Set<Position> positions = new LinkedHashSet<>();
		int minHitBoxX = getPosition().getX()  - range;
		int minHitBoxY = getPosition().getY() - range;
		int maxHitBoxX = getTexture().getSize().getWidth()+getPosition().getX() + range;
		int maxHitBoxY = getTexture().getSize().getHeight()+getPosition().getY() + range;
		
		for (int x = minHitBoxX; x <= maxHitBoxX; x++) {
			for (int y = minHitBoxY; y <= maxHitBoxY; y++) {
				if (x == minHitBoxX || y == minHitBoxY || x == maxHitBoxX || y == maxHitBoxY)
					positions.add(new Position(x, y));
			}
		}
		return positions;
	}
	
	
}
