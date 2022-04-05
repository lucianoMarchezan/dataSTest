//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Date;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.net.SocketException;
import java.io.*;

/**
  * Controls the updating of the GlobalMemory and has the option
  * of creating a window displaying the agent's world view.
  * The world model seperates the dynamic and static objects from the
  * agent's perception.  The static objects are represented by
  * absolute coordinates and used to calculate the agent's and then
  * everything else's absolute position on the field.  Information
  * on the dynamic objects is kept limited in order to create a
  * nearly reactive agent architecture for the player.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.24 $, $Date: 2001/12/11 15:59:12 $
  *
  */

public class WorldModel
{
	/** Used for communications between the world model and the server. */
	public DatagramWrapper    dgWrapper;
	/** Reference to information from the configuration file. */
	public static ConfigurationData  cfgData;
	/** Reference to the window used to display the agent's perception. */
	public DisplayWorldModel  displayWorldModel;
	/** Player object.  Uses memory to decide on and take appropriate actions */
	public PlayerFoundation player;
  /** Determines how long stale information will remain in the dynamicObjects list.*/
  public int age;

  /** The current time (cycle number)*/
  public long currentCycle;

  /** List of what the agent can see. */
	public ArrayList dynamicObjects;
	/** List of information the agent hears. */
	public ArrayList hearInformation;
	/** Map of the locations of the flags. */
	public HashMap staticObjects;
	/** Information on the player. */
	public SelfInfo self;
	/** Holds a string describing the current playing mode.  Determined by the referee.*/
	public RefereeMessage lastMessage;
  

  /** The ball information. This is also the same object in dynamicObjects, kept here so
      that it is faster to access*/
  private BallInfo ballinfo;

  public BallInfo getBallInfo(){
    return ballinfo;
  }

  public char getSide(){
    return self.getSide();
  }

	/**
   * Constructs a new WorldModel object from given objects.
   * @param cfgData holds information from configuration file
   * @param dgWrapper used to receive messages from the server
   * @param player the decision making, action performing part of the program
   * @param display determines whether or not to create a graphical
   * @param age specifies how long the memory retains stale data
   * @param team the player's team
   * @param uniformNumber the uniform Number
   * @param side which side
   * @param lastMessage last message from referee.
   * representation of the player's memory information
   */
	public WorldModel ( ConfigurationData cfgData, DatagramWrapper dgWrapper,
											PlayerFoundation player, boolean display, int age, String team, int uniformNumber,
											char side, RefereeMessage lastMessage)
    throws SocketException, IOException
	{     
    this.cfgData = cfgData;
    this.dgWrapper = dgWrapper;
    this.player = player;
    this.age = age;
    currentCycle = 0;

    if (display)
      displayWorldModel = new DisplayWorldModel(this, age);

    dynamicObjects = new ArrayList();
    hearInformation = new ArrayList();
    staticObjects = initStaticObjects();
    self = new SelfInfo( team, uniformNumber, side );
    this.lastMessage = lastMessage;
	}


  /**
     Adds the input information to the world model
     @param myData the information to add */
	public void addInput (Input myData)
	{
    //Dont even bother
    if (myData == null)
      return;
    
    // see if the message that triggered the processing was
    // from the referee - if so update the global playmode
    if( myData instanceof RefereeMessage )
		{
      //      System.out.println("Got refMessage:" + myData);
      RefereeMessage refMessage = (RefereeMessage)myData;
      lastMessage.setPlayMode(refMessage.getPlayMode());
      lastMessage.setTimeStamp(refMessage.getTimeStamp());
      //      self.setTimeStamp(refMessage.getTimeStamp());
		}
    else if( myData instanceof SenseBody )
		{
      SenseBody sense = (SenseBody)myData;
      self.setSense(sense);
      //      self.setTimeStamp(sense.getTimeStamp());
		}
    else if( myData instanceof ObjectInfoContainer )
		{
      ArrayList sensoryInfo = ((ObjectInfoContainer)(myData)).objects;
      if (sensoryInfo == null)
        System.out.println("ERROR: sensoryInfo is null");

      if (!sensoryInfo.isEmpty())
        self.setTimeStamp(myData.getTimeStamp());

      // determine player location    
      approximateLocationOfSelf(sensoryInfo, staticObjects);

      //add new sensoryInfo to world model
      updateWorldModel(sensoryInfo);

      //      incorporateHearInfo(hearInformation, dynamicObjects, self);

		}
    else if (myData instanceof ObjectInfo) //must be a "hear" message.
		{
      //      System.out.println("Got hear:" + myData);
      hearInformation.add(myData);
		}
    else
      System.out.println("ERROR: addInput called with unknown input.");

	}

