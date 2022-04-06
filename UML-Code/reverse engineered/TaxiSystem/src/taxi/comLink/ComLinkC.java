/*
 * Created on 08.08.2004
 */
package comLink;
import java.util.Enumeration;
import java.util.Hashtable;
import proxy.GPSProxy;
import structures.OrderStructT;
import structures.TaxiStruct;
import taxi.Taximodule;
import tools.Import;
import tools.Randomizer;
public class ComLinkC implements Runnable {
	private OrderStructT actualOrder = null; //the order that is actually sent
	// to the taxi time
	private structures.DriverStruct driver;//to save driver information
	private int messageNumber = 0; //increases with every message sent for
	private int numberOfFreeCars = 7; //initial value for this field, could
	// change from time to
	private int numberOfZones = 23; //initial value for this field, not knowing
	// where to take info
	private int operatorNumber = 13;
	private Hashtable orders = new Hashtable(); //to save all open orders
	private int price = 1; //price per millisecond
	private Hashtable readMessages = new Hashtable(); // to mark read
	// TextMessages
	private int signals; //indicates the mode the server should be run in. 0
	// for listen server.
	private boolean stopThread = false; // receives data from taximodule and
	// sends data back
	private Thread t = null;
	private Taximodule taxiModule = null;
	private TaxiStruct taxiStruct = new TaxiStruct();
	private int taxiNumber, driverNumber;
	private boolean voiceAlive = false, voiceSend = false,
			voiceConnecting = false;
	public int answer = 666; //0 for negative answers, 1 for positive answers

	/**
	 * Creates a ComLinkC with 0 for sig.
	 * 
	 * @param taxiModule The calling Taximodule.
	 */
	public ComLinkC(Taximodule taxiModule) {
		init(taxiModule, 0);
	}

	/**
	 * On creating a new ComLinkC the signals that the server should send can be set.
	 * 
	 * @param taxiModule The calling Taximodule.
	 * @param sig 0: no signals, 1: send an order, 2: send voice, 3: send text message, 9: all
	 * signals
	 */
	public ComLinkC(Taximodule taxiModule, int sig) {
		init(taxiModule, sig);
	}

