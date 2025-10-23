package edu.ncsu.csc316.surveillance.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.surveillance.data.Call;
/**
 * Testing date time comparison
 * @author Henry Wang
 */
public class CallDateComparatorTest {

	/**
	 * Comparing date times with different ids
	 */
	@Test
	void testCompare() {
		String[] phoneNumbers = {"abcdefghijklmno", "bcdefghijklmnop"};
		Call call1 = new Call("12", phoneNumbers, LocalDateTime.of(2019, 03, 1, 1, 1), 12);
		Call call2 = new Call("11", phoneNumbers, LocalDateTime.of(2019, 03, 1, 1, 1), 12);
		CallDateComparator comparator = new CallDateComparator();
		assertEquals(1, comparator.compare(call1, call2));
		assertEquals(-1, comparator.compare(call2, call1));

	}

}
