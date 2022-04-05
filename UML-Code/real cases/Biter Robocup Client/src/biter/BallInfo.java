//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
  * Contains information about the ball.
  * Does not add to the DynamicObjectInfo class,
  * but is used for clarification.
  *
  * @author Shaun P. Wood
  * @version $Revision: 1.8 $, $Date: 2001/02/27 22:24:31 $
  *
*/

public class BallInfo extends DynamicObjectInfo
{
	/**
	 * Constructs a new BallInfo object
	 * from information contained in an
	 * ObjectInfo object.  Called by
	 * WorldModel.updateReactiveWorldModel.
	 * @param object contains the data for
	 * the new BallInfo object
	 */
	public BallInfo(ObjectInfo object)
	{
		super(object);
    setObjectName("ball");
	}

  public BallInfo(String name, int timeStamp){
    super(name, timeStamp);
  }

  public boolean equals(BallInfo b){
    return true;
  }
}
