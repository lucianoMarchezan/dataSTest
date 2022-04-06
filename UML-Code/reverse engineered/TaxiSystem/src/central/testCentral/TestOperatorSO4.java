
package testCentral;

import structures.OrderStructC;
import central.Operator;


public class TestOperatorSO4 {
	
	public int points = 12;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		cen.sendSignal(6);
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		
		OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,0,false,0);
		op.newOrderC(order);
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nOperator:newOrderC\nCentral:newOrderTM\n" +
				"ComLinkTM:newOrder:1\nCentral:rejectOrder\n" +
				"Operator:manualDispatchOrder\nCentral:newOrderTM\nComLinkTM:newOrder:1\nCentral:acceptOrderO\n" +
				"Operator:acceptOrder\nCentral:confirmOrderTM\nCentral:meterOn\nCentral:detailedPosition\n" +
				"Central:driverSoonAvailable\nCentral:meterOff\nCentral:requestPrice\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