	/**
     * Computes the player's facing direction with respect to the field
     * in degrees, 0 is up and 90 is to the right.
     * Code was adapted from field.cc from AT-Humbolt 98 team code.
     * Uses lines to determine the facing direction, or two flags and
     * the law of cosines if no lines are available.
     * @param visualInfo an array of what the player can see
     * @param staticObjects contains the locations of all the flags
     * @return the facing direction of player, in degrees
     */
	private double computeFaceDirection( ArrayList visualInfo, HashMap staticObjects)
	{
  
    double rtnval = 0;
    
    // create identifiers to hold visual line information
    ObjectInfo line[] = new ObjectInfo[4];
    // note: location 0 is for top
    //    location 1 is for left
    //    location 2 is for bottom
    //    location 3 if for right

    // note if the visual information does not exist null is returned
    // once the ObjectInfo is retrieved we no longer need the line in 
    // the array list... after each get, call remove to remove the line
    // PAB 4/11/2000
    Iterator iterator = visualInfo.iterator();
    Iterator filteredIterator = new FilteredIterator( iterator, "line.*" );
    while( filteredIterator.hasNext() )
    {
      ObjectInfo lineInfo = (ObjectInfo)filteredIterator.next();

      if( lineInfo.getObjectName().equals( "line t" ) )
        line[0] = lineInfo;
      else if( lineInfo.getObjectName().equals( "line l" ) )
        line[1] = lineInfo;
      else if( lineInfo.getObjectName().equals( "line b" ) )
        line[2] = lineInfo;
      else if( lineInfo.getObjectName().equals( "line r" ) )
        line[3] = lineInfo;
      // done with the line; remove it from the list of observed objects
      filteredIterator.remove();
    }


    // at most two of the lines will have information... we are interested
    // in the line that has the greatest distance... if we have more than 
    // two line sightings the one with the greater magnitude will represent
    // an inside the field to outside view.  PAB 4/5/2000
    int indexToLargest = -1;

    // initialize currentLargest to the smallest number available for the
    // primitive type double. Anything found will be larger than this number.
    double currentLargest = Double.NEGATIVE_INFINITY;

    for( int i=0; i<line.length; i++ )
		{
      // short-circuited logical and is used to prevent null dereference
      if( line[i] != null && 
					line[i].getDistance() > currentLargest )
			{
        indexToLargest = i;
        currentLargest = line[i].getDistance();       
			}
		}
    // if we have not seen any lines, indexToLargest will be -1
    if( indexToLargest >= 0 )
		{
      double angle = line[indexToLargest].getDirection();

      switch( indexToLargest )
			{
        // logic for line t and b are reversed from AT Humbolt code... have to think 
        // longer about why I need to do this to get correct absolute position. May
        // have to do
        // This code converts the angle given by .getDirection() to the absolute angle
        // for the agent's system (zero is right, and angles increase counterclockwise)
			case 0:   // line t
        if( line[indexToLargest].getDirection() > 0 )
          rtnval = angle;
        else
          rtnval = 180 + angle;
        break;
			case 1:   // line l
        if( line[indexToLargest].getDirection() > 0 )
          rtnval = 90 + angle;
        else
          rtnval = 270 + angle;
        break;
			case 2:   // line b
        if( line[indexToLargest].getDirection() > 0 )
          rtnval = 180 + angle;
        else
          rtnval = 360 + angle;
        break;
			case 3:   // line r
        if( line[indexToLargest].getDirection() > 0 )
          rtnval = 270 + angle;
        else
          rtnval = 90 + angle;
        break;
			}
		}
    else  //it can't see a line, so it must find its facing angle trigonometrically
		{
      Point2D.Double f1, f2; //Two flags
      iterator = visualInfo.iterator();
      ObjectInfo object, flag1 = null, flag2 = null;
      
      //finds two flags using a regular expression
      //Won't accept just flag or Flag, must be of the form flag [l|r|p]...
      filteredIterator = new FilteredIterator(iterator, "([fF]lag|[gG]oal).+");
      flag1 = (ObjectInfo)filteredIterator.next();
      flag2 = (ObjectInfo)filteredIterator.next();

      if (flag2 != null)  //it saw at least two flags
			{
        //Regular expression accepts flag or Flag, but all static object names
        //are stored in the HashMap in lowercases
        f1 = (Point2D.Double)staticObjects.get(flag1.getObjectName().toLowerCase());
        f2 = (Point2D.Double)staticObjects.get(flag2.getObjectName().toLowerCase());

        //treats the two flags and the player as the corners of a triangle
        //and uses the law of cosines to compute the necessary angle
        //to determine the player's facing direction
        double   offsetAngle, //the angle at flag2's corner (necessary to compute facing direction)
          f2Angle,    //= flag2.getDirection (also necessary to compute facing direction)
          oppositeLineDirection,  //the direction the line from flag1 to flag2 to points in
          oppositeLineLength, //the length of the line from flag1 to flag2
          xChange, yChange, f1Distance, f2Distance;

        xChange = f2.getX() - f1.getX();
        yChange = f2.getY() - f1.getY();

        f1Distance = flag1.getDistance();
        f2Distance = flag2.getDistance();
        oppositeLineLength = Math.sqrt(xChange * xChange + yChange * yChange);

        f2Angle = flag2.getDirection();

        //Angle of the triangle at flag2
        //Server frequently sends innaccurate data which
        //could cause an error in the computation of
        //the offsetAngle, i.e. it says that one side of the
        //triangle made up of the player and the two flags
        //is longer than the sum of the other two sides.
        //When this happens, make a guess as to the angle.
        if (oppositeLineLength >= f2Distance + f1Distance)
          offsetAngle = 0.;
        else if (f1Distance >= oppositeLineLength + f2Distance)
          offsetAngle = 180.;
        else if (f2Distance >= oppositeLineLength + f1Distance)
          offsetAngle = 0.;
        else
          offsetAngle = Math.toDegrees(Math.acos((oppositeLineLength * 
																									oppositeLineLength + f2Distance * f2Distance - f1Distance * 
																									f1Distance) / (2. * f2Distance * oppositeLineLength)));

        //Direction of line from flag1 to flag2
        if (xChange == 0)
          oppositeLineDirection = (yChange >= 0) ? 90 : 270;
        else
				{
          oppositeLineDirection = Math.toDegrees(Math.atan(yChange / xChange));
          if (xChange < 0)
            oppositeLineDirection += 180;
				}
        if (flag1.getDirection() < flag2.getDirection())
          rtnval = oppositeLineDirection + offsetAngle + f2Angle;
        else
          rtnval = oppositeLineDirection - offsetAngle + f2Angle;
			}
      //else, cry uncle
		}
    return rtnval;
	}

