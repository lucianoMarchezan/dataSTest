//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
 * The abstract behavior class.
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.5 $, $Date: 2001/02/27 22:24:31 $
 *
 */
public abstract class Behavior extends Activity
{

  Behavior(ActivityManager am, WorldModel wm){
    super(am,wm);
	}

	/** Determine if this Behavior can handle the given input. This will
	return false if i is an instanceof Message since those should be
	handled by a Conversation. Otherwise, we call the appropicate
	canHandle method.
  @param i the input
  @return true if we can handle it */
	public final boolean canHandle(Input i){
    if (i instanceof SensorInput)
      return canHandle((SensorInput)i);
    else if (i instanceof Event)
      return canHandle((Event)i);
    return false;
	};

  /** The classes that inheret from this one must implement this.*/
	public abstract boolean canHandle(SensorInput s);
  /** The classes that inheret from this one must implement this.*/
	public abstract boolean canHandle(Event e);
  
	/** Have this Behavior fandle the given input. This will return true
	without doing anything if i is an instanceof Message since those
	should be handled by a Conversation. Otherwise, we call the
	appropicate canHandle method.
  @param i the input
  @return true if we can handle it */
	public final boolean handle (Input i){
    if (i instanceof SensorInput)
      return handle((SensorInput)i);
    else if (i instanceof Event)
      return handle((Event)i);
    return true;
	};

  /** The classes that inheret from this one must implement this.*/
	public abstract boolean handle (SensorInput s);
  
  /** The classes that inheret from this one must implement this.*/
  public abstract boolean handle (Event e);

	/** We should have at most one instance of each behavior executing 
	 * at any one time, therefore this will always return true.
	 * @return true, always */
	public boolean busy(){
    return true;
	}

}
