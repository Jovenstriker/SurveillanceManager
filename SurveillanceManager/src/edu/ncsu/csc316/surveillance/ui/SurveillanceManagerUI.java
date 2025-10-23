package edu.ncsu.csc316.surveillance.ui;

import java.io.FileNotFoundException;
import java.util.Scanner;
import edu.ncsu.csc316.surveillance.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.surveillance.data.Person;
import edu.ncsu.csc316.surveillance.manager.ReportManager;
import edu.ncsu.csc316.surveillance.manager.SurveillanceManager;

/**
 * SurveillanceManager UI class for both reports and surveillance
 * @author Henry Wang
 */
public class SurveillanceManagerUI {

	/**
	 * Console input
	 * @param args Input to machine
	 * @throws FileNotFoundException If not valid files
	 */
	public static void main(String[] args) throws FileNotFoundException {
//	    Scanner scanner = new Scanner(System.in);
//	    System.out.println("Type anything to continue, type stop to end application\n");
//	    while (!scanner.nextLine().equals("stop")) {
//		    System.out.println("People file?\n");
//		    //input/people.csv
//		    
//		    String loadPeople = scanner.nextLine();
//		    System.out.println("Call file?\n");
//		    //input/calls.csv
//		    String loadCall = scanner.nextLine();
//		    
//		    ReportManager manager = new ReportManager(loadPeople, loadCall);
//		    SurveillanceManager surveillanceManager = new SurveillanceManager(loadPeople, loadCall);
//
//		    System.out.println("See people? [y/n]\n");
//		    if (scanner.nextLine().equals("y")) {
//		    	Map<String, Person> numberPerson = surveillanceManager.getPeople();
//		    	StringBuilder output = new StringBuilder();
//		    	for (Entry<String, Person> s : numberPerson.entrySet()) {
//		    		output.append(s.getKey()).append(" (").append(s.getValue().getFirst()).append(" ").append(s.getValue().getLast()).append(")\n\n");
//		    	}
//		    	System.out.println(output);
//		    }
//		    
//		    
//		    
//		    System.out.println("Generate report? [y/n]\n");
//		    if (scanner.nextLine().equals("y")) {
//		    	System.out.println(manager.getCallsByPerson());
//		    }
//		    System.out.println("Generate warrant? [y/n]\n");
//		    if (scanner.nextLine().equals("y")) {
//		    	System.out.println("hops");
//		    	int hops = Integer.parseInt(scanner.nextLine());
//		    	System.out.println("number?");
//		    	String number = scanner.nextLine();
//		    	System.out.println(manager.getPeopleCoveredByWarrant(hops, number));
//		    }
//	    }
//	    
//	    scanner.close();
//	   

//		ArrayBasedList<DataStructure> list =  new ArrayBasedList<DataStructure>();
//		list.addLast(DataStructure.UNORDEREDLINKEDMAP);
//		list.addLast(DataStructure.SEARCHTABLE);
//		list.addLast(DataStructure.SKIPLIST);
//		list.addLast(DataStructure.SPLAYTREE);
//		list.addLast(DataStructure.REDBLACKTREE);
//		list.addLast(DataStructure.LINEARPROBINGHASHMAP);
//
//		ReportManager manager = new ReportManager("SurveillanceManager_ExperimentFiles/people_" + inputSize + ".csv", "SurveillanceManager_ExperimentFiles/calls_" + inputSize + ".csv", DataStructure.UNORDEREDLINKEDMAP);

//		for (DataStructure a : list) {
		
		
		int inputSize = 14; //The current size, 2^n, of file
		long start = System.currentTimeMillis();
		
		DataStructure mapType = DataStructure.LINEARPROBINGHASHMAP; //Sample data structure
		ReportManager manager = new ReportManager("/Users/henrywang/Documents/NCSU Files/CSC316/csc316-651-P-009/SurveillanceManager/SurveillanceManager_ExperimentFiles/people_" + inputSize + ".csv", "/Users/henrywang/Documents/NCSU Files/CSC316/csc316-651-P-009/SurveillanceManager/SurveillanceManager_ExperimentFiles/calls_" + inputSize + ".csv", mapType);
		manager.getPeopleCoveredByWarrant(100000, "123-456-7890123");
		
		long end = System.currentTimeMillis();
			
		long duration = end - start;
			
		System.out.println(duration);
		
	}

}
