package GameSystem;

/** 
  * A class for building Road-instances. 
  */
class RoadBuilder{


	// CONSTRUCTORS
	
	/**
	  * Creates a new road of the given difficulty level and stores it
	  *
	  * @param level: The difficulty level of the new road.
	  * @exception NullPointerException
	  */
	void buildRoad(Level level){	
		if (level==null)
			throw new NullPointerException("Invalid level");	
		// create Road-instance
		$road = new Road(level);
		
		// determine number of roadparts
		LevelType ltype = level.getLevelType();
		int nbParts = ltype.getNumberOfParts();
		
		// create and add RoadPart-instances
		RoadPartBuilder roadPartBuilder = new RoadPartBuilder();
		for(int i=1;i<=nbParts;i++){
			roadPartBuilder.buildRoadPart(level);
			$road.addRoadPart(roadPartBuilder.getRoadPart());
		}
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns a built road (if no road was built, null will be returned).
	  *
	  * @return A built road and if no road was built, null will be returned.     
	  */
	public Road getRoad(){
		return $road;
	}

	
	// VARIABLES	
	
	/*
	 * The built road. 
	 */
	private Road $road;
}