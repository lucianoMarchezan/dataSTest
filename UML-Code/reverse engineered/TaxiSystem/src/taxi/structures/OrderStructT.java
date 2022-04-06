/*
 * Created on 08.08.2004
 *  
 */
package structures;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class OrderStructT {
	public int address;
	public boolean manualDispatch;
	public String misc;
	public String name;	
	public int orderNumber;
	public int priority;
	public int taxiNumber;
	public int time;

	/**
	 * @param orderNumber
	 * @param time
	 * @param address
	 * @param name
	 * @param misc
	 * @param priority
	 * @param taxiNumber
	 * @param manualDispatch
	 */
	public OrderStructT(int orderNumber, int time, int address, String name, String misc,
			int priority, int taxiNumber, boolean manualDispatch) {
		this.orderNumber = orderNumber;
		this.time = time;
		this.address = address;
		this.name = name;
		this.misc = misc;
		this.priority = priority;
		this.taxiNumber = taxiNumber;
		this.manualDispatch = manualDispatch;
	}


	/*
	public OrderStructT convertOrder() {
		return new OrderStructT(orderNumber, time, address, name, misc, priority, taxiNumber,
								manualDispatch);
	}

	
	public int getAddress() {
		return address;
	}

	
	public String getMisc() {
		return misc;
	}

	
	public String getName() {
		return name;
	}

	
	public int getOrderNumber() {
		return orderNumber;
	}

	
	public int getPriority() {
		return priority;
	}

	
	public int getTaxiNumber() {
		return taxiNumber;
	}

	public int getTime() {
		return time;
	}


	public boolean isManualDispatch() {
		return manualDispatch;
	}

	
	public void setManualDispatch(boolean manualDispatch) {
		this.manualDispatch = manualDispatch;
	}

	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	
	public void setTaxiNumber(int taxiNumber) {
		this.taxiNumber = taxiNumber;
	}

	
	public void setTime(int time) {
		this.time = time;
	}
    */
	
	public String toString() {
		String result = new String("");
		result = "OrderNumber: " + orderNumber + "\n";
		result += "Time: " + time + "\n";
		result += "Address: " + address + "\n";
		result += "Name: " + name + "\n";
		if (!misc.equals(new String(""))) result += "Misc: " + misc + "\n";
		result += "Priority: " + priority + "\n";
		result += "TaxiNumber: " + taxiNumber + "\n";
		if (manualDispatch == true)
			result += "ManualDispatch: Yes" + "\n";
		else
			result += "ManualDispatch: No" + "\n";
		return result;
	}
}