	/**
     * Determines the location of the player using its facing direction
     * and the nearest flag.
     * @param visualInfo contains a list of what the player can see
     * @param staticObjects a map of the locations of all the flags
     */
	public void approximateLocationOfSelf( ArrayList visualInfo, HashMap staticObjects)
	{
    Point2D.Double rtnval = null;
    double  playerFacingDirection = 0;

    playerFacingDirection = computeFaceDirection( visualInfo, staticObjects );

    // the closest object has the least error... instead of using a weighted average
    // of all static object sightings, simply compute the player's position from the
    // closest object. The closestObjectValue object will hold the ObjectInfo data
    // for the closest static object.
    ObjectInfo closestObjectInfo = new ObjectInfo();
    // set the distance to a value that any of the object will be closer than
    closestObjectInfo.setDistance( Double.POSITIVE_INFINITY );

    // iterate through the visual info... see if the name of the object is a key
    // into the staticObjects HashMap... if it is, then the object can help us 
    // gain our bearings on the field.
    Iterator iterator = visualInfo.iterator();

    while( iterator.hasNext() )
		{
      ObjectInfo objectInfo = (ObjectInfo)iterator.next();

      if( staticObjects.containsKey( objectInfo.getObjectName() ) ) 
			{
        // if object is closer than previous closest.... save it
        if( objectInfo.getDistance() < closestObjectInfo.getDistance() )
          closestObjectInfo = objectInfo;

        // we are done with the objectInfo data for this static object sighting... 
        // remove the object from the list
				iterator.remove();
			}
		}

    double y_offset = Math.sin( Math.toRadians( 
																							 playerFacingDirection - closestObjectInfo.getDirection()) ) * 
      closestObjectInfo.getDistance();
    double x_offset = Math.cos( Math.toRadians( 
																							 playerFacingDirection - closestObjectInfo.getDirection()) ) * 
      closestObjectInfo.getDistance();
    
    Point2D.Double location = 
      (Point2D.Double)staticObjects.get( closestObjectInfo.getObjectName() );

    self.setDirection( playerFacingDirection );
    Point2D.Double myLocation;

//     if (self.getSide() == 'l')
//       myLocation = new Point2D.Double( location.getX() - x_offset, -1 *(location.getY() - y_offset));
//     else
    if (location != null){
      self.setAbsoluteFieldPosition(new Point2D.Double( location.getX() - x_offset, location.getY() - y_offset ));

      //      self.setLocationOnly(myLocation);
      //      self.setVector(0,playerFacingDirection); 
    }
  }

