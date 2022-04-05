//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import regexp.*;  //use regular expressions in compareTo method

/**
  * Holds the necessary information about field objects.
  * Uses regular expressions in the compareTo method
  * for greater flexibility.
  * @author Paul A. Buhler
  * @version $Revision: 1.15 $, $Date: 2001/02/27 22:24:31 $
  *
*/
class ObjectInfo extends SensorInput implements Comparable 
{
	/**
	 * Name of the object.  Also is the value used
	 * by the compareTo() method.
     */
	private String objectName;

	/** The distance the object is from the agent. */
	protected double    distance;
    
	/** 
   * The direction the agent sees the object at.
   * Zero means the object is directly infront of the
   * agent.
   */
	protected double    direction;

  /** True if we got a distchng and dircgng from the server */
  private boolean gotChanges;
  
	/** Change in distance from last cycle. */
	private double    distChng;
    
	/** Change in direction from last cycle. */ 
	private double    dirChng;
    
	/** Direction body is facing. */
	private double    bodyDir;
    
	/** Direction head is facing relative to the body. */
	private double    headDir;

	/**
    * Constructs a new ObjectInfo object with all fields initialized
    * to null or 0.  Not currently used by the program.
  */
	public ObjectInfo() 
	{
    this( null, 0, 0, 0, 0, 0 );
	}

	/**
    * Constructs a new ObjectInfo object initializing the
    * objectName and timeStamp fields.
    * All other fields are set to zero.
  */
	public ObjectInfo( String objectName, long timeStamp )
	{
    this( objectName, timeStamp, 0, 0, 0, 0 );
    gotChanges = false;
		//    System.out.println("Making: " + objectName);
	}

	/**
    * Constructs a new ObjectInfo object initializing all
    * fields.
  */
	public ObjectInfo( String objectName, long timeStamp, 
										 double distance, double direction, 
										 double distChng, double dirChng )
	{
    super(timeStamp);
    this.objectName = objectName;
    this.distance = distance;
    this.direction = direction;
    this.distChng = distChng;
    this.dirChng = dirChng;
    gotChanges = false;
	}

  /**
   * Get the value of gotChanges.
   * @return value of gotChanges.
   */
  public boolean isGotChanges() {
    return gotChanges;
  }
  
  /**
   * Set the value of gotChanges.
   * @param v  Value to assign to gotChanges.
   */
  public void setGotChanges(boolean  v) {
    
    this.gotChanges = v;
  }
  

	/**
    * Returns the objectName.
  */
	public String getObjectName()
	{
    return objectName;
	}

	/**
    * Returns the distance of the object from the agent.
  */
	public double getDistance()
	{
    return distance;
	}

	/**
    * Returns the direction of the object from the agent's
    * facing direction.  If the object is the agent, then
    * it returns the agents facing direction.
  */
	public double getDirection()
	{
    return direction;
	}

	/**
    * Returns the approximate change of distance from the last
    * update of positions by the server.
  */
	public double getDistChng()
	{
    return distChng;
	}

	/**
    * Returns the approximate change of direction from the last
    * update of the directions by the server.
  */
	public double getDirChng()
	{
    return dirChng;
	}

	/**
    * Returns the direction the body is facing.
  */
	public double getBodyDir()
	{
    return bodyDir;
	}

	/**
    * Returns the returns the direction the head is facing
    * relative to the body.
  */
	public double getHeadDir()
	{
    return headDir;
	}

	/**
    * Sets the object name.
  */
	public void setObjectName( String objectName )
	{
    this.objectName = objectName;
	}

	/**
    * Sets the distance.
  */
	public void setDistance( double distance )
	{
    this.distance = distance;
	}

	/**
    * Sets the direction.
  */
	public void setDirection( double direction )
	{
    this.direction = direction;
	}

  public void addToDirection (double deltaAngle)
  {
    direction -= deltaAngle;
    if (direction > 180)
      while (direction > 180) direction -= 360;
    else if (direction < -180)
      while (direction < -180) direction += 360;
  }

	/**
    * Sets the distance change.
  */
	public void setDistChng( double distChng )
	{
    this.distChng = distChng;
	}

	/**
    * Sets the direction change.
  */
	public void setDirChng( double dirChng )
	{
    this.dirChng = dirChng;
	}

	/**
    * Sets the body direction.
  */
	public void setBodyDir( double bodyDir )
	{
    this.bodyDir = bodyDir;
	}

	/**
    * Sets the head direction.
  */
	public void setHeadDir( double headDir )
	{
    this.headDir = headDir;
	}

	/**
    * Abstract method from interface Comparable.
    * Uses regular expressions to compare the field
    * objectName to a test string.
    * @param object a String object whose value is compared to
    * the objectName field.
    * @return 0 if the parameter and objectName are the same, otherwise
    * the value of objectName.compareTo((String)object).
  */
	public int compareTo( Object object )
	{
    int rtnval;

    // I wanted to write the FilteredIterator as generic as possible... 
    // the compareTo method in ObjectInfo returns 0 when the name of the 
    // current object matches the regular expression passed by the FilteredIterator
    // For example, if this.objectName is "line t" then it 
    // would be declared equal to the expression "line.*"... this allows the
    // filteredIterator to work on partial matches  PAB

    rtnval = -1;
    try
		{
      RE compareValue = new RE((String)object);
      if (compareValue.isMatch(objectName))
        rtnval = 0;
		}
    catch (REException e)
		{
      System.err.println("Regular expression error:" + e.getMessage());
		}

    return rtnval;
	}

	/**
    * Converts the data of the object to readable information.
  */
	public String toString()
	{
    return "ObjName: " + objectName + "\nTime Last Seen: " + timeStamp + 
      "\nDist: " + distance + "\nDirection: " + direction;
	}
}
