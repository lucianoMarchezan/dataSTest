package testCentral;

import central.Central;
import central.Operator;


public class FTestOperatorLogout {
	public int points = 16;
	
	public boolean test(){
		try{
		Output.resetresult();
		Central cen = new Central();
		Operator op = new Operator(cen);
		op.logoutC();
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}

		if(Output.result.equals("Operator:logoutC\nCentral:logout\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
