package edu.ncsu.csc316.surveillance.comparator;

import java.util.Comparator;

import edu.ncsu.csc316.surveillance.data.Person;

/**
 * Comparator for person with respect to their name and number
 * @author Henry Wang
 */
public class PersonComparator implements Comparator<Person> {
	/**
	 * Compares call based last, first, then phone number
	 */
	@Override
	public int compare(Person one, Person two) {
		if (one.getLast().compareTo(two.getLast()) == 0) {
			if (one.getFirst().compareTo(two.getFirst()) == 0) {
				return one.getPhoneNumber().compareTo(two.getPhoneNumber());
			}
			return one.getFirst().compareTo(two.getFirst());
		} else {
			return one.getLast().compareTo(two.getLast());
		}

	}
}
