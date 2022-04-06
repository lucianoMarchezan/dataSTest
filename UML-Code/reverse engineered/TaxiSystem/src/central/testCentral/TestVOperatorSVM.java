package testCentral;

import central.Operator;

public class TestVOperatorSVM {
	
	public int points = 4;
		public boolean test(){
			try{
			Output.resetresult();
			CentralTesting cen = new CentralTesting();
			Operator op = new Operator(cen);
			
			op.loginC(1,"aaa");
			op.startVoiceC(-1);
			}
			catch(Exception e){
				return false;
			}
			
			if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nOperator:startVoiceC\n")){
				System.out.println("Test successful");
				return true;
			}
			else{
				System.out.println("Test failed");
				return false;
			}
		}
}


