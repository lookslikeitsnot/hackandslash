package be.kiop.obstacles;

import java.util.Set;

import be.kiop.UI.Drawable;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Tile;

public abstract class Obstacle extends Drawable {//implements HitBox {
	private final boolean destructible;
	
	public Obstacle(Set<Texture> availableTextures, Texture texture, Tile tile, boolean destructible) {
		super(availableTextures, texture, tile);
		this.destructible = destructible;
	}

	public boolean isDestructible() {
		return destructible;
	}

//	@Override
//	public Set<Position> getHitBox(int range) {
//		int minHitBoxX;
//		int minHitBoxY;
//		int maxHitBoxX;
//		int maxHitBoxY;
//		Set<Position> positions = new LinkedHashSet<>();
//		minHitBoxX = getAbsolutePosition().getX() - range;
//		minHitBoxY = getAbsolutePosition().getY() - range;
//		maxHitBoxX = getTexture().getSize().getWidth() + getAbsolutePosition().getX() + range;
//		maxHitBoxY = getTexture().getSize().getHeight() + getAbsolutePosition().getY() + range;
//		
//		if(getTexture() instanceof HitBoxTexture) {
//			int hitBoxWidth = ((HitBoxTexture)getTexture()).getHitBoxSize().getWidth();
//			int hitBoxHeight = ((HitBoxTexture)getTexture()).getHitBoxSize().getHeight();
//			int textureWidth = getTexture().getSize().getWidth();
//			int textureHeight = getTexture().getSize().getHeight();
//			minHitBoxX += (textureWidth-hitBoxWidth)/2;
//			minHitBoxY += (textureHeight-hitBoxHeight)/2;
//			maxHitBoxX -= (textureWidth-hitBoxWidth)/2;
//			maxHitBoxY -= (textureHeight-hitBoxHeight)/2;
//		}
//		
//		for (int x = minHitBoxX; x < maxHitBoxX; x++) {
//			for (int y = minHitBoxY; y < maxHitBoxY; y++) {
//				if (x == minHitBoxX || y == minHitBoxY || x == maxHitBoxX-1 || y == maxHitBoxY-1)
//					positions.add(new Position(x, y));
//			}
//		}
//		return positions;
//	}

}
