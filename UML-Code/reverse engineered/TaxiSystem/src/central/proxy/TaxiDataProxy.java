/*
 * Created on 21.09.2004
 */
package proxy;
import java.util.Enumeration;
import java.util.Hashtable;
import structures.TaxiStruct;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TaxiDataProxy {
	private Hashtable allTaxis;

	/**
	 * Initializes the data structure to be used to store taxis.
	 */
	public TaxiDataProxy() {
		allTaxis = new Hashtable();
	}

	/**
	 * Is used to store a taxi inside a data structure.
	 */
	public void add(TaxiStruct taxi) {
		allTaxis.put(new Integer(taxi.taxiNumber), taxi);
	}

	/**
	 * Is used to remove an order from the data structure.
	 */
	public void remove(int taxiNumber) {
		allTaxis.remove(new Integer(taxiNumber));
	}

	/**
	 * Is used to update a taxi inside the data structure.
	 */
	public void update(TaxiStruct taxi) {
		allTaxis.put(new Integer(taxi.taxiNumber), taxi);
	}

	/**
	 * Returns a taxi from the data structure specified by it's taxiNumber; 
	 * returns null if no taxi was found.
	 */
	public TaxiStruct get(int taxiNumber) {
		return (TaxiStruct) allTaxis.get(new Integer(taxiNumber));
	}
	
	/**
	 * Returns an Enumeration of all taxis in the data structure.
	 * @return
	 */
	public Enumeration objects(){
		return allTaxis.elements();
	}
	
	/**
	 * Returns an Enumeration of all taxiNumbers in the data structure.
	 * @return
	 */
	public Enumeration numbers(){
		return allTaxis.keys();
	}
	
	/**
	 * Returns the number of taxis in the data structure.
	 * 
	 * @return
	 */
	public int size() {
		return allTaxis.size();
	}
}