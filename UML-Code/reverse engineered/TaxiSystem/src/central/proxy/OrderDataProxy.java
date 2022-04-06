/*
 * Created on 20.09.2004 TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package proxy;
import java.util.*;
import structures.OrderStructC;
/**
 * @author user TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class OrderDataProxy {
	private Hashtable allOrders = null;

	/**
	 * Initializes the data structure to be used to store orders.
	 */
	public OrderDataProxy() {
		allOrders = new Hashtable();
	}

	/**
	 * Is used to store an order inside a data structure.
	 * 
	 * @param order
	 */
	public void add(OrderStructC order) {
		allOrders.put(new Integer(order.orderNumber), order);
	}

	/**
	 * Is used to remove an order from the data structure.
	 * 
	 * @param orderNumber
	 */
	public void remove(int orderNumber) {
		allOrders.remove(new Integer(orderNumber));
	}

	/**
	 * Is used to update an order inside the data structure.
	 * 
	 * @param order
	 */
	public void update(OrderStructC order) {
		allOrders.put(new Integer(order.orderNumber), order);
	}

	/**
	 * Returns an order from the data structure specified by it's orderNumber;
	 * returns null if the order was not found.
	 * 
	 * @param orderNumber
	 * @return
	 */
	public OrderStructC get(int orderNumber) {
		return (OrderStructC) allOrders.get(new Integer(orderNumber));
	}

	/**
	 * Returns the number of available orders in the data structure.
	 * 
	 * @return
	 */
	public int size() {
		return allOrders.size();
	}

	/**
	 * Returns an Enumeration of all orderNumbers inside the data structure.
	 * 
	 * @return
	 */
	public Enumeration numbers() {
		return allOrders.keys();
	}
	/**
	 * Returns an Enumeration of all OrderStructs stored in the data structure.
	 * @return
	 */
	public Enumeration objects(){
		return allOrders.elements();
	}
	
}