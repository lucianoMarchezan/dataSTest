/*
 * Created on 01.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package central;

import java.util.Enumeration;
import java.util.Random;

import proxy.*;

import structures.OrderStructC;
import structures.TaxiStruct;


import comLink.ComLinkTM;

public class OrderDispatchThread extends Thread {
	
	private long starttime;
	private ComLinkTM comLink;
	private Random r;
	
	private OrderDataProxy odp;
	private OperatorDataProxy operatordp;
	private TaxiDataProxy tdp;
	
	
	private boolean stopThread = false;
	
	
	private long timeout = 1000; //every second...
	/**
	 * This thread automatically dispatches orders where a dispatch time is set;
	 * it needs a DataOrderProxy, TaxiDataProxy, a comLink and the OperatorDataProxy to handle this action.
	 * @param odp
	 * @param tdp
	 * @param comLink
	 * @param operatordp
	 */
	public OrderDispatchThread(OrderDataProxy odp, TaxiDataProxy tdp,ComLinkTM comLink, OperatorDataProxy operatordp) {
		this.odp = odp;
		this.tdp = tdp;
		this.comLink = comLink;
		this.operatordp = operatordp;
		starttime = System.currentTimeMillis();
		r = new Random();
	}
	
	public void run() {
		while (!stopThread) {
			try {
				Thread.sleep(timeout);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!stopThread){
				Enumeration e = odp.numbers();
				int ordernr;
				OrderStructC order = null;
				while(e.hasMoreElements()){
					order = odp.get(((Integer)e.nextElement()).intValue());
					if(System.currentTimeMillis() > ((order.dispatchTime*1000)+starttime)){
						//dispatchTime -1 : the order has been sended by the system
						if(order.status==1){
							TaxiStruct t = getFirstAvailableTaxi();
							if(t == null){
								//setting new dispatch time
								System.out.println("OrderDispatchThread:no taxi available for order "+order.orderNumber);
								order.dispatchTime = order.dispatchTime+2;
								odp.update(order);
							}
							else{
								//found taxi...
								
								//but there is no operator logged in
								if(operatordp.size()==0){
									System.out.println("OrderDispatchThread:No Operator logged in.. dispatch time + 5 secs");
									order.dispatchTime = order.dispatchTime+2;
									odp.add(order);
								}
								else{
									//order.setDispatchTime(-1);
									order.taxiNumber = t.taxiNumber;
									int op = r.nextInt(operatordp.size())+1;
									t.orderNumber = order.orderNumber;
									t.status = TaxiStruct.WAITING_FOR_CUSTOMER;
									tdp.update(t);
									System.out.println("TT:sending order:\n"+order.toString()+"\n to taxi "+t.taxiNumber+" from operator "+op);
									//do not insert it in hashtable because newOrder does it
									comLink.newOrder(order,op);	
								}
							}
						}
					}
				}
			}
			
		}
	}
	
	private TaxiStruct getFirstAvailableTaxi(){
		Enumeration e = tdp.objects();
		TaxiStruct t = null;
		while(e.hasMoreElements()){
			t = (TaxiStruct)e.nextElement();
			if(t.status==TaxiStruct.AVAILABLE){
				System.out.println("found available taxi:"+t.taxiNumber);
				return t;
			}
		}
		return null;
	}
	
/*	public void printtaxistatus(){
		TaxiStruct ti = null;
		for(int i=1;i<tdp.size();i++){
			ti = (TaxiStruct)taxis.get(new Integer(i));
			System.out.println("Taxi "+i+": "+ti.status);
		}
	}*/
	
	public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}
	
}
