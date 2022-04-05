//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;

/**
  * Like the Player, the Goalie makes decisions
  * and performs actions, only its are specific
  * to a Goalie's behavior.
  *
  * @author Shaun P. Wood
  * @version $Revision: 1.11 $, $Date: 2001/02/27 22:24:31 $
  *
*/

public class Goalie extends PlayerFoundation
{
  ActivityManager manager;

	/** Helps the goalie remember if and when it caught the ball. */
	private int caughtBall;

  /**
	 * Constructs a new Goalie object from the
	 * supplied objects.
	 * @param dgWrapper used to communicate to the server and create
	 * log files
	 * @param memory the players memory object
	 * @param configData the data from the configuration file
	 */
	public Goalie(WorldModel wm, int number)
	{
    super(wm, new Point2D.Double(-51.5, 0), number);
    caughtBall = -1;

    manager = new ActivityManager(this); //create aa new manager

    //add some behaviors to the manager

    //start it running
    manager.start();
	}

	//     public void act( ArrayList dynamicObjects,
	//              ArrayList hearInformation,
	//              HashMap staticObjects,
	//              SelfInfo self,
	//              RefereeMessage lastMessage,
	//              Object myData )
	//     {
	//    BallInfo ball;
	//    FilteredIterator filteredIterator;
	//    filteredIterator = new FilteredIterator(dynamicObjects.iterator(), "[bB]all");
	//    ball = (BallInfo)filteredIterator.next();
	//    //If you've recently caught the ball, move just inside the lower penalty box
	//    //flag and kick the ball away, then return to the goal.
	//    if (caughtBall >= 0)
	//    {
	//      caughtBall++;
	//      if (caughtBall < 2)
	//        {
	//        Point2D.Double penaltyBoxBottom = (Point2D.Double)staticObjects.get(
	//                            "flag p " + self.getSide() + " b");
	//        move(penaltyBoxBottom.getX(), penaltyBoxBottom.getY());
	//        }
	//      else if (caughtBall < 5)
	//      {
	//        if (ball != null)
	//          kickBallToPoint((Point2D.Double)staticObjects.get( "goal " + 
	//            ((self.getSide() == 'l') ? "r" : "l") ), self, ball);
	//        else
	//        {
	//          //You're kicking it towards the goal, so turn goal-wards (straight
	//          //accross the field
	//          double turnDirection;
	//          turnDirection = (self.getSide() == 'l')?0:180 - self.getDirection();
	//          if (turnDirection > 180)  
	//            while (turnDirection > 180) turnDirection -= 360;
	//          else if (turnDirection < -180)
	//            while (turnDirection < -180) turnDirection += 360;
	//          turn(turnDirection);
	//        }
	//      }
	//      else
	//        {
	//        Point2D.Double me, myGoal;
	//        double xChange, yChange;

	//        me = self.getAbsoluteFieldPosition();
	//        myGoal = (Point2D.Double)staticObjects.get("goal " + self.getSide());
	//        xChange = me.getX() - myGoal.getX();
	//        yChange = me.getY() - myGoal.getY();
	//        if (Math.sqrt(xChange * xChange + yChange * yChange) > 5.)
	//            dashToPoint(80, self, myGoal);
	//        else
	//            caughtBall = -1;
	//        }
	//      return;
	//      }

	//    //Stay on top of Goal flag and watch ball
	//    //Whenever someone is nearby, go to them
	//    //and catch the ball
	//    if (ball != null)
	//      {
	//      double xOffset, yOffset;
	//      xOffset = ball.getDistance() * Math.cos(Math.toRadians(ball.getDirection()));
	//      yOffset = ball.getDistance() * Math.sin(Math.toRadians(ball.getDirection())); 

	//      //ball is in catchable range, catch it
	//      if ((Math.abs(xOffset) < cfgData.catchable_area_l / 2) &&
	//          (Math.abs(yOffset) < cfgData.catchable_area_w / 2))
	//        {
	//        catchBall( (int)ball.getDirection() );
	//        //Assume it catches the ball because there is no way of checking right
	//        //now - server sends the 'goalie_catch_ball message, but also sends
	//        //the free_kick_right, which usually covers it up.
	//        caughtBall = 0; 
	//        }
	//      //ball is nearby, move to intercept
	//      else if (ball.getDistance() < 15.)
	//          dashToPoint(120, self, ball.getAbsoluteFieldPosition());
	//      //ball is far away; if you're far from the goal, move back to it
	//      //otherwise, keep your eye on the ball
	//      else if (ball.getDistance() > 30.)
	//        {
	//        Point2D.Double me, myGoal;
	//        double xChange, yChange, distance;
	//        me = self.getAbsoluteFieldPosition();
	//        myGoal = (Point2D.Double)staticObjects.get("goal " + self.getSide());
	//        xChange = me.getX() - myGoal.getX();
	//        yChange = me.getY() - myGoal.getY();

	//        if (Math.sqrt(xChange * xChange + yChange * yChange) > 5.)
	//            dashToPoint(40, self, myGoal);
	//        else
	//          {
	//          if (self.getTimeStamp() % 5 != 0)
	//            turn (ball.getDirection());
	//          else
	//            say (ball.toMessage());
	//          }
	//        }
	//      //ball is within range of concern, keep watching it.
	//      else
	//          turn (ball.getDirection());
	//      }
	//    //can't see ball; if too far from goal, move back, else look for ball
	//    else
	//      {
	//      Point2D.Double me, myGoal;
	//      double xChange, yChange, distance;
	//      me = self.getAbsoluteFieldPosition();
	//        myGoal = (Point2D.Double)staticObjects.get("goal " + self.getSide());
	//      xChange = me.getX() - myGoal.getX();
	//      yChange = me.getY() - myGoal.getY();
	//      if (Math.sqrt(xChange * xChange + yChange * yChange) > 5.)
	//          dashToPoint(40, self, myGoal);
	//      else
	//          turn (60);
	//      }
	//  }
}
