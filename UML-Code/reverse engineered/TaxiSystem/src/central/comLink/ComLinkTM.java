package comLink;
import structures.*;
import central.Central;

import java.util.*;

import proxy.GPSProxy;

public class ComLinkTM {
	private OrderStructC actualorder = null;
	private Hashtable actualorders = null;
	private AlarmThread al = null;
	private Central central;
	
	private boolean isalive = false;// other side is talking
	private boolean sendvoice = true; // i am talking
	private int signals;
	private boolean onlyaccept = true;

	private boolean stop = false; //stops the thread
	private Thread t;
	private int voiceoperator = -1;
	
	/**
	 * Creates a new ComLinkTM Object
	 * @param central
	 * @param signals use the signal flag to set what signals the ComlInk should send to you central:
	 * <ol>
	 * <li> no signals
	 * <li> a taxidriver logs ins and out
	 * <li> a taxi requests a new order and drives it (meterOff,...)
	 * <li> a taxi requests informations about a driver(driverNumber 1) and about a zone (zoneNumber 1)
	 * <li> a taxi starts the alarm and handels a full voice communication (send voice, voicemessage,..)
	 * </ol>
	 */
	public ComLinkTM(Central central, int signals) {
		this.central = central;
		this.signals = signals;
		actualorders = new Hashtable();
	}
	
	public void cancelOrderC(int taxiNumber, int orderNumber) {
		central.cancelOrder(taxiNumber,orderNumber);
		central.driverAvailable(taxiNumber);
	}

	/**
	 * An operator confirmed an order.
	 * @param orderNumber
	 * @param operatorNumber
	 * @param taxiNumber
	 */
	public void confirmOrder(int orderNumber, int operatorNumber, int taxiNumber) {
		System.out.println("ComLinkTM:Order "+orderNumber+" from taxi "+taxiNumber+" has been confirmed by Operator "+operatorNumber);
		OrderStructC o = (OrderStructC) actualorders.get(new Integer(taxiNumber));
		o.orderNumber = orderNumber;
		actualorders.put(new Integer(taxiNumber),o);

	}

	/**
	 *  An operator confirmed the voice communication to a taxi
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void confirmVoice(int taxiNumber, int operatorNumber) {
		System.out.println("ComLinkTM:Operator "+operatorNumber+" confirmed voice to taxi "+taxiNumber+".");
	}

	/**
	 * A taxi confirmes a voice communication.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void confirmVoiceC(int taxiNumber, int operatorNumber) {
		central.confirmVoiceO(taxiNumber,operatorNumber);
	}
		
	/**
	 * @param taxiNumber
	 */
	public void driverSoonAvailableC(int taxiNumber) {
		central.driverSoonAvailable(taxiNumber);
	}

	/**
	 * The login for a taxi failed.
	 * @param taxiNumber
	 */
	public void loginFailure(int taxiNumber) {
		testCentral.Output.print("ComLinkTM:loginFailure");
		System.out.println("ComLinkTM: Login for Taxi "+taxiNumber+" : failure");
	}
	
	/**
	 * The login for a taxi was ok.
	 * @param taxiNumber
	 */
	public void loginOk(int taxiNumber) {
		testCentral.Output.print("ComLinkTM:loginOk");
		System.out.println("ComLinkTM: Login for Taxi "+taxiNumber+" : ok");
	}
	
	public void loginSignalC(int taxiNumber, int driverNumber){
		central.loginSignal(taxiNumber,driverNumber);
		central.detailedPosition(taxiNumber,1,1);
	}

	/**
	 * The logout for a taxi failed.
	 * @param taxiNumber
	 */
	public void logoutFailure(int taxiNumber) {
		testCentral.Output.print("ComLinkTM:logoutFailure");
		System.out.println("ComLinkTM: Logout for Taxi "+taxiNumber+" : failure");
	}

