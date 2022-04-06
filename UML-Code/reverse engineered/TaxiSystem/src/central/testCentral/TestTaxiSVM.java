
package testCentral;
import central.Operator;

public class TestTaxiSVM {
	
	public int points = 30;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);

		cen.comLinkTM.startVoiceC(1,1);
		cen.comLinkTM.sendVoiceC(1,1);
		cen.comLinkTM.voiceMessageC(1,1,"Testvoice from Taxi");
		cen.comLinkTM.stopVoiceC(1,1);
		cen.comLinkTM.terminateVoiceC(1,1);
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nCentral:startVoiceO\nOperator:startVoice\n" +
				"Operator:confirmVoiceC\nCentral:confirmVoiceTM\nCentral:sendVoiceO\nOperator:sendVoice\nCentral:voiceMessageO\n" +
				"Operator:voiceMessage\nCentral:stopVoiceO\nOperator:stopVoice\nCentral:terminateVoiceO" +
				"\nOperator:terminateVoice\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
