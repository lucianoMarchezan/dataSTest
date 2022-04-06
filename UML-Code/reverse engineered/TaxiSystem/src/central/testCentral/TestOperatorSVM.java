/*
 * Created on 27.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;

import central.Operator;

public class TestOperatorSVM {
	
	public int points = 20;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);

		op.startVoiceC(1);
		op.sendVoiceC(1);
		op.voiceMessageC(1,"Testvoice");
		op.stopVoiceC(1);
		op.terminateVoiceC(1);
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nOperator:startVoiceC\nCentral:startVoiceTM\nCentral:confirmVoiceO\n" +
				"Operator:confirmVoice\nOperator:sendVoiceC\nCentral:sendVoiceTM\nOperator:voiceMessageC\n" +
				"Central:voiceMessageTM\nOperator:stopVoiceC\nCentral:stopVoiceTM\nOperator:terminateVoiceC\n" +
				"Central:terminateVoiceTM\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
