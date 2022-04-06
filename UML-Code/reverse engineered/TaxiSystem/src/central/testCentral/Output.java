/*
 * Created on 01.09.2004
 *
 */
package testCentral;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Output {
	public static String result = new String(""); 

	public static void print(String msg){
		System.out.println(msg);
		result = result  + msg + "\n";
	}
	
	public static void resetresult(){
		result = new String("");
	}
	
	
}
