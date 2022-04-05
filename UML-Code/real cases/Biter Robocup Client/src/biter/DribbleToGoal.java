//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
/**
 * Go to the ball.
 * 
 * @author Paul A. Buhler and Jose M. Vidal
 * @version $RevisionL$, $Date: 2001/02/27 22:24:31 $
 *
 */

class DribbleToGoal extends RobocupBehavior
{
  /** How much I want to move at each dribble step. Setting this too
      big or too small will not work well. 2 works well. */
  private double moveDistance; 

  private double myDesiredAngleToBall;

  private double myDesiredDistanceToBall;
  
  DribbleToGoal(ActivityManager am, WorldModel wm){
    super(am, wm);
    moveDistance = 2;
    myDesiredAngleToBall = 30;
    myDesiredDistanceToBall = 1;
  }

  /** Always return false */
  public boolean canHandle(SensorInput s){
    return false;
  }

  /**  */
  public boolean canHandle(Event e){
		if (e.name.equals("act"))
        return true;
    return false;
  }

  /** Always return false */
  public boolean handle (SensorInput s){
    return false;
  }

  
  public boolean handle (Event s){
    //    System.out.println("DribbleToGoal");
    
    //dont do anything if its not time
    if (wm.isPlayMode("before_kick_off"))
      return false;

    //if I don't know were the ball is, twirl around.
    if (wm.getBallInfo() == null){
      turn(30);
    }
    else {
      Point2D.Double point = (Point2D.Double) wm.staticObjects.get((wm.self.getSide() == 'l') ? "goal r" : "goal l");
      dribbleBallToPoint(moveDistance, point, myDesiredDistanceToBall, myDesiredAngleToBall);
    }
    return false; //never done!
  }

  public boolean inhibits (Activity a){
		if (a instanceof DashToBall)
			return true;
		return false;
  }
}
