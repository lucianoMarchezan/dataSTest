
package structures;

public class OrderStructC {

	public int orderNumber;
	public int time;
	public int address;
	public String name;
	public String misc;
	public int priority;
	public int taxiNumber;
	public boolean manualDispatch;
	public int dispatchTime;
	public int status;
	

	public OrderStructC(int orderNumber, int time, int address, String name, String misc,
			int priority, int taxiNumber, boolean manualDispatch, int dispatchTime) {
		this.orderNumber = orderNumber;
		this.time = time;
		this.address = address;
		this.name = name;
		this.misc = misc;
		this.priority = priority;
		this.taxiNumber = taxiNumber;
		this.manualDispatch = manualDispatch;
		this.dispatchTime = dispatchTime;
		status = 1;
	}
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
		result += "DispatchTime: " + dispatchTime + "\n" + "Status: "+ status + "\n";
		return result;
	}
}