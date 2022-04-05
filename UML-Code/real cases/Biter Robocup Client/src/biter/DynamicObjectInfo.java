//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.awt.geom.Point2D;

/**
  * Generic parent class for objects
  * that will go on the GlobalMemory's
  * dynamicObject list.  The classes will
  * actually be of type PlayerInfo or BallInfo
  * but will share DynamicObjectInfo as a parent.
  *
  * @author Shaun P. Wood
  * @version $Revision: 1.14 $, $Date: 2001/02/27 22:24:31 $
  *
*/
public class DynamicObjectInfo extends ObjectInfo
{
  /** The absolute field position and a vector indicating its movement.
   */
  private AbsoluteMathVector position;
  
  /**
    * Constructs a new DynamicObjectInfo object
    * with the given name and time stamp.
    * @param objName the name for the object
    * @param timeStamp the current server cycle
    * when the data for the object was received
  */
	public DynamicObjectInfo(String objName, int timeStamp)
	{
		super(objName, timeStamp);
		position = new AbsoluteMathVector();
		//    absoluteFieldPosition = null;
		//  predictedFieldPosition = null;
	}

	/**
    * Constructs a new DynamicObjectInfo object initializing
    * all inherited members using the information from
    * an ObjectInfo object.
    * @param object the ObjectInfo object that
    * contains the information to create the new
    * DynamicObjectInfo object
  */
	public DynamicObjectInfo(ObjectInfo object)
	{
		super(object.getObjectName(), object.getTimeStamp(),
					object.getDistance(), object.getDirection(),
					object.getDistChng(), object.getDirChng());
    if (object instanceof DynamicObjectInfo)
      position = new AbsoluteMathVector(((DynamicObjectInfo)object).getPosition());
    else
      position = new AbsoluteMathVector();
	}

	/**
    * Returns the position of the object on the field.
    * The center of the field is treated as [0,0] and 
    * the field is treated as a normal Cartesian plane.
  */
	public Point2D.Double getAbsoluteFieldPosition()
	{
    return position.getLocation();
	}

  public AbsoluteMathVector getPosition(){
    return position;
  }

	/**
    * Returns the predicted field position.
  */
	public Point2D.Double getPredictedFieldPosition()
	{
    return position.getEndPointLocation();
	}

	/**
    * Sets the absolute field position and updates the vector to reflect its movement.
  */
	public void setAbsoluteFieldPosition( Point2D.Double point)
	{
    position.setEndPoint(point); //set the new point to end point, this updates the vector
    position.set(point); //move to that point, vector stay the same.
  }

  public void setNextPosition(DynamicObjectInfo next){
      setAbsoluteFieldPosition(next.getAbsoluteFieldPosition());
  }
  
  public void setLocationOnly(Point2D.Double point )
	{
    position.set(point); 
  }

  public void setVector(double magnitude, double angle){
    position.setAngle(angle);
    position.setMagnitude(magnitude);
  }

	/**
    * Sets the predicted field position.
  */
	public void setPredictedFieldPosition( Point2D.Double point)
	{
    position.setEndPoint(point.getX(), point.getY());
	}

  /** This must be called after each change of position in order to
      maintain the invariance that distance and direction must always be the
      relative distance and direction from the player to this object.
      However, it is up to the user to do this since sometimes we want to update
      one (from observation) and not the other.
  */
  public void resetDistDir(AbsoluteMathVector playerPosition){
    AbsoluteMathVector ballVector = new AbsoluteMathVector(playerPosition.getLocation(), position.getLocation());
    distance = ballVector.getMagnitude();
    direction = playerPosition.getAngle() - ballVector.getAngle();
  }    

  public void moveToNextPosition(double decay)
  {
    position.extrapolateToNextPosition(decay);
    if (position.getMagnitude() > 10) //to avoid jumps.
      position.setMagnitude(0);
  }

  public void moveToNextPosition()
  {
    position.extrapolateToNextPosition(1);
    if (position.getMagnitude() > 10)
      position.setMagnitude(0);
  }

  /**
    * Converts the absolute data of the object to a 'sayable' message.
  */
  public String toMessage()
  {
    return "(" + this.getObjectName() + ") " + position.toString(); 
  }

  public String toString()
  {
    return super.toString() + "\n" + position.toString();
  }

  /** Sets this object's vector to be that of its current position minus
    its old positions. That is, assume the object will keep moving in
    the same direction and at the same speed. Then set it
    @param oldPosition the old position, its vector is <b>modified</b>.*/
  public void setVector(DynamicObjectInfo oldInfo)
  {
    oldInfo.position.setEndPoint(position.getLocation());
    position.set(oldInfo.position);
  }

  public void doTurn(double deltaAngle)
  {
    position.addToAngle(deltaAngle);
    addToDirection(deltaAngle);
  }

}
