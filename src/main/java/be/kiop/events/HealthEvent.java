package be.kiop.events;

public class HealthEvent {
	public final float oldHealth;
	public final float newHealth;

	public HealthEvent(float oldHealth, float newHealth) {
		this.oldHealth = oldHealth;
		this.newHealth = newHealth;
	}
}
