package biter;
import java.awt.geom.Point2D;

/*
 *	Kick the ball back and forth between two teams.
 *
 * 	@author Hrishikesh J. Goradia 2001/09/11
 */
 
class KickTest extends RobocupBehavior
{
	private static final int KICK_MODE = 0;
	private static final int WITHDRAW_MODE = 1;
	
	private int mode;
	
	private Point2D.Double myPoint, hisPoint;
	
	private double desiredAngleToBall, desiredDistanceToBall;
	
	KickTest(ActivityManager am, WorldModel wm)
	{
		super(am, wm);
		
		desiredAngleToBall = 0;
		desiredDistanceToBall = 1;
		
		mode = ((wm.self.getSide() == 'l') ? KICK_MODE : WITHDRAW_MODE );
		
		myPoint = ((wm.self.getSide() == 'l') ? new Point2D.Double(-20,0) : new Point2D.Double(20,0));
		//myPoint = new Point2D.Double(-20,0);
		
		hisPoint = ((wm.self.getSide() == 'l') ? new Point2D.Double(20,0) : new Point2D.Double(-20,0));
		//hisPoint = new Point2D.Double(20,0);
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
			double ballDistance;
			System.out.println("MY MODE IS: " + mode);
			
			switch (mode) 
			{
			case KICK_MODE:
					
				kickToOpponent();

				if (((self.getSide() == 'l') && (ball.getPosition().getX() > -5))	// for team on left
						|| ((self.getSide() == 'r') && (ball.getPosition().getX() < 5)))// for team on right
					mode = WITHDRAW_MODE;

				break;
				
			case WITHDRAW_MODE:
				
				withdrawToPosition();

				if (((self.getSide() == 'l') && (ball.getPosition().getX() < -5))	// for team on left
						|| ((self.getSide() == 'r') && (ball.getPosition().getX() > 5)))// for team on right
					mode = KICK_MODE;

				break;
			}
		}
		return false;
	}
	
	public boolean inhibits (Activity a) {
		return false;
	}
	
	public void kickToOpponent()
	{
		SelfInfo self = wm.self;
		BallInfo ball = wm.getBallInfo();
			
		Point2D.Double ballPredictedLocation = ball.getAbsoluteFieldPosition();

		if (ball.getDistance() < wm.cfgData.kickable_area) 
		{
			AbsoluteMathVector desiredKickVector = new AbsoluteMathVector(ballPredictedLocation, hisPoint);
			desiredKickVector.addToAngle(desiredAngleToBall);
			desiredKickVector.setMagnitude(desiredDistanceToBall);
			Point2D.Double ballDesiredLocation = desiredKickVector.getEndPointLocation();

			kickBallToPoint(ballDesiredLocation, 1);
		}
		else 
		{
			dashToPoint(100, findInterceptPoint(self, ball));      		
		}
	}
    
	public void withdrawToPosition()
	{
		SelfInfo self = wm.self;
		BallInfo ball = wm.getBallInfo();
    	
		if (distance(myPoint, self.getAbsoluteFieldPosition()) > 5) {
			dashToPoint(100, myPoint);
			return;
		}
		turn(ball.getDirection());
	}
}
