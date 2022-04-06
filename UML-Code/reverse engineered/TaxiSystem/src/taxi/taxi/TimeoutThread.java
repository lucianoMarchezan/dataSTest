/*
 * Created on 19.08.2004
 */
package taxi;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TimeoutThread extends Thread {
	private long timeout;
	private Taximodule taxiModule;
	private int orderNumber;
	private int operatorNumber;
	private boolean stopThread;

	/**
	 * @param timeout
	 * @param taxiModule
	 * @param orderNumber
	 * @param operatorNumber
	 * @param taxiNumber
	 */
	public TimeoutThread(Taximodule taxiModule, int orderNumber, int operatorNumber) {
		new TimeoutThread(15000, taxiModule, orderNumber, operatorNumber);
	}

	public TimeoutThread(long timeout, Taximodule taxiModule, int orderNumber, int operatorNumber) {
		this.timeout = timeout;
		this.taxiModule = taxiModule;
		this.orderNumber = orderNumber;
		this.operatorNumber = operatorNumber;
		this.stopThread = false;
	}

	public void run() {
		try {
			Thread.sleep(timeout);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!stopThread) {
			taxiModule.acceptOrderMissingC(orderNumber, operatorNumber);
		}
	}

	public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}
}