//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.Vector;

/**
 * An agent-created event. These are usually "alarms" set to go off
 * after a certain time period.
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.6 $, $Date: 2001/02/27 22:24:31 $
 *
 */

public class Event extends Input
{
  /** The time when this event is scheduled to happen */
	public long time;

  /** The name of this event */
	public String name;
    
	Event (String name, long time){
		this.time = time;
		this.name = name;
    this.realTime = System.currentTimeMillis();
	}

	Event (Event e){
		this.time = e.time;
		this.name = e.name;
    this.realTime = System.currentTimeMillis();
	}

	/** Does this event happen before another one?
     * @param e the other event
     * @return true this.time < e.time */
	public boolean isBefore (Event e){
		return time < e.time;
	}

	public String toString(){
    return "Event name: " + name + "\ntime: " + time + "\n";
  }

  /**
     @param e the other event to compare to.
     @return true if both have the same name */
  public boolean equals(Event e){
    return name.equals(e.name);
  }
}

