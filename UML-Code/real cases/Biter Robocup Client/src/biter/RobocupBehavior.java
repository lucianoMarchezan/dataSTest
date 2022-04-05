//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * The abstract behavior class.
 * 
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.15 $, $Date: 2001/12/11 16:02:30 $
 */

public abstract class RobocupBehavior extends Behavior
{

  /**
     The constructor.
     @param am the activity manager for this player
     @param wm the world model for this player.
  */
  RobocupBehavior(ActivityManager am, WorldModel wm){
    super(am,wm);
    lastActionWasTurn = false;
  }

  /** Used by dashToPoint to keep track of what we did last time */
  private boolean lastActionWasTurn;
  
  /**
     Causes the player to run towards a point. Takes stamina and effort into account.
     The player might either do a dash or a move, depending on what direction it is
     facing with respect to point.
     @param maxPower the maximum value for the dash power.
     @param point the point to dash to.

     BUGS: if the dash point is very far from the current position
     this function returns 0. It should return maxPower.
  */
  protected void dashToPoint(double maxPower, Point2D.Double point)
  {
    
    //Check whether destination point is within the field
    double x = point.getX();
    double y = point.getY();
    Point2D.Double location = wm.self.getAbsoluteFieldPosition();
    double cx= location.getX();
    double cy = location.getY();
    if((x>51.5)||(x<-51.5)||(y>33)||(y<-33))
    {
      //check whether current position is near boundary lines
      if((cx>51)||(cx<-51)||(cy>32)||(cy<-32))
      {
        //System.out.println("dx = "+x+" dy= "+y+" cx="+cx+" cy="+cy);
        turn(90); //turn to find actual position of ball, the  player is looking at ghost
        return;
      }
      else
      {
        if(x>51.5) x = 51.5;
        else
          if(x<-51.5)x = -51.5;
			
        if(y>33) y = 33;
        else
          if(y<-33) y = -33;
        point = new Point2D.Double(x,y);

      }
    }
	
    MathVector dashVector = new MathVector(location, point);
    double facingDirection = wm.self.getDirection();
    double turnDirection = facingDirection - dashVector.getAngle();
    double distance = dashVector.getMagnitude();

    //    System.out.println("dashing to " + point);

    //Determined using the formula for the dash power rate
    //in section 3.1.4 on player's actions of the SoccerServer
    //manual, version 5, revision 00 beta.
    double dashPower = Math.min(distance * 1000 / wm.cfgData.simulator_step, wm.cfgData.player_speed_max
                                - wm.self.getSense().getSpeed());
    dashPower /=  wm.cfgData.dash_power_rate * wm.self.getSense().getEffort();

    //keep stamina over recover_dec_thr * stamina_max, or else effort and
    //recovery decrease, reducing the power of dashes and the speed at which stamina
    //is recovered.  Stamina is reduced by the power of the dash.
    if (dashPower > wm.self.getSense().getStamina() - wm.cfgData.recover_dec_thr * 
        wm.cfgData.stamina_max - 100)
      dashPower = wm.self.getSense().getStamina() - wm.cfgData.recover_dec_thr * 
        wm.cfgData.stamina_max - 100;

    if (dashPower > maxPower)
      dashPower = maxPower;
    else if (dashPower < 0)
      dashPower = 0;

    double distanceTravelled = dashPower * wm.cfgData.dash_power_rate* wm.self.getSense().getEffort();
    MathVector myPosition = wm.self.getPosition();
    MathVector actualDashVector = new MathVector(distanceTravelled, facingDirection);
    
      
    // If angle to point is too big then turn, if small then dash, otherwise alternate between them.
    if( Math.abs(turnDirection) > 45){
      turn( turnDirection );
      myPosition.setAngle(turnDirection); //update my angle
    }
    else if (Math.abs(turnDirection) < 5){
      dash( dashPower );
      myPosition.add(actualDashVector); //update my position vector
    }
    else if (lastActionWasTurn){
      dash( dashPower );
      myPosition.add(actualDashVector);
    }
    else {
      turn( turnDirection );
      myPosition.setAngle(turnDirection);
    }
    
    //    System.out.println("actual dash to " + ((AbsoluteMathVector)myPosition).getEndPointLocation());
  }
  

  /** Kiks the ball so that it will be at point on the next tick.
      @param point the point where we want the ball to be in the next tick.
  */
  protected void kickBallToPoint(Point2D.Double point){
    kickBallToPoint(point, 1);
  }

