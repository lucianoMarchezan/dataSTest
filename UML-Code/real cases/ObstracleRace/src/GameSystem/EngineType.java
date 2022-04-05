package GameSystem;

/**
  * A class of EngineTypes
  */
public class EngineType extends PartType{

	
	// CONSTRUCTORS	
	
	/**
	  * Create a new engineType with a given name, accelerationFactor,maximumSpeed,
	  *	 price, filename, strength, repairPriceIndex.
	  *
	  * @param name: The name of the engineType.
	  * @param filename: The filename of the picture of the engineType.
	  * @param accelerationFactor: Factor at which this engineType will accelarate.
	  * @param maximumSpeed: The maximumSpeed that this engineType can produce.
	  * @param price: The price for buying this engineType.
	  * @param strength: The strength associated with this engineType.
	  * @param repairPriceIndex: The price to pay to repair this engineType with one percentage.
	  * @exception IllegalArgumentException
	  */
	EngineType(String name, double accelerationFactor, int maximumSpeed, int price, double strength, int repairPriceIndex, String filename){
		super(name, price, strength, repairPriceIndex, filename);
		if(accelerationFactor <= 0)
			throw new IllegalArgumentException("Invalid acceleration-factor");
		$ACCELERATIONFACTOR = accelerationFactor;	
		if(maximumSpeed <= 0)
			throw new IllegalArgumentException("Invalid maximumSpeed");
		$MAXIMUMSPEED = maximumSpeed;		
	}


	// INSPECTORS
	
	/**
	  * Return the ACCELERATIONFACTOR of this engineType.
	  *
	  * @return The ACCELERATIONFACTOR of this engineType.
	  */
	public double getAccelerationFactor(){
		return $ACCELERATIONFACTOR;
	}
	
	/**
	  * Return the description of this engineType.
	  *
	  * @return The description of this engineType.
	  */
	public String getDescription(){
		String hulp = "<html>\n" +
					  "<ul>\n" +
					  "<li>The price: "+ getPrice() +" EUR\n"+ 
			   		  "<li>The maximumSpeed: " + getMaximumSpeed()+" km/h\n"+ 
			   		  "<li>The accelerationFactor: "+ getAccelerationFactor()+"\n"+
			   		  "<li>The strength: "+ getStrength()+".\n"+
			   		  "<li>The repairPriceIndex: "+ getRepairPriceIndex()+"\n"+
			   		  "</ul>\n";
		return(hulp);
	}
	
	/**
	  * Return the MAXIMUMSPEED of this engineType.
	  *
	  * @return The MAXIMUMSPEED of this engineType.
	  */
	public int getMaximumSpeed(){
		return $MAXIMUMSPEED;
	}
	
	
	// VARIABLES

	/*
	 * The accelerationFactor of this engineType.
	 */
	private double $ACCELERATIONFACTOR;
	
	/*
	 * The maximumDamage of this engineType.
	 */
	private int $MAXIMUMSPEED;
}