import taxi.Driver;
/*
 * Created on 31.08.2004
 *  
 */
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestWaitThread extends Thread {
	private long timeout = 25000;
	private Driver driver;
	

	public TestWaitThread(Driver driver) {
		this.driver = driver;
	}

	public void run() {
		try {
			Thread.sleep(timeout);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.requestViewDriverTM();
		driver.logoutSignalTM();
	}
}