  /** Kiks the ball so that it will be at point on the next tick.
      @param point the point where we want the ball to be in the next tick.
      @param steps how many steps the ball should take to reach point. The calculation does not take into account the ball_day.
  */
  protected void kickBallToPoint(Point2D.Double point, int steps){

    BallInfo ball = wm.getBallInfo();
    SelfInfo self = wm.self;
    double margin = wm.cfgData.player_size + wm.cfgData.ball_size + .1;
    point = isStraightKick(point, margin);

//     System.out.println("kickBallToPoint");
//     System.out.println("point=" + point);
    
    double dir_diff = Math.abs(ball.getDirection());
    double dist_ball = ball.getDistance();
    MathVector kickVector = new MathVector(ball.getAbsoluteFieldPosition(), point);
    kickVector.subtract(ball.getPosition()); //correct for ball momentum

    //not really correct since it does not take into account ball_decay, but approx.
    double distance = kickVector.getMagnitude()/steps;
    
    double power = distance / ((1
                                - 0.25 * (dir_diff/180)
                                - 0.25 * (dist_ball - wm.cfgData.player_size - wm.cfgData.ball_size)/ wm.cfgData.kickable_margin)
                               * wm.cfgData.kick_power_rate);
    
    if (power > wm.cfgData.maxpower) //can only kick so hard.
      power = wm.cfgData.maxpower;
    
    double direction = self.getDirection() - kickVector.getAngle();

    //update the ball's vector in the world model
    if (wm.getBallInfo() != null){
      MathVector ballvector = wm.getBallInfo().getPosition();
      ballvector.add(kickVector);
    }

    //fix the direction
    if (direction > 180)
      while (direction > 180) direction -= 360;
    else if (direction < -180)
      while (direction < -180) direction += 360;

    //wm.dgWrapper.send("(kick " + Double.toString(Math.round(power)) + " " + Double.toString(direction) + ")");
    wm.dgWrapper.send("(kick " + Double.toString(power) + " " + Double.toString(direction) + ")");
  }

  /**
     Calculate the distance between two points.
     @param a one point
     @param b another point
     @return the distance between a and b */
  protected double distance (Point2D.Double a, Point2D.Double b){
    return Math.sqrt((a.getX() - b.getX())*(a.getX() - b.getX()) +
                     (a.getY() - b.getY())*(a.getY() - b.getY()));
  }

  /**
     Dribble the ball to a specific point. One side-effect of this algorithms is that
     if the player is too far from the ball to kick it, the player will dash towards the ball.
     @param moveDistance how far should the player move on each move. This is a distance, not a power
     @param finalPoint the desired location of the ball.
     @param desiredDistanceToBall The angle we want for the arc formed by the ball's location, the player's location,
     and the location the player wants to be in.
     @param desiredAngleToBall The angle we want for the arc formed by the ball's location, the player's location,
     and the location the player wants to be in. 
  */
  protected void dribbleBallToPoint(double moveDistance, Point2D.Double finalPoint,
                                    double desiredDistanceToBall, double desiredAngleToBall)
  {
    SelfInfo self = wm.self;
    BallInfo ball = wm.getBallInfo();
    double margin = wm.cfgData.player_size + wm.cfgData.ball_size + .1;
    Point2D.Double mypos = self.getAbsoluteFieldPosition();

    AbsoluteMathVector myDesiredVector = new AbsoluteMathVector(mypos, finalPoint);
    myDesiredVector.setMagnitude(moveDistance);
    Point2D.Double myDesiredLocation = myDesiredVector.getEndPointLocation();

    Point2D.Double ballPredictedLocation = ball.getPosition().getEndPointLocation();

//    System.out.println("mypos= " + self.getPosition());
//    System.out.println("mydirection= " + self.getDirection());
//    System.out.println("ballpos= " + ball.getPosition());
    
    if ((distance(myDesiredLocation, ballPredictedLocation) < wm.cfgData.kickable_area) &&
        (distance(myDesiredLocation, ballPredictedLocation) > wm.cfgData.player_size + wm.cfgData.ball_size + .1))
    {
//       AbsoluteMathVector trajectory = new AbsoluteMathVector(mypos,myDesiredLocation);
//       Point2D.Double closestPoint = trajectory.getClosestPoint(ball.getAbsoluteFieldPosition());
//       if (distance(closestPoint,ball.getAbsoluteFieldPosition()) > margin){
      dashToPoint(100, myDesiredLocation);
      return;
        //      }
    }
    
    AbsoluteMathVector ballDesiredVector = new AbsoluteMathVector(myDesiredLocation, finalPoint);

    ballDesiredVector.addToAngle(desiredAngleToBall);
    ballDesiredVector.setMagnitude(desiredDistanceToBall);
    Point2D.Double ballDesiredLocation = ballDesiredVector.getEndPointLocation();

    if (ball.getDistance() < wm.cfgData.kickable_area) {
      //if we can kick the ball, do so.
      kickBallToPoint(ballDesiredLocation, 3);
    }
    else {
      //ball has gotten away from us, go to it.
      Point2D.Double ballFutureLocation = ball.getPosition().getEndPointLocation();
      myDesiredVector = new AbsoluteMathVector(ballFutureLocation, desiredDistanceToBall, desiredAngleToBall);
      myDesiredVector.addToAngle(180);
//       System.out.println("ballfuture= " + ballFutureLocation);
//       System.out.println("dashingto= " + myDesiredVector.getEndPointLocation());
      dashToPoint(100, myDesiredVector.getEndPointLocation());
    }
  }
    

