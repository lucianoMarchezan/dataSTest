/*
 * Created on 25.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;

import tools.*;
import central.*;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorLoginLogout {
	
	public int points = 8;
	public boolean test(){
		try{
			Output.resetresult();
			Central cen = new Central();
			Operator op = new Operator(cen);
			op.loginC(1,"aaa");
			op.logoutC();
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nOperator:logoutC" +
				"\nCentral:logout\nOperator:logoutOk\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
