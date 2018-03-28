package be.kiop.valueobjects;

import java.util.Set;

public interface HitBox {
	Set<Position> getHitBox(int range);
}
