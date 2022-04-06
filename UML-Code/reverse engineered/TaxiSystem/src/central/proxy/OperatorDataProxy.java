/*
 * Created on 21.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package proxy;

import java.util.Enumeration;
import java.util.Hashtable;

import central.Operator;

/**
 * @author Uni
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperatorDataProxy {
	
	private Hashtable operators = null;
	
	/**
	 * Initializes the data structure to be used to store operators.
	 *
	 */
	public OperatorDataProxy(){
		operators = new Hashtable();
	}
	
	/**
	 * Is used to store an operator inside a data structure.
	 * @param operatorNumber
	 * @param operator
	 */
	public void add(int operatorNumber, Operator operator){
		operators.put(new Integer(operatorNumber),operator);
	}
	
	/**
	 * Is used to delete an operator out of a data structure.
	 * @param operatorNumber
	 */
	public void remove(int operatorNumber){
		operators.remove(new Integer(operatorNumber));
	}
	
	/**
	 * Returns a specific operator to a specific operatornumber;
	 * returns null if the operator was not found.
	 * @param operatorNumber
	 * @return
	 */
	public Operator get(int operatorNumber){
		return (Operator) operators.get(new Integer(operatorNumber));
	}
	
	/**
	 * Returns the number of elements in the structure.
	 * @return
	 */
	public int size(){
		return operators.size();
	}
	
	/**
	 * Returns an Enumeration of all operatornumbers used in the data structure.
	 * @return
	 */
	public Enumeration numbers(){
		return operators.keys();
	}
	
	/**
	 * Returns an Enumeration of all Operators stored in the data structure.
	 * @return
	 */
	public Enumeration objects(){
		return operators.elements();
	}
	
	/**
	 * Is used to update an order inside the data structure.
	 * 
	 * @param order
	 */
	public void update(int operatorNumber, Operator operator){
		operators.put(new Integer(operatorNumber),operator);
	}
}
