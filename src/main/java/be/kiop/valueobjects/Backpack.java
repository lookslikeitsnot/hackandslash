package be.kiop.valueobjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kiop.events.BackpackEvent;
import be.kiop.exceptions.BackpackFullException;
import be.kiop.exceptions.IllegalDropException;
import be.kiop.items.Drop;
import be.kiop.listeners.BackpackListener;

public class Backpack {
	private final Set<BackpackListener> backpackListeners = new HashSet<>();

	List<Drop> items;
	private int maxSize;

	public Backpack(int maxSize) {
		items = new ArrayList<>();
		this.maxSize = maxSize;
	}

	public void remove(int index) {
		BackpackEvent event;
		synchronized (backpackListeners) {
			if (items.size() < 1) {
				throw new BackpackFullException();
			}
			List<Drop> itemsCopy = new ArrayList<>(items);
			items.remove(index);
			event = new BackpackEvent(itemsCopy, items);
		}
		if (event.oldContent != event.newContent) {
			broadcast(event);
		}
	}

	public void remove(Drop drop) {
		BackpackEvent event;
		synchronized (backpackListeners) {
			if (drop == null) {
				throw new IllegalDropException();
			}
			if (items.size() < 1) {
				throw new BackpackFullException();
			}
			List<Drop> itemsCopy = new ArrayList<>(items);
			items.remove(drop);
			event = new BackpackEvent(itemsCopy, items);
		}
		if (event.oldContent != event.newContent) {
			broadcast(event);
		}
	}

	public Drop get(int index) {
		return items.get(index);
	}

	public void add(Drop drop) {
		BackpackEvent event;
		synchronized (backpackListeners) {
			if (drop == null) {
				throw new IllegalDropException();
			}
			if (items.size() >= maxSize) {
				throw new BackpackFullException();
			}
			List<Drop> itemsCopy = new ArrayList<>(items);
			items.add(drop);
			event = new BackpackEvent(itemsCopy, items);
		}
		if (event.oldContent != event.newContent) {
			broadcast(event);
		}
	}

	public void add(Drop drop, int index) {
		BackpackEvent event;
		synchronized (backpackListeners) {
			if (drop == null) {
				throw new IllegalDropException();
			}
			if (items.size() >= maxSize) {
				throw new BackpackFullException();
			}
			List<Drop> itemsCopy = new ArrayList<>(items);
			items.add(index, drop);
			event = new BackpackEvent(itemsCopy, items);
		}
		if (event.oldContent != event.newContent) {
			broadcast(event);
		}
	}

	private void broadcast(BackpackEvent backpackEvent) {
		Set<BackpackListener> snapshot;
		synchronized (backpackListeners) {
			snapshot = new HashSet<>(backpackListeners);
		}
		for (BackpackListener backpackListener : snapshot) {
			backpackListener.backpackChanged(backpackEvent);
		}
	}

	public void addBackpackListener(BackpackListener backpackListener) {
		synchronized (backpackListeners) {
			backpackListeners.add(backpackListener);
		}
	}

	public List<Drop> getItems() {
		synchronized (backpackListeners) {
			return items;
		}
	}

	public boolean isFull() {
		return items.size() >= maxSize;
	}
}
