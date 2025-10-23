package edu.ncsu.csc316.surveillance.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;

class SurveillanceManagerTest {

	@Test
	void testInvalidPersonfile() {
		assertThrows(FileNotFoundException.class, () -> new SurveillanceManager("", "input/calls.csv"));
		assertThrows(FileNotFoundException.class, () -> new SurveillanceManager("input/people.csv", ""));

	}
	
	@Test
	void testGetPeople() throws FileNotFoundException {
		String[] numbers = {"134-530-7421728", "289-378-3996038", "358-721-0140950", "442-000-9865092", "541-777-4740981", "663-879-6377778", "853-257-0109509", "881-633-0099232", "903-282-4112077"};
		SurveillanceManager manager = new SurveillanceManager("input/people.csv", "input/calls.csv");
		Map<String, Person> phonePerson = manager.getPeople();
		
		assertEquals(9, phonePerson.size());
		int index = 0;
		for (String number : manager.getPeople()) {
			assertEquals(numbers[index], number);
			index++;
		}
	}
	
	@Test
	void testGetCallsByPerson() throws FileNotFoundException {
		
		int[] callSize = {2, 2, 2, 1, 3, 3, 0, 1, 1};
		SurveillanceManager manager = new SurveillanceManager("input/people.csv", "input/calls.csv");
		Map<String, List<Call>> phoneCall = manager.getCallsByPerson();
		
		assertEquals(9, phoneCall.size());
		int index = 0;
		for (String number : phoneCall) {
			assertEquals(callSize[index], phoneCall.get(number).size());
			index++;
		}
	}
	
	@Test
	void testGetPeopleByHop() throws FileNotFoundException {
		String[] solvedPhoneCalls = {"663-879-6377778", "541-777-4740981", "442-000-9865092", "134-530-7421728", "903-282-4112077", "289-378-3996038", "881-633-0099232"};
		
		Integer[] correspondingHops = {1, 1, 2, 2, 2, 3, 4};
		
		SurveillanceManager manager = new SurveillanceManager("input/people.csv", "input/calls.csv");
		Map<String, Integer> phoneCall = manager.getPeopleByHop("358-721-0140950");
		
		assertEquals(7, phoneCall.size());
		
		for (int i = 0; i < 7; i++) {
			assertEquals(correspondingHops[i], phoneCall.get(solvedPhoneCalls[i]));
		}
		

	}


}
