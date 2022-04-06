/*
 * Created on 08.08.2004
 */
package taxi;
import proxy.GPSProxy;
import structures.OrderStructT;
import tools.Output;
import tools.Randomizer;
public class Driver {
	//  final fields allow only one assignment during runtime
	/* DO NOT CHANGE THE FOLLOWING DEFINITIONS DUE TO TESTING REASONS */
	public final Taximodule taxiModule;
	public final int driverNumber;
	public final int taxiNumber;

	/* ************************************************************* */
	public Driver(int taxiNumber, int driverNumber) {
		boolean error = false;
		if (taxiNumber < 1) {
			Output.printError("Driver: taxiNumber out of range!");
			error = true;
		}
		if (driverNumber < 1) {
			System.out.println("Driver: driverNumber out of range!");
			error = true;
		}
		if (!error) {
			System.out.println("new Driver()");
			this.driverNumber = driverNumber;
			this.taxiNumber = taxiNumber;
			taxiModule = new Taximodule(this);
		}
		else {
			this.driverNumber = -1;
			this.taxiNumber = -1;
			taxiModule = null;
		}
	}

	/**
	 * To accept an incoming order sent by the central.
	 * 
	 * @param orderNumber The number of the order.
	 * @param operatorNumber The number of the operator that sent the order.
	 * @param taxiNumber The number of the receiving taxi.
	 * @param zoneNumber The actual zone represented by an integer.
	 * @param estimateTime The estimated time until pickup.
	 */
	public void acceptOrderTM(int orderNumber, int operatorNumber,
			int zoneNumber, int estimateTime) {
		boolean error = false;
		if (orderNumber < 1) {
			System.out.println("Driver: orderNumber out of range!");
			error = true;
		}
		if (operatorNumber < 1) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (zoneNumber < 1) {
			System.out.println("Driver: zoneNumber out of range!");
			error = true;
		}
		if (estimateTime < 1) {
			System.out.println("Driver: estimateTime out of range!");
			error = true;
		}
		if (!error) {
			taxiModule.acceptOrderC(orderNumber, operatorNumber, zoneNumber,
									estimateTime);
			//simulate driving the order:
			meterOnTM();
			setSoonAvailableTM();
			meterOffTM();
		}
	}

