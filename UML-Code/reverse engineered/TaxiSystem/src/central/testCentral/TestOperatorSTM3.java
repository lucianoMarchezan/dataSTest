package testCentral;
import central.Operator;


public class TestOperatorSTM3 {
	
	public int points = 3;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);
		cen.comLinkTM.loginSignalC(2,2);
		
		int[] taxi = {0};
		op.textMessageC(taxi,1,"Testmessage");
		}
		catch(Exception e){
			return false;
		}
		int index = 0;
		if((index = Output.result.indexOf("Central:ackMessageO"))!=-1){
			if((index = Output.result.indexOf("Central:ackMessageO",index))!=-1){
				System.out.println("Test successful");
				return true;
			}
		}
		System.out.println("Test failed");
			return false;
	}
}
