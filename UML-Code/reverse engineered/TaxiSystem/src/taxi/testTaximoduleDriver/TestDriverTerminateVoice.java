/*
 * Created on 14.10.2004
 *  
 */
package testTaximoduleDriver;
import taxi.Driver;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestDriverTerminateVoice {
	public static int p = 4;
	public boolean test() {
		Driver d = null;
		try {
			Output.resetresult();
			d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.loginSignalTM();
			d.startVoiceTM(17);
			d.sendVoiceTM(17);
			d.voiceMessageTM(17, "Voice");
			d.stopVoiceTM(17);
			d.terminateVoiceTM(17);
		} catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
                && Output.result.indexOf("Taximodule:loginSignalC\n") != -1
                && Output.result.indexOf("Taximodule:loginOk\n") != -1
				&& Output.result.indexOf("Driver:startVoiceTM\n") != -1
				&& Output.result.indexOf("Taximodule:startVoiceC\n") != -1
				&& Output.result.indexOf("Taximodule:confirmVoice\n") != -1
				&& Output.result.indexOf("Driver:sendVoiceTM\n") != -1
				&& Output.result.indexOf("Taximodule:sendVoiceC\n") != -1
				&& Output.result.indexOf("Driver:voiceMessageTM\n") != -1
				&& Output.result.indexOf("Taximodule:voiceMessageC\n") != -1
				&& Output.result.indexOf("Driver:stopVoiceTM\n") != -1
				&& Output.result.indexOf("Taximodule:stopVoiceC\n") != -1
				&& Output.result.indexOf("Driver:terminateVoiceTM\n") != -1
				&& Output.result.indexOf("Taximodule:terminateVoiceC\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}