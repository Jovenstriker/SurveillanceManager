package edu.ncsu.csc316.surveillance.comparator;

import java.util.Comparator;

import edu.ncsu.csc316.surveillance.data.Call;

/**
 * Comparing call date with date time
 * @author Henry Wang
 */
public class CallDateComparator implements Comparator<Call> {
	/**
	 * Compares call based oldest then based on id
	 */
	@Override
	public int compare(Call one, Call two) {
		if (one.getTimestamp().isBefore(two.getTimestamp())) {
			return -1;
		} else if (one.getTimestamp().isEqual(two.getTimestamp())){
			if (one.getId().compareTo(two.getId()) < 0) {
				return -1;
			} 
			return 1;
		} else {
			return 1;
		}

	}
}
