/*
 * Created on 08.08.2004
 */
package central;
import structures.*;
import tools.*;
import proxy.*;
import comLink.ComLinkTM;
import java.util.*;

public class Central {
	
	public ComLinkTM comLinkTM;
	
	private DriverDataProxy ddp = null;
	private OrderDataProxy odp = null;
	private OperatorDataProxy operatordp = null;
	public TaxiDataProxy tdp = null;
	
	private int nextOrderNumber; //Number of the next Order
	private double pricepersecond = 1; // Price per second
	
	private OrderDispatchThread odt = null;
	
	public Central() {
		odp = new OrderDataProxy();
		operatordp = new OperatorDataProxy();
		tdp = Import.readTaxis("taxi.txt");
		ddp = Import.readDriver("driver.txt");
		nextOrderNumber = 1;
		comLinkTM = new ComLinkTM(this, 0);
		odt = new OrderDispatchThread(odp, tdp, comLinkTM, operatordp);
		odt.start();
	}
	
	/**
	 * The taxidriver has accept an order.
	 */
	public void acceptOrderO(int orderNumber, int operatorNumber, int taxiNumber, int zoneNumber,
			int estimateTime) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator mot logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if(odp.get(orderNumber)==null)
			Output.printError("OrderNumber invalid.");
		else {
			operatordp.get(operatorNumber).acceptOrder(orderNumber,taxiNumber, taxiNumber, zoneNumber,
					estimateTime);
		}
	}
	
	/**
	 * The taxidriver has received a textmessage and accepted it.
	 */
	public void ackMessageO(int taxiNumber, int messageNumber, int operatorNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator mot logged in, cannot perform operation.");
		else{
			operatordp.get(operatorNumber).ackMessage(taxiNumber, messageNumber);
		}
	}
	
	/**
	 * A taxi cancels an order.
	 */
	public void cancelOrder(int taxiNumber, int orderNumber) {
		if (operatordp.size() == 0)
			Output.printError("No operator logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if(odp.get(orderNumber)==null)
			Output.printError("OrderNumber invalid.");
		else{
			int op = getRandomOperatorNumber();
			TaxiStruct ts = tdp.get(taxiNumber);
			ts.orderNumber = -1;
			ts.status = TaxiStruct.AVAILABLE;
			tdp.update(ts);
			System.out.println("Operator " + op + " manual dispatch Order " + orderNumber+".");
			OrderStructC order = (OrderStructC) odp.get(orderNumber);
			operatordp.get(op).manualDispatchOrder(taxiNumber, order);
		}
	}
	
	/**
	 * Sends an confirmation to the taxi.
	 */
	public void confirmOrderTM(int orderNumber, int operatorNumber, int taxiNumber) {
		if (operatordp.get(operatorNumber) == null) 
			Output.printError("Operator not logged in.");
		else if(tdp.get(taxiNumber)==null) 
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if(odp.get(orderNumber)==null)
			Output.printError("OrderNumber invalid.");
		else {
			TaxiStruct ts = tdp.get(taxiNumber);
			if (ts.status == TaxiStruct.AVAILABLE) {
				System.out.println("Taxi " + taxiNumber + " is driving order "+ orderNumber+".");
				ts.status = TaxiStruct.WAITING_FOR_CUSTOMER;
				tdp.update(ts);
				comLinkTM.confirmOrder(orderNumber, operatorNumber, taxiNumber);
			}
		}	
	}
	
	/**
	 * The taxidriver confirms the start of a voice communication from the operator..
	 */
	public void confirmVoiceO(int taxiNumber, int operatorNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if (operatordp.get(operatorNumber) == null) 
				Output.printError("Operator not logged in.");
		else
			operatordp.get(operatorNumber).confirmVoice(taxiNumber);
	}
	
	/**
	 * Confirms the establishment of a voice communication.
	 */
	public void confirmVoiceTM(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			System.out.println("Operator not logged in.");
		else if(tdp.get(taxiNumber)==null) 
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			comLinkTM.confirmVoice(taxiNumber, operatorNumber);	
	}
	
	/**
	 * A taxi sends its detail position and zone to the central..
	 */
	public void detailedPosition(int taxiNumber, int zoneNumber, int position) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if(zoneNumber < 1 || zoneNumber > 23)
			Output.printError("ZoneNumber invalid.");
		else if(position < 1 || position > 2500)
			Output.printError("Position invalid.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			ts.zone = zoneNumber;
			ts.position = position;
			tdp.update(ts);
			System.out.println("Taxi " + taxiNumber + " is at zone " + zoneNumber + " and position "
					+ position + ".");
		}
	}
	
	/**
	 * A taxidriver is now in state available.
	 */
	public void driverAvailable(int taxiNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			if ((ts.status == TaxiStruct.SOON_AVAILABLE)
					|| (ts.status == TaxiStruct.WAITING_FOR_CUSTOMER)) {
				ts.status = TaxiStruct.AVAILABLE;
				ts.orderNumber = -1;
				tdp.update(ts);
			}
		}
	}
	
	/**
	 * A taxidriver switches to state soon available.
	 */
	public void driverSoonAvailable(int taxiNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			if (ts.status == TaxiStruct.DRIVING_A_CUSTOMER) {
				ts.status = TaxiStruct.SOON_AVAILABLE;
				tdp.update(ts);
			}
		}
	}
	
	/**
	 * Finds the first available taxi.
	 * 
	 * @return
	 */
	public TaxiStruct getFirstAvailableTaxi() {
		Enumeration e = tdp.objects();
		TaxiStruct t = null;
		while (e.hasMoreElements()) {
			t = (TaxiStruct) e.nextElement();
			if (t.status == TaxiStruct.AVAILABLE) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Finds a random Operatornumber
	 * 
	 * @return
	 */
	public int getRandomOperatorNumber() {
		int nr = 0;
		Enumeration e = operatordp.numbers();
		Vector v = new Vector();
		while (e.hasMoreElements()) {
			v.add(e.nextElement());
		}
		nr = ((Integer) v.get(Randomizer.getInt(v.size()-1))).intValue();
		return nr;
	}
	
	/**
	 * An Operator sends a login signal.
	 */
	public void login(int operatorNumber, Operator operator, String password) {
		Hashtable passwords = Import.readOperators("operator.txt");
		if (password.equals((String) passwords.get(new Integer(operatorNumber)))) {
			operatordp.add(operatorNumber, operator);
			operator.loginOk();
		}
		else
			operator.loginFailure();
	}
	
	/**
	 * A taxidriver logs in.
	 */
	public void loginSignal(int taxiNumber, int driverNumber) {
		if (tdp.get(taxiNumber) != null && 
				tdp.get(taxiNumber).status == TaxiStruct.OFFLINE &&
				ddp.get(driverNumber) != null) {
			TaxiStruct ts = tdp.get(taxiNumber);
			ts.driverNumber = driverNumber;
			ts.status = TaxiStruct.AVAILABLE;
			tdp.update(ts);
			comLinkTM.loginOk(taxiNumber);
		}
		else
			comLinkTM.loginFailure(taxiNumber);
	}
	
	/**
	 * An operator sends a logout signal.
	 */
	public void logout(int operatorNumber) {
		if (operatordp.get(operatorNumber) != null) {
			operatordp.get(operatorNumber).logoutOk();
			operatordp.remove(operatorNumber);
			if (operatordp.size() == 0) odt.setStopThread(true);
		}
		else
			Output.printError("Logout for Operator " +operatorNumber+" failed.");
	}
	
	/**
	 * A taxidriver sends a logout signal to the central
	 * 
	 * @param taxinumber
	 * @param drivernumber
	 */
	public void logoutSignal(int taxiNumber, int driverNumber) {
		if (tdp.get(taxiNumber) != null) {
			TaxiStruct ts = tdp.get(taxiNumber);
			if ((ts.status == TaxiStruct.AVAILABLE) && (ts.driverNumber == driverNumber)) {
				ts.status = TaxiStruct.OFFLINE;
				ts.driverNumber = -1;
				tdp.update(ts);
				comLinkTM.logoutOk(taxiNumber);
			}
			else
				comLinkTM.logoutFailure(taxiNumber);
		}
		else
			comLinkTM.logoutFailure(taxiNumber);
	}
	
	/**
	 * A taxidriver turns the meter off.
	 */
	public void meterOff(int taxiNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			if (ts.status == TaxiStruct.SOON_AVAILABLE ||
					ts.status == TaxiStruct.DRIVING_A_CUSTOMER) {
				ts.stopTime = System.currentTimeMillis();
				ts.zone = GPSProxy.getZoneNumber();
				ts.position = GPSProxy.getPosition();
				ts.status = TaxiStruct.AVAILABLE;
				OrderStructC order = odp.get(ts.orderNumber);
				order.status = 3;
				odp.update(order);
				ts.orderNumber = -1;
				tdp.update(ts);
			}
		}
	}
	
	/**
	 * A taxidriver turns the meter on.
	 */
	public void meterOn(int taxiNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			if (ts.status == TaxiStruct.WAITING_FOR_CUSTOMER) {
				ts.status = TaxiStruct.DRIVING_A_CUSTOMER;
				ts.startTime = System.currentTimeMillis();
				OrderStructC order = odp.get(ts.orderNumber);
				order.status = 2;
				odp.update(order);
				ts.zone = GPSProxy.getZoneNumber();
				ts.position = GPSProxy.getPosition();
				tdp.update(ts);
			}
		}
	}
	
	/**
	 * This method sends the order to the operator (which has been entered by a driver) in order to
	 * confirm (the operatornumber is set by the system).
	 */
	public void newOrderO(OrderStructC order) {
		if(tdp.get(order.taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(order.taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			if (operatordp.size() == 0)
				Output.printError("No operator logged in, cannot perform operation.");
			else {
				int operatorNumber = getRandomOperatorNumber();
				TaxiStruct ts = tdp.get(order.taxiNumber);
				ts.status = TaxiStruct.DRIVING_A_CUSTOMER;
				ts.startTime = System.currentTimeMillis();
				ts.orderNumber = nextOrderNumber;
				order.orderNumber = nextOrderNumber;
				order.status = 2;
				nextOrderNumber++;
				tdp.update(ts);
				odp.add(order);
				operatordp.get(operatorNumber).newOrder(order.orderNumber,order.taxiNumber);
			}
		}
	}
	
	/**
	 * An order is sent from the central system to a specific taxi.
	 */
	public void newOrderTM(OrderStructC order, int operatorNumber) {
		if (operatordp.get(operatorNumber) != null) {
			if (order.orderNumber == -1) {
				order.orderNumber = nextOrderNumber;
				nextOrderNumber++;
			}
			order.status = 1;
			if (order.dispatchTime > 0) {
				odp.add(order);
			}
			else {
				int taxiNumber = order.taxiNumber;
				if(taxiNumber > 0 && tdp.get(taxiNumber).status == TaxiStruct.AVAILABLE){
					TaxiStruct ts = tdp.get(taxiNumber);
					ts.orderNumber = order.orderNumber;
					ts.status = TaxiStruct.WAITING_FOR_CUSTOMER;
					tdp.update(ts);
					odp.add(order);
					comLinkTM.newOrder(order, operatorNumber);
				}
				else{
					TaxiStruct ts = getFirstAvailableTaxi();
					if (ts != null) {
						order.taxiNumber = ts.taxiNumber;
						ts.orderNumber = order.orderNumber;
						ts.status = TaxiStruct.WAITING_FOR_CUSTOMER;
						tdp.update(ts);
						odp.add(order);
						comLinkTM.newOrder(order, operatorNumber);
					}
					else {
						order.dispatchTime = order.dispatchTime + 5;
						odp.add(order);
					}
				}//else
			} //else
		} //if
		else
			Output.printError("Operator not logged in.");
	}
	
	/**
	 * A driver rejected an order.
	 */
	public void rejectOrder(int taxiNumber, int operatorNumber, int orderNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if (operatordp.get(operatorNumber) == null) 
			Output.printError("Operator not logged in.");
		else if(odp.get(orderNumber)==null)
			Output.printError("OrderNumber invalid.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			ts.orderNumber = -1;
			ts.status = TaxiStruct.AVAILABLE;
			tdp.update(ts);
			System.out.println("Operator " + operatorNumber + " manual dispatch Order " + orderNumber+".");
			OrderStructC order = (OrderStructC) odp.get(orderNumber);
			operatordp.get(operatorNumber).manualDispatchOrder(taxiNumber,order);
		}
	}
	
	/**
	 * A taxidriver requests the price for an order.
	 */
	public void requestPrice(int taxiNumber, int orderNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if(odp.get(orderNumber)==null)
			Output.printError("OrderNumber invalid.");
		else{
			TaxiStruct ts = tdp.get(taxiNumber);
			double price = (ts.stopTime - ts.startTime) * pricepersecond;
			comLinkTM.responsePrice(taxiNumber, orderNumber, price);
		}
	}

	/**
	 * The alarm for the taxi is reseted.
	 */
	public void resetAlarmTM(int taxiNumber, int operatorNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if (operatordp.get(operatorNumber) == null) 
			Output.printError("Operator not logged in.");
		else
			if (tdp.get(taxiNumber).alarm == true) {
				tdp.get(taxiNumber).alarm = false;
				comLinkTM.resetAlarm(taxiNumber);
			}
			else 
				System.out.println("No alarm started.");
	}
	
	/**
	 * A taxi starts a voice communication to an operator.
	 */
	public void sendVoiceO(int taxiNumber, int operatorNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else{
			if (operatordp.get(operatorNumber) == null)
				Output.printError("No operator logged in, cannot perform operation.");
			else {
				operatordp.get(operatorNumber).sendVoice(taxiNumber);
			}
		}
	}
	
	/**
	 * An operator beginns to talk on a voice communication.
	 */
	public void sendVoiceTM(int taxiNumber, int operatorNumber) {
		if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else if (operatordp.get(operatorNumber) == null) 
			Output.printError("Operator not logged in.");
		else
			comLinkTM.sendVoice(taxiNumber, operatorNumber);
	}
	
	/**
	 * A taxidriver started an alarm.
	 */
	public void startAlarm(int taxiNumber) {
		if (operatordp.size() == 0)
			Output.printError("No operator logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else {
			int operatorNumber = getRandomOperatorNumber();
			tdp.get(taxiNumber).alarm = true;
			operatordp.get(operatorNumber).startVoiceC(taxiNumber);
		}
	}
	
	/**
	 * A voice communication initiated by the driver.
	 */
	public void startVoiceO(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber).status==TaxiStruct.OFFLINE ||
				tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else {
			if (operatorNumber == 0) {
				Random r = new Random();
				operatorNumber = getRandomOperatorNumber();
			}
			operatordp.get(operatorNumber).startVoice(taxiNumber);
		}
	}

	/**
	 * Sent when an operator wants to initiate a voice communication.
	 */
	public void startVoiceTM(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("No operator logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			comLinkTM.startVoice(taxiNumber, operatorNumber);
	}
	
	/**
	 * A taxidriver stops the voice communication.
	 */
	public void stopVoiceO(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else {
			operatordp.get(operatorNumber).stopVoice(taxiNumber);
		}
	}
	
	/**
	 * A operator stops the voice communication.
	 */
	public void stopVoiceTM(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			comLinkTM.stopVoice(taxiNumber, operatorNumber);
	}
	
	/**
	 * When the other side has terminated the communication, this is sent, independent of which side
	 * that terminated first.
	 */
	public void terminateVoiceO(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in, cannot perform operation.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			operatordp.get(operatorNumber).terminateVoice(taxiNumber);
	}
	
	/**
	 * This signal is sent when the operator side has terminated the voice communication. This
	 * method is also used to cancel a voice communication before it is initiated.
	 */
	public void terminateVoiceTM(int taxiNumber, int operatorNumber) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			comLinkTM.terminateVoice(taxiNumber, operatorNumber);			
	}
	
	/**
	 * Sending an text message to the taximodule.
	 */
	public void textMessageTM(int[] taxiNumbers, int messageNumber, String textMessage,
			int operatorNumber) {
		if (operatordp.get(operatorNumber) != null) {
			if ((taxiNumbers.length == 1) && (taxiNumbers[0] == 0)) {
				Enumeration e = tdp.numbers();
				while (e.hasMoreElements()) {
					TaxiStruct ts = (tdp.get(((Integer) e.nextElement()).intValue()));
					if (ts.status == TaxiStruct.OFFLINE ||
							tdp.get(ts.taxiNumber)==null) 
						Output.printError("Taxi not logged in. Cannot perform operation.");
					else
						comLinkTM.textMessage(ts.taxiNumber, messageNumber, textMessage,
								operatorNumber);
				}
			}
			else
				for (int i = 0; i < taxiNumbers.length; i++)
					if(tdp.get(taxiNumbers[i])==null || 
							(tdp.get(taxiNumbers[i]).status == TaxiStruct.OFFLINE))
						Output.printError("Taxi "+taxiNumbers[i]+" not logged in.");
					else
						comLinkTM.textMessage(taxiNumbers[i], messageNumber, textMessage,
							operatorNumber);
		}
		else
			Output.printError("Operator not logged in.");
	}
	
	/**
	 * A voice message from a taxi to an operator.
	 */
	public void voiceMessageO(int taxiNumber, int operatorNumber, String voicedata) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			operatordp.get(operatorNumber).voiceMessage(taxiNumber,voicedata);
	}
	
	/**
	 * This signal contains voice data in digital form (represented by a string).
	 */
	public void voiceMessageTM(int taxiNumber, int operatorNumber, String voicedata) {
		if (operatordp.get(operatorNumber) == null)
			Output.printError("Operator not logged in.");
		else if(tdp.get(taxiNumber)==null)
			Output.printError("Taxi not logged in. Cannot perform operation.");
		else
			comLinkTM.voiceMessage(taxiNumber, operatorNumber, voicedata);
	}
}