	/**
	 * The logout for a taxi was ok.
	 * @param taxiNumber
	 */
	public void logoutOk(int taxiNumber) {
		testCentral.Output.print("ComLinkTM:logoutOk");
		System.out.println("ComLinkTM: Logout for Taxi "+taxiNumber+" : ok");
	}
	public void logoutSignalC(int taxiNumber, int driverNumber) {
		central.logoutSignal(taxiNumber,driverNumber);
	}

	public void meterOffC(int taxiNumber) {
		central.meterOff(taxiNumber);
		OrderStructC o = (OrderStructC)actualorders.get(new Integer(taxiNumber));
		central.requestPrice(taxiNumber,o.orderNumber);
		actualorders.remove(new Integer(taxiNumber));
		
	}

	public void meterOnC(int taxiNumber) {
		central.meterOn(taxiNumber);
		central.detailedPosition(taxiNumber,GPSProxy.getZoneNumber(),GPSProxy.getPosition());
	}
	/**
	 * A new order is sended from the central to a taxi.
	 * @param order
	 * @param operatorNumber
	 */
	public void newOrder(OrderStructC order, int operatorNumber) {
		testCentral.Output.print("ComLinkTM:newOrder:"+order.taxiNumber);
		if(onlyaccept==true){
			int taxiNumber = order.taxiNumber;
			actualorders.put(new Integer(order.taxiNumber),order);
			System.out.println("ComLinkTM:Taxi "+order.taxiNumber+" accepted order "+order.taxiNumber+".");
			central.acceptOrderO(order.orderNumber,operatorNumber,order.taxiNumber,GPSProxy.getZoneNumber(),GPSProxy.getEstimateTime());
			meterOnC(taxiNumber);
			driverSoonAvailableC(taxiNumber);
			meterOffC(taxiNumber);
		}
		else{
			onlyaccept= true;
			System.out.println("ComLinkTM:Taxi "+order.taxiNumber+" rejected order "+order.orderNumber+".");
			central.rejectOrder(order.taxiNumber,operatorNumber,order.orderNumber);
		}
	}

	public void newOrderC(OrderStructC order){
		actualorders.put(new Integer(order.taxiNumber),order);
		central.newOrderO(order);
		int taxiNumber = order.taxiNumber;
		/*driverSoonAvailableC(taxiNumber);
		meterOffC(taxiNumber);*/
	}
	
	/**
	 * The alarm, first started from a taxi, will now be reseted by an operator.
	 * @param taxiNumber
	 */
	public void resetAlarm(int taxiNumber) {
		al.setStopThread(true);
		System.out.println("ComLinkTM: Alarm to Taxi "+taxiNumber+" reseted");
	}

	/**
	 * The taxi gets the price from an requestprice.
	 * @param taxiNumber
	 * @param orderNumber
	 * @param price
	 */
	public void responsePrice(int taxiNumber, int orderNumber, double price) {
		System.out.println("ComLinkTM:Price for Order "+orderNumber+" is: "+price+"€.");
	}

	/**
	 * A taxidriver gets informations about a driver
	 * @param driver
	 * @param taxiNumber
	 */
	public void responseViewDriver(DriverStruct driver, int taxiNumber) {
		System.out.println("ComLinkTM:Driver informations for Taxi "+taxiNumber+":\n"+driver.toString());
	}

	/**
	 * A taxidriver gets informations about a zone
	 * @param taxiNumber
	 * @param numberOfZones
	 * @param numberOfFreeCars
	 * @param numberOfOrders
	 */
	public void responseZoneInfo(int taxiNumber, int numberOfZones, int numberOfFreeCars,
			int numberOfOrders) {
		System.out.println("ComLinkTM:Zone informations fpr taxi "+taxiNumber+" :\nNumber of Zones: "
				+numberOfZones+"\nNumber of free cars: "+numberOfFreeCars+"\nNumber of orders: "
				+numberOfOrders);
	}
	
	


