/*
 * Created on 01.09.2004
 */
package testTaximoduleDriver;
import taxi.Driver;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestDriverLogin2 {
	public static int p = 10;
	public boolean test() {
		try {
			Output.resetresult();
			Driver d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 0;
			d.loginSignalTM();
		}
		catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
			&& Output.result.indexOf("Taximodule:loginSignalC\n") != -1
			&& Output.result.indexOf("Taximodule:loginFailure\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}
		//System.out.println("Test failed");
		return false;
	}
}