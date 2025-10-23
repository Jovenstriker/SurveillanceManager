package edu.ncsu.csc316.surveillance.comparator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.surveillance.data.Person;

/**
 * Comparator with people
 */
class PersonComparatorTest {

	/**
	 * Testing with same last name
	 */
	@Test
	void testCompare() {
		Person person1 = new Person("123", "H", "W");
		Person person2 = new Person("12", "H", "W");
		Person person3 = new Person("123", "j", "W");
		PersonComparator comparator = new PersonComparator();
		
		assertEquals(1, comparator.compare(person1, person2));

		assertEquals(-34, comparator.compare(person1, person3));

	}

}
