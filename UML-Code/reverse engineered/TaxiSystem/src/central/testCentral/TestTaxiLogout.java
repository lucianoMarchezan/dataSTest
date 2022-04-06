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
public class TestTaxiLogout {
	
	public int points = 16;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
	
		cen.comLinkTM.loginSignalC(1,1);
		cen.comLinkTM.logoutSignalC(1,1);
		
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Central:loginSignal\nComLinkTM:loginOk\nCentral:detailedPosition\n" +
				"Central:logoutSignal\nComLinkTM:logoutOk\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
