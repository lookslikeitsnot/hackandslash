package be.kiop.utils;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetUtils {
	public static boolean isValidSet(Set<?> set) {
		return !(set == null || set.isEmpty());
	}
	
	@SafeVarargs
	public static <T> Set<T> merge(Set<? extends T>... sets){
		Set<T> newSet = new LinkedHashSet<>();
		for(Set<? extends T> set : sets) {
			newSet.addAll(set);
		}
		return newSet;
	}
}
