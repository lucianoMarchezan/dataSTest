package testCentral;

import central.Operator;

public class TestVOperatorSTM2 {
	
	public int points = 2;
		public boolean test(){
			try{
			Output.resetresult();
			CentralTesting cen = new CentralTesting();
			Operator op = new Operator(cen);
			
			op.loginC(1,"aaa");
			int taxis[] = {-1};
			op.textMessageC(taxis,1,"Test");
			}
			catch(Exception e){
				return false;
			}
			
			if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nOperator:textMessageC\n")){
				System.out.println("Test successful");
				return true;
			}
			else{
				System.out.println("Test failed");
				return false;
			}
		}
}


