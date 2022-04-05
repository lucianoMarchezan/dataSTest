package GameSystem;

/**
  * An abstact class of Parts.
  */
public abstract class Part{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new part with given PartType.
	  *
	  * @param partType: The partType of this part.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Part(PartType partType){
		if (partType==null)
			throw new NullPointerException("Invalid part type");
		$PARTTYPE = partType;
		$damage = 0;
	}
	
	// MUTATORS
	
	/**
	  * A part is damaged with a given percentage.
	  *
	  * @param percentage: A percentage indicating how much the part is damaged.
	  */
	protected void damage(double percentage){
		$damage = Math.min($damage+percentage,1);
	}
		
	/**
	  * A part is repaired for a given percentage.
	  *
	  * @param percentage: A percentage indicating how much the part is repaired.
	  */
	protected void repair(double percentage){
		$damage = Math.max(0,$damage-percentage);
	}
		
	// INSPECTORS 
	 
	/**
	  * Return the partType of this part.
	  *
	  * @return The partType of this part.
	  */
	public PartType getPartType(){
		return $PARTTYPE;
	}

 	/**
	  * Return the damage of this part.
	  *
	  * @return The damage of this part.
	  */
	public double getDamage(){
		return $damage;
	}
		
	// VARIABLES 
	 
	/*
	 * A variable (PartType) for holding the PartType of this part.
	 */
	protected final PartType $PARTTYPE;
	
	/*
	 * A variable (Damage) for holding the damage of this part.
	 * The damage is a real number between 0 and 1.
	 */
	protected double $damage;
}