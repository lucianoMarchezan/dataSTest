/*
 * Created on 26.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;

import structures.OrderStructC;
import tools.*;
import central.Operator;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorSO {
	
	public int points = 12;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		cen.sendSignal(5);
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		
		OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,0,false,0);
		op.newOrderC(order);
		}
		catch(Throwable e){
			return false;
		}

		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nOperator:newOrderC\nCentral:newOrderTM\nComLinkTM:newOrder:1\nCentral:acceptOrderO\n" +
				"Operator:acceptOrder\nCentral:confirmOrderTM\nCentral:meterOn\nCentral:detailedPosition\n" +
				"Central:driverSoonAvailable\nCentral:meterOff\nCentral:requestPrice\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
