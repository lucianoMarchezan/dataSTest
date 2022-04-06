/*
 * Created on 14.10.2004
 */
package testTaximoduleDriver;
import taxi.Driver;
import tools.Randomizer;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestComLinkSendVoice2 {
	public static int p = 4;
	public boolean test() {
		Driver d = null;
		try {
			Output.resetresult();
			Randomizer.num = 0;
			d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.loginSignalTM();
			
			d.taxiModule.comLinkC.startVoiceTM(1, 17);
			d.taxiModule.comLinkC.sendVoiceTM(1, 17);
		}
		catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
			&& Output.result.indexOf("Taximodule:loginSignalC\n") != -1
			&& Output.result.indexOf("Taximodule:loginOk\n") != -1
			&& Output.result.indexOf("Taximodule:startVoiceD\n") != -1
			&& Output.result.indexOf("Driver:startVoice\n") != -1
			&& Output.result.indexOf("Driver:terminateVoiceTM\n") != -1
			&& Output.result.indexOf("Taximodule:terminateVoiceC\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}