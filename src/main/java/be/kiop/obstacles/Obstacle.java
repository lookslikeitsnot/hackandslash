package be.kiop.obstacles;

import java.util.LinkedHashSet;
import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.textures.HitBoxTexture;
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
	public Set<Position> getHitBox(int range) {
		int minHitBoxX;
		int minHitBoxY;
		int maxHitBoxX;
		int maxHitBoxY;
		Set<Position> positions = new LinkedHashSet<>();
		minHitBoxX = getPosition().getX() - range;
		minHitBoxY = getPosition().getY() - range;
		maxHitBoxX = getTexture().getSize().getWidth() + getPosition().getX() + range;
		maxHitBoxY = getTexture().getSize().getHeight() + getPosition().getY() + range;
		
		if(getTexture() instanceof HitBoxTexture) {
			int hitBoxWidth = ((HitBoxTexture)getTexture()).getHitBoxSize().getWidth();
			int hitBoxHeight = ((HitBoxTexture)getTexture()).getHitBoxSize().getHeight();
			int textureWidth = getTexture().getSize().getWidth();
			int textureHeight = getTexture().getSize().getHeight();
			minHitBoxX += (textureWidth-hitBoxWidth)/2;
			minHitBoxY += (textureHeight-hitBoxHeight)/2;
			maxHitBoxX -= (textureWidth-hitBoxWidth)/2;
			maxHitBoxY -= (textureHeight-hitBoxHeight)/2;
		}
		
		for (int x = minHitBoxX; x < maxHitBoxX; x++) {
			for (int y = minHitBoxY; y < maxHitBoxY; y++) {
				if (x == minHitBoxX || y == minHitBoxY || x == maxHitBoxX-1 || y == maxHitBoxY-1)
					positions.add(new Position(x, y));
			}
		}
		return positions;
	}

}
