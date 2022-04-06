/*
 * Created on 27.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package testCentral;
import tools.*;
import central.Operator;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FTestOperatorSVM3 {
	
	public int points = 20;
	public boolean test(){
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		op.loginC(1,"aaa");
		
		cen.comLinkTM.loginSignalC(1,1);

		op.startVoiceC(1);
		cen.comLinkTM.sendVoiceC(1,1);
		cen.comLinkTM.voiceMessageC(1,1,"Testvoice from Taxi");
		op.voiceMessageC(1,"Testvoice from Operator");
		cen.comLinkTM.stopVoiceC(1,1);
		cen.comLinkTM.terminateVoiceC(1,1);
		}
		catch(Exception e){
			return false;
		}
		
		if(Output.result.equals("Operator:loginC\nCentral:login\nOperator:loginOk\nCentral:loginSignal\n" +
				"ComLinkTM:loginOk\nCentral:detailedPosition\nOperator:startVoiceC\nCentral:startVoiceTM\nCentral:confirmVoiceO\n" +
				"Operator:confirmVoice\nCentral:sendVoiceO\nOperator:sendVoice\nCentral:voiceMessageO\n" +
				"Operator:voiceMessage\nOperator:voiceMessageC\nCentral:stopVoiceO\nOperator:stopVoice\n" +
				"Central:terminateVoiceO\nOperator:terminateVoice\n")){
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
