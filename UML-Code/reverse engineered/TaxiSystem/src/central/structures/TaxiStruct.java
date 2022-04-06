/*
 * Created on 22.08.2004 TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package structures;
/**
 * @author user TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TaxiStruct {
	public int passengers;
	public int size;
	public String properties;
	public int taxiNumber = -1;
	public static final int OFFLINE = 1;
	public static final int AVAILABLE = 2;
	public static final int WAITING_FOR_CUSTOMER = 3;
	public static final int DRIVING_A_CUSTOMER = 4;
	public static final int SOON_AVAILABLE = 5;
	public int driverNumber = -1;
	public long startTime = -1;
	public long stopTime = -1;
	public int status = -1;
	// 1 = offline 2= available 3= waiting for customer 4 = driving a customer 5 = soon available
	public int orderNumber = -1;
	public int zone = -1;
	public int position = -1;
	public boolean meterOn = false;
	public boolean alarm = false;

	public TaxiStruct() {
		status = OFFLINE;
		zone = 1;
		position = 1;
	}

	public TaxiStruct(int taxinr, int passengers, int size, String properties) {
		this.taxiNumber = taxinr;
		this.passengers = passengers;
		this.size = size;
		this.properties = properties;
		status = OFFLINE;
		zone = 1;
		position = 1;
	}

	public String toString() {
		return taxiNumber + "\n" + passengers + "\n" + size + "\n" + properties;
	}
}