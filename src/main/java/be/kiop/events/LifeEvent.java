package be.kiop.events;

public class LifeEvent {
	public final int oldLives;
	public final int newLives;
	
	public LifeEvent(int oldLives, int newLives) {
		this.newLives = newLives;
		this.oldLives = oldLives;
	}
}
