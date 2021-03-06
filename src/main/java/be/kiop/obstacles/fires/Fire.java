package be.kiop.obstacles.fires;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import be.kiop.UI.Animated;
import be.kiop.UI.Drawable;
import be.kiop.obstacles.Obstacle;
import be.kiop.textures.FireTextures;
import be.kiop.textures.Texture;
import be.kiop.valueobjects.Position;

public class Fire extends Obstacle implements Animated{
	private int movementFrame;
	private final static Set<Texture> AVAILABLE_TEXTURES = Arrays.stream(FireTextures.values()).collect(Collectors.toSet());
	
	public Fire(Texture fire, Position position) {
		super.setAvailableTextures(AVAILABLE_TEXTURES);
		super.setTexture(fire);
		super.setPosition(position);
		setDestructible(false);
		movementFrame = 1;
	}
	
	@Override
	public void setNextTexture() {
		movementFrame++;
		movementFrame = movementFrame>Drawable.ANIMATION_LENGTH ? 1 : movementFrame;
		int associatedFrame = getAssociatedFrameNumber(movementFrame);
		setTexture(Enum.valueOf(FireTextures.class, (getTexture().getName() +"_"+ Integer.toString(associatedFrame))));
//		setTexture(TextureBuilder.getTexture(Fires.class, getTexture().getName(), Integer.toString(associatedFrame)));
	}

	@Override
	public Drawable copy() {
		try {
			return (Fire) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
