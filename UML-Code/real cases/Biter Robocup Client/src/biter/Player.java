//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
/**
 * The decision making, action performing part
 * of the program.  While running, first it looks for the ball.
 * Then it moves to the ball.  It then looks for the goal, and
 * kicks the ball towards it. 
  * 
  * @author Paul A. Buhler
  * @version $Revision: 1.19 $, $Date: 2001/02/27 22:24:31 $
  */

public class Player extends PlayerFoundation
{

	public Player(WorldModel wm, Point2D.Double startLocation, int number)
	{
    super(wm, startLocation, number);
    
    //add some behaviors to the manager
//     Activity dashtoball = new DashToBall(manager, wm);
//     manager.addActivity(dashtoball);

    //This activity dashes to the ball, then dribbles the ball to the goal.
    Activity dribbletogoal = new DribbleToGoal(manager, wm);
    manager.addActivity(dribbletogoal);

    //This activity incorporates new observations into the world model.
    Activity incobs = new IncorporateObservation(manager, wm);
    manager.addActivity(incobs);

    //start it running
    manager.start();
	}
    

}
