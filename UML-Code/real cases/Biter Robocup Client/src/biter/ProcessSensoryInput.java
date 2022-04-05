//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.*;
import java.awt.geom.Point2D;

/**
  * Parses information from the server into a SensorInput
  * Object. This is a factory for SensorInput objects. The
  * actual parsing, however, is done by the individual Input
  * classes.
  * 
  * @author Paul A. Buhler
  * @version $Revision: 1.15 $, $Date: 2001/02/27 22:24:31 $
  *
*/
public class ProcessSensoryInput
{
	/**
	 * @param data the String method sent by the server
	 * @return the sensorInput
	 */
	public static SensorInput parse(String data )
	{
    long timeReceived = System.currentTimeMillis();
    SensorInput rtnval = null;
    //    System.out.println(data);
    if (data == null){
      System.out.println("parse called with null data");
      return null;
    }
    if (data == ""){
      System.out.println("parse called with null string");
      return null;
    }
    try {
      if( data.charAt(1) == 's' )
      {
        if( data.charAt(3) == 'e'){ //A 'see' message
          rtnval = new ObjectInfoContainer(data, timeReceived);
        }
        else{       //A 'sense_body' message
          rtnval = new SenseBody( data, timeReceived );
        }
        //      System.out.println(rtnval.toString());
      }
      else {
        StringTokenizer strtok = new StringTokenizer(data.substring(1, data.length() - 1));
        if( strtok.nextToken().equals( "hear" ) )
        {
          System.out.println("hear: " + data);
          String timestamp = strtok.nextToken();
          String sender = strtok.nextToken();
          if( sender.equals("referee") ) {
            rtnval = new RefereeMessage(data, timeReceived);
          }
          else {
            System.out.println("Unknown hear message:" + data);
            rtnval = null;
          }
          //else..add code to deal with messages from other agents
        }
        else {
          System.out.println("ERROR: Unknown message: " + data);
        }
      }
      return rtnval;
    }
    catch (StringIndexOutOfBoundsException e) {
      System.out.println("ERROR: ProcessSensoryInput.parse(). Could not parse:\n" + data);
      return null;
    }
      
  }
}
