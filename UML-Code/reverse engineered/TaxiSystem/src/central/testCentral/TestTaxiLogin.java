/*
 * Created on 28.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;
import tools.*;
import central.Operator;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestTaxiLogin {
	
	public int points = 20;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		
		cen.comLinkTM.loginSignalC(1,1);
		cen.comLinkTM.loginSignalC(1,2);
		
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Central:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nCentral:loginSignal\nComLinkTM:loginFailure\n" +
				"Central:detailedPosition\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
