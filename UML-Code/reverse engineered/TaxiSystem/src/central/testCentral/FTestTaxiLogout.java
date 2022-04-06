
package testCentral;
import structures.OrderStructC;
import tools.*;
import central.Operator;

public class FTestTaxiLogout {
	
	public int points = 8;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
	
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,1,false,0);
		cen.comLinkTM.newOrderC(order);
		cen.comLinkTM.logoutSignalC(1,1);
		
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nCentral:newOrderO\nOperator:newOrder\n" +
				"Central:confirmOrderTM\nCentral:logoutSignal\nComLinkTM:logoutFailure\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
