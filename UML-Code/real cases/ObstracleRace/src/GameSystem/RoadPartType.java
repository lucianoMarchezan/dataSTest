package GameSystem;

import java.awt.*;

/**
  * A class of roadPartTypes (e.g. snow, ice, mud,....).
  */
public class RoadPartType{


	// CONSTRUCTORS
	
	/**
	  * Creates a new RoadPartType and initializes it with the given values.
	  *
	  * @param   name: The name of this RoadPartType.  
	  * @param   maximumSpeed: The maximum speed at which a car can drive on this type of road. 
	  * @param   minimumSpeed: The minimum speed at which a car can drive on this type of road. 
	  * @param   steerability: The flexibility with which a car can steer on this type of road. 
	  * @param   accelerationFactor: The factor with which a car can accelerate on this type of road. 
	  * @param   decelerationFactor: The factor with which a car can decelerate on this type of road. 
	  * @param   colour: The colour of this type of road.   
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	RoadPartType(String name, int maximumSpeed, int minimumSpeed,
	                    int steerability, double accelerationFactor, double decelerationFactor, Color colour){
		if(name==null)
			throw new NullPointerException("Invalid name (null)");
		if(name.equals(""))
			throw new IllegalArgumentException("Invalid name (empty)");
		$NAME=name;
		if(maximumSpeed<0 || maximumSpeed<minimumSpeed)
			throw new IllegalArgumentException("Invalid maximumspeed");
		$MAXIMUMSPEED=maximumSpeed;
		if(minimumSpeed<0)
			throw new IllegalArgumentException("Invalid minimumspeed");
		$MINIMUMSPEED=minimumSpeed;	
		if(steerability<0)
			throw new IllegalArgumentException("Invalid steerability");	
		$STEERABILITY=steerability;
		if(accelerationFactor<0)
			throw new IllegalArgumentException("Invalid accelerationFactor");
		$ACCELERATIONFACTOR=accelerationFactor;
		if(decelerationFactor<0)
			throw new IllegalArgumentException("Invalid decelerationFactor");
		$DECELERATIONFACTOR=decelerationFactor;
		if(colour==null)
			throw new NullPointerException("Invalid colour");	
		$COLOUR=colour;
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the name of this RoadPartType.
	  *
	  * @return The name of this RoadPartType.
	  */
	public String getName(){
		return $NAME;
	}
	
	/**
	  * Returns the maximum speed at which a car can drive on this type of road.
	  *
	  * @return The maximum speed at which a car can drive on this type of road. 
	  */
	public int getMaximumSpeed(){
		return $MAXIMUMSPEED;
	}
	
	/**
	  * Returns the minimum speed at which a car can drive on this type of road.
	  *
	  * @return The minimum speed at which a car can drive on this type of road.
	  */
	public int getMinimumSpeed(){
		return $MINIMUMSPEED;
	}
	
	/**
	  * Returns the flexibility with which a car can steer on this type of road.
	  *
	  * @return The flexibility with which a car can steer on this type of road.     
	  */
	public int getSteerability(){
		return $STEERABILITY;
	}
		
	/**
	  * Returns the factor with which a car can accelerate on this type of road.
	  *
	  * @return The factor with which a car can accelerate on this type of road.  
	  */
	public double getAccelerationFactor(){
		return $ACCELERATIONFACTOR;
	}
	
	/**
	  * Returns the factor with which a car can decelerate on this type of road.
	  *
	  * @return The factor with which a car can decelerate on this type of road.  
	  */
	public double getDecelerationFactor(){
		return $DECELERATIONFACTOR;
	}
	
	/**
	  * Returns the colour of this type of road.
	  *
	  * @return The color of this type of road. 
	  */
	public Color getColour(){
		return $COLOUR;
	}
	
	
	// VARIABLES
	
	/*
	 * The name of this roadPartType. 
	 */
	private final String $NAME;
	
	/*
	 * The maximum speed at which a car can drive on this type of road.
	 */
	private final int $MAXIMUMSPEED;
	
	/*
	 * The minimum speed at which a car can drive on this type of road. 
	 */
	private final int $MINIMUMSPEED;
	
	/* 
	 * The flexibility with which a car can steer on this type of road
	 * this is a value ranging from 1 to 10.
	 * 1 means very limited steerability
	 * 5 means normal steerability
 	 * 10 means difficult to control steerability
 	 */
	private final int $STEERABILITY;
	
	/* 
	 * The factor with which a car can accelerate on this type of road.
	 */
	private final double $ACCELERATIONFACTOR;
	
	/*
	 * The factor with which a car can decelerate on this type of road. 
	 */
	private final double $DECELERATIONFACTOR;
	
	/*
	 * The colour of the road. 
	 */
	private final Color $COLOUR;
}