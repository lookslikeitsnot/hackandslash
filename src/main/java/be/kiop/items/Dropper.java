package be.kiop.items;

import java.util.Optional;
import java.util.Set;

public interface Dropper {
	public void setDroppables(Set<Drop> droppables);
	public Optional<Drop> getDrop();
}
