//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
/**
 * This is a fun little behavior which dribles the ball around the player while
 * the player is standing still. This only works sometimes (because of errors and
 * the fact that the player does not move). The player needs to be placed right
 * in front of the ball for this to work.
 *
 * This behavior was used for testing the accuracy of the kickBallToPoint function.
 * Since this works, we can be pretty sure that kickBallToPoint works OK. 
 * 
 * @author Jose M. Vidal
 * @version $RevisionL$, $Date: 2001/02/27 22:24:31 $
 *
 */

class DribbleAroundPlayer extends RobocupBehavior
{

  /** Each step agent tries to move this much. If this number is too
      high dribbling won't work. If too small, it will be slow. */
  private double moveDistance; 
  
  DribbleAroundPlayer(ActivityManager am, WorldModel wm){
    super(am, wm);
    moveDistance = 1;
    position = 0;
    haveKicked = false;
    direction = 1;
  }

  /** Always return false */
  public boolean canHandle(SensorInput s){
    return false;
  }

  /**  */
  public boolean canHandle(Event e){
		if (e.name.equals("act") && 
        (wm.getBallInfo() != null) &&
        (wm.cfgData.kickable_area + moveDistance >= wm.getBallInfo().getDistance()))
      return true;
    return false;
  }

  /** Always return false */
  public boolean handle (SensorInput s){
    return false;
  }

  private int position;
  /** Kick the ball towards the goal. */

  private Point2D.Double oldpos;

  private boolean haveKicked;

  private int direction;
  
  public boolean handle (Event s){
    System.out.println("DribbleAroundPlayer");
    

    //dont do anything if its not time
    if (wm.isPlayMode("before_kick_off"))
      return false;
//     Point2D.Double point = (Point2D.Double) wm.staticObjects.get((wm.self.getSide() == 'l') ? "goal r" : "goal l");
    //     dribbleBallToPoint(moveDistance, point, wm.self, wm.getBallInfo());

    if (oldpos != null){
      System.out.println("ERROR= " + distance(oldpos,wm.getBallInfo().getAbsoluteFieldPosition()));
    }
    
    if (!haveKicked && wm.isPlayMode("kick_off_" + wm.getSide())){ //we are in kickoff, and we need to kick it
      Point2D.Double mypos = wm.self.getAbsoluteFieldPosition();
      AbsoluteMathVector ballDesiredVector;
      ballDesiredVector = new AbsoluteMathVector(mypos, 1.49, 0); //kick it closer to me
      System.out.println("position=" + position);
      kickBallToPoint(ballDesiredVector.getEndPointLocation());
      oldpos = ballDesiredVector.getEndPointLocation();
      haveKicked = true;
    }
    else if (wm.isPlayMode("play_on")){ //we are in play
      Point2D.Double mypos = wm.self.getAbsoluteFieldPosition();
      AbsoluteMathVector ballDesiredVector = new AbsoluteMathVector(mypos, 1.5, position);
      System.out.println("position=" + position);
      kickBallToPoint(ballDesiredVector.getEndPointLocation());
      oldpos = ballDesiredVector.getEndPointLocation();
      position = (position + direction*30) % 360;
      if (position == 0)
        direction = direction * -1;
    }
    return false; //never done!
  }

  public boolean inhibits (Activity a){
		if (a instanceof DashToBall)
			return true;
		return false;
  }
}
