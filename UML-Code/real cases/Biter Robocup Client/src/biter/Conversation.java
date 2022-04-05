//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
 * Every converstation that needs to be handled by the
 * agent is implemented as a sub-class of this class.
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.5 $, $Date: 2001/02/27 22:24:31 $
 *
 */
public abstract class Conversation extends Activity
{

	/** Determine if this conversation can handle the given
      input. Returns false if i is not an instance of Message since
      conversations can only handle Messages.
      @param message the message from another agent.
      @return true if we can handle it */
	public final boolean canHandle(Input i){
		if (i instanceof Message)
      return canHandle((Message)i);
		else
      return false;
	};
  
	public abstract boolean canHandle(Message m);
  
  /** If we want many instances of the same conversation, this can be done
      by having handle() create a new Converstation, adding it to the
      manager and then calling handle() on it */
	public final boolean handle (Input i){
		if (i instanceof Message)
      return handle((Message)i);
		return true;
	};
    
	public abstract boolean handle (Message m);

}
