package be.kiop.listeners;

import be.kiop.events.HealthEvent;

public interface HealthListener {
	void healthChanged(HealthEvent event);
}
