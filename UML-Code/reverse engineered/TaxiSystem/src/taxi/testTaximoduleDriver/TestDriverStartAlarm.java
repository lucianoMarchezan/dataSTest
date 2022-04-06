/*
 * Created on 14.10.2004
 */
package testTaximoduleDriver;
import taxi.Driver;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestDriverStartAlarm {
	public static int p = 30;
	public boolean test() {
		Driver d = null;
		try {
			Output.resetresult();
			d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.startAlarmTM();
			d.taxiModule.comLinkC.resetAlarmTM(d.taxiNumber);
		}
		catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:startAlarmTM\n") != -1
			&& Output.result.indexOf("Taximodule:startAlarmC\n") != -1
			/* && Output.result.indexOf("Taximodule:detailedPositionC\n") != -1 */
			&& Output.result.indexOf("Driver:startVoiceTM\n") != -1
			&& Output.result.indexOf("Taximodule:startVoiceC\n") != -1
			&& Output.result.indexOf("Taximodule:confirmVoice\n") != -1
			&& Output.result.indexOf("Taximodule:resetAlarm\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}