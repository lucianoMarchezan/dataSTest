/*
 * Created on 28.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;
import structures.OrderStructC;
import structures.TaxiStruct;
import central.Operator;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestTaxiState2 {
	
	public int points = 20;
	public boolean test(){
		TaxiStruct ts;
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,1,false,0);
		cen.comLinkTM.newOrderC(order);
		ts = cen.tdp.get(1);
		
		}
		catch(Exception e){
			return false;
		}
		
		if((Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\n" +
				"Central:newOrderO\nOperator:newOrder\nCentral:confirmOrderTM\n"))&&
				(ts.status==TaxiStruct.DRIVING_A_CUSTOMER)){
			
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
