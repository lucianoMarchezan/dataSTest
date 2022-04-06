/*
 * Created on 05.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;

import structures.OrderStructC;
import tools.*;
import central.Central;
import central.Operator;

/**
 * @author Uni
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorNewOrder {
	
	public int points = 12;
	public boolean test(){
		try{
			Output.resetresult();
			Central cen = new Central();
			Operator op = new Operator(cen);
			op.loginC(1,"aaa");
			OrderStructC o = null;
			op.newOrderC(o);
		}
		catch(Exception e){
			return false;
		}
		System.out.println(Output.result);
		if(Output.result.equals("Driver:loginSignal\nTaxiModule:loginSignalC\nTaxiModule:loginOkD\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
