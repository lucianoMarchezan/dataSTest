/*
 * Created on 31.08.2004
 *  
 */
package comLink;

import central.Central;


public class AlarmThread extends Thread {
	private long positiontime = 5000;
	private Central central;
	private int taxiNumber;
	private boolean stopThread = false;
	private int zoneNumber;
	private int position;

	public AlarmThread(Central central, int taxiNumber, int zoneNumber, int position) {
		this.central = central;
		this.taxiNumber = taxiNumber;
		this.zoneNumber = zoneNumber;
		this.position = position;
	}

	public void run() {
		while (!stopThread) {
			try {
				Thread.sleep(positiontime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!stopThread)
							central.detailedPosition(taxiNumber,zoneNumber,position);
		}
	}

	public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}

}