	/**
	 *  An operator wanst to talk at the voice communication channel
	 * @param taxiNumber
	 * @param operatorNumber
	 * @param voice
	 */
	public void sendVoice(int taxiNumber, int operatorNumber) {
		sendvoice = false;
		isalive = true;		
	}
	
	public void sendVoiceC(int taxiNumber, int operatorNumber){
		if(sendvoice == true && isalive == true){
			central.sendVoiceO(taxiNumber,operatorNumber);
		}	
		else
			System.out.println("ComLinkTM:not allowed to speak on channel");
	}
	
	public void startAlarmC(int taxiNumber) {
		al = new AlarmThread(central,taxiNumber,2,5);
		al.start();
		central.startAlarm(taxiNumber);
		
	}
	
	/**
	 *  An operator start a voice communication to a taxi
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void startVoice(int taxiNumber, int operatorNumber) {
		isalive = true;
		sendvoice = true;
		voiceoperator = operatorNumber;
		central.confirmVoiceO(taxiNumber,operatorNumber);
	}
	
	/**
	 * A taxi sends a start voice signal.
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void startVoiceC(int taxiNumber, int operatorNumber) {
		if(isalive==false){
		isalive = true;
		sendvoice = true;
		central.startVoiceO(taxiNumber,operatorNumber);
		}
		else
			System.out.println("ComLinkTM:not allowed to speak on channel");
	}
	public void stopThread(){
		stop = true;
	}

	/**
	 *   The operator stops talking on the voice communication channel
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void stopVoice(int taxiNumber, int operatorNumber) {
		sendvoice = true;
		isalive = true;
	}
	
	public void stopVoiceC(int taxiNumber, int operatorNumber){
		if(sendvoice==true && isalive == true){
			central.stopVoiceO(taxiNumber,operatorNumber);
		}
		else
			System.out.println("ComLinkTM:not allowed to speak on channel");
		
	}


	/**
	 * An operator terminates a voice communication to a taxi
	 * @param taxiNumber
	 * @param operatorNumber
	 */
	public void terminateVoice(int taxiNumber, int operatorNumber) {
		isalive = false;
		System.out.println("ComLinkTM:voice from from Operator "+operatorNumber+ "to taxi "+taxiNumber + " terminated");
	}

	public void terminateVoiceC(int taxiNumber, int operatorNumber) {
		if((sendvoice == true)&&(isalive == true))
			central.terminateVoiceO(taxiNumber,operatorNumber);
		else
			System.out.println("ComLinkTM:Not allowed to speak on channel");
		
	}

	/**
	 * An operator sends a text message to a taxi.
	 * @param taxiNumber
	 * @param messageNumber
	 * @param textMessage
	 * @param operatornumber
	 */
	public void textMessage(int taxiNumber, int messageNumber, String textMessage,
			int operatornumber) {
		System.out.println("ComLinkTM:textMessage from Operator "+operatornumber+" to taxi "+taxiNumber+":\n"+textMessage);
		central.ackMessageO(taxiNumber,messageNumber,operatornumber);
	}

	/**
	 *   The Operator sends a voice Message to a taxi
	 * Added Method
	 * @param taxiNumber
	 * @param operatorNumber
	 * @param voicedata
	 */
	public void voiceMessage(int taxiNumber, int operatorNumber, String voicedata) {
		System.out.println("ComLinkTM:Operator "+operatorNumber+" to Taxi "+taxiNumber+":"+voicedata);
		
	}
	
	public void voiceMessageC(int taxiNumber, int operatorNumber, String voicedata) {
		if(sendvoice == true && isalive == true)
			central.voiceMessageO(taxiNumber,operatorNumber,voicedata);
		else
			System.out.println("ComLinkTM:Not allowed to speak on channel");
	}
	/**
	 * @param signal
	 */
	public void sendSignal(int signal) {
		if(signal == 5){
			onlyaccept= true;
		}
		else if(signal == 6)
			onlyaccept = false;
	}
}