  /** Can the ball get to point in a straight kick? without hitting the player and without entering a safety
      margin aroune the player?
     @param point the destination point
     @param margin the minimum distance we want between the player and the ball, at any time.
     @return The point to which the agent should kick. If its the same as point then a straight kick is enough. */
  protected Point2D.Double isStraightKick(Point2D.Double point, double margin){

    SelfInfo self = wm.self;
    BallInfo ball = wm.getBallInfo();
    Point2D.Double mypos = self.getAbsoluteFieldPosition();
    MathVector destVector = new MathVector(mypos, point); 

    Point2D.Double ballpos = ball.getAbsoluteFieldPosition();
    AbsoluteMathVector kickVector = new AbsoluteMathVector(ball.getAbsoluteFieldPosition(), point);
    
    double angle = Math.acos((destVector.getMagnitude()*destVector.getMagnitude() -
                              kickVector.getMagnitude()*kickVector.getMagnitude() -
                              ball.getDistance()*ball.getDistance())/
                             ( - 2 * ball.getDistance() * kickVector.getMagnitude()));
    double dist = Math.sin(angle) * ball.getDistance();
//     System.out.println("dist=" + dist + " margin=" + margin);
//     System.out.println("angle=" + Math.toDegrees(angle));

    //if Me,ball,point angle is obtuse then its a straight kick
    if (Math.abs(Math.toDegrees(angle)) > 85){ //should be 90, but we allow for some error.
      //      System.out.println("Obvious straight kick");
      return point;
    }

    Point2D.Double closestPoint = kickVector.getClosestPoint(mypos);

    //If the closest point between ball-to-point and me is within margin, then kick around margin.
    if (distance(closestPoint, mypos) <= margin) { //closest point
      kickVector = new AbsoluteMathVector(mypos, closestPoint);
      kickVector.setMagnitude(margin);
      //      System.out.println("Intermediate kick to " + kickVector.getEndPointLocation());
      return kickVector.getEndPointLocation();
    }
    //    System.out.println("Straight kick");
    return point;
  }
                                  
