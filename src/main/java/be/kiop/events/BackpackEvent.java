package be.kiop.events;

import java.util.List;

import be.kiop.items.Drop;

public class BackpackEvent {
	public final List<Drop> oldContent;
	public final List<Drop> newContent;
	
	public BackpackEvent(List<Drop> oldContent, List<Drop> newContent) {
		this.oldContent = oldContent;
		this.newContent = newContent;
	}
}
