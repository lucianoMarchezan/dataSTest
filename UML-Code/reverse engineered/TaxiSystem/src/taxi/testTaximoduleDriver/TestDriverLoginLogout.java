/*
 * Created on 01.09.2004
 */
package testTaximoduleDriver;
import taxi.Driver;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestDriverLoginLogout {
	public boolean test() {
		try {
			Output.resetresult();
			Driver d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.loginSignalTM();
			d.logoutSignalTM();
		}
		catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
			&& Output.result.indexOf("Taximodule:loginSignalC\n") != -1
			&& Output.result.indexOf("Taximodule:loginOk\n") != -1
			&& Output.result.indexOf("Driver:logoutSignalTM\n") != -1
			&& Output.result.indexOf("Taximodule:logoutSignalC\n") != -1
			&& Output.result.indexOf("Taximodule:logoutOk\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}