  /**
   * Finds an interception point with the ball. It iss not
   * perfect, but it greatly reduces the number of times a player 
   * turns when intercepting a ball.
   * @param self contains information on the agent calling the method
   * @param player contains information on the player
   * @param ball contains information on the ball
   * @return the interception point.
   */
  protected Point2D.Double findInterceptPoint(SelfInfo self, PlayerInfo player, BallInfo ball)
  {
    double ballX, ballY, ballDistance, ballVelocityX, ballVelocityY, playerX, playerY, 
      interceptX, interceptY;
    int time = 0;
    ballX = ball.getAbsoluteFieldPosition().getX();
    ballY = ball.getAbsoluteFieldPosition().getY();
    playerX = player.getAbsoluteFieldPosition().getX();
    playerY = player.getAbsoluteFieldPosition().getY();
  
    ballDistance = Math.sqrt((ballX - playerX) * (ballX - playerX) + 
                             (ballY - playerY) * (ballY - playerY));
    //calculate ball velocity
    double xPoint1, xPoint2, yPoint1, yPoint2;//points relative to the agent calling the method.
    xPoint1 = ball.getDistance() * Math.cos(Math.toRadians(self.getDirection() - ball.getDirection()));
    yPoint1 = ball.getDistance() * Math.sin(Math.toRadians(self.getDirection() - ball.getDirection()));
    xPoint2 = (ball.getDistance() + ball.getDistChng()) *
      Math.cos(Math.toRadians(
                              self.getDirection() - ball.getDirection() - ball.getDirChng()));
    yPoint2 = (ball.getDistance() + ball.getDistChng()) * 
      Math.sin(Math.toRadians(
                              self.getDirection() - ball.getDirection() - ball.getDirChng()));

    ballVelocityX = (xPoint2 - xPoint1);
    ballVelocityY = (yPoint2 - yPoint1);

    //The maximum distance the player can move is represented by
    //time * max_speed.  Dash at the first point the ball is at 
    //within that distance.
    while (ballDistance > time * wm.cfgData.player_speed_max * wm.cfgData.simulator_step / 100 && time < 50)
    {
      //advance the ball's estimated position one time cycle
      ballX += ballVelocityX;
      ballY += ballVelocityY;
      ballVelocityX *= wm.cfgData.ball_decay;
      ballVelocityY *= wm.cfgData.ball_decay;
      ballDistance = Math.sqrt((ballX - playerX) * (ballX - playerX) + 
                               (ballY - playerY) * (ballY - playerY));
      time++;
    }
    return new Point2D.Double(ballX, ballY);
  }

  /**
   	* Finds an interception point with the ball. 
   	* @param self contains information on the agent calling the method
   	* @param ball contains information on the ball
   	* @return the interception point.
   	*
   	* @author Hrishikesh J. Goradia 2001/08/26
   	*/
  protected Point2D.Double findInterceptPoint(SelfInfo self, BallInfo ball)
  {
    Point2D.Double ballPredictedLocation, selfPredictedLocation;
    double ballX, ballY, ballDistance, ballVelocityX, ballVelocityY, 
      selfX, selfY, interceptX, interceptY;
    int time = 0;
    	
    ballPredictedLocation = ball.getPredictedFieldPosition();
    selfPredictedLocation = self.getPredictedFieldPosition();

    ballX = ballPredictedLocation.getX();
    ballY = ballPredictedLocation.getY();
    selfX = selfPredictedLocation.getX();
    selfY = selfPredictedLocation.getY();
  
    ballDistance = Math.sqrt((ballX - selfX) * (ballX - selfX) + 
                             (ballY - selfY) * (ballY - selfY));
    //calculate ball velocity
    double xPoint1, xPoint2, yPoint1, yPoint2;//points relative to the agent calling the method.
    xPoint1 = ball.getDistance() * Math.cos(Math.toRadians(self.getDirection() - ball.getDirection()));
    yPoint1 = ball.getDistance() * Math.sin(Math.toRadians(self.getDirection() - ball.getDirection()));
    xPoint2 = (ball.getDistance() + ball.getDistChng()) * 
      Math.cos(Math.toRadians(self.getDirection() - ball.getDirection() - ball.getDirChng()));
    yPoint2 = (ball.getDistance() + ball.getDistChng()) * 
      Math.sin(Math.toRadians(self.getDirection() - ball.getDirection() - ball.getDirChng()));

    ballVelocityX = (xPoint1 - xPoint2);
    ballVelocityY = (yPoint1 - yPoint2);

    //The maximum distance the player can move is represented by
    //time * max_speed.  Dash at the first point the ball is at 
    //within that distance.
    while (ballDistance > time * wm.cfgData.player_speed_max * wm.cfgData.simulator_step / 100 && time < 50)
    {
      //advance the ball's estimated position one time cycle
      ballX += ballVelocityX;
      ballY += ballVelocityY;
      ballVelocityX *= wm.cfgData.ball_decay;
      ballVelocityY *= wm.cfgData.ball_decay;
      ballDistance = Math.sqrt((ballX - selfX) * (ballX - selfX) + 
                               (ballY - selfY) * (ballY - selfY));
      time++;
    }
    return new Point2D.Double(ballX, ballY);
  }

