package GameSystem;

/**
  * A abstract class of Part types
  */
public abstract class PartType{

	
	// CONSTRUCTORS	
	/**
	  * Create a new PartType with a given name, price, strength, repairPriceIndex and filename.
	  *
	  * @param name: The name of the partType.
	  * @param filename: The name of the picture of the partType.
	  * @param price: The price of the partType
	  * @param strength: The strength of the partType.
	  * @param repairPriceIndex: The amount of money to be paid for each percent of damage you want to repair.
	  * @exception IllegalArgumentException
	  * @exception NullPointerException
	  */
	PartType(String name, int price, double strength, int repairPriceIndex, String filename){
		if(filename==null)
			throw new NullPointerException("Invalid name (null)");
		if(filename.equals(""))
			throw new IllegalArgumentException("Invalid name (empty)");
		$FILENAME = filename;
		if(name==null)
			throw new NullPointerException("Invalid filename (null)");
		if(name.equals(""))
			throw new IllegalArgumentException("Invalid name (empty)");
		$NAME=name;
		if(price <= 0)
			throw new IllegalArgumentException("Invalid PRICE");
		$PRICE = price;
		if(strength < 0)
			throw new IllegalArgumentException("Invalid strength");
		$STRENGTH = strength;
		if(repairPriceIndex < 0)
			throw new IllegalArgumentException("Invalid Repair Price Index");
		$REPAIR_PRICE_INDEX = repairPriceIndex;
	}


	// INSPECTORS
	
	/**
	  * Return the PRICE of this PartType.
	  *
	  * @return The PRICE of this PartType.
	  */
	public int getPrice(){
		return $PRICE;
	}
	
	/**
	  * Return the NAME of this PartType.
	  *
	  * @return The NAME of this PartType.
	  */
	public String getName(){
		return $NAME;
	}
	
	/**
	  * Return the description of this PartType.
	  *
	  * @return The description of this PartType.
	  */
	public abstract String getDescription();

	/**
	  * Return the FILENAME of the picture of this PartType.
	  *
	  * @return The FILENAME of the picture of this PartType.
	  */
	public String getFileName(){
		return $FILENAME;
	}

			
	/**
	  * Return the STRENGTH of this PartType.
	  *
	  * @return The STRENGTH of this PartType.
	  */
	public double getStrength(){
		return $STRENGTH;
	}
	
	/**
	  * Return the REPAIR_PRICE_INDEX of this PartType.
	  *
	  * @return The REPAIR_PRICE_INDEX of this PartType.
	  */
	public int getRepairPriceIndex(){
		return $REPAIR_PRICE_INDEX;
	}

	// VARIABLES

	/*
	 * The name of this PartType.
	 */
	private final String $NAME;

	/*
	 * The filename of the picture of this PartType.
	 */
	private final String $FILENAME;
	
	/*
	 * The PRICE of this PartType.
	 */
	private final int $PRICE;
	
	/*
	 * The strength of this PartType.
	 */
	private final double $STRENGTH;
	
	/*
	 * The repairPriceIndex of this PartType.
	 */
	private final int $REPAIR_PRICE_INDEX;
	
	
}