	/**
	 * This method is called when the driver accepted an order.
	 * 
	 * @param orderNumber The number of the order.
	 * @param operatorNumber The number of the operator.
	 * @param taxiNumber The number of the taxi.
	 * @param zoneNumber The zone where the taxi is located at the moment, represented by an
	 * integer.
	 * @param estimateTime The estimated time until pickup.
	 */
	public void acceptOrder(int orderNumber, int operatorNumber,
			int taxiNumber, int zoneNumber, int estimateTime) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Accept Order failed!");
		}
		else {
			System.out.println("ComLinkC: Order #" + orderNumber
								+ " accepted by driver.");
			System.out.println("ComLinkC: Estimated time until pickup:"
								+ estimateTime);
			taxiStruct.status = TaxiStruct.WAITING_FOR_CUSTOMER;
			taxiStruct.zone = zoneNumber;
		}
	}

	/**
	 * This method is called when the driver didn't answer on a sent order after a certain amount of
	 * time.
	 * 
	 * @param orderNumber The number of the order.
	 * @param operatorNumber The number of the operator.
	 * @param taxiNumber The number of the taxi.
	 */
	public void acceptOrderMissing(int orderNumber, int operatorNumber,
			int taxiNumber) {
		System.out.println("ComLinkC: Accept Order missing!");
		taxiStruct.status = TaxiStruct.AVAILABLE;
	}

	/**
	 * This method is called when the taxi sends an acknowledgement for a received text message.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param messageNumber The number of the message.
	 * @param operatorNumber The number of the operator that has sent the message.
	 */
	public void ackMessage(int taxiNumber, int messageNumber, int operatorNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: AckMessage #" + messageNumber
								+ " failed!");
		}
		else {
			System.out.println("ComLinkC: Message #" + messageNumber
								+ " acknowledged!");
			readMessages.put(new Integer(messageNumber), new Boolean(true));
		}
	}

	/**
	 * This method is called, when the driver cancels an accepted order.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param orderNumber The number of the order.
	 */
	public void cancelOrder(int taxiNumber, int orderNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Cancel Order #" + orderNumber
								+ " failed!");
		}
		else {
			taxiStruct.status = TaxiStruct.AVAILABLE;
		}
	}

	public void confirmOrderTM(int orderNumber, int operatorNumber,
			int taxiNumber) {
		taxiModule.confirmOrder(orderNumber, operatorNumber);
	}

	/**
	 * This method is called when the driver sends a confirmation for the voice communication
	 * request.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to be talked to.
	 */
	public void confirmVoice(int taxiNumber, int operatorNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: confirmVoice failed!");
		}
		else {
			//voiceCom allowed
			if (voiceConnecting) {
				voiceAlive = true;
				voiceSend = true;
				voiceConnecting = false;
			}
			else
				System.out.println("ComLinkC: confirmVoice failed!");
		}
	}

	public void confirmVoiceTM(int taxiNumber, int operatorNumber) {
		//System.out.println("ComLinkC: Establishing voice-channel ...");
		//voiceCom = new VoiceCom(false, true);
		if (!voiceAlive && voiceSend && voiceConnecting) {
			voiceAlive = true;
			voiceConnecting = false;
			taxiModule.confirmVoice(operatorNumber);
		}
		else
			System.out.println("ComLinkC: Confirm Voice failed!");
	}

	/**
	 * This method is called when the taxi sends detailed information about it's actual position.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param zoneNumber The zone that the taxi is located in, represented by an integer.
	 * @param position The position inside of the zone that the taxi is located in, represented by
	 * an integer.
	 */
	public void detailedPosition(int taxiNumber, int zoneNumber, int position) {
		if (!taxiStruct.alarm && taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Detailed Position failed!");
		}
		else {
			System.out.println("ComLinkC: Taxi #" + taxiNumber + " in Zone #"
								+ zoneNumber + " at Position #" + position
								+ ".");
		}
	}

	/**
	 * This method is called when the driver is available.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void driverAvailable(int taxiNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Driver Available failed!");
		}
		else {
			taxiStruct.status = TaxiStruct.AVAILABLE;
		}
	}

	/**
	 * This method is called when the driver assumes to be soon available.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void driverSoonAvailable(int taxiNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Driver Soon Available failed!");
		}
		else {
			taxiStruct.status = TaxiStruct.SOON_AVAILABLE;
			//estimate time until available
			System.out.println("ComLinkC: Estimated time until available is "
								+ GPSProxy.getEstimateTime() + " minutes.");
		}
	}

	private void init(Taximodule taxiModule, int sig) {
		this.signals = sig;
		//System.out.println("new ComLinkC()");
		taxiStruct.status = TaxiStruct.OFFLINE;
		this.taxiModule = taxiModule;
		this.operatorNumber = Randomizer.getInt(100) + 1; //create random
		// operator number
		this.taxiNumber = taxiModule.taxiNumber;
		this.driverNumber = taxiModule.driverNumber;
		orders = Import.readOrders("orders.txt"); //get orders from textfile
		//taxiStruct.taxiNumber = taxiModule.getTaxiNumber();
		t = new Thread(this);
		t.start(); //start comlink server
	}

	public void loginFailureTM(int taxiNumber) {
		taxiModule.loginFailure();
	}

	public void loginOkTM(int taxiNumber) {
		taxiModule.loginOk();
	}

	/**
	 * This method is called when the driver sends a login signal.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param driverNumber The number of the driver that is logging in.
	 */
	public void loginSignal(int taxiNumber, int driverNumber) {
		boolean letLogin = true;
		if (this.taxiStruct.status == TaxiStruct.OFFLINE) {
			try {
				int i = 0;
				if (answer == 666) i = Randomizer.getInt(4);
				if (answer == 0) i = 1;
				if (answer == 1) i = 3;
				switch (i) {
					case 1 :
						this.driver = new structures.DriverStruct(
																	driverNumber,
																	"Gernot Bauer",
																	"Graz");
						letLogin = false;
						break;
					case 2 :
						this.driver = new structures.DriverStruct(
																	driverNumber,
																	"Johannes Maier",
																	"Wien");
						letLogin = false;
						break;
					case 3 :
						this.driver = new structures.DriverStruct(
																	driverNumber,
																	"Gerda Stickl",
																	"Prag");
						this.taxiStruct = new TaxiStruct();
						this.taxiStruct.driverNumber = driverNumber;
						this.taxiStruct.status = TaxiStruct.AVAILABLE;
						this.taxiStruct.taxiNumber = taxiNumber;
						break;
					default :
						this.driver = new structures.DriverStruct(
																	driverNumber,
																	"Your Name",
																	"Your City");
						this.taxiStruct = new TaxiStruct();
						this.taxiStruct.driverNumber = driverNumber;
						this.taxiStruct.status = TaxiStruct.AVAILABLE;
						this.taxiStruct.taxiNumber = taxiNumber;
						break;
				}
			}
			catch (Exception e) {
				this.driver = new structures.DriverStruct(driverNumber,
															"Your Name",
															"Your City");
			}
			if (letLogin)
				taxiModule.loginOk();
			else
				taxiModule.loginFailure();
		}
		else
			taxiModule.loginFailure();
	}

	public void logOutFailureTM(int taxiNumber) {
		taxiModule.logoutFailure();
	}

	public void logOutOkTM(int taxiNumber) {
		taxiModule.logoutOk();
	}

	/**
	 * This method is called when the driver sends a logout signal.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param driverNumber The number of the driver that is logging out.
	 */
	public void logoutSignal(int taxiNumber, int driverNumber) {
		if (taxiStruct.status == TaxiStruct.AVAILABLE
			&& taxiStruct.taxiNumber == taxiNumber
			&& taxiStruct.driverNumber == driverNumber) {
			int i = 1;
			if (answer == 666) i = Randomizer.getInt(4);
			if (answer == 0) i = 0;
			if (answer == 1) i = 3;
			switch (i) {
				default :
					taxiStruct.status = TaxiStruct.OFFLINE;
					taxiModule.logoutOk();
					stopThread = true;
					break;
				case 0 :
					taxiModule.logoutFailure();
					break;
			}
		}
		else {
			System.out
						.println("ComLinkC: Logout not successful, possibly wrong status!");
			taxiModule.logoutFailure();
		}
	}

	/**
	 * This method is called when the driver turns off the meter.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void meterOff(int taxiNumber) {
		if (/* taxiStruct.taxiNumber==taxiNumber&& */taxiStruct.status != TaxiStruct.OFFLINE) {
			taxiStruct.stopTime = System.currentTimeMillis();
			taxiStruct.status = TaxiStruct.AVAILABLE;
			taxiStruct.meterOn = false;
		}
		else {
			System.out.println("ComLinkC: Meter Off failed!");
		}
	}

	/**
	 * This method is called when the driver turns on the meter.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void meterOn(int taxiNumber) {
		if (taxiStruct.status != TaxiStruct.OFFLINE) {
			taxiStruct.startTime = System.currentTimeMillis();
			taxiStruct.status = TaxiStruct.DRIVING_A_CUSTOMER;
			taxiStruct.meterOn = true;
		}
		else {
			System.out.println("ComLinkC: Meter On failed!");
		}
	}

	/**
	 * This method is called when a new order is submitted by the driver.
	 * 
	 * @param order The order to be submitted.
	 */
	public void newOrder(OrderStructT order) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: New Order failed!");
		}
		else {
			//find a key for the new order that is not yet assigned to another
			// order
			int newKey = 1;
			Enumeration keys = orders.keys();
			Integer currentKey = (Integer) keys.nextElement();
			while (currentKey.equals(new Integer(newKey))) {
				newKey++;
				currentKey = (Integer) keys.nextElement();
			}
			OrderStructT newOrder = new OrderStructT(newKey, order.time,
														order.address,
														order.name, order.misc,
														order.priority,
														order.taxiNumber,
														order.manualDispatch);
			//orders.put(new Integer(newKey), newOrder);
			confirmOrderTM(newKey, operatorNumber, taxiStruct.taxiNumber);
			if (taxiStruct.status == TaxiStruct.AVAILABLE) { //if available let
				// taxi get the new
				// order
				actualOrder = newOrder;
				taxiStruct.status = TaxiStruct.WAITING_FOR_CUSTOMER;
				newOrderTM(newOrder); //send order to taxi
			}
		}
	}

	/* ********************************************** */
	public void newOrderTM(OrderStructT order) {
		taxiModule.newOrderD(order, operatorNumber);
	}

	/**
	 * This method is called when the taxi sends information about it's actual position.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param zoneNumber The zone that the taxi is located in, represented by an integer.
	 */
	public void position(int taxiNumber, int zoneNumber) {
		if (!taxiStruct.alarm && taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Position failed!");
		}
		else {
			System.out.println("ComLinkC: Taxi #" + taxiNumber + " in Zone #"
								+ zoneNumber + ".");
		}
	}

	/**
	 * This method is called when the driver rejects an incoming order.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param orderNumber The number of the order.
	 */
	public void rejectOrder(int taxiNumber, int orderNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Reject Order #" + orderNumber
								+ " failed!");
		}
		else {
			System.out.println("ComLinkC:  Order #" + orderNumber
								+ " rejected.");
			taxiStruct.status = TaxiStruct.AVAILABLE;
		}
	}

	/**
	 * This method is called when the price for the order is requested.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param orderNumber The number of the order.
	 */
	public void requestPrice(int taxiNumber, int orderNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Request Price failed!");
		}
		else {
			responsePriceTM(taxiNumber, orderNumber, (Randomizer.getInt(25))
														* price);
			orders.remove(new Integer(orderNumber));
		}
	}

	/**
	 * This method is called when the driver wants to view his/her own details.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param driverNumber The number of the driver.
	 */
	public void requestViewDriver(int taxiNumber, int driverNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			|| taxiStruct.driverNumber != driverNumber) {
			System.out.println("ComLinkC: Request View Driver failed!");
		}
		else {
			responseViewDriverTM(driver, taxiNumber);
		}
	}

	/**
	 * This method iscalled when the driver requests information about a certain zone.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param middleZone The zone that is in the middle of all zones of interest, represented by an
	 * integer.
	 */
	public void requestZoneInfo(int taxiNumber, int middleZone) {
		if (taxiStruct.status == TaxiStruct.OFFLINE) {
			System.out.println("ComLinkC: Request Zone Info failed!");
		}
		else {
			responseZoneInfoTM(taxiNumber, this.numberOfZones,
								this.numberOfFreeCars, orders.size());
		}
	}

	public void resetAlarmTM(int taxiNumber) {
		taxiModule.resetAlarm();
	}

	public void responsePriceTM(int taxiNumber, int orderNumber, double price) {
		taxiModule.priceShow(orderNumber, price);
	}

	public void responseViewDriverTM(structures.DriverStruct driver,
			int taxiNumber) {
		taxiModule.viewDriver(driver);
	}

	public void responseZoneInfoTM(int taxiNumber, int numberOfZones,
			int numberOfFreeCars, int numberOfOrders) {
		taxiModule.responseZoneInfo(numberOfZones, numberOfFreeCars,
									numberOfOrders);
	}

	public void run() {
		//System.out.println("ComLinkC: Thread started ...");
		boolean sendOrder = true;
		boolean startVoice = true;
		boolean sendVoice = true;
		boolean voiceMessage = true;
		boolean stopVoice = true;
		boolean terminateVoice = true;
		boolean textMessage = true;
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (!this.stopThread) { //Server should run until logout of driver
			if (this.taxiStruct.status != TaxiStruct.OFFLINE) {
				switch (signals) {
					//0: no signals, 1: send an order, 2: send voice, 3: send
					// text
					// message, 9: all
					// signals
					case 0 :
						break;
					case 1 :
						if (sendOrder
							&& taxiStruct.status == TaxiStruct.AVAILABLE) { //if
							// driver
							// is
							// available,
							// send an order
							sendOrder = false;
							//get an order to send
							OrderStructT order = (OrderStructT) orders
																		.get((Integer) orders
																								.keys()
																								.nextElement());
							order.taxiNumber = taxiStruct.taxiNumber; // send to
							// logged in
							// taxi
							// order sent
							System.out
										.println("ComLinkC: Sending new Order to TM");
							taxiStruct.status = TaxiStruct.WAITING_FOR_CUSTOMER;
							actualOrder = order;
							newOrderTM(order);
						}
						break;
					case 2 :
						if (startVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending start voice to TM...");
							startVoiceTM(taxiStruct.taxiNumber,
											this.operatorNumber);
							startVoice = false;
						}
						if (!startVoice && sendVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending send voice to TM...");
							sendVoiceTM(taxiStruct.taxiNumber,
										this.operatorNumber);
							sendVoice = false;
						}
						if (!startVoice && !sendVoice && voiceMessage
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending voice message to TM...");
							voiceMessageTM(taxiStruct.taxiNumber,
											this.operatorNumber,
											"Central sends this Voice Message.");
							voiceMessage = false;
						}
						if (!startVoice && !sendVoice && stopVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending stop voice to TM...");
							stopVoiceTM(taxiStruct.taxiNumber,
										this.operatorNumber);
							stopVoice = false;
						}
						if (!startVoice && !sendVoice && !stopVoice
							&& terminateVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending terminate voice to TM...");
							terminateVoiceTM(taxiStruct.taxiNumber,
												this.operatorNumber);
							terminateVoice = false;
						}
						break;
					case 3 :
						if (textMessage
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending text message to TM...");
							textMessageTM(taxiStruct.taxiNumber, messageNumber,
											"Central sends this Text Message.",
											operatorNumber);
							textMessage = false;
						}
						break;
					case 9 :
						try {
							Thread.sleep(3500);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (sendOrder
							&& taxiStruct.status == TaxiStruct.AVAILABLE) { //if
							// driver
							// is
							// available,
							// send an order
							sendOrder = false;
							//get an order to send
							OrderStructT order = (OrderStructT) orders
																		.get((Integer) orders
																								.keys()
																								.nextElement());
							order.taxiNumber = taxiStruct.taxiNumber; // send to
							// logged
							// in taxi
							// order sent
							System.out
										.println("ComLinkC: Sending new Order to TM...");
							taxiStruct.status = TaxiStruct.WAITING_FOR_CUSTOMER;
							actualOrder = order;
							newOrderTM(order);
						}
						if (!sendOrder && startVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending start voice to TM...");
							startVoiceTM(taxiStruct.taxiNumber,
											this.operatorNumber);
							startVoice = false;
						}
						if (!sendOrder && !startVoice && sendVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending send voice to TM...");
							sendVoiceTM(taxiStruct.taxiNumber,
										this.operatorNumber);
							sendVoice = false;
						}
						if (!sendOrder && !startVoice && !sendVoice
							&& voiceMessage
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending voice message to TM...");
							voiceMessageTM(taxiStruct.taxiNumber,
											this.operatorNumber,
											"Central sends this Voice Message.");
							voiceMessage = false;
						}
						if (!sendOrder && !startVoice && !sendVoice
							&& stopVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending stop voice to TM...");
							stopVoiceTM(taxiStruct.taxiNumber,
										this.operatorNumber);
							stopVoice = false;
						}
						if (!sendOrder && !startVoice && !sendVoice
							&& !stopVoice && terminateVoice
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending terminate voice to TM...");
							terminateVoiceTM(taxiStruct.taxiNumber,
												this.operatorNumber);
							terminateVoice = false;
						}
						if (!sendOrder && !startVoice && !sendVoice
							&& !stopVoice && !terminateVoice && textMessage
							&& taxiStruct.status != TaxiStruct.OFFLINE) {
							System.out
										.println("ComLinkC: Sending text message to TM...");
							textMessageTM(taxiStruct.taxiNumber, messageNumber,
											"Central sends this Text Message.",
											operatorNumber);
							textMessage = false;
						}
						break;
					default :
						break;
				}
			}
		}
	}

	/**
	 * This method is called when the driver sends the signal just before sending a voice message.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator talking to.
	 */
	public void sendVoice(int taxiNumber, int operatorNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: Send Voice failed!");
		}
		else {
			if (voiceAlive && voiceSend && !voiceConnecting)
				voiceSend = false;
			else
				System.out.println("ComLinkC: Send Voice failed!");
		}
	}

	public void sendVoiceTM(int taxiNumber, int operatorNumber) {
		if (voiceAlive && voiceSend && !voiceConnecting) {
			taxiModule.sendVoice(operatorNumber);
		}
		//		else
		//			System.out.println("ComLinkC: No voice-channel available!");
	}

	/**
	 * This method is called when the driver activates the alarm.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void startAlarm(int taxiNumber) {
		System.out.println("ComLinkC: Taxi #" + taxiNumber + " sends alarm!");
		taxiStruct.alarm = true;
		//startVoiceTM(taxiNumber, operatorNumber);
		//AlarmResetThread art = new AlarmResetThread(this, taxiNumber);
		//art.start();
	}

	/**
	 * This method is called when the driver initiates a voice communication.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to be talked to.
	 */
	public void startVoice(int taxiNumber, int operatorNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: Start Voice failed!");
		}
		else {//voiceCom allowed
			if (!voiceAlive && !voiceSend && !voiceConnecting) {
				int r = 1;
				if (answer == 666) r = Randomizer.getInt(4);
				if (taxiStruct.alarm) r = 1;
				if (answer == 0) r = 0;
				if (answer == 1) r = 1;
				switch (r) {
					case 0 :
						//deny
						//voiceConnecting = true;
						//voiceSend = true;
						terminateVoiceTM(taxiNumber, operatorNumber);
						break;
					default :
						voiceConnecting = true;
						voiceSend = true;
						confirmVoiceTM(taxiNumber, operatorNumber);
						break;
				}
			}
			else
				System.out
							.println("ComLinkC: Voice-channel already available!");
		}
	}

	public void startVoiceTM(int taxiNumber, int operatorNumber) {
		if (!voiceAlive && !voiceSend && !voiceConnecting) {
			//voiceAlive = true;
			voiceSend = true;
			voiceConnecting = true;
			taxiModule.startVoiceD(operatorNumber);
		}
		//else System.out.println("ComLinkC: startVoiceTM failed!");
	}

	/**
	 * This method is called when the driver sends the signal after having sent a voice message.
	 * This method is called when the driver sends the signal just before sending a voice message.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator talking to.
	 */
	public void stopVoice(int taxiNumber, int operatorNumber) {
		//method added to set voiceCom data
		//used to switch to sending (not receiving) mode
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: Stop Voice failed!");
		}
		else {
			//voiceCom allowed
			if (voiceAlive && !voiceSend && !voiceConnecting) voiceSend = true;
		}
	}

	public void stopVoiceTM(int taxiNumber, int operatorNumber) {
		//method added to set voiceCom data
		//used to switch to receiving (not sending) mode
		if (voiceAlive && voiceSend && !voiceConnecting) {
			taxiModule.stopVoice(operatorNumber);
		}
		//		else
		//			System.out.println("ComLinkC: No voice-channel available!");
	}

	/**
	 * This method is called when the driver terminates the voice communication.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator talking to.
	 */
	public void terminateVoice(int taxiNumber, int operatorNumber) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: terminateVoice failed!");
		}
		else {
			System.out
						.println("ComLinkC: Voice Communication terminated by driver.");
			voiceAlive = false;
			voiceSend = false;
			voiceConnecting = false;
		}
	}

	public void terminateVoiceTM(int taxiNumber, int operatorNumber) {
		voiceAlive = false;
		voiceSend = false;
		voiceConnecting = false;
		taxiModule.terminateVoice(operatorNumber);
	}

	public void textMessageTM(int taxiNumber, int messageNumber,
			String textMessage, int operatorNumber) {
		this.messageNumber++;
		this.taxiModule.textMessage(messageNumber, textMessage, operatorNumber);
	}

	/**
	 * This method is called when the driver actually sends a voice message.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator talking to.
	 * @param voicedata The voice message itself, actually represented by a String.
	 */
	public void voiceMessage(int taxiNumber, int operatorNumber,
			String voicedata) {
		if (taxiStruct.status == TaxiStruct.OFFLINE
			&& taxiStruct.alarm == false) {
			System.out.println("ComLinkC: Voice Message failed!");
		}
		else {
			if (voiceAlive && !voiceConnecting) {
				if (!voiceSend)
					System.out
								.println("ComLinkC: Incoming Voice: "
											+ voicedata);
				else
					System.out
								.println("ComLinkC: Voice Message failed! Not in receiving mode!");
			}
			else
				System.out
							.println("ComLinkC: Voice Message failed! No connection established!");
		}
	}

	public void voiceMessageTM(int taxiNumber, int operatorNumber,
			String voicedata) {
		//System.out.println(voiceAlive + " " + voiceSend + " " +
		// voiceConnecting + " " +
		// voicedata);
		if (voiceAlive && voiceSend && !voiceConnecting && voicedata != null) {
			taxiModule.voiceMessage(operatorNumber, voicedata);
		}
	}
}