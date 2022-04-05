package GameSystem;

import java.io.*;

/**
  * A class of gears
  */
public class Gear implements Serializable{

	//CONSTRUCTOR
	/**
	  * Create a new Gear with a given number, maximum speed and acceleration factor
	  *
	  * @exception IllegalArgumentException
	  */
	public Gear(int number,int maximumSpeed,double accelerationFactor){
		if(number < 1)
			throw new IllegalArgumentException("The given number is lower than 1");
		if(maximumSpeed < 0)
			throw new IllegalArgumentException("The given maximum speed is lower than zero");
		if(accelerationFactor < 0)
			throw new IllegalArgumentException("The given acceleration factor is lower than zero");
		$NUMBER = number;
		$MAXIMUMSPEED = maximumSpeed;
		$ACCELERATIONFACTOR = accelerationFactor;
	}
	
	//INSPECTORS
	/**
	  * Return the number of this gear
	  *
	  * @return the number of this gear
	  */
	public int getNumber(){
		return $NUMBER;
	}
	
	/**
 	  * Return the maximum speed of this gear
	  *
	  * @return the maximum speed of this gear
	  */
	 public int getMaximumSpeed(){
	 	return $MAXIMUMSPEED;
	 }
	 
	 /**
 	   * Return the acceleration factor of this gear
	   *
	   * @return the acceleration factor of this gear
	   */
	 public double getAccelerationFactor(){
	 	return $ACCELERATIONFACTOR;
	 }
	 
	 //VARIABLES
	 /**
	   * A variable (int) for the number of this gear
	   */
	 final private int $NUMBER;
	 
	 /**
	   * A variable (int) for the maximum speed of this gear
	   */
	 final private int $MAXIMUMSPEED;
	 
	 /**
	   * A variable (double) for the acceleration factor of this gear
	   */
	 final private double $ACCELERATIONFACTOR;
}