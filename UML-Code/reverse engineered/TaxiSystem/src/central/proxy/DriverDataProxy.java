/*
 * Created on 21.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package proxy;

import java.util.Enumeration;
import java.util.Hashtable;
import structures.DriverStruct;

/**
 * @author Uni
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DriverDataProxy {
	
	private Hashtable drivers = null;
	
	public DriverDataProxy(){
		drivers = new Hashtable();
	}
	
	/**
	 * Adds a new Driver to the data structure.
	 * @param driver
	 */
	public void add(DriverStruct driver){
		drivers.put(new Integer(driver.driverNumber),driver);
	}
	
	/**
	 * Deletes a driver from the data structure.
	 * @param driverNumber
	 */
	public void remove(int driverNumber){
		drivers.remove(new Integer(driverNumber));
	}
	
	/**
	 * Updates a driver.
	 * @param driver
	 */
	public void update(DriverStruct driver){
		drivers.put(new Integer(driver.driverNumber),driver);
	}
	/**
	 * Returns the driver to a specific drivernumber; returns null
	 * if the driver was not found.
	 * @param driverNumber
	 * @return
	 */
	public DriverStruct get(int driverNumber){
		return (DriverStruct) drivers.get(new Integer(driverNumber));
	}
	
	/**
	 * Returns the number of drivers stored in the data structure
	 */
	public int size(){
		return drivers.size();
	}
	
	/**
	 * Returns an Enumeration of all driver numbers.
	 */
	public Enumeration numbers(){
		return drivers.keys();
	}
	
	/**
	 * Returns an Enumeration of all TaxiStructs stored in the data structure.
	 * @return
	 */
	public Enumeration objects(){
		return drivers.elements();
	}
	
}
