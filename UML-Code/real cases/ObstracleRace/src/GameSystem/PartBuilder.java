package GameSystem;

/**
  * A class for building parts
  */
class PartBuilder{
	
	//CONSTRUCTOR
	/**
	  * Create a new partbuilder
	  */
	public PartBuilder(){
		$parts = new Part[0];
	}
	
	//INSPECTORS
	/**
	  * Return the parts built by this partbuilder
	  *
	  * @return The parts built by this partbuilder
	  */
	Part[] getParts(){
		return $parts;
	}
	
	//MUTATORS
	/**
	  * Build parts
	  */
	void buildParts(){						
		//build 15 tiresets for each tireset type
		for(int i=0; i < TiresetCatalog.getReference().getTiresetTypes().length; i++)
			for(int j=0;j<15;j++)
				addPart(new Tireset(TiresetCatalog.getReference().getTiresetTypes()[i]));
		//build 15 bodies for each body type
		for(int i=0; i < BodyCatalog.getReference().getBodyTypes().length; i++)
			for(int j=0;j<15;j++)
			    addPart(new Body(BodyCatalog.getReference().getBodyTypes()[i]));
		//build 15 engines for each engine type
		for(int i=0; i < EngineCatalog.getReference().getEngineTypes().length; i++)	
			for(int j=0;j<15;j++)
				addPart(new Engine(EngineCatalog.getReference().getEngineTypes()[i]));
	}
	
	/**
	  * Add a part to the array of parts built by this partbuilder
	  */
	private void addPart(Part part){
		Part[] temp = new Part[$parts.length+1];
		for(int i=0;i<$parts.length;i++)
				temp[i] = $parts[i];
		temp[$parts.length] = part;
		$parts = temp; 	
	}
	
	//VARIABLES
	/**
	  * A variable for holding the parts built by this partbuilder
	  */
	private Part[] $parts;
	

}