  /** Remove the ball from the world model. */
  public void forgetBall(){
    ///    System.out.println("forgetBall");
    Iterator iterator = dynamicObjects.iterator();
    while( iterator.hasNext() ) {
      ObjectInfo object = (ObjectInfo)iterator.next();
      if (object.getObjectName().equals("ball")){
        iterator.remove();
        ballinfo = null;
      }
    }
  }
  

	/**
   * Calculates and sets the absolute field positions of all the
   * dynamic objects. If their were not seen in currentTime then we use dead-recckoning.
   * This function should be called once, and only once, for every tick. As such, it is
   * executed whenever the "act" event fires.
   */
	public void updateAbsolutePositions()
	{
    if (isPlayMode("goal")){
      forgetBall();
    }
    
    Iterator iterator = dynamicObjects.iterator();
    Point2D.Double myLocation = self.getAbsoluteFieldPosition(); 
    //    System.out.println("updateAbsolutePositions:");

    //update my position if needed
    if (self.getTimeStamp() != currentCycle){
      //      System.out.println("####updating my postion");
      self.moveToNextPosition(cfgData.player_decay);
    }

    while( iterator.hasNext() )
		{
      // now that we know the player's position, compute the position of the dynamic objects
      DynamicObjectInfo dynamicObjectInfo = (DynamicObjectInfo)iterator.next();

      if (dynamicObjectInfo.getTimeStamp() == currentCycle){
        //we just saw it, so calculate its position

        double y_offset = Math.sin(Math.toRadians( 
                          self.getDirection() - dynamicObjectInfo.getDirection()) ) * 
                          dynamicObjectInfo.getDistance();
        double x_offset = Math.cos( Math.toRadians(
                          self.getDirection() - dynamicObjectInfo.getDirection()) ) * 
                          dynamicObjectInfo.getDistance();
        Point2D.Double objectLocation = new Point2D.Double(myLocation.getX() + x_offset, myLocation.getY() + y_offset );
//         System.out.println("Setting " + dynamicObjectInfo.getObjectName() + " location to " + objectLocation);
//         System.out.println("I am at " + self.getAbsoluteFieldPosition());
//         System.out.println("I am facing " + self.getDirection());
//         System.out.println("Object is " + dynamicObjectInfo);
//         System.out.println("xoff=" + x_offset + " yoff=" + y_offset);
        dynamicObjectInfo.setAbsoluteFieldPosition(objectLocation); 
      }
      else {
        //we did not see it, use dead-reckoning
        if (dynamicObjectInfo.getObjectName().equals("ball")){ //update the ball's position
          dynamicObjectInfo.moveToNextPosition(cfgData.ball_decay);
          dynamicObjectInfo.resetDistDir(self.getPosition());
        }
        if (dynamicObjectInfo.getObjectName().equals("player")){ //update the ball's position
          dynamicObjectInfo.moveToNextPosition(cfgData.player_decay);
          dynamicObjectInfo.resetDistDir(self.getPosition());
        }
      }
      //for debugging
//       if (dynamicObjectInfo.getObjectName().equals("ball")){
//         System.out.println(self);
//         System.out.println(ballinfo);
//       }
    }

    //Remove all info that's too old
    iterator = dynamicObjects.iterator();
    while (iterator.hasNext())
    {
      ObjectInfo object = (ObjectInfo)iterator.next();
      long timestamp = object.getTimeStamp();

      if (timestamp < currentCycle - age){
        //        System.out.println("Removing " + object.toString());
        iterator.remove();
        if (object.getObjectName().equals("ball"))
          ballinfo = null;
      }
    }

    // update display if present
    if( displayWorldModel != null ) {
//       if (ballinfo != null && ballinfo.getAbsoluteFieldPosition() != null)
//         System.out.println("*" + ballinfo.getAbsoluteFieldPosition());
      displayWorldModel.repaint();
    }
  }

