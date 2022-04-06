/*
 * Created on 31.08.2004
 *  
 */
package comLink;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class AlarmResetThread extends Thread {
	private long timeout = 15000;
	private ComLinkC comLinkC;
	private int taxiNumber;

	public AlarmResetThread(ComLinkC comLinkC, int taxiNumber) {
		this.comLinkC = comLinkC;
		this.taxiNumber = taxiNumber;
	}

	public void run() {
		try {
			Thread.sleep(timeout);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		comLinkC.resetAlarmTM(taxiNumber);
	}
}