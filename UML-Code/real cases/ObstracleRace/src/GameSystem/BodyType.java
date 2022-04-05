package GameSystem;

/**
  * A class of BodyTypes
  */
public class BodyType extends PartType{

	
	// CONSTRUCTORS	
	
	/**
	  * Create a new bodyType with a given name, accelerationFactor,
	  *	 price, filename, strength, repairPriceIndex.
	  *
	  * @param name: The name of the bodyType.
	  * @param filename: The filename of the picture of the bodyType.
	  * @param accelerationFactor: Factor at which this bodyType will accelarate.
	  * @param price: The price for buying this bodyType.
	  * @param strength: The strength associated with this bodyType.
	  * @param repairPriceIndex: The price to pay to repair this bodyType with one percentage.
	  * @exception IllegalArgumentException
	  */
	BodyType(String name, double accelerationFactor, int price, double strength, int repairPriceIndex,String filename){
		super(name, price, strength, repairPriceIndex,filename);
		if(accelerationFactor <= 0)
			throw new IllegalArgumentException("Invalid acceleration-factor");
		$ACCELERATIONFACTOR = accelerationFactor;	
	}


	// INSPECTORS
	
	/**
	  * Return the ACCELERATIONFACTOR of this bodyType.
	  *
	  * @return The ACCELERATIONFACTOR of this bodyType.
	  */
	public double getAccelerationFactor(){
		return $ACCELERATIONFACTOR;
	}
	
	/**
	  * Return the description of this bodyType.
	  *
	  * @return The description of this bodyType.
	  */
	public String getDescription(){
		String hulp = "<html>\n" +
					  "<ul>\n" +
					  "<li>The price: "+ getPrice()+" EUR.\n"+ 
					  "<li>The accelerationFactor: "+ getAccelerationFactor()+"\n"+ 
					  "<li>The strength: "+ getStrength()+"\n"+
					  "<li>The repairPriceIndex: "+ getRepairPriceIndex()+"\n"+
					  "</ul>\n"; 
		return(hulp);
	}

	// VARIABLES

	/*
	 * The accelerationFactor of this bodyType.
	 */
	private final double $ACCELERATIONFACTOR;
}