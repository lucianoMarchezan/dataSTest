/*
 * Created on 22.08.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package structures;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DriverStruct {
	public int driverNumber;
	public String name;
	public String properties;
	
	public DriverStruct(int drivernr,String name, String properties){
		this.driverNumber = drivernr;
		this.name = name;
		this.properties = properties;
		
	}
	
	public String toString(){
		return driverNumber+"\n"+name+"\n"+properties;
	}
	
}
