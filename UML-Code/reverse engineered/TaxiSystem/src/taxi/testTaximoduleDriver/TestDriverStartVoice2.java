/*
 * Created on 14.10.2004
 *  
 */
package testTaximoduleDriver;
import taxi.Driver;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestDriverStartVoice2 {
	public static int p = 4;
	public boolean test() {
		Driver d = null;
		try {
			Output.resetresult();
			d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.loginSignalTM();
			d.taxiModule.comLinkC.answer = 0;
			d.startVoiceTM(17);
		} catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
                && Output.result.indexOf("Taximodule:loginSignalC\n") != -1
                && Output.result.indexOf("Taximodule:loginOk\n") != -1
				&& Output.result.indexOf("Driver:startVoiceTM\n") != -1
				&& Output.result.indexOf("Taximodule:startVoiceC\n") != -1
				&& Output.result.indexOf("Taximodule:terminateVoice\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}