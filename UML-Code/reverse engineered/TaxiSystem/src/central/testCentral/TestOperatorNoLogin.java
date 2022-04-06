/*
 * Created on 27.09.2004
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
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestOperatorNoLogin {
	
	public int points = 4;
	public boolean test(){
		try{
		Output.resetresult();
		Central cen = new Central();
		Operator op = new Operator(cen);
		
		OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,0,false,0);
		op.newOrderC(order);
		
		}
		catch(Exception e){
			return false;
		}
		if(Output.result.equals("Operator:newOrderC\nCentral:newOrderTM\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
