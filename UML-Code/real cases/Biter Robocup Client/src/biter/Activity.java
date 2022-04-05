//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
 *
 * @author Jose M. Vidal
 * @version $Revision: 1.3 $, $Date: 2001/02/27 22:24:31 $
 *
 */
public abstract class Activity
{
	/** Each activity needs to have a reference to its manager, this is it. */
  protected ActivityManager manager;

  /** A reference to the world model (i.e. agent state) */
  protected WorldModel wm;

  Activity(){
    manager = null;
  }
  
	Activity(ActivityManager am, WorldModel wm){
    manager = am;
    this.wm = wm;
	}
    
	/** Can we handle input i?
     * @param i the input
     * @return true or false */
	public abstract boolean canHandle(Input i);

	/** Handle input i. That is, do what needs to be done.
   * @return true if this activity is done, false if it still has more stuff to do.*/ 
	public abstract boolean handle(Input i);

	/** Are we already busy? If we are then the Activity manager is
     * free to create another instance of this activity.
     * @return true if this activity is busy */
	public abstract boolean busy();

	/** Does this activity inhibit another activity a? Note that a
     * could be another instance of this activity.
     * @param a the other activity.
     * @return true if this activity inhibits a.*/
	public abstract boolean inhibits(Activity a);
    
}
