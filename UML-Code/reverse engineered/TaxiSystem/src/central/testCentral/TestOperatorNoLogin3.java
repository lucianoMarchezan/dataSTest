package testCentral;

import central.Central;
import central.Operator;

public class TestOperatorNoLogin3 {
	
	public int points = 4;
	public boolean test(){
		try{
		Output.resetresult();
		Central cen = new Central();
		Operator op = new Operator(cen);
		
		op.startVoiceC(1);
		
		}
		catch(Exception e){
			return false;
		}
		if(Output.result.equals("Operator:startVoiceC\nCentral:startVoiceTM\n")){
			System.out.println("Test successful");
			return true;
		}
			System.out.println("Test failed");
			return false;
	}
}
