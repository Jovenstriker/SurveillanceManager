package edu.ncsu.csc316.surveillance.manager;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.surveillance.dsa.Algorithm;
import edu.ncsu.csc316.surveillance.dsa.DSAFactory;
import edu.ncsu.csc316.surveillance.dsa.DataStructure;
import edu.ncsu.csc316.surveillance.io.InputReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.surveillance.comparator.StringComparator;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;

/**
 * Surveillance Manager to load in people, calls, and get map to combine information
 * Also calculates hops
 * 
 * @author Henry Wang
 */
public class SurveillanceManager {

	/**
	 * List of people
	 */
	private List<Person> people;
	
	/**
	 * List of calls
	 */
	private List<Call> calls;

	/**
	 * Constructor to load in people
	 * @param peopleFile File for people
	 * @param callFile Call records
	 * @throws FileNotFoundException If any of the files don't work
	 */
    public SurveillanceManager(String peopleFile, String callFile) throws FileNotFoundException {
        this(peopleFile, callFile, DataStructure.SKIPLIST);
    }
    

    /**
     * Constructor to load in people and specify map data structure type
     * @param peopleFile File for people
     * @param callFile Call record
     * @param mapType Adjustable map data structure
     * @throws FileNotFoundException If any of the files don't work
     */
    public SurveillanceManager(String peopleFile, String callFile, DataStructure mapType)
            throws FileNotFoundException {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
        DSAFactory.setMapType(mapType);
        
        people = DSAFactory.getIndexedList();
        calls = DSAFactory.getIndexedList();
        people = InputReader.readPersonData(peopleFile);
        calls = InputReader.readCallData(callFile);
    }

    /**
     * Gets a map of phone number to people
     * @return Map of phone number to people
     */
    public Map<String, Person> getPeople() {
    	Map<String, Person> map = DSAFactory.getMap(null);
    	for (Person p : people) {
    		map.put(p.getPhoneNumber(), p);
    	}
    	return map;
    }


    /**
     * Gets map of phone number to list of calls from that phone number
     * @return map of phone number to list of calls from that phone number
     */
    public Map<String, List<Call>> getCallsByPerson() {
    	
		StringComparator stringComparator = new StringComparator();

    	Map<String, List<Call>> map = DSAFactory.getMap(stringComparator);

    	
    	for (Person p : people) {
    		map.put(p.getPhoneNumber(), DSAFactory.getIndexedList());
    	}
    	if (people.size() > 0) {
	    	for (Call call : calls) {
	    		String[] numbersCalled = call.getPhoneNumbers();
	    		for (int i = 0; i < numbersCalled.length; i++) {
	    			map.get(numbersCalled[i]).addLast(call);
	    		}
	    	}
    	}
    	return map;

   
    }
    

    /**
     * Gets the people by hops (how many phone calls until first
     * @param originPhoneNumber The starting phone number
     * @return A map of phone number and how many hops to starting
     */
    public Map<String, Integer> getPeopleByHop(String originPhoneNumber) {
    	Map<String, List<Call>> map = getCallsByPerson();
    	
    	Map<String, Integer> mapHops = DSAFactory.getMap(null);
    	mapHops.put(originPhoneNumber, 0);
    	
    	Integer hop = 0;
    	
    	List<String> l = DSAFactory.getIndexedList();
    	l.addLast(originPhoneNumber);
    	
    	while (!l.isEmpty()) {
    		hop++;
    		List<String> peopleCalled = DSAFactory.getIndexedList();
    		for (String p : l) {
    			List<Call> peopleCalls = map.get(p);
    			if (peopleCalls != null) {
    				for (Call c : peopleCalls) {
    					String[] phoneCalls = c.getPhoneNumbers();
    					
    					for (int i = 0; i < phoneCalls.length; i++) {
    						String number = phoneCalls[i];
    						if (mapHops.get(number) == null) {
    							mapHops.put(number,  hop);
    							peopleCalled.addLast(number);
    						}
    					}
    				}
    			}
    			
    		}
    		l = peopleCalled;
    	}
    	mapHops.remove(originPhoneNumber);
    	return mapHops;

    }



}