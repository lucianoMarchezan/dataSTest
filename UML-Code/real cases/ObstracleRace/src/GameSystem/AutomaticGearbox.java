package GameSystem;

/**
  * A class of automatic gearboxes
  */
  
class AutomaticGearbox extends Gearbox{
	
	//CONSTRUCTOR
	/**
	  * Create a new Automatic Gearbox with a given array of gears
	  *
	  * @exception NullPointerException
	  */
	public AutomaticGearbox(Gear[] gears){
		super(gears,"Automatic");
	}

	//MUTATORS
	/**
	  * Check whether the current gear has to be changed and change it
	  * if necessary.
	  */
	void check(){
		Car car = RaceController.getReference().getRace().getCar();
		//check whether the current gear has to be changed in a higher gear
		// and change the gear if so
		if(car.getSpeed() >= car.getMaximumReachableSpeed(car.getGearbox().getCurrentGear()))
			gearUp();
		//check whether the current gear has to be changed in a higher gear
		// and change the gear if so
		if(car.getGearbox().getCurrentGear().getNumber()>=2)	
			if(car.getSpeed() < car.getMaximumReachableSpeed(car.getGearbox().getGears()[car.getGearbox().getCurrentGear().getNumber()-2]))
				gearDown();
	}	

}