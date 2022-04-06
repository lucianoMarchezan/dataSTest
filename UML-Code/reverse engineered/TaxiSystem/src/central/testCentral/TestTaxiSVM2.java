
package testCentral;

import central.Operator;

public class TestTaxiSVM2 {
	
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
		op.terminateVoiceC(1);
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nCentral:startVoiceO\nOperator:startVoice\nOperator:confirmVoiceC\n" +
				"Central:confirmVoiceTM\nCentral:sendVoiceO\n" +
				"Operator:sendVoice\nCentral:voiceMessageO\nOperator:voiceMessage\nCentral:stopVoiceO\n" +
				"Operator:stopVoice\nOperator:terminateVoiceC\nCentral:terminateVoiceTM\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
