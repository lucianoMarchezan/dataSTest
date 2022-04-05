package GameSystem;

import java.io.*;

/**
  * An abstract class of gearboxes
  */
public abstract class Gearbox implements Serializable{

	//CONSTRUCTOR
	/**
	  * Create a new gearbox with a given array of gears
	  *
	  * @param gears The gears you want to set as gears of this gearbox
	  * @exception NullPointerException
	  */
	public Gearbox(Gear[] gears, String name){
		if(gears == null)
			throw new NullPointerException("The given gears are not valid");
		$NAME=name;
		$GEARS = gears;
		reset();
	}

	//INSPECTORS
	/**
	  * Return the gears of this gearbox
	  *
	  * @return The gears of this gearbox
	  */
	Gear[] getGears(){
		return $GEARS;
	}

	/**
	  * Return the number of gears for this gearbox
	  *
	  * @return The number of gears for this gearbox
	  */
	int getNbGears(){
		return $GEARS.length;
	}
	
	/**
	  * Return the current gear of this gearbox
	  *
	  * @return The current gear of this gearbox
	  */
	public Gear getCurrentGear(){
		return $currentGear;
	}
	
	/**
	  * Return the name of this gearbox
	  *
	  * @return: The name of this gearbox
	  */
	public String getName(){
		return $NAME;
	}

	//MUTATORS
	/**
	  * Change the current gear of this gearbox in a higher gear
	  */
	void gearUp(){
		if(getCurrentGear().getNumber() < getNbGears())
			$currentGear = $GEARS[getCurrentGear().getNumber()];
	}
	
	/**
	  * Change the current gear of this gearbox in a lower gear
	  */
	void gearDown(){
		//The current gear is only changed in a lower gear if the lowest gear has
		// not yet been reached.
		//Damage is also applied to the engine if the car drives in a speed that is too
		// high for the new current gear
		if(getCurrentGear().getNumber() > 1)
			$currentGear = $GEARS[getCurrentGear().getNumber()-2];
		if(RaceController.getReference().getRace().getCar().getSpeed()>getCurrentGear().getMaximumSpeed())
			{RaceController.getReference().getRace().getCar().getEngine().damage(0.1);
			 RaceController.getReference().getRace().getCar().setSpeed(getCurrentGear().getMaximumSpeed());
			}
	}
	
	/**
	  * Check whether the current gear has to be changed and change it if necessary.
	  */
	abstract void check();
	
	/**
	  * Reset this gearbox: put the gearbox in first gear
	  */	
	void reset(){
		$currentGear = $GEARS[0];
	}
	
	//VARIABLES
	/**
	  * An array for the different Gears of this Gearbox
	  */
	protected final Gear[] $GEARS;
	
	/**
	  * A variable(Gear) for holding in which gear the car is currently driving
	  */
	protected Gear $currentGear;
	
	/**
	  * A variable (String) for the name of this gearbox
	  */
	protected final String $NAME;
}