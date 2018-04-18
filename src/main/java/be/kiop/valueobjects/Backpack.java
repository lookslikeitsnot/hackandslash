package be.kiop.valueobjects;

import java.util.Set;

import be.kiop.exceptions.BackpackFullException;
import be.kiop.exceptions.IllegalDropException;
import be.kiop.items.Drop;

public class Backpack {
	Set<Drop> items;
	private int size;
	
	public Backpack(int size){
		this.size = size;
	}
	
	public void add(Drop drop) {
		if(drop == null) {
			throw new IllegalDropException();
		}
		if(items.size()>=size) {
			throw new BackpackFullException();
		}
		items.add(drop);
	}
	
	public Set<Drop> getItems(){
		return items;
	}
}
