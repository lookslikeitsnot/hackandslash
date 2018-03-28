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
	public Set<Position> getHitbox(){
		Set<Position> positions = new LinkedHashSet<>();
		for(int x = getPosition().getX(); x <= getTexture().getSize().getWidth()+getPosition().getX(); x++) {
			for(int y = getPosition().getY(); y <= getTexture().getSize().getHeight()+getPosition().getY(); y++) {
				if (x == getPosition().getX() || y == getPosition().getY()
						|| x == getTexture().getSize().getWidth() + getPosition().getX()
						|| y == getTexture().getSize().getHeight() + getPosition().getY())
					positions.add(new Position(x, y));
			}
		}
		return positions;
	}
	
	
}
