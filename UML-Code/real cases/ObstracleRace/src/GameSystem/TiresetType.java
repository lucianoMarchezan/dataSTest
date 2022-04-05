package GameSystem;

/**
  * A class of TiresetTypes
  */
public class TiresetType extends PartType{

	
	// CONSTRUCTORS	
	/**
	  * Create a new tiresetType with a given name, durability, grip,
	  *	 steerability, price, filename, strength, repairPriceIndex.
	  *
	  * @param name: The name of the tiresetType.
	  * @param filename: The filename of the picture of the tiresetType.
	  * @param durability: Factor that indicates the durability of this tiresetType.
	  * @param grip: The grip for this tiresetType.
	  * @param steerability: The steerability of this tiresetType.
	  * @param price: The price of this tiresetType
	  * @param strength: The strength of this tiresetType
	  * @param repairPriceIndex: The amount of money to be paid for each percent of damage you want to repair
	  * @exception IllegalArgumentException
	  */
	TiresetType(String name, double durability, double grip, double steerability, int price, double strength, int repairPriceIndex, String filename){
		super(name, price, strength, repairPriceIndex,filename);
		if(durability <= 0)
			throw new IllegalArgumentException("Invalid durability");
		$DURABILITY = durability;
		if(grip <= 0)
			throw new IllegalArgumentException("Invalid grip");
		$GRIP = grip;
		if(steerability <= 0)
			throw new IllegalArgumentException("Invalid steerability");
		$STEERABILITY = steerability;	
	}


	// INSPECTORS
	
	/**
	  * Return the DURABILITY of this tiresetType.
	  *
	  * @return The DURABILITY of this tiresetType.
	  */
	public double getDurability(){
		return $DURABILITY;
	}
	
	/**
	  * Return the GRIP of this tiresetType.
	  *
	  * @return The GRIP of this tiresetType.
	  */
	public double getGrip(){
		return $GRIP;
	}
	
	/**
	  * Return the STEERABILITY of this tiresetType.
	  *
	  * @return The STEERABILITY of this tiresetType.
	  */
	public double getSteerability(){
		return $STEERABILITY;
	}
	
	/**
	  * Return the description of this tiresetType.
	  *
	  * @return The description of this tiresetType.
	  */
	public String getDescription(){
		String hulp = "<html>\n" +
					  "<ul>\n" +
					  "<li>The price: "+ getPrice() + " EUR.\n" +
			          "<li>The durability: " + getDurability()+ "\n" +
			   		  "<li>The grip: "+ getGrip()+ "\n" +
			   		  "<li>The strength: "+ getStrength()+"\n" +
			   		  "<li>The steerability: "+ getSteerability()+"\n" +
			   		  "</ul>\n";
		return(hulp);
	}
	
	
	// VARIABLES

	/*
	 * The DURABILITY of this tiresetType.
	 */
	private final double $DURABILITY;
	
	/*
	 * The GRIP of this tiresetType.
	 */
	private final double $GRIP;
	
	/*
	 * The STEERABILITY of this tiresetType.
	 */
	private final double $STEERABILITY;
}