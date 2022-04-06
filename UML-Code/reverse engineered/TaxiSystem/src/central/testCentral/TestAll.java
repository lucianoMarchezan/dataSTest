/*
 * Created on 01.09.2004
 *
 */
package testCentral;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestAll {
	public static void main(String[] args) {
		int successful = 0;
		int failed = 0;
		int totalpoints = 0;
		final int maxpoints = 373; 
		Hashtable tests = new Hashtable();
		Hashtable points = new Hashtable();
		//-------------OPERATOR Test Cases---------------//
		try{
			TestOperatorLogin tol = new TestOperatorLogin();
			tests.put("TestOperatorLogin",new Boolean(tol.test()));
			points.put("TestOperatorLogin",new Integer(tol.points));
		}
		catch(Error e){
			tests.put("TestOperatorLogin",new Boolean(false));}
		
		try{
			TestOperatorLoginLogout toll = new TestOperatorLoginLogout();
			tests.put("TestOperatorLoginLogout",new Boolean(toll.test()));
			points.put("TestOperatorLoginLogout",new Integer(toll.points));
			
		}
		catch(Error e){
			tests.put("TestOperatorLoginLogout",new Boolean(false));
			}
		
		try{
			FTestOperatorLogout ftol = new FTestOperatorLogout();
			tests.put("FTestOperatorLogout",new Boolean(ftol.test()));
			points.put("FTestOperatorLogout",new Integer(ftol.points));
		}
		catch(Error e){
			tests.put("FTestOperatorLogout",new Boolean(false));}
		
		try{
			TestOperatorSO toso = new TestOperatorSO();
			tests.put("TestOperatorSubmitOrder",new Boolean(toso.test()));
			points.put("TestOperatorSubmitOrder",new Integer(toso.points));
		}
		catch(Error e){
			tests.put("TestOperatorSubmitOrder",new Boolean(false));}
		
		try{
			TestOperatorSO2 toso2 = new TestOperatorSO2();
			tests.put("TestOperatorSubmitOrder2",new Boolean(toso2.test()));
			points.put("TestOperatorSubmitOrder2",new Integer(toso2.points));
		}
		catch(Error e){
			tests.put("TestOperatorSubmitOrder2",new Boolean(false));}
		
		try{
			TestOperatorSO3 toso3 = new TestOperatorSO3();
			tests.put("TestOperatorSubmitOrder3",new Boolean(toso3.test()));
			points.put("TestOperatorSubmitOrder3",new Integer(toso3.points));
		}
		catch(Error e){
			tests.put("TestOperatorSubmitOrder3",new Boolean(false));}
		
		try{
			TestOperatorSO4 toso4 = new TestOperatorSO4();
			tests.put("TestOperatorSubmitOrder4",new Boolean(toso4.test()));
			points.put("TestOperatorSubmitOrder4",new Integer(toso4.points));
		}
		catch(Error e){
			tests.put("TestOperatorSubmitOrder4",new Boolean(false));}
		
		try{
			TestOperatorSO5 toso5 = new TestOperatorSO5();
			tests.put("TestOperatorSubmitOrder5",new Boolean(toso5.test()));
			points.put("TestOperatorSubmitOrder5",new Integer(toso5.points));
		}
		catch(Error e){
			tests.put("TestOperatorSubmitOrder5",new Boolean(false));}
		
		try{
			TestOperatorSTM tostm = new TestOperatorSTM();
			tests.put("TestOperatorSendTextMessage",new Boolean(tostm.test()));
			points.put("TestOperatorSendTextMessage",new Integer(tostm.points));
		}
		catch(Error e){
			tests.put("TestOperatorSendTextMessage",new Boolean(false));}
		
		try{
			TestOperatorSTM2 tostm2 = new TestOperatorSTM2();
			tests.put("TestOperatorSendTextMessage2",new Boolean(tostm2.test()));
			points.put("TestOperatorSendTextMessage2",new Integer(tostm2.points));
		}
		catch(Error e){
			tests.put("TestOperatorSendTextMessage2",new Boolean(false));}
		
		/*try{
			TestOperatorSTM3 tostm3 = new TestOperatorSTM3();
			tests.put("TestOperatorSendTextMessage3",new Boolean(tostm3.test()));
		}
		catch(Error e){
			tests.put("TestOperatorSendTextMessage3",new Boolean(false));}
		*/
		try{
			TestOperatorSVM tosvm = new TestOperatorSVM();
			tests.put("TestOperatorSendVoiceMessage",new Boolean(tosvm.test()));
			points.put("TestOperatorSendVoiceMessage",new Integer(tosvm.points));
		}
		catch(Error e){
			tests.put("TestOperatorSendVoiceMessage",new Boolean(false));}
		
		try{
			TestOperatorSVM2 tosvm2 = new TestOperatorSVM2();
			tests.put("TestOperatorSendVoiceMessage2",new Boolean(tosvm2.test()));
			points.put("TestOperatorSendVoiceMessage2",new Integer(tosvm2.points));
		}
		catch(Error e){
			tests.put("TestOperatorSendVoiceMessage2",new Boolean(false));}
		
		try{
			FTestOperatorSVM3 ftosvm3 = new FTestOperatorSVM3();
			tests.put("FTestOperatorSendVoiceMessage3",new Boolean(ftosvm3.test()));
			points.put("FTestOperatorSendVoiceMessage3",new Integer(ftosvm3.points));
		}
		catch(Error e){
			tests.put("FTestOperatorSendVoiceMessage3",new Boolean(false));}
		
		try{
			TestOperatorNoLogin tonl = new TestOperatorNoLogin();
			tests.put("TestOperatorNoLogin",new Boolean(tonl.test()));
			points.put("TestOperatorNoLogin",new Integer(tonl.points));
		}
		catch(Error e){
			tests.put("TestOperatorNoLogin",new Boolean(false));}
		
		try{
			TestOperatorNoLogin2 tonl2 = new TestOperatorNoLogin2();
			tests.put("TestOperatorNoLogin2",new Boolean(tonl2.test()));
			points.put("TestOperatorNoLogin2",new Integer(tonl2.points));
		}
		catch(Error e){
			tests.put("TestOperatorNoLogin2",new Boolean(false));}
		
		try{
			TestOperatorNoLogin3 tonl3 = new TestOperatorNoLogin3();
			tests.put("TestOperatorNoLogin3",new Boolean(tonl3.test()));
			points.put("TestOperatorNoLogin3",new Integer(tonl3.points));
		}
		catch(Error e){
			tests.put("TestOperatorNoLogin3",new Boolean(false));}
		
		//------------TAXI Test Cases---------------//
		
		try{
			TestTaxiLogin ttl = new TestTaxiLogin();
			tests.put("TestTaxiLogin",new Boolean(ttl.test()));
			points.put("TestTaxiLogin",new Integer(ttl.points));
		}
		catch(Error e){
			tests.put("TestTaxiLogin",new Boolean(false));}
		
		try{
			TestTaxiLogout ttlo = new TestTaxiLogout();
			tests.put("TestTaxiLogout",new Boolean(ttlo.test()));
			points.put("TestTaxiLogout",new Integer(ttlo.points));
		}
		catch(Error e){
			tests.put("TestTaxiLogout",new Boolean(false));}
		
		try{
			TestTaxiSVM ttsvm = new TestTaxiSVM();
			tests.put("TestTaxiSendVoiceMessage",new Boolean(ttsvm.test()));
			points.put("TestTaxiSendVoiceMessage",new Integer(ttsvm.points));
		}
		catch(Error e){
			tests.put("TestTaxiSendVoiceMessage",new Boolean(false));}
		
		try{
			TestTaxiSVM2 ttsvm2 = new TestTaxiSVM2();
			tests.put("TestTaxiSendVoiceMessage2",new Boolean(ttsvm2.test()));
			points.put("TestTaxiSendVoiceMessage2",new Integer(ttsvm2.points));
		}
		catch(Error e){
			tests.put("TestTaxiSendVoiceMessage2",new Boolean(false));}
		
		try{
			TestTaxiState tts = new TestTaxiState();
			tests.put("TestTaxiState",new Boolean(tts.test()));
			points.put("TestTaxiState",new Integer(tts.points));
		}
		catch(Error e){
			tests.put("TestTaxiState",new Boolean(false));}
		
		try{
			TestTaxiState2 tts2 = new TestTaxiState2();
			tests.put("TestTaxiState2",new Boolean(tts2.test()));
			points.put("TestTaxiState2",new Integer(tts2.points));
		}
		catch(Error e){
			tests.put("TestTaxiState2",new Boolean(false));}
		
		try{
			TestTaxiState3 tts3 = new TestTaxiState3();
			tests.put("TestTaxiState3",new Boolean(tts3.test()));
			points.put("TestTaxiState3",new Integer(tts3.points));
		}
		catch(Error e){
			tests.put("TestTaxiState3",new Boolean(false));}
		
		try{
			TestTaxiSO ttso = new TestTaxiSO();
			tests.put("TestTaxiSubmitOrder",new Boolean(ttso.test()));
			points.put("TestTaxiSubmitOrder",new Integer(ttso.points));
		}
		catch(Error e){
			tests.put("TestTaxiSubmitOrder",new Boolean(false));}
		
		try{
			FTestTaxiLogout fttlo = new FTestTaxiLogout();
			tests.put("FTestTaxiLogout",new Boolean(fttlo.test()));
			points.put("FTestTaxiLogout",new Integer(fttlo.points));
		}
		catch(Error e){
			tests.put("FTestTaxiLogout",new Boolean(false));
		}
		
		// ------------ validate parameters Tests ------------------//
		try{
			TestVOperatorLogin tvo = new TestVOperatorLogin();
			tests.put("TestValidateOperator",new Boolean(tvo.test()));
			points.put("TestValidateOperator",new Integer(tvo.points));
		}
		catch(Error e){
			tests.put("TestValidateOperator",new Boolean(false));}
		
		try{
			TestVOperatorNewOrder tvono = new TestVOperatorNewOrder();
			tests.put("TestValidateOperatorNewOrder",new Boolean(tvono.test()));
			points.put("TestValidateOperatorNewOrder",new Integer(tvono.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorNewOrder",new Boolean(false));}
		
		try{
			TestVOperatorNewOrder2 tvono2 = new TestVOperatorNewOrder2();
			tests.put("TestValidateOperatorNewOrder2",new Boolean(tvono2.test()));
			points.put("TestValidateOperatorNewOrder2",new Integer(tvono2.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorNewOrder2",new Boolean(false));}
		
		try{
			TestVOperatorNewOrder3 tvono3 = new TestVOperatorNewOrder3();
			tests.put("TestValidateOperatorNewOrder3",new Boolean(tvono3.test()));
			points.put("TestValidateOperatorNewOrder3",new Integer(tvono3.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorNewOrder3",new Boolean(false));}
		
		try{
			TestVOperatorNewOrder4 tvono4 = new TestVOperatorNewOrder4();
			tests.put("TestValidateOperatorNewOrder4",new Boolean(tvono4.test()));
			points.put("TestValidateOperatorNewOrder4",new Integer(tvono4.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorNewOrder4",new Boolean(false));}
		
		try{
			TestVOperatorNewOrder5 tvono5 = new TestVOperatorNewOrder5();
			tests.put("TestValidateOperatorNewOrder5",new Boolean(tvono5.test()));
			points.put("TestValidateOperatorNewOrder5",new Integer(tvono5.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorNewOrder5",new Boolean(false));}
		
		try{
			TestVOperatorSTM tvstm = new TestVOperatorSTM();
			tests.put("TestValidateOperatorSendTextMessage",new Boolean(tvstm.test()));
			points.put("TestValidateOperatorSendTextMessage",new Integer(tvstm.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorSendTextMessage",new Boolean(false));}
		
		try{
			TestVOperatorSTM2 tvstm2 = new TestVOperatorSTM2();
			tests.put("TestValidateOperatorSendTextMessage2",new Boolean(tvstm2.test()));
			points.put("TestValidateOperatorSendTextMessage2",new Integer(tvstm2.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorSendTextMessage2",new Boolean(false));}
		
		try{
			TestVOperatorSVM tvsvm = new TestVOperatorSVM();
			tests.put("TestValidateOperatorSendVoiceMessage",new Boolean(tvsvm.test()));
			points.put("TestValidateOperatorSendVoiceMessage",new Integer(tvsvm.points));
		}
		catch(Error e){
			tests.put("TestValidateOperatorSendVoiceMessage",new Boolean(false));}
		
		//all test done
		Enumeration keys = tests.keys();
		String s = null;
		String result = new String();
		while(keys.hasMoreElements()){
			s = (String)keys.nextElement();
			if(tests.get(s).equals(new Boolean(true))){
				totalpoints += ((Integer)points.get(s)).intValue();
				successful++;
				result += s + " succesful\n";
			}
			else {
				failed++;
				result += s + " failed\n";
			}
		}
		result +="\n--------------------------------\n"+successful+" Tests ok\n"+failed+" Tests failed";
		result +="\n"+totalpoints+"/"+maxpoints +" Points";
		
		System.out.println(result);
		//		write results to file
		File out = new File("results.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(out));
			bw.write(result);
			bw.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
