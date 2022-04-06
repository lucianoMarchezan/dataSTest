/*
 * Created on 27.09.2004
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
public class TestOperatorSTM {
	
	public int points = 3;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		int[] taxi = {1};
		op.textMessageC(taxi,1,"Testmessage");
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\n" +
				"Central:loginSignal\nComLinkTM:loginOk\nCentral:detailedPosition\nOperator:textMessageC\n" +
				"Central:textMessageTM\nCentral:ackMessageO\nOperator:ackMessage\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
