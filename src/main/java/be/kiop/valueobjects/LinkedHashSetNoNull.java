package be.kiop.valueobjects;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class LinkedHashSetNoNull<E> extends LinkedHashSet<E>{
	private static final long serialVersionUID = 1L;
	
	@SafeVarargs
	public LinkedHashSetNoNull(E... elements) {
		super();
		this.addAll(Arrays.stream(elements).collect(Collectors.toSet()));
	}

	@Override
	public boolean add(E e) {
		if(e != null) {
			return super.add(e);
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for(Iterator<? extends E> iterator = c.iterator(); iterator.hasNext();) {
			E element = iterator.next();
			if(element == null) {
				iterator.remove();
			}
		}
		return super.addAll(c);
	}
}