  /**
   * Determines how many players are in a rectangular area
   * @param left contains the leftmost x coordinate of the rectangular region
   * @param top contains the upper y coordinate of the rectangular region
   * @param right contains the rightmost x coordinate of the rectangular region
   * @param bottom contains the lower y coordinate of the rectangular region
   * @param dynamicInfo contains information on the players
   * @param bOnlyTeamMembers is a flag that indicates filtering by teamName
   * @param teamName is the name of the team to filter on
   */
  protected int playersInRect(double left, double top, double right, double bottom, 
                              ArrayList dynamicInfo, boolean bOnlyTeamMembers, String teamName)
  {
    int rtnval = 0;
    String filterString = (bOnlyTeamMembers) ? "[pP]layer " + teamName : "[pP]layer.*";

    // create a Rectangle object representing the region of interest
    Rectangle2D.Double rect = new Rectangle2D.Double( left, top, right - left, top - bottom );

    FilteredIterator iterator = new FilteredIterator(dynamicInfo.iterator(), filterString);
    while (iterator.hasNext())
    {
      PlayerInfo player = (PlayerInfo)iterator.next();
      Point2D.Double location = player.getAbsoluteFieldPosition();

      // is the player's location contained in the rectangular region
      if( rect.contains( location ) )
        rtnval++;
    }

    return rtnval;
  }

  /**
   * Determines how many players are in a region defined as a cone 
   * @param radius contains the radius of the cone 
   * @param thetaMin contains the minimal angle 
   * @param thetaMax contains the maximal angle 
   * @param dynamicInfo contains information on the players
   * @param bOnlyTeamMembers is a flag that indicates filtering by teamName
   * @param teamName is the name of the team to filter on
   */
  protected int playersInCone(double r, double thetaMin, double thetaMax,
                              ArrayList dynamicInfo, boolean bOnlyTeamMembers, String teamName)
  {
    int rtnval = 0;
    String filterString = (bOnlyTeamMembers) ? "[pP]layer " + teamName : "[pP]layer.*";
    FilteredIterator iterator = new FilteredIterator(dynamicInfo.iterator(), filterString);

    while (iterator.hasNext())
    {
      PlayerInfo player = (PlayerInfo)iterator.next();

      // test to see if the player is in the cone
      if (player.getDistance() < r &&
          player.getDirection() > thetaMin &&
          player.getDirection() < thetaMax)
        rtnval++;
    }

    return rtnval;
  }

  /**
   * This function sends a move command to the server
   */
  protected void move(double x, double y)
  {
    wm.dgWrapper.send("(move " + Double.toString(x) + " " + Double.toString(y) + ")");
    lastActionWasTurn = false;
  }

  /**
   * This function sends a move command to the server
   */
  protected void move(Point2D.Double p) {
    move(p.getX(), p.getY());
  }
    
  /**
   * This function sends a turn command to the server
   */
  protected void turn(double moment)
  {
    if (moment > 180)
      while (moment > 180) moment -= 360;
    else if (moment < -180)
      while (moment < -180) moment += 360;
    wm.self.doTurn(moment);
     wm.dgWrapper.send("(turn " + Double.toString(moment) + ")");
    lastActionWasTurn = true;
  }
    
  /**
   * This function sends a dash command to the server
   */
  protected void dash(double power)
  {
     wm.dgWrapper.send("(dash " + Double.toString(power) + ")");
     lastActionWasTurn = false;
  }
    
  /**
   * This function sends a kick command to the server.
   @param power the power
   @param direction the direction in which to kick the ball
   */
  protected void kick(double power, double direction)
  {
    if (direction > 180)
      while (direction > 180) direction -= 360;
    else if (direction < -180)
      while (direction < -180) direction += 360;
    //wm.dgWrapper.send("(kick " + Double.toString(Math.round(power)) + " " + Double.toString(direction) + ")");
    wm.dgWrapper.send("(kick " + Double.toString(power) + " " + Double.toString(direction) + ")");
    lastActionWasTurn = false;
  }
    
  /**
   * This function sends a say command to the server
   */
  protected void say(String message)
  {
    wm.dgWrapper.send("(say " + message + ")");
  }
    
  /**
   * This function sends a chage_view command to the server
   */
  protected void changeView(String angle, String quality)
  {
    wm.dgWrapper.send("(change_view " + angle + " " + quality + ")");
  }

  /**
   * This function sends the 'sense_body' command to the server.
   * @deprecated This function is not used for versions of SoccerServer
   * greater than 5.00.  In such later versions, sense_body information
   * is automaticly sent to the agents at a regular interval.
   */
  protected void senseBody()
  {
    wm.dgWrapper.send("(sense_body)");
  }

  /**
   * This function sends the 'catchBall' command to the server.
   * Only applicable for a goalie.
   */
  protected void catchBall(int direction)
  {
    wm.dgWrapper.send("(catch " + direction + ")");
  }
}
