package be.kiop.utils;

import java.util.Map;

public class MapUtils {
	public static boolean isValidMap(Map<?,?> map) {
		return !(map == null || map.isEmpty());
	}
}
