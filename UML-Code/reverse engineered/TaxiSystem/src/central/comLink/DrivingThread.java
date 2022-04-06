/*
 * Created on 18.09.2004
 *
 */
package comLink;

import java.util.Random;

public class DrivingThread extends Thread {
	private int taxiNumber;
	private boolean orderFromOperator;
	private ComLinkTM comLinkTM = null;
	private Random r;
	
	public DrivingThread(ComLinkTM comLinkTM,int taxiNumber, boolean orderfromOperator){
		this.taxiNumber = taxiNumber;
		this.orderFromOperator = orderfromOperator;
		this.comLinkTM = comLinkTM;
		r = new Random();
	}
	
	public void run(){
		System.out.println("Starting DT");
		if(orderFromOperator == true){
			comLinkTM.meterOnC(taxiNumber);
			comLinkTM.driverSoonAvailableC(taxiNumber);
			comLinkTM.meterOffC(taxiNumber);
			
		}
		else{
			comLinkTM.driverSoonAvailableC(taxiNumber);
			comLinkTM.meterOffC(taxiNumber);
		}
	}
}
