package GameSystem;

/**
  * A class of cars.
  */
public class Car{
	
	
	// CONSTRUCTORS 
	/**
	  * A new car with given maximum speed, minimum speed, position, 
	  * distance to checkpoint, steerability and maximum damage is created.
	  *
	  * @param position: The position of the car.
	  * @param distance: The distance to checkpoint of the car.
	  * @param gearbox : The gearbox for this car.
	  *
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	public Car(int position, int distance,Gearbox gearbox){
		if(distance < 0)
			throw new IllegalArgumentException("The given distance is lower than 0");
		if(gearbox == null)
			throw new NullPointerException("The given gearbox is a null-reference");	
		setSpeed(0);
		setPosition(position);
		setDistanceToCheckPoint(distance);
		setGearbox(gearbox);
		$parts = new Part[3];
	}
		
	// INSPECTORS 
	 
	/**
	  * Return the current speed of this car.
	  *
	  * @return The speed of this car.
	  */
	public double getSpeed(){
		return $speed;
	}
	
	/**
	  * Returns a textual representation of this car
	  * @return A string containing the speed of this car
	  */
	public String getSpeedString(){
		return ""+(Math.floor(getSpeed()*10)/10);
	}
	
	/**
	  * Return the position of this car.
	  *
	  * @return The position of this car.
	  */
	public int getPosition(){
		return $position;
	}
	
	/**
	  * Return the parts of this car
	  *
	  * @return the parts of this car
	  */
	public Part[] getParts(){
		return $parts;
	}
	
	/**
	  * Return the distance to checkpoint of this car.
	  *
	  * @return The distance to checkpoint of this car.
	  */
	public int getDistanceToCheckPoint(){
		return $distanceToCheckPoint;
	}
			
	/**
	  * Return the body of this car
	  *
	  * @pre A body was already added to this car
	  * @return The body of this car
	  */
	public Body getBody(){
		return (Body)($parts[0]);
	}
	
	/**
	  * Return the engine of this car
	  *
	  * @pre An engine was already added to this car
	  * @return The engine of this car
	  */
	public Engine getEngine(){
		return (Engine)($parts[1]);
	}
	
	/**
	  * Return the tireset of this car
	  *
	  * @pre A tireset was already added to this car
	  * @return The tireset of this car
	  */
	public Tireset getTireset(){
		return (Tireset)($parts[2]);
	}
	
	/**
	  * Return the gearbox of this car
	  *
	  * @return The gearbox of this car
	  */
	public Gearbox getGearbox(){
		return $gearbox;
	}

	/**
	  * Return a boolean indicating whether the car is crashed.
	  *
	  * @return a boolean indicating whether the car is crashed.
	  */
	public boolean isCrashed(){
		return (getEngine().getDamage()==1 ||
				getBody().getDamage()==1 ||
				getTireset().getDamage()==1);
	}

	/**
	  * Return a boolean indicating whether the car has all his parts.
	  *
	  * @return a boolean indicating whether the car has all his parts.
	  */
	public boolean hasAllParts(){
		boolean allParts = true;
		for(int i=0; i < getParts().length;i++){
			if(getParts()[i]==null)
				allParts = false;
		}
		return(allParts);
	}
	
	/**
	  * Return the maximum speed this car can reach
	  *
	  * @return The maximum speed this car can reach
	  */
	double getMaximumReachableSpeed(Gear gear){
	//the minimum of the next speeds is returned:
	// *the maximumspeed of the current roadpart
	// *the maximumspeed of the engine (decreased with damage factor)
	// *the maximumspeed of the given gear
	return Math.min
			(Math.min(gear.getMaximumSpeed()*(1-getEngine().getDamage())
			,((EngineType)(getEngine().getPartType())).getMaximumSpeed())
			,RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getMaximumSpeed());		
	}
	
	/**
	  * Return the minimum reachable speed
	  *
	  * @return The minimum speed this car can reach
	  */
	private int getMinimumReachableSpeed(){
		return RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getMinimumSpeed();
	}
	
	/**
	  * Return the acceleration factor of this car
	  */
	private double getAccelerationFactor(){
	//The acceleration factor is calculated as the product of the acceleration factor of
	// the body, the engine, the current gear and the current roadpart.
	return(
	
		((EngineType)(getEngine().getPartType())).getAccelerationFactor()
		*
		((BodyType)(getBody().getPartType())).getAccelerationFactor()
		*
		getGearbox().getCurrentGear().getAccelerationFactor()	
		*
		RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getAccelerationFactor()
		*
		((TiresetType)(getTireset().getPartType())).getGrip()
	);
	}
	
	/**
	  * Return the deceleration factor of this car
	  */
	private double getDecelerationFactor(){
		//The deceleration factor of this car is calculated by multiplying the
		// deceleration factor of the current road part by the grip of this car.
		return (RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getDecelerationFactor()
				*
				((TiresetType)(getTireset().getPartType())).getGrip());
	}
	
