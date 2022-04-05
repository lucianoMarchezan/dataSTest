package GameSystem;

/**
  * A class of manual gearboxes
  */
  
class ManualGearbox extends Gearbox{

	//CONTSTRUCTOR
	/**
	  * Create a new manual Gearbox with a given array of gears
	  *
	  * @exception NullPointerException
	  */
	public ManualGearbox(Gear[] gears){
		super(gears,"Manual");
	}

	//MUTATOR
	/**
	  * Check whether the current gear has to be changed and change it if necessary.
	  */
	void check(){
	//nothing is done because this is a manual gearbox
	}	

}