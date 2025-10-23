package edu.ncsu.csc316.surveillance.comparator;

import java.util.Comparator;


/**
 * Used for string comparison, simple compare to
 * @author Henry Wang
 */
public class StringComparator implements Comparator<String> {
	/**
	 * Compares string
	 */
	@Override
	public int compare(String one, String two) {
		return one.compareTo(two);
	}
}
