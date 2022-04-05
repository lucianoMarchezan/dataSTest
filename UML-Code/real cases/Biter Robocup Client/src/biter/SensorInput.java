//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.ArrayList;

/**
 * A message from another agent.
 * 
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.4 $, $Date: 2001/02/27 22:24:31 $
 *
 */

public abstract class SensorInput extends Input {

  public SensorInput(){
  }

  public SensorInput(long timeStamp){
    super(timeStamp);
  }

  public String toString(){
    return super.toString();
  }

}
    
