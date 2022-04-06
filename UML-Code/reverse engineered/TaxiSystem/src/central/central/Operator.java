/*
 * Created on 08.08.2004
 *  
 */
package central;
import structures.*;
import tools.Output;

public class Operator {

	private Central central;
	private int operatorNumber = -1; //the operators number
	private boolean voiceAlive = false;// other side is talking
	private boolean voiceConnecting = false;
	private boolean voiceSend = false; // i am talking
	

	/**
	 * Creates a new Operator Object, the argument is an instance of a central Object.
	 * @param central
	 */
	public Operator(Central central) {
		this.central = central;
	}

	/**
	 * Sent when the taxidriver accepted an order
	 * @param orderNumber
	 * @param operatorNumber
	 * @param taxiNumber
	 */
	public void acceptOrder(int orderNumber, int taxiNumber, int zoneNumber, int positionNumber, int estimateTime) {
		central.confirmOrderTM(orderNumber,operatorNumber,taxiNumber);
	}

	/**
	 * A message sent from the operator successfully arrived at the taxi.
	 * @param taxiNumber
	 * @param messageNumber
	 * @param operatorNumber
	 */
	public void ackMessage(int taxiNumber, int messageNumber) {
 		System.out.println("ackMessage:Message "+messageNumber+" arrived at Taxi:"+taxiNumber+" successful.");
		
	}

	/**
	 * A taxi confirmed the voice communication.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void confirmVoice(int taxiNumber) {
		if(voiceConnecting){
			voiceAlive = true;
			voiceSend = true;
			voiceConnecting = false;
			System.out.println("confirmVoice:Voice from Operator "+operatorNumber+" to taxi: "+taxiNumber+" confirmed.");
		}
		else
			System.out.println("confirmVoice: No voice communication started.");
	}

	/**
	 * Sent to confirm an incoming voice communication.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void confirmVoiceC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(!voiceAlive && voiceSend && voiceConnecting){
				voiceAlive = true;
				voiceSend = true;
				voiceConnecting = false;
				central.confirmVoiceTM(taxiNumber,operatorNumber);
			}
			else
				System.out.println("confirmVoiceC: No voice communication started.");
		}
	}

	/**
	 * Sent when the operator wants to login. 
	 * @param operatorNumber
	 * @param password
	 */
	public void loginC(int operatorNumber, String password) {
		if(operatorNumber<1) Output.printError("loginC:operatorNumber invalid");
		else if(password == null) Output.printError("loginC:password invalid");
		else{
			this.operatorNumber = operatorNumber;
			central.login(operatorNumber,this,password);
		}
	}

	/**
	 * The login for the operator failed.
	 * @param operatorNumber
	 */
	public void loginFailure() {
			System.out.println("Login for Operator " +operatorNumber+" failed.");
			this.operatorNumber = -1;
	}

	/**
	 * The login for the operator was successful.
	 * @param operatorNumber
	 */
	public void loginOk() {
			System.out.println("Login for Operator " +operatorNumber+" was ok.");
	}

	/**
	 * Sent when the operator wants to logout.
	 * @param operatorNumber
	 */
	public void logoutC() {
		central.logout(operatorNumber);
	}

	/**
	 * The Logout for the operator was successful.
	 * @param operatorNumber
	 */
	public void logoutOk() {
		this.operatorNumber = -1;
		System.out.println("Logout for Operator " +operatorNumber+" was ok.");	
	}

	/**
	 * The operator should manual dispatch an order.
	 * @param taxiNumber
	 * @param operatorNumber
	 * @param order
	 */
	public void manualDispatchOrder(int taxiNumber, OrderStructC order) {
 		order.manualDispatch = true;
 		TaxiStruct ts = central.getFirstAvailableTaxi();
 		if(ts == null){
 			order.dispatchTime = order.dispatchTime+2;	
 			Output.printError("No taxi available.");
 		}
 		else {
 			order.taxiNumber = ts.taxiNumber;
 			order.dispatchTime = 0;
 			System.out.println("Operator manual dispatches order "+order.orderNumber+" to taxi "+ts.taxiNumber);
 		}
 		central.newOrderTM(order,operatorNumber);
	}
	
	/**
	 * Sent when the operator should confirm an order.
	 * @param orderNumber
	 * @param operatorNumber
	 * @param taxinumber
	 */
	public void newOrder(int orderNumber, int taxinumber) {
		central.confirmOrderTM(orderNumber,operatorNumber,taxinumber);
	}
	
	/**
	 * When the operator has received an order, it is submitted to the system.
	 * @param order	The order from the operator. If the taxinumber is 0 the system should allocate 
	 * the taxinumber, if it is a real taxinumber the operator manual dispatches a taxi.
	 */
	public void newOrderC(OrderStructC order) {
		if(order.taxiNumber<0) Output.printError("newOrderC:taxiNumber invalid");
		else if(order.dispatchTime<0) Output.printError("newOrderC:dispatchTime invalid");
		else if(order.priority<1 || order.priority>10) Output.printError("newOrderC:priority invalid");
		else if(order.orderNumber!=-1 ) Output.printError("newOrderC:orderNumber invalid");
		else{
			central.newOrderTM(order,operatorNumber);
		}
	}

