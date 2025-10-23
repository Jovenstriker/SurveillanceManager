/**
 * 
 */
package edu.ncsu.csc316.surveillance.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Testing for report manager
 * @author Henry Wang
 */
class ReportManagerTest {

	/**
	 * Testing expected output with get calls
	 * @throws FileNotFoundException If any of the files are wrong
	 */
	@Test
	void testGetCallsByPerson() throws FileNotFoundException {
		
		String expectedOutput = "Calls involving 134-530-7421728 (Roseanna Herman) [\n"
				+ "   9/2/2019 at 10:11:43 AM involving 1 other number(s):\n"
				+ "      541-777-4740981 (Brett Mueller)\n"
				+ "   9/9/2019 at 11:10:34 AM involving 1 other number(s):\n"
				+ "      289-378-3996038 (Daniel Walker)\n"
				+ "]\n"
				+ "Calls involving 289-378-3996038 (Daniel Walker) [\n"
				+ "   9/22/2014 at 14:03:37 PM involving 1 other number(s):\n"
				+ "      881-633-0099232 (Enoch Quitzon)\n"
				+ "   9/9/2019 at 11:10:34 AM involving 1 other number(s):\n"
				+ "      134-530-7421728 (Roseanna Herman)\n"
				+ "]\n"
				+ "Calls involving 358-721-0140950 (Tereasa Kuphal) [\n"
				+ "   9/29/2014 at 14:03:37 PM involving 1 other number(s):\n"
				+ "      541-777-4740981 (Brett Mueller)\n"
				+ "   4/22/2020 at 14:56:45 PM involving 1 other number(s):\n"
				+ "      663-879-6377778 (Rudolph Buckridge)\n"
				+ "]\n"
				+ "Calls involving 442-000-9865092 (Albertina Braun) [\n"
				+ "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
				+ "      663-879-6377778 (Rudolph Buckridge)\n"
				+ "      903-282-4112077 (Tomas Nguyen)\n"
				+ "]\n"
				+ "Calls involving 541-777-4740981 (Brett Mueller) [\n"
				+ "   9/29/2014 at 14:03:37 PM involving 1 other number(s):\n"
				+ "      358-721-0140950 (Tereasa Kuphal)\n"
				+ "   5/14/2017 at 10:52:47 AM involving 1 other number(s):\n"
				+ "      663-879-6377778 (Rudolph Buckridge)\n"
				+ "   9/2/2019 at 10:11:43 AM involving 1 other number(s):\n"
				+ "      134-530-7421728 (Roseanna Herman)\n"
				+ "]\n"
				+ "Calls involving 663-879-6377778 (Rudolph Buckridge) [\n"
				+ "   5/14/2017 at 10:52:47 AM involving 1 other number(s):\n"
				+ "      541-777-4740981 (Brett Mueller)\n"
				+ "   4/22/2020 at 14:56:45 PM involving 1 other number(s):\n"
				+ "      358-721-0140950 (Tereasa Kuphal)\n"
				+ "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
				+ "      442-000-9865092 (Albertina Braun)\n"
				+ "      903-282-4112077 (Tomas Nguyen)\n"
				+ "]\n"
				+ "Calls involving 853-257-0109509 (Sarai Rodriguez) [\n"
				+ "   (none)\n"
				+ "]\n"
				+ "Calls involving 881-633-0099232 (Enoch Quitzon) [\n"
				+ "   9/22/2014 at 14:03:37 PM involving 1 other number(s):\n"
				+ "      289-378-3996038 (Daniel Walker)\n"
				+ "]\n"
				+ "Calls involving 903-282-4112077 (Tomas Nguyen) [\n"
				+ "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
				+ "      442-000-9865092 (Albertina Braun)\n"
				+ "      663-879-6377778 (Rudolph Buckridge)\n"
				+ "]\n";
		ReportManager manager = new ReportManager("input/people.csv", "input/calls.csv");
		assertEquals(expectedOutput, manager.getCallsByPerson());
	}
	
	/**
	 * Testing the invalid get calls (empty people or empty calls)
	 * @throws FileNotFoundException If the people or call records are wrong
	 */
	@Test
	void testInvalidGetCallsByPerson() throws FileNotFoundException {
		ReportManager manager = new ReportManager("input/emptyPeople.csv", "input/calls.csv");
		assertEquals("No people information was provided.", manager.getCallsByPerson());
		manager = new ReportManager("input/people.csv", "input/emptyCalls.csv");
		assertEquals("No calls exist in the call logs.", manager.getCallsByPerson());
	}
	
	/**
	 * Testing the expected vs actual people covered by warrant
	 * @throws FileNotFoundException If wrong files
	 */
	@Test
	void testGetPeopleCoveredByWarrant() throws FileNotFoundException {
		ReportManager manager = new ReportManager("input/people.csv", "input/calls.csv");

		String twoHop = "Phone numbers covered by a 2-hop warrant originating from 358-721-0140950 (Tereasa Kuphal) [\n"
				+ "   1-hop: 663-879-6377778 (Rudolph Buckridge)\n"
				+ "   1-hop: 541-777-4740981 (Brett Mueller)\n"
				+ "   2-hop: 442-000-9865092 (Albertina Braun)\n"
				+ "   2-hop: 134-530-7421728 (Roseanna Herman)\n"
				+ "   2-hop: 903-282-4112077 (Tomas Nguyen)\n"
				+ "]";
		assertEquals(twoHop, manager.getPeopleCoveredByWarrant(2, "358-721-0140950"));
		String fourHop = "Phone numbers covered by a 4-hop warrant originating from 358-721-0140950 (Tereasa Kuphal) [\n"
				+ "   1-hop: 663-879-6377778 (Rudolph Buckridge)\n"
				+ "   1-hop: 541-777-4740981 (Brett Mueller)\n"
				+ "   2-hop: 442-000-9865092 (Albertina Braun)\n"
				+ "   2-hop: 134-530-7421728 (Roseanna Herman)\n"
				+ "   2-hop: 903-282-4112077 (Tomas Nguyen)\n"
				+ "   3-hop: 289-378-3996038 (Daniel Walker)\n"
				+ "   4-hop: 881-633-0099232 (Enoch Quitzon)\n"
				+ "]";
		
		assertEquals(fourHop, manager.getPeopleCoveredByWarrant(4, "358-721-0140950"));
		
		String tenHop = "Phone numbers covered by a 10-hop warrant originating from 358-721-0140950 (Tereasa Kuphal) [\n"
				+ "   1-hop: 663-879-6377778 (Rudolph Buckridge)\n"
				+ "   1-hop: 541-777-4740981 (Brett Mueller)\n"
				+ "   2-hop: 442-000-9865092 (Albertina Braun)\n"
				+ "   2-hop: 134-530-7421728 (Roseanna Herman)\n"
				+ "   2-hop: 903-282-4112077 (Tomas Nguyen)\n"
				+ "   3-hop: 289-378-3996038 (Daniel Walker)\n"
				+ "   4-hop: 881-633-0099232 (Enoch Quitzon)\n"
				+ "]";
		assertEquals(tenHop, manager.getPeopleCoveredByWarrant(10, "358-721-0140950"));


	}
	
	/**
	 * Testing the invalid warrants (empty people or empty calls)
	 * @throws FileNotFoundException If the people or call records are wrong
	 */
	@Test
	void testInvalidGetPeopleCoveredByWarrant() throws FileNotFoundException {
		ReportManager manager = new ReportManager("input/emptyPeople.csv", "input/calls.csv");
		assertEquals("No people information was provided.", manager.getPeopleCoveredByWarrant(4, "358-721-0140950"));
		manager = new ReportManager("input/people.csv", "input/emptyCalls.csv");
		assertEquals("No calls exist in the call logs.", manager.getPeopleCoveredByWarrant(4, "358-721-0140950"));
	    manager = new ReportManager("input/people.csv", "input/calls.csv");
		assertEquals("Number of hops must be greater than 0.", manager.getPeopleCoveredByWarrant(0, "358-721-0140950"));
		assertEquals("Phone number [12345] does not exist.", manager.getPeopleCoveredByWarrant(4, "12345"));

		
	}
	


}
