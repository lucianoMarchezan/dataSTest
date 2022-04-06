/*
 * Created on 01.09.2004
 *
 */
package tools;

/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Output {
	public static String error = new String("");
	
	public static void printError(String e){
		System.out.println(e);
		error = error + e;
	}	
}
