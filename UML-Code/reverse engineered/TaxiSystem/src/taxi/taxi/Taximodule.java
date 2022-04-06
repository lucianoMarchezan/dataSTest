/*
 * Created on 08.08.2004
 */
package taxi;
import java.awt.Toolkit;
import proxy.GPSProxy;
import structures.OrderStructT;
import comLink.ComLinkC;
public class Taximodule {
	/* DO NOT CHANGE THE FOLLOWING DEFINITIONS DUE TO TESTING REASONS */
	public final ComLinkC comLinkC;
	public final int taxiNumber;
	public final int driverNumber;
	/* ************************************************************* */	
	private AlarmThreadTaxi a;
	private boolean alarm = false;
	private Driver driver;
	private OrderStructT order = null;
	private TimeoutThread t;
	private boolean voiceAlive = false;
	private boolean voiceSend = false;
	private boolean voiceConnecting = false;
	public Taximodule(Driver driver) {
		System.out.println("new Taximodule()");
		this.driver = driver;
		this.taxiNumber = driver.taxiNumber;
		this.driverNumber = driver.driverNumber;
		this.comLinkC = new ComLinkC(this, 0);
	}
	/**
	 * Forwards to the central that an order has been accepted by the driver.
	 * 
	 * @param orderNumber
	 *            The number of the order.
	 * @param operatorNumber
	 *            The number of the operator that sent the order.
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param zoneNumber
	 *            The number of the zone where the taxi is located at the
	 *            moment.
	 * @param estimateTime
	 *            The time until pickup is estimated to take place.
	 */
	public void acceptOrderC(int orderNumber, int operatorNumber,
			int zoneNumber, int estimateTime) {
		t.setStopThread(true); //timeout doesn't matter anymore
		comLinkC.acceptOrder(orderNumber, operatorNumber, taxiNumber,
				zoneNumber, estimateTime);
	}
	/**
	 * This method sends a signal to the central, that the driver didn't answer
	 * after a certain amount of time
	 * 
	 * @param orderNumber
	 *            The number of the order.
	 * @param operatorNumber
	 *            The number of the operator that sent the order.
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void acceptOrderMissingC(int orderNumber, int operatorNumber) {
		//timeout reached
		comLinkC.acceptOrderMissing(orderNumber, operatorNumber, taxiNumber);
	}
	/**
	 * Forwards to the central that an accepted order has been canceled by the
	 * driver.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param orderNumber
	 *            The number of the order.
	 */
	public void cancelOrderC(int orderNumber) {
		comLinkC.cancelOrder(taxiNumber, orderNumber);
	}
	/**
	 * If the driver has submitted a new order to the system, in case of
	 * confirmation this method displays a corresponding message.
	 * 
	 * @param orderNumber
	 *            The number of the order.
	 * @param operatorNumber
	 *            The number of the operator.
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void confirmOrder(int orderNumber, int operatorNumber) {
		System.out.println("Taximodule: Order confirmed by central!");
		System.out.println("            Order: " + orderNumber + ", Operator: "
				+ operatorNumber + ", Taxi: " + taxiNumber);
	}
	/**
	 * This method is called when the central has confirmed an incoming request
	 * for voice communication.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator.
	 */
	public void confirmVoice(int operatorNumber) {
		//if (voiceCom == null)
		if (voiceConnecting) {
			voiceAlive = true;
			voiceSend = true;
			voiceConnecting = false;
		} else {
			System.out.println("Taximodule: confirmVoice failed!");
		}
	}
	/**
	 * Forwards the signal that is sent by the driver to confirm a requested
	 * voice communication by the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator to be talked to.
	 */
	public void confirmVoiceC(int operatorNumber) {
		if (!voiceAlive && voiceSend && voiceConnecting) {
			System.out.println("Taximodule: Establishing voice-channel ...");
			voiceAlive = true;
			voiceConnecting = false;
			comLinkC.confirmVoice(taxiNumber, operatorNumber);
		} else
			System.out.println("Taximodule: confirmVoiceC failed!");
	}
	/**
	 * Sends detailed information about the actual position of the taxi to the
	 * central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param zoneNumber
	 *            The zone that the taxi is located in, represented by an
	 *            integer.
	 * @param position
	 *            The position inside of the zone that the taxi is located in,
	 *            represented by an integer.
	 */
	public void detailedPositionC(int zoneNumber, int position) {
		comLinkC.detailedPosition(taxiNumber, zoneNumber, position);
	}
	/**
	 * Forwards the signal that is sent when the driver is available to the
	 * central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void driverAvailableC() {
		comLinkC.driverAvailable(taxiNumber);
	}
	/**
	 * Forwards the signal that is sent when the driver assumes to be soon
	 * available to the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void driverSoonAvailableC() {
		comLinkC.driverSoonAvailable(taxiNumber);
		detailedPositionC(GPSProxy.getZoneNumber(), GPSProxy.getPosition());
	}
	/**
	 * This method should display the system's estimated time until the taxi is
	 * available.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void estimateTimeShow() {
		System.out.println("Taximodule: Estimated Time: "
				+ GPSProxy.getEstimateTime());
	}
	/**
	 * This method is called when the driver's login at the central failed.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void loginFailure() {
		System.out.println("Taximodule: Login failed!");
	}
	/**
	 * This method is called when the driver's login at the central was OK.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void loginOk() {
		System.out.println("Taximodule: Login successful!");
	}
	/**
	 * Forwards the login signal sent by the driver to the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param driverNumber
	 *            The number of the driver that is logging in.
	 */
	public void loginSignalC() {
		comLinkC.loginSignal(taxiNumber, driverNumber);
		detailedPositionC(GPSProxy.getZoneNumber(),
				GPSProxy.getPosition());
	}
	/**
	 * This method is called when the driver's logout at the central failed.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void logoutFailure() {
		System.out.println("Taximodule: Logout failed!");
	}
	/**
	 * This method is called when the driver's logout at the central was OK.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void logoutOk() {
		System.out.println("Taximodule: Logout successful!");
	}
	/**
	 * Forwards the logout signal sent by the driver to the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param driverNumber
	 *            The number of the driver that is logging out.
	 */
	public void logoutSignalC() {
		comLinkC.logoutSignal(taxiNumber, driverNumber);
	}
	/**
	 * Forwards the signal that is sent when turning off the meter to the
	 * central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void meterOffC() {
		comLinkC.meterOff(taxiNumber);
		comLinkC.detailedPosition(taxiNumber, GPSProxy.getZoneNumber(),
				GPSProxy.getPosition());
		requestPriceC(order.orderNumber);
	}
	/**
	 * Forwards the signal that is sent when turning on the meter to the
	 * central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void meterOnC() {
		comLinkC.meterOn(taxiNumber);
		comLinkC.detailedPosition(taxiNumber, GPSProxy.getZoneNumber(),
				GPSProxy.getPosition());
	}
	/**
	 * Forwards a new order subitted by the driver to the central.
	 * 
	 * @param order
	 *            The order to be submitted.
	 */
	public void newOrderC(OrderStructT order) {
		comLinkC.newOrder(order);
	}
	/**
	 * When the central sends a new order to the taxi this method informs the
	 * driver.
	 * 
	 * @param order
	 *            The incoming order.
	 * @param operatorNumber
	 *            The number of the operator that sent the order.
	 */
	public void newOrderD(OrderStructT order, int operatorNumber) {
		this.order = order;
		System.out.println("Taximodule: New order:\n\n" + order.toString());
		/* check Timeout */
		t = new TimeoutThread(10000, this, order.orderNumber, operatorNumber);
		t.start();
		driver.newOrder(order, operatorNumber);
	}
	/**
	 * Sends information about the actual position of the taxi to the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param zoneNumber
	 *            The zone that the taxi is located in, represented by an
	 *            integer.
	 */
	public void positionC(int zoneNumber) {
		comLinkC.position(taxiNumber, zoneNumber);
	}
	/**
	 * This method is called when the central sends the price for the finished
	 * order.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param orderNumber
	 *            The number of the order.
	 * @param price
	 *            The price for the finished order.
	 */
	public void priceShow(int orderNumber, double price) {
		System.out.println("Taximodule: Price for this fare is €" + price
				+ ".-");
		printReceipt(price);
	}
	/**
	 * This method is called after a finished order to print a receipt.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param price
	 *            The price for the finished order.
	 */
	public void printReceipt(double price) {
		System.out.println("Taximodule: PrintReceipt: Price for this fare is €"
				+ price + ".-");
	}
	/**
	 * Forwards to the central that an order has been rejected by the driver.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param orderNumber
	 *            The number of the order.
	 */
	public void rejectOrderC(int orderNumber) {
		t.setStopThread(true); //timeout doesn't matter anymore
		comLinkC.rejectOrder(taxiNumber, orderNumber);
	}
	/**
	 * This method is used to request the price after having finished an order.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param orderNumber
	 *            The number of the order that has been finished yet.
	 */
	public void requestPriceC(int orderNumber) {
		comLinkC.requestPrice(taxiNumber, orderNumber);
	}
	/**
	 * Forwards the driver's request to view his/her details.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param driverNumber
	 *            The number of the driver.
	 */
	public void requestViewDriverC() {
		comLinkC.requestViewDriver(taxiNumber, driverNumber);
	}
	/**
	 * Forwards the driver's request to get information about a zone.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param middleZone
	 *            The zone that is in the middle of all zones of interest,
	 *            represented by an integer.
	 */
	public void requestZoneInfoC(int middleZone) {
		comLinkC.requestZoneInfo(taxiNumber, middleZone);
	}
	/**
	 * This method is called when the central resets the alarm for the taxi.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void resetAlarm() {
		this.alarm = false;
		a.setStopThread(true);
		System.out.println("Taximodule: Alarm has been reset!");
	}
	/**
	 * This method is called when the central sends requested zone information
	 * to the taxi.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param numberOfZones
	 *            The number of zones located around the middleZone.
	 * @param numberOfFreeCars
	 *            The number of available cars in the zone.
	 * @param numberOfOrders
	 *            The number of orders to be done in the zone.
	 */
	public void responseZoneInfo(int numberOfZones, int numberOfFreeCars,
			int numberOfOrders) {
		System.out.println("Taximodule: ZoneInfo: NumberOfZones:    "
				+ numberOfZones);
		System.out.println("                      NumberOfFreeCars: "
				+ numberOfFreeCars);
		System.out.println("                      NumberOfOrders:   "
				+ numberOfOrders);
	}
	/**
	 * This method is called before the central attempts to send a voice
	 * message.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator.
	 */
	public void sendVoice(int operatorNumber) {
		if (voiceAlive && voiceSend && !voiceConnecting)
			voiceSend = false;
		else {
			System.out.println("Taximodule: sendVoice failed!");
		}
	}
	/**
	 * Forwards the signal that is sent by the driver before sending a voice
	 * message.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator talking to.
	 */
	public void sendVoiceC(int operatorNumber) {
		if (voiceAlive && voiceSend) {
			comLinkC.sendVoice(taxiNumber, operatorNumber);
		} else
			System.out.println("Taximodule: sendVoiceC failed!");
	}
	/**
	 * Forwards that the alarm has been activated in the specified taxi.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void startAlarmC() {
		this.alarm = true;
		comLinkC.startAlarm(taxiNumber);		
		a = new AlarmThreadTaxi(this);
		a.start();
	}
	/**
	 * Forwards the signal sent by the driver to initiate a voice communication.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator to be talked to.
	 */
	public void startVoiceC(int operatorNumber) {
		if (!voiceAlive && !voiceConnecting) {
			//voiceAlive = true;
			voiceSend = true;
			voiceConnecting = true;
			comLinkC.startVoice(taxiNumber, operatorNumber);
		} else
			System.out.println("Taximodule: startVoiceC failed!");
	}
	/**
	 * If the central wants to initiate a voice communication, this method is
	 * called.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator.
	 */
	public void startVoiceD(int operatorNumber) {
		if (!voiceAlive && !voiceSend && !voiceConnecting) {
			voiceSend = true;
			voiceConnecting = true;
			driver.startVoice(operatorNumber);
		} else {
			System.out
					.println("Taximodule: startVoiceD failed! Voice-channel already available!");
		}
	}
	/**
	 * This method is called when the central has finished sending a voice
	 * message.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator.
	 */
	public void stopVoice(int operatorNumber) {
		//method added to set voiceCom data
		//used to switch to sending mode
		if (voiceAlive && !voiceSend && !voiceConnecting)
			voiceSend = true;
		else {
			System.out.println("Taximodule: stopVoice failed!");
		}
	}
	/**
	 * Forwards the signal that is sent by the driver after having sent a voice
	 * message.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator talking to.
	 */
	public void stopVoiceC(int operatorNumber) {
		//method added to set voiceCom data
		//used to switch to receiving (not sending) mode
		//if (voiceCom != null) {
		if (voiceAlive && voiceSend && !voiceConnecting) {
			comLinkC.stopVoice(taxiNumber, operatorNumber);
		} else
			System.out.println("Taximodule: stopVoiceC failed!");
	}
	/**
	 * This method is called when the central terminates an established voice
	 * channel.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator to communicate with.
	 */
	public void terminateVoice(int operatorNumber) {
		voiceAlive = false;
		voiceSend = false;
		voiceConnecting = false;
	}
	/**
	 * Forwards the signal that is sent by the driver to terminate an
	 * established voice channel.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator talking to.
	 */
	public void terminateVoiceC(int operatorNumber) {
		if (voiceSend) {
			voiceAlive = false;
			voiceSend = false;
			voiceConnecting = false;
			comLinkC.terminateVoice(taxiNumber, operatorNumber);
		} else
			System.out.println("Taximodule: terminateVoiceC failed!");
	}
	/**
	 * This method is called to display a textmessage sent by the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param messageNumber
	 *            The number of the message.
	 * @param textmessage
	 *            The message itself, represented by a String.
	 * @param operatorNumber
	 *            The number of the operator that sent the message.
	 */
	public void textMessage(int messageNumber, String textmessage,
			int operatorNumber) {
		System.out.println("Taximodule: Message #" + messageNumber + ": "
				+ textmessage);
		Toolkit.getDefaultToolkit().beep();
		comLinkC.ackMessage(taxiNumber, messageNumber, operatorNumber);
	}
	/**
	 * This method is called when the central sends requested information about
	 * the driver to the driver.
	 * 
	 * @param driver
	 *            The driver that requested the information including all known
	 *            details.
	 * @param taxiNumber
	 *            The number of the taxi.
	 */
	public void viewDriver(structures.DriverStruct driver) {
		System.out.println("Taximodule: DriverInfo:\n" + driver);
	}
	/**
	 * To receive the voice message sent by the central over the established
	 * voice channel.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator.
	 * @param voicedata
	 *            The voice message itself, actually represented by a String.
	 */
	public void voiceMessage(int operatorNumber, String voicedata) {
		if (voiceAlive && !voiceConnecting) {
			if (!voiceSend)
				System.out.println("Taximodule: Voice: " + voicedata);
			else
				System.out
						.println("Taximodule:  Voice Message failed! Not in receiving mode!");
		} else
			System.out
					.println("Taximodule: Voice Message failed! No connection established!");
	}
	/**
	 * Forwards a voice message sent by the driver to the central.
	 * 
	 * @param taxiNumber
	 *            The number of the taxi.
	 * @param operatorNumber
	 *            The number of the operator to be talked to.
	 * @param voicedata
	 *            The voice message itself, actually represented by a String.
	 */
	public void voiceMessageC(int operatorNumber, String voicedata) {
		if (voiceAlive && voiceSend && !voiceConnecting && voicedata != null) {
			comLinkC.voiceMessage(taxiNumber, operatorNumber, voicedata);
		} else
			System.out.println("Taximodule: voiceMessageC failed!");
	}
}