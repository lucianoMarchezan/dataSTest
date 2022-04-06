package testCentral;

import structures.OrderStructC;
import central.Operator;

public class TestVOperatorNewOrder5 {
	
	public int points = 1;
		public boolean test(){
			try{
			Output.resetresult();
			CentralTesting cen = new CentralTesting();
			Operator op = new Operator(cen);
			
			op.loginC(1,"aaa");
			OrderStructC order = new OrderStructC(-1,10,1,"Peter","Pete",5,1,false,-5);
			op.newOrderC(order);
			}
			catch(Exception e){
				return false;
			}
			
			if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nOperator:newOrderC\n")){
				if(tools.Output.error!="")
				{
					System.out.println("Test successful");
					return true;
				}
			}
				System.out.println("Test failed");
				return false;
			
		}
}


