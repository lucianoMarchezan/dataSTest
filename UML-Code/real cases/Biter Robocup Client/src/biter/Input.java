//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
 * The most general from of input
 * 
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.5 $, $Date: 2001/02/27 22:24:31 $
 *
 */
public abstract class Input
{
	/** The time this input was created */
	protected long timeStamp;

  /** The real time, in ms since the epoch, when this input was received */
  protected long realTime;
  
	Input(){
    timeStamp = 0;
	}
    
	/** Constructor.
     * @param timeStamp the time of creation, we let this be a parameter
     * because the time might be simulated time. */
	Input(long timeStamp){
    this.timeStamp = timeStamp;
	}

  public long getRealTime(){
    return realTime;
  }
  
  /**
     * Gets the time stamp from the object,
     * @param data the object to get the time stamp from
     */
	public long getTimeStamp()
	{
    return timeStamp;
	}

  /**
   * Sets the time stamp.
  */
	public void setTimeStamp(long timeStamp)
	{
    this.timeStamp = timeStamp;
	}
  public String toString(){
    String s = new String("timeStamp: " + timeStamp);
    return s;
  }

}
