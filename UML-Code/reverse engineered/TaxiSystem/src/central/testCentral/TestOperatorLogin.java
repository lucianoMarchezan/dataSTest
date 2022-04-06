/*
 * Created on 19.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;

import central.Central;
import central.Operator;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorLogin {
	
	public int points = 4;
	public boolean test(){
		try{
		Output.resetresult();
		Central cen = new Central();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		}
		catch(Exception e){
			return false;
		}

		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
