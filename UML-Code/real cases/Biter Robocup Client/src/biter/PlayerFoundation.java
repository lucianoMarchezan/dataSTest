//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
  * Abstract super class for Player and Goalie objects. 
  * 
  * @author Paul A. Buhler
  * @version $Revision: 1.12 $, $Date: 2001/02/27 22:24:31 $
  *
  */

public abstract class PlayerFoundation
{

	/** Reference to the agent's world model object. */
	protected WorldModel wm;

	/** The player's number */
	public int playerNumber;

  protected ActivityManager manager;

  protected Point2D.Double startLocation;

	/**
	 * Constructs a new Player object using the supplied objects.
   @param worldModel
   @param startLocation the point on the field where the player will start
   @param number the player's number
  */
	public PlayerFoundation( WorldModel worldModel,
													 Point2D.Double startLocation, int number)
               
	{    
    this.wm = worldModel;
    this.startLocation = startLocation;
    playerNumber = number;

    //create aa new manager
    manager = new ActivityManager(this); 

    // take a position on the field
    wm.dgWrapper.send("(move " + Double.toString( startLocation.getX())
											+ " " + Double.toString(startLocation.getY()) + ")");

    //set my start location and direction
    wm.self.setDirection(0);
    wm.self.setAbsoluteFieldPosition(startLocation);
	}

  /**
   * Get the value of startLocation.
   * @return value of startLocation.
   */
  public Point2D.Double getStartLocation() {
    return startLocation;
  }
  
  /**
   * Set the value of startLocation.
   * @param v  Value to assign to startLocation.
   */
  public void setStartLocation(Point2D.Double  v) {
    
    this.startLocation = v;
  }

}