	// MUTATORS 

	/**
	  * Set the given speed as the current speed of this car.
	  *
	  * @param speed: The speed of this car.
	  * @exception IllegalArgumentException
	  */
	void setSpeed(double speed){
		if(speed < 0)
			throw new IllegalArgumentException("Invalid speed. The speed is "+speed);
		$speed = speed;
	}
	
	/**
	  * Set the given position as the position of this car.
	  *
	  * @param position: the position of this car.
	  * @exception IllegalArgumentException
	  */
	void setPosition(int position){
		if(position < 0)
			throw new IllegalArgumentException("Invalid position.");
		$position = position;
	}
	
	/**
	  * Set the given distance as the distance to checkpoint of this car.
	  *
	  * @param distance: The distance to checkpoint of this car.
	  * @exception IllegalArgumentException
	  */
	void setDistanceToCheckPoint(int distance){
		if(distance < 0)
			throw new IllegalArgumentException("Invalid distance.");
		$distanceToCheckPoint = distance;
	}

	/**
	  * Set the given gearbox as gearbox for this car
	  *
	  * @param gearbox The gearbox which you want to set as gearbox for this car
	  */
	void setGearbox(Gearbox gearbox){
		if(gearbox == null)
			throw new NullPointerException("The given gearbox is a null reference");
		$gearbox = gearbox;
	}
	
	/**
	  * Add a part to this car.  If the car has already a part of the type of the given
	  *  part, the part is replaced by the new part.
	  *
	  * @param part: The part to add to the car
	  */
	void addPart(Part part){
		if(part instanceof Body)
			$parts[0] = part;	
		if(part instanceof Engine)
			$parts[1] = part;	
		if(part instanceof Tireset)
			$parts[2] = part;	
	}
	
	/**
	  * Repair a part of this car
	  *
	  * @param part: The part of this car you want to repair
	  * @param percentage: The percentage with which you want to repair the part
	  *
	  * @exception IllegalArgumentException
	  */
	void repairPart(Part part,double percentage)throws IllegalArgumentException{
		if(part == $parts[0])
			$parts[0].repair(percentage);
		else	{if(part == $parts[1])
					$parts[1].repair(percentage);
				 else {if(part == $parts[2])
						$parts[2].repair(percentage);
					   else throw new IllegalArgumentException("The given part is not a part of this car");
					   }
				 }	
	}
	 	 
	/**
	  * Accelerate the car
	  */
	void accelerate(){
		double step = getAccelerationFactor();
		setSpeed(
			Math.min(getSpeed()+step,getMaximumReachableSpeed(RaceController.getReference().getRace().getCar().getGearbox().getCurrentGear()))
		);	
	}
	
	/**
	  * Decelerate the car
	  */
	void decelerate(){
		double step = getDecelerationFactor();
		setSpeed(Math.max(getSpeed()-step,getMinimumReachableSpeed()));	
	
	}
	
	/**
	  * Steer the car to the left
	  */
	void steerLeft(){
		if (getSpeed()>0){
			double step = ((TiresetType)(getTireset().getPartType())).getSteerability()*(1-getTireset().getDamage())*RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getSteerability();
			setPosition(Math.max((int)(Math.round(getPosition()-step)),10));
		}
	}
	
	/**
	  * Steer the car to the right
	  */
	void steerRight(){
		if (getSpeed()>0){
			double step = ((TiresetType)(getTireset().getPartType())).getSteerability()*(1-getTireset().getDamage())*RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getSteerability();
			setPosition(Math.min((int)(Math.round(getPosition()+step)),433));
		}
	}
	
	/**
	  * Change the gear of this car one down
	  */
	void gearDown(){
		getGearbox().gearDown();
	}
	
	/**
	  * Change the gear of this car one up
	  */
	void gearUp(){
		getGearbox().gearUp();
	}
	
	/**
	  * Gradually wear the tires down
	  */
	void wearDownTires(){
		getTireset().damage(((TiresetType)(getTireset().getPartType())).getDurability()/1000000);
	}
		 
	// VARIABLES 
	 
	/*
	 * A variable (double) for holding the speed of this car.
	 */
	private double $speed;
		
	/*
	 * A variable (int) for holding the position of this car.
	 */
	private int $position;
	
	/*
	 * A variable (int) for holding the distance to checkpoint of this car.
	 */
	private int $distanceToCheckPoint;
		
	/*
	 * A variable for holding the different parts of this car
	 *
	 * The first part is always the body
	 * The second part is always the engine
	 * The last part is always the tireset
	 */
	private Part[] $parts;
	
	/*
	 * A variable for holding the gearbox of this car
	 */
	private Gearbox $gearbox;
}
  