	/**
	 * Sent to reset the alarm in a the taxi.
	 * @param taxiNumber
	 */
	public void resetAlarmC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else
			central.resetAlarmTM(taxiNumber, operatorNumber);
	}
	
	/**
	 * The operator receives this signal when the other side (a taxi) starts the voice communication.
	 * 
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void sendVoice(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(voiceAlive && voiceSend && !voiceConnecting)
				voiceSend = false;
			else
				System.out.println("sendVoice: Error at voice Communication.");
		}
	}

	/**
	 * This signal indicates when the sound transmission should occur, as pressing the send button on a communication radio. 
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void sendVoiceC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(voiceSend  && voiceAlive && !voiceConnecting){
				central.sendVoiceTM(taxiNumber,operatorNumber);
			}	
			else
				System.out.println("Not allowed to speak on channel");
		}
	}

	/**
	 * A taxi starts a voice communication.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void startVoice(int taxiNumber){
			if(!voiceAlive && !voiceSend && ! voiceConnecting){
				voiceConnecting = true;
				voiceSend = true;
				confirmVoiceC(taxiNumber);
			}
			else
				System.out.println("Not allowed to speak on channel");
	}
	
	
	/**
	 * Sent from the operator to establish a radio communication to a taxi. 
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void startVoiceC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(!voiceAlive && ! voiceSend && !voiceConnecting){
				voiceAlive = true;
				voiceConnecting = true;
				central.startVoiceTM(taxiNumber,operatorNumber);
			}
		}
	}


	/**
	 * The operator receives this signal when the other side (a taxi) stops the voice communication.
	 * Reason: see at Central.send_voice
	 * @param taxiNumber
	 */
	public void stopVoice(int taxiNumber) {
		if(voiceAlive && !voiceSend && !voiceConnecting){
			voiceSend = true;
		}
		
	}

	/**
	 * This signal indicates to the voice radio link to stop sending sounds across the link. 
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void stopVoiceC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(voiceSend && voiceAlive && !voiceConnecting){
				central.stopVoiceTM(taxiNumber,operatorNumber);
			}
			else 
				System.out.println("Not allowed to speak on channel");
		}
	}

	/**
	 * Terminates a voice communication.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void terminateVoice(int taxiNumber) {
		if(! voiceConnecting){
			voiceAlive = false;
			voiceSend = false;
			voiceConnecting = false;
			System.out.println("Voice from taxi "+taxiNumber+" to Operator "+operatorNumber+" terminated.");
		}
	}

	/**
	 * The operator sends this signal to terminate the voice communication. This method is also used to cancel a voice communication before it is initiated. 
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void terminateVoiceC(int taxiNumber) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(voiceAlive && voiceSend){
				voiceAlive = false;
				voiceSend = false;
				voiceConnecting = false;
				central.terminateVoiceTM(taxiNumber,operatorNumber);
			}
			else
				System.out.println("Not allowed to speak on channel");
		}
	}

	/**
	 * An operator sends a text message.
	 * @param taxiNumber	the receiving taxi (if 0 then all taxis are receivers else all taxis named in the array are receivers)
	 * @param messageNumber	the message Number
	 * @param textMessage	the text message
	 * @param operatorNumber	the operator who sends the message
	 */
	public void textMessageC(int[] taxiNumber, int messageNumber, String textMessage) {
		if(messageNumber < 1) Output.printError("messageNumber invalid.");
		else {
			boolean variablesok = true;
			for(int i = 0;i<taxiNumber.length;i++)
				if(taxiNumber[i] < 1){
					Output.printError("taxiNumber invalid.");
					variablesok = false;
				}
			if(variablesok==true)
				central.textMessageTM(taxiNumber,messageNumber,textMessage,operatorNumber);
		}
	}

	/**
	 * An operator receives a voicemessage.
	 * @param taxiNumber
	 * @param operatorNumber
	 * @param voicedata
	 */
	public void voiceMessage(int taxiNumber, String voicedata) {
		System.out.println("Taxi "+taxiNumber+ " to Operator "+operatorNumber+":"+voicedata);
	}
	/**
	 * The Operator sends a voice (as String) through the radio channel
	 * @param taxiNumber the receiver of the message
	 * @param operatorNumber the sending operator
	 * @param voicedata	the voice (represented as String)
	 */
	public void voiceMessageC(int taxiNumber, String voicedata) {
		if(taxiNumber < 1) Output.printError("taxiNumber invalid.");
		else{
			if(voiceSend == true && voiceAlive == true)
				central.voiceMessageTM(taxiNumber,operatorNumber,voicedata);
			else
				System.out.println("Not allowed to speak on channel");
		}
	}
}