  /**
    * Determines if a DynamicObject is in the dynamicObjects list.
    * @param object the object to search for
    * @param list the list to search
    * @return the index of the object in the list, or -1 if it is not
    * present
  */
  public int isObjectInList(DynamicObjectInfo object, ArrayList list)
  {

    Iterator iterator = list.iterator();
    int index = 0;
    while (iterator.hasNext())
    {
      DynamicObjectInfo dynamicObject = (DynamicObjectInfo)iterator.next();
      if (object instanceof BallInfo){
        if (dynamicObject instanceof BallInfo)
          return index;
      }
      else if (object instanceof DynamicObjectInfo)
        if (((DynamicObjectInfo)object).equals((DynamicObjectInfo)dynamicObject))
          return index;
      index++;
    }
    return -1;
  }

	/**
   * Updates the visual and audio information in the WorldModel by adding new information.
   * Turns the ObjectInfo objects in the sensoryInfo list into
   * DynamicObjectInfo objects that replace the data on the
   * dynamicObjects list.
   * @param sensoryInfo the new information that is to be added to the world model
   */
	public void updateWorldModel(ArrayList sensoryInfo)
	{
    Iterator iterator = sensoryInfo.iterator();
    DynamicObjectInfo dynamicInfo;
    boolean sawball = false;
    //add in new info, or replace older
    while (iterator.hasNext())
    {
      ObjectInfo object = (ObjectInfo)iterator.next();
      
      if (object.compareTo("[bB]all") == 0){
        sawball = true;
        dynamicInfo = new BallInfo(object);
      }
      else if (object.compareTo("[Pp]layer.*") == 0){ //object is a player
        dynamicInfo = new PlayerInfo(object);
      }
      else continue;
      
      //Check to make sure it doesn't already exist first.
      int position = isObjectInList(dynamicInfo, dynamicObjects);
      if (position != -1){
        DynamicObjectInfo currentObject = (DynamicObjectInfo)dynamicObjects.get(position);
//         currentObject.setNextPosition(dynamicInfo);
//         dynamicInfo = currentObject;
        dynamicInfo.setVector(currentObject); //set its vector to extrapolate from old position
        dynamicObjects.set(position, dynamicInfo);
      }
      else {
        dynamicObjects.add(dynamicInfo);
        if (dynamicInfo instanceof BallInfo)
          System.out.println("First time we see ball");
      }
        
      if (dynamicInfo instanceof BallInfo){
        ballinfo = (BallInfo) dynamicInfo;
//         System.out.println("Saw-adding:");
//         System.out.println(ballinfo);
      }
    }

    if ((!sawball && ballinfo!=null) //if we did not see the ball but should have, remove it from world model
        && (currentCycle - ballinfo.getTimeStamp()) > 3) {
      
      Point2D.Double ballpos = ballinfo.getAbsoluteFieldPosition();
      if ((ballinfo.getDistance() < cfgData.kickable_area) ||
          (Math.abs(ballinfo.getDirection()) < cfgData.visible_angle))
        forgetBall();
      //         MathVector vtoball = new MathVector(self.getAbsoluteFieldPosition(), ballnextpos);
      //         if (Math.abs(vtoball.getAngle()) < cfgData.visible_angle)
      //           forgetBall();
    }
  }


	/**
     * Adds information from hear messages
     * about visible objects to the dynamicObjects
     * list in the GlobalMemory.
     * @param hearInformation the ObjectInfo objects
     * create from 'hear' messages
     * @param dynamicObjects the list of visual information
     * @param self contains information about the player
     */
    //6/20/00
	public void incorporateHearInfo(ArrayList hearInformation,
																	ArrayList dynamicObjects, SelfInfo self)
	{
    Iterator hearIterator = hearInformation.iterator(), seeIterator;
    FilteredIterator filteredIterator;
    Object object;
    DynamicObjectInfo dynamicObject;
    double distance, direction, xChange, yChange;
    Point2D.Double selfLocation, objectLocation;
    selfLocation = self.getAbsoluteFieldPosition();
    while (hearIterator.hasNext())
		{
      object = hearIterator.next();

      if (object instanceof DynamicObjectInfo)
      {
        DynamicObjectInfo objectInfo = (DynamicObjectInfo)object;
        if (objectInfo.compareTo("[bB]all") == 0)
          dynamicObject = new BallInfo(objectInfo);
        else
          dynamicObject = new PlayerInfo(objectInfo);

        dynamicObject.setAbsoluteFieldPosition(objectInfo.getAbsoluteFieldPosition());

        seeIterator = dynamicObjects.iterator();
        filteredIterator = new FilteredIterator(seeIterator,
																								dynamicObject.getObjectName());
        if (!filteredIterator.hasNext())
				{
          //Information in hear messages is given in absolute
          //positions.  Add the distance and direction to the
          //object before adding it to the dynamicObjects list.
          objectLocation = dynamicObject.getAbsoluteFieldPosition();
          xChange = objectLocation.getX() - selfLocation.getX();
          yChange = objectLocation.getY() - selfLocation.getY();
          distance = Math.sqrt(xChange * xChange + yChange * yChange);
          if (xChange == 0)
						direction = (yChange >= 0) ? 90 : 270;
          else
					{
            direction = Math.toDegrees(Math.atan(yChange / xChange));
            if (xChange < 0)
							direction = direction + 180;
					}
          dynamicObject.setDistance(distance);
          dynamicObject.setDirection(direction);
          dynamicObjects.add(dynamicObject);
				}

        //Remove seen objects from hearInformation
        hearIterator.remove();
			}
		}
  }

