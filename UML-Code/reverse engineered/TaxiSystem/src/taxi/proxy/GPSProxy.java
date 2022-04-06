/*
 * Created on 21.09.2004
 *
 */
package proxy;

import java.util.Random;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class GPSProxy {
	
	/**
	 * This method returns the actual zone for the specified taxi.
	 * @param taxiNumber
	 * @return
	 */
	public static int getZoneNumber() {
		Random r = new Random();
			return r.nextInt(23) + 1;
	}
	
	/**
	 * This method returns the actual position in a zone for the specified taxi.
	 * @param taxiNumber
	 * @return
	 */
	public static int getPosition() {
		Random r = new Random();
		return r.nextInt(2500) + 1;		
	}
	
	
	/**
	 * This method returns an estimated time (in minutes) for required use cases.
	 * @param taxiNumber
	 * @return
	 */
	public static int getEstimateTime() {
		Random r = new Random();
		return r.nextInt(45) + 10;	
	}
	
}
