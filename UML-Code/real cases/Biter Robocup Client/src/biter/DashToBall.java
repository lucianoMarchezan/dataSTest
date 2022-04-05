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
 * @version $RevisionL$, $Date: 2001/10/19 21:28:59 $
 * @modified by Hrishikesh J. Goradia 2001/09/08
 */

class DashToBall extends RobocupBehavior
{
		
  private double desiredAngleToBall;
  	
  private double desiredDistanceToBall;	
  
  DashToBall(ActivityManager am, WorldModel wm){
    super(am, wm);
    desiredAngleToBall = 0;
    desiredDistanceToBall = 0.1;
  }

  /** Always return false */
  public boolean canHandle(SensorInput s){
    return false;
  }

  /** Always return true if its an act.*/
  public boolean canHandle(Event e){
		if (e.name.equals("act"))
			return true;
		return false;
  }

  /** Always return false */
  public boolean handle (SensorInput s){
    return false;
  }

  /** If the event is an "act" then do our thing */
  public boolean handle (Event s){

    if (wm.currentCycle <= 0)
      return false;

    if( wm.getBallInfo() == null ) //do not know where the ball is, turn to look for it
		{
			turn( 30 );     
		}
    else
		{
    	SelfInfo self = wm.self;
    	BallInfo ball = wm.getBallInfo();
      Point2D.Double finalPoint = (Point2D.Double) wm.staticObjects.get((wm.self.getSide() == 'l') ? "goal r" : "goal l");
      Point2D.Double ballPredictedLocation = ball.getPredictedFieldPosition();

    	if (ball.getDistance() < wm.cfgData.kickable_area) 
    	{
        AbsoluteMathVector desiredKickVector = new AbsoluteMathVector(ballPredictedLocation, finalPoint);
    		desiredKickVector.addToAngle(desiredAngleToBall);
    		desiredKickVector.setMagnitude(desiredDistanceToBall);
    		Point2D.Double ballDesiredLocation = desiredKickVector.getEndPointLocation();

        //if we can kick the ball, do so.
        kickBallToPoint(ballDesiredLocation, 1);
    	}
    	else 
        dashToPoint(100, findInterceptPoint(self, ball));      		
		
		}
    return false; //never done!
  }

  public boolean inhibits (Activity a){
    return false;
  }
}
