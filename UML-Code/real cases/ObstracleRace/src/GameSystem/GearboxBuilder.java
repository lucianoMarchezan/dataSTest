package GameSystem;

/**
  * A class for building gearboxes
  */

class GearboxBuilder{

	// INSPECTORS
		
	/**
	  * Return the gearbox built by this gearbox builder.
	  *
	  * @return The gearbox built by this gearbox builder.
	  */
	Gearbox getGearbox(){
		return $gearbox;
	}
	
	// MUTATORS
	
	/**
	  * Build an automatic gearbox.
	  */
	void buildAutomaticGearbox(){
		//build the necessary gears
		Gear[] gears = buildGears();
		$gearbox = new AutomaticGearbox(gears);
	}
	
	/**
	  * Build a manual gearbox.
	  */
	void buildManualGearbox(){
		//build the necessary gears
		Gear[] gears = buildGears();
		$gearbox = new ManualGearbox(gears);
	}
	
	/**
	  * Build the gears
	  */
	private Gear[] buildGears(){
		Gear gear1 = new Gear(1,20,0.8);
		Gear gear2 = new Gear(2,50,0.7);
		Gear gear3 = new Gear(3,85,0.5);
		Gear gear4 = new Gear(4,135,0.4);
		Gear gear5 = new Gear(5,200,0.3);		
		return new Gear[]{gear1,gear2,gear3,gear4,gear5};
	}
		
	// VARIABLES
	
	/*
	 * The gearbox this gearbox builder has built.
	 */
	private Gearbox $gearbox;

}