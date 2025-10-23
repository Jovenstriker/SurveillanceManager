package edu.ncsu.csc316.surveillance.manager;

import java.io.FileNotFoundException;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.surveillance.comparator.CallDateComparator;
import edu.ncsu.csc316.surveillance.comparator.PersonComparator;
import edu.ncsu.csc316.surveillance.comparator.StringComparator;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;
import edu.ncsu.csc316.surveillance.dsa.Algorithm;
import edu.ncsu.csc316.surveillance.dsa.DSAFactory;
import edu.ncsu.csc316.surveillance.dsa.DataStructure;

/**
 * Generates reports and warrants
 * @author Henry Wang
 */
public class ReportManager {

	/**
	 * Surveillance manager to get maps of people and calls
	 */
	private SurveillanceManager manager;
	/**
	 * Formatter for month, day, and year
	 */
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
	
	/**
	 * formatter for hour, minute, and second
	 */
	private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm:ss a");

	/**
	 * Constructor for report manager
	 * @param peopleFile File for people
	 * @param callFile File for call
	 * @throws FileNotFoundException If any error with people or call
	 */
	public ReportManager(String peopleFile, String callFile) throws FileNotFoundException {
		this(peopleFile, callFile, DataStructure.SKIPLIST);
	}

	/**
	 * Constructor with selectable map type datastructure
	 * @param peopleFile File for people info
	 * @param callFile File for call records
	 * @param mapType Map type data structure
	 * @throws FileNotFoundException If any error with the two files
	 */
	public ReportManager(String peopleFile, String callFile, DataStructure mapType) throws FileNotFoundException {
		manager = new SurveillanceManager(peopleFile, callFile, mapType);
		DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
		DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
		DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
		DSAFactory.setMapType(mapType);
	
	}

	/**
	 * Gets the calls by people, sorted by phone number
	 * 
	 * Inside, displays chronological calls with date time information and people called
	 * People called are ordered by their phone number
	 * 
	 * None if no calls 
	 * @return Output message containing all call records by people
	 */
	public String getCallsByPerson() {
		
		
		Map<String, Person> phoneMap = manager.getPeople();
		Map<String, List<Call>> map = manager.getCallsByPerson();
		

		if (phoneMap.size() == 0) {
			return "No people information was provided.";
		}
		
		boolean nullCalls = true;
		for (List<Call> calls : manager.getCallsByPerson().values()) {
			if (calls.size() != 0) {
				nullCalls = false;
				break;
			}
		} 
		if (nullCalls) return "No calls exist in the call logs.";
		
		StringBuilder output = new StringBuilder();
		String indent = "   ";

		StringComparator stringComparator = new StringComparator();

		CallDateComparator dateComparator = new CallDateComparator();

		
		Iterator<String> itr = map.iterator();
		Iterator<List<Call>> valItr = map.values().iterator();

		for (int i = 0; i < map.size(); i++) {
			String key = itr.next();
			List<Call> calls = valItr.next();
			Person person = phoneMap.get(key);
			output.append("Calls involving ").append(key).append(" (").append(person.getFirst()).append(" ").append(person.getLast()).append(") [\n");
 			Call[] callsArray = new Call[calls.size()];
			int callIndex = 0;
 			for (Call c : calls) {
				callsArray[callIndex] = c;
				callIndex++;
			}
 			
 			DSAFactory.getComparisonSorter(dateComparator).sort(callsArray);
 			
			for (int x = 0; x < callsArray.length; x++) {
				Call call = callsArray[x];
				
				String date = dateFormat.format(call.getTimestamp());
				String hour = timeFormat.format(call.getTimestamp());


				output.append(indent).append(date).append(" at ").append(hour);
				output.append(" involving ").append(String.valueOf(call.getPhoneNumbers().length - 1)).append(" other number(s):\n");
				
				String[] peopleCalledArray = call.getPhoneNumbers();

				DSAFactory.getComparisonSorter(stringComparator).sort(peopleCalledArray);

				for (int j = 0; j < peopleCalledArray.length; j++) {
					if (!peopleCalledArray[j].equals(key)) {
						String number = peopleCalledArray[j];
						Person personCalled = phoneMap.get(number);
						output.append(indent).append(indent).append(number).append(" (").append(personCalled.getFirst()).append(" ").append(personCalled.getLast()).append(")\n");
					}
				}
				
			}
			
			if (callsArray.length == 0) {
				output.append(indent).append("(none)\n");
			}
			output.append("]\n");
		}

		return output.toString();
		
	}

	/**
	 * Gets people covered by warrant, or hops less than or equal to the hop specified
	 * Ordered first by number of hops, then by person comparator
	 * 
	 * @param hops Hop less than or equal to for warrants
	 * @param originPhoneNumber Original phone number to track for warrant
	 * @return Output message for people covered by warrant
	 */
	public String getPeopleCoveredByWarrant(int hops, String originPhoneNumber) {
		Map<String, Person> phoneMap = manager.getPeople();

		if (phoneMap.size() == 0) {
			return "No people information was provided.";
		}
		boolean nullCalls = true;
		for (Entry<String, List<Call>> entry : manager.getCallsByPerson().entrySet()) {
			if (entry.getValue().size() != 0) {
				nullCalls = false;
				break;
			}
		} 
		if (nullCalls) return "No calls exist in the call logs.";

		Person p = phoneMap.get(originPhoneNumber);
		if (p == null) {
			return "Phone number [" + originPhoneNumber + "] does not exist.";
		}
		
		if (hops < 1) {
			return "Number of hops must be greater than 0.";
		}
		
		Map<String, Integer> peopleByHop = manager.getPeopleByHop(originPhoneNumber);
		PersonComparator personComparator = new PersonComparator();

		int max = 0;
		for (Entry<String, Integer> entry : peopleByHop.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		
		StringBuilder output = new StringBuilder();
		
		output.append("Phone numbers covered by a ").append(hops).append("-hop warrant originating from ").append(originPhoneNumber);
		output.append(" (").append(p.getFirst()).append(" ").append(p.getLast()).append(") [\n");
		
		for (int i = 1; i <= min(hops, max); i++) {
			List<String> peopleHopsOrBelow = DSAFactory.getIndexedList();
			for (String number : peopleByHop) {
				if (peopleByHop.get(number) == i) {
					peopleHopsOrBelow.addLast(number);
				}
			}
			
			Person[] peopleHopsOrBelowArray = new Person[peopleHopsOrBelow.size()];
			
			int index = 0;
			for (String c : peopleHopsOrBelow) {
//				System.out.println(phoneMap.get(c).toString());

				peopleHopsOrBelowArray[index] = phoneMap.get(c);
				
				index++;
			}
			
			DSAFactory.getComparisonSorter(personComparator).sort(peopleHopsOrBelowArray);
			for (int j = 0; j < peopleHopsOrBelowArray.length; j++) {
				Person person = peopleHopsOrBelowArray[j];
				output.append("   ").append(i).append("-hop: ").append(person.getPhoneNumber()).append(" (").append(person.getFirst()).append(" ").append(person.getLast()).append(")\n");
			}
			
		}
		output.append("]");
		
		
		return output.toString();
	}
	
	/**
	 * Private helper method to find min of two numbers
	 * @param a First number
	 * @param b Second number
	 * @return Smaller number
	 */
	private int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}
	

}