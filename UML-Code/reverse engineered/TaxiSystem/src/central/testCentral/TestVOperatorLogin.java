package testCentral;

import central.Operator;

public class TestVOperatorLogin {
	
	public int points = 4;
		public boolean test(){
			try{
			Output.resetresult();
			CentralTesting cen = new CentralTesting();
			Operator op = new Operator(cen);
			
			op.loginC(-7,"aaa");
			}
			catch(Exception e){
				return false;
			}
			
			if(Output.result.equals("Operator:loginC\n")){
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

