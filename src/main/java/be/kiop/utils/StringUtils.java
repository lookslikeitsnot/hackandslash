package be.kiop.utils;

public class StringUtils {
	public static boolean isValidString(String string) {
		return !(string == null || string.trim().isEmpty());
	}
}
