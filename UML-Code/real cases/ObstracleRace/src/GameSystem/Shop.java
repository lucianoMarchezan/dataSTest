package GameSystem;

/**
  * A class of shops
  */
class Shop{

	//CONSTRUCTOR
	/**
	  * Create a new shop
	  */
	public Shop(){
		$parts = new Part[0];
	}

	//INSPECTORS
	/**
	  * Get the parts of this shop
	  *
	  * @return The parts this shop offers
	  */
	Part[] getParts(){
		return $parts;
	}
	
	/**
	  * Get a part of a specific part type
	  *
	  * @return A part of this shop of the given part type
	  */
	public Part getPart(PartType type){
		int i = 0;
		while(!(getParts()[i].getPartType() == type))
			i++;
		return getParts()[i];	
	}
	
	
	//MUTATORS
	/**
	  * Add parts to the parts of this shop
	  *
	  * @param parts: the parts you want to add to this shop
	  */
	void addParts(Part[] parts){
		Part[] temp = new Part[getParts().length+parts.length];
		for(int i=0;i<getParts().length;i++)
			temp[i] = getParts()[i];
		for(int i=getParts().length;i<getParts().length+parts.length;i++)
			temp[i] = parts[i-getParts().length];
		$parts = temp;	
	}
	
	/**
	  * Sell a part from this shop
	  *
	  * @param  part you want to sell from this shop
	  * @param  car to which you add the part
	  *
	  * @pre The given part has to be a part of this shop
	  *
	  * @exception NullPointerException	  
	  */
	void sellPart(Part part, Car car){
		if(part == null)
			throw new NullPointerException("The given part is a null reference");
		if(car == null)
			throw new NullPointerException("The given car is a null reference");	
		int index=0;
		while(getParts()[index] != part)
			index++;
		car.addPart($parts[index]);
		$parts[index] = null;
		for(int i=index;i<=getParts().length-2;i++)
			$parts[i] = $parts[i+1];
		Part[] temp = new Part[getParts().length-1];
		for(int i=0;i<temp.length;i++)
			temp[i] = getParts()[i];
		$parts = temp;	
	}
	  

	//VARIABLES
	/**
	  * A variable for holding the parts that this shop offers
	  */
	private Part[] $parts;
}