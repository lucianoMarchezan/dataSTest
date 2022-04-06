import structures.OrderStructT;
import taxi.Driver;
/*
 * Created on 18.08.2004
 *  
 */
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Test {
	public static void main(String[] args) {
		Driver driver = new Driver(1, 666); //taxinr, drivernr
		driver.loginSignalTM();
//		driver.newOrderTM(new OrderStructT(1, 17, 45, "Hans Burschi", "Stinker", 4, 1, false));
//		driver.requestViewDriverTM();
//		driver.requestZoneInfoTM(5);
//		driver.setSoonAvailableTM();
//		driver.startAlarmTM();
		driver.startVoiceTM(34);
		driver.sendVoiceTM(34);
		driver.voiceMessageTM(34, "TestVoice");
		driver.stopVoiceTM(34);
		driver.terminateVoiceTM(34);
		
		new TestWaitThread(driver).start();
			
		
	}
}