/*
 * Created on 28.09.2004
 */
package testTaximoduleDriver;
import structures.OrderStructT;
import taxi.Driver;
import tools.Randomizer;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestComLinkNewOrder {
	
	public static int p = 15;
	
	public boolean test() {
		Driver d = null;
		Randomizer.num = 1;
		try {
			Output.resetresult();
			d = new Driver(1, 1);
			d.taxiModule.comLinkC.answer = 1;
			d.loginSignalTM();
			d.taxiModule.comLinkC.newOrderTM(new OrderStructT(1, 17, 45,
																"Hans Burschi",
																"Stinker", 4,
																1, false));
		}
		catch (Throwable e) {
			return false;
		}
		System.out.println(Output.result);
		if (Output.result.indexOf("Driver:loginSignalTM\n") != -1
			&& Output.result.indexOf("Taximodule:loginSignalC\n") != -1
			&& Output.result.indexOf("Taximodule:loginOk\n") != -1
			&& Output.result.indexOf("Taximodule:newOrderD\n") != -1
			&& Output.result.indexOf("Driver:newOrder\n") != -1
			&& Output.result.indexOf("Driver:acceptOrderTM\n") != -1
			&& Output.result.indexOf("Taximodule:acceptOrderC\n") != -1) {
			//System.out.println("Test successful");
			return true;
		}		
		//System.out.println("Test failed");
		return false;
	}
}