	/**
	 * To cancel an accepted order.
	 * 
	 * @param taxiNumber The number of the receiving taxi.
	 * @param orderNumber The number of the order.
	 */
	public void cancelOrderTM(int orderNumber) {
		boolean error = false;
		if (orderNumber < 1) {
			System.out.println("Driver: orderNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.cancelOrderC(orderNumber);
	}

	/**
	 * To confirm an incoming request for a voice communication with the central.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 */
	public void confirmVoiceTM(int operatorNumber) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.confirmVoiceC(operatorNumber);
	}

	/**
	 * To send the signal for login to the central.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param driverNumber The number of the driver that is logging in.
	 */
	public void loginSignalTM() {
		taxiModule.loginSignalC();
	}

	/**
	 * To send the signal for logout to the central.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param driverNumber The number of the driver that is logging out.
	 */
	public void logoutSignalTM() {
		taxiModule.logoutSignalC();
	}

	/**
	 * To send the signal when the meter is turned off.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void meterOffTM() {
		taxiModule.meterOffC();
	}

	/**
	 * To send the signal when the meter is turned on.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void meterOnTM() {
		taxiModule.meterOnC();
	}

	/**
	 * This method is used to handle an incoming order.
	 * 
	 * @param order The order itself.
	 * @param operatorNumber The number of the operator that sent the order.
	 */
	public void newOrder(OrderStructT order, int operatorNumber) {
		/* CREATE RANDOM ANSWER FROM DRIVER */
		int answer = Randomizer.getInt(2);
		switch (answer) {
			case 0 :
				//reject order
				System.out.println("Driver: Driver rejects order");
				rejectOrderTM(order.orderNumber);
				break;
			case 1 :
				//accept order
				System.out.println("Driver: Driver accepts order");
				acceptOrderTM(order.orderNumber, operatorNumber,
								GPSProxy.getZoneNumber(),
								GPSProxy.getEstimateTime());
				break;
			case 2 :
				//no answer
				System.out.println("Driver: Driver sends no answer");
				break;
		}
	}

	/**
	 * Lets the driver submit a new order to the system.
	 * 
	 * @param order The order to be submitted.
	 */
	public void newOrderTM(OrderStructT order) {
		boolean error = false;
		if (order == null) {
			System.out.println("Driver: No valid order given!");
			error = true;
		}
		if (!error) taxiModule.newOrderC(order);
	}

	/**
	 * To reject an incoming order sent by the central.
	 * 
	 * @param taxiNumber The number of the receiving taxi.
	 * @param orderNumber The number of the order.
	 */
	public void rejectOrderTM(int orderNumber) {
		boolean error = false;
		if (orderNumber < 1) {
			System.out.println("Driver: orderNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.rejectOrderC(orderNumber);
	}

	/**
	 * This method offers the driver the possibility to view his/her own details.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void requestViewDriverTM() {
		taxiModule.requestViewDriverC();
	}

	/**
	 * This method lets the driver get some information about the zone he/she is actually driving
	 * in.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param middleZone The zone, actually represented by an integer.
	 */
	public void requestZoneInfoTM(int middleZone) {
		boolean error = false;
		if (middleZone < 1) {
			System.out.println("Driver: middleZone out of range!");
			error = true;
		}
		if (!error) taxiModule.requestZoneInfoC(middleZone);
	}

	/**
	 * To send the signal before the voice message can be sent.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 */
	public void sendVoiceTM(int operatorNumber) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.sendVoiceC(operatorNumber);
	}

	/**
	 * When the driver assumes that he will be soon available, this is the signal to be sent.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void setSoonAvailableTM() {
		taxiModule.driverSoonAvailableC();
	}

	/**
	 * When the driver is in any kind of trouble this signal sends an alarm to the central.
	 * 
	 * @param taxiNumber The number of the taxi.
	 */
	public void startAlarmTM() {
		taxiModule.startAlarmC();
		taxiModule.detailedPositionC(GPSProxy.getZoneNumber(),
										GPSProxy.getPosition());
		startVoiceTM(0);
	}

	/**
	 * This method is used to decide whether to confirm or terminate an incoming voice communication
	 * request.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to be talked to.
	 */
	public void startVoice(int operatorNumber) {
		int answer = Randomizer.getInt(1);
		switch (answer) {
			case 0 :
				//terminate
				System.out.println("Driver: Driver terminates/denies voice");
				terminateVoiceTM(operatorNumber);
				break;
			case 1 :
				//confirm
				System.out.println("Driver: Driver confirms voice");
				confirmVoiceTM(operatorNumber);
				break;
		}
	}

	/**
	 * To initiate a voice communication with the central.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 */
	public void startVoiceTM(int operatorNumber) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.startVoiceC(operatorNumber);
	}

	/**
	 * To send the signal after voice message has been sent.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 */
	public void stopVoiceTM(int operatorNumber) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.stopVoiceC(operatorNumber);
	}

	/**
	 * To terminate an established voice channel.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 */
	public void terminateVoiceTM(int operatorNumber) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.terminateVoiceC(operatorNumber);
	}

	/**
	 * The voice message to be sent over the established voice channel.
	 * 
	 * @param taxiNumber The number of the taxi.
	 * @param operatorNumber The number of the operator to communicate with.
	 * @param voicedata The voice message itself, actually represented by a String.
	 */
	public void voiceMessageTM(int operatorNumber, String voicedata) {
		boolean error = false;
		if (operatorNumber < 0) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (voicedata == null || voicedata.equals("")) {
			System.out.println("Driver: operatorNumber out of range!");
			error = true;
		}
		if (!error) taxiModule.voiceMessageC(operatorNumber, voicedata);
	}
}