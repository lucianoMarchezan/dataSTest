/*
 * Created on 08.08.2004
 *  
 */
package structures;
import structures.OrderStructT;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class OrderStructC extends OrderStructT {
	/*
	 * The address is represented by an integer because there exists no real map for navigation. All
	 * times are represented by one integer which is the number of seconds since 01-01-2000
	 * 00:00:00.
	 */
	private int dispatchTime;
	private int status;

	/**
	 * @param orderNumber
	 * @param time
	 * @param address
	 * @param name
	 * @param misc
	 * @param priority
	 * @param taxiNumber
	 * @param manualDispatch
	 * @param dispatchTime
	 * @parm status
	 */
	public OrderStructC(int orderNumber, int time, int address, String name, String misc,
			int priority, int taxiNumber, boolean manualDispatch, int dispatchTime) {
		super(orderNumber, time, address, name, misc, priority, taxiNumber, manualDispatch);
		this.dispatchTime = dispatchTime;
		status = 1;
	}

	public String toString() {
		String result = new String("");
		result = super.toString();
		result += "DispatchTime: " + dispatchTime + "\n" + "Status: "+ status + "\n";
		return result;
	}


	public int getDispatchTime() {
		return dispatchTime;
	}


	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}