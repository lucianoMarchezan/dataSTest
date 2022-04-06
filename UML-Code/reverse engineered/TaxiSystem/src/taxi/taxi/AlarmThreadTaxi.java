/*
 * Created on 31.08.2004
 *  
 */
package taxi;

import proxy.GPSProxy;

public class AlarmThreadTaxi extends Thread {
	private long timeout = 5000; //should be 30000
	private Taximodule taxiModule;	
	private boolean stopThread = false;

	public AlarmThreadTaxi(Taximodule taxiModule) {
		this.taxiModule = taxiModule;
		
	}

	public void run() {
		while (!stopThread) {
			try {
				Thread.sleep(timeout);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!stopThread)
							taxiModule.detailedPositionC(GPSProxy.getZoneNumber(),
															GPSProxy.getPosition());
		}
	}

	public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}
}