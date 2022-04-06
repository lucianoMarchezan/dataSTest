/*
 * Created on 28.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;
import tools.*;
import central.Central;
import central.Operator;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorNoLogin2 {
	
	public int points = 4;
	public boolean test(){
		try{
		Output.resetresult();
		Central cen = new Central();
		Operator op = new Operator(cen);
			
		int[] taxis = {1,2};
		op.textMessageC(taxis,1,"testmessage");
		
		}
		catch(Exception e){
			return false;
		}
		if(Output.result.equals("Operator:textMessageC\nCentral:textMessageTM\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
