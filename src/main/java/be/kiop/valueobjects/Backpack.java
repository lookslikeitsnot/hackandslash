package be.kiop.valueobjects;

import java.util.ArrayList;
import java.util.List;

import be.kiop.exceptions.BackpackFullException;
import be.kiop.exceptions.IllegalDropException;
import be.kiop.items.Drop;

public class Backpack {
	List<Drop> items;
	private int size;
	
	public Backpack(int size){
		items = new ArrayList<>();
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
	
	public List<Drop> getItems(){
		return items;
	}
}