	/**
     * Sets the location of all the flags.
     * Called by the GlobalMemory constructor.
     * @return a map of the loctations of all
     * the static objects
     */
	public static HashMap initStaticObjects()
	{
    HashMap staticObjects = new HashMap();

    // perimeter flags clockwise from lower-left
    staticObjects.put( "flag l b", new Point2D.Double(-52.5, -34));
    staticObjects.put( "flag l b 30", new Point2D.Double(-57.5, -30 ));
    staticObjects.put( "flag l b 20", new Point2D.Double(-57.5, -20 ));
    staticObjects.put( "flag l b 10", new Point2D.Double(-57.5, -10 ));
    staticObjects.put( "flag l 0", new Point2D.Double(-57.5, 0 ));
    staticObjects.put( "flag l t 10", new Point2D.Double(-57.5, 10 ));
    staticObjects.put( "flag l t 20", new Point2D.Double(-57.5, 20 ));
    staticObjects.put( "flag l t 30", new Point2D.Double(-57.5, 30 ));
    staticObjects.put( "flag l t", new Point2D.Double(-52.5, 34 ));
    staticObjects.put( "flag t l 50", new Point2D.Double(-50, 39 ));
    staticObjects.put( "flag t l 40", new Point2D.Double(-40, 39 ));
    staticObjects.put( "flag t l 30", new Point2D.Double(-30, 39 ));
    staticObjects.put( "flag t l 20", new Point2D.Double(-20, 39 ));
    staticObjects.put( "flag t l 10", new Point2D.Double(-10, 39 ));
    staticObjects.put( "flag t 0", new Point2D.Double(0, 39 ));
    staticObjects.put( "flag t r 10", new Point2D.Double(10, 39 ));
    staticObjects.put( "flag t r 20", new Point2D.Double(20, 39 ));
    staticObjects.put( "flag t r 30", new Point2D.Double(30, 39 ));
    staticObjects.put( "flag t r 40", new Point2D.Double(40, 39 ));
    staticObjects.put( "flag t r 50", new Point2D.Double(50, 39 ));
    staticObjects.put( "flag r t", new Point2D.Double(52.5, 34 ));
    staticObjects.put( "flag r t 30", new Point2D.Double( 57.5, 30 ));
    staticObjects.put( "flag r t 20", new Point2D.Double(57.5, 20 ));
    staticObjects.put( "flag r t 10", new Point2D.Double(57.5, 10 ));
    staticObjects.put( "flag r 0", new Point2D.Double(57.5, 0 ));
    staticObjects.put( "flag r b 10", new Point2D.Double(57.5, -10 ));
    staticObjects.put( "flag r b 20", new Point2D.Double(57.5, -20 ));
    staticObjects.put( "flag r b 30", new Point2D.Double(57.5, -30 ));
    staticObjects.put( "flag r b", new Point2D.Double(52.5, -34 ));
    staticObjects.put( "flag b r 50", new Point2D.Double(50, -39 ));
    staticObjects.put( "flag b r 40", new Point2D.Double(40, -39 ));
    staticObjects.put( "flag b r 30", new Point2D.Double(30, -39 ));
    staticObjects.put( "flag b r 20", new Point2D.Double(20, -39 ));
    staticObjects.put( "flag b r 10", new Point2D.Double(10, -39 ));
    staticObjects.put( "flag b 0", new Point2D.Double(0, -39 ));
    staticObjects.put( "flag b l 10", new Point2D.Double(-10, -39 ));
    staticObjects.put( "flag b l 20", new Point2D.Double(-20, -39 ));
    staticObjects.put( "flag b l 30", new Point2D.Double(-30, -39 ));
    staticObjects.put( "flag b l 40", new Point2D.Double(-40, -39 ));
    staticObjects.put( "flag b l 50", new Point2D.Double(-50, -39 ));

    // goals and penalty area flags
    // note: approximated the width of the penalty box to be 16 yds

    staticObjects.put( "goal l", new Point2D.Double(-52.5, 0 ));
    staticObjects.put( "flag g l b", new Point2D.Double(-52.5, -cfgData.goal_width/2 ));
    staticObjects.put( "flag g l t", new Point2D.Double(-52.5, cfgData.goal_width/2 ));
    staticObjects.put( "flag p l b", new Point2D.Double(-36.5, -20 ));
    staticObjects.put( "flag p l c", new Point2D.Double(-36.5, 0 ));
    staticObjects.put( "flag p l t", new Point2D.Double(-36.5, 20 ));
    staticObjects.put( "goal r", new Point2D.Double(52.5, 0 ));
    staticObjects.put( "flag g r b", new Point2D.Double(52.5, -cfgData.goal_width/2 ));
    staticObjects.put( "flag g r t", new Point2D.Double(52.5, cfgData.goal_width/2 ));
    staticObjects.put( "flag p r b", new Point2D.Double(36.5, -20 ));
    staticObjects.put( "flag p r c", new Point2D.Double(36.5, 0 ));
    staticObjects.put( "flag p r t", new Point2D.Double(36.5, 20 ));

    // center line flags
    staticObjects.put( "flag c", new Point2D.Double(0, 0 ));
    staticObjects.put( "flag c b", new Point2D.Double(0, -34 ));
    staticObjects.put( "flag c t", new Point2D.Double(0, 34 ));

    // lines... the names were changed so that they would not match the names in the
    // received sensory data. These objects are being defined for rendering purposes
    // only.

    staticObjects.put( "field line t", 
											 new Line2D.Double((Point2D.Double)staticObjects.get("flag l t"),
																				 (Point2D.Double)staticObjects.get("flag r t")));
    staticObjects.put( "field line b", 
											 new Line2D.Double((Point2D.Double)staticObjects.get("flag l b"),
																				 (Point2D.Double)staticObjects.get("flag r b")));
    staticObjects.put( "field line r", 
											 new Line2D.Double((Point2D.Double)staticObjects.get("flag r t"),
																				 (Point2D.Double)staticObjects.get("flag r b")));
    staticObjects.put( "field line l",
											 new Line2D.Double((Point2D.Double)staticObjects.get("flag l t"),
																				 (Point2D.Double)staticObjects.get("flag l b")));

    staticObjects.put( "field line c",
											 new Line2D.Double((Point2D.Double)staticObjects.get("flag c t"),
																				 (Point2D.Double)staticObjects.get("flag c b")));

    // consider creating rectangles to represent the field 
    // and the penalty box    PAB 4/2/2000
    // note on the above comment.... need to flip-vertically the points before
    // rendering... played havoc with Rectangle objects. top,left became 
    // bottom,left after transformation. 4/11/2000

    return staticObjects;
	}

  public boolean isPlayMode(String s){
    return lastMessage.getPlayMode().startsWith(s);
  }

  
}
