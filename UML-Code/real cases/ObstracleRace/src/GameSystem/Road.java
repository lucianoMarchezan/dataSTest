package GameSystem;

/**
  * A class of roads.
  */
public class Road{

	
	// CONSTRUCTORS
	
	/**
	  * Creates a new road and initializes with the given level and with no roadparts as yet. 
	  *
	  * @param level of this road.
	  * @exception NullPointerException
	  */
	Road(Level level){
		if (level==null)		
			throw new NullPointerException("Invalid level");
		$roadParts = new RoadPart[0];
		$LEVEL=level;
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the roadparts of which this road consists.
	  *
	  * @return The roadparts of which this road consists.     
	  */
	public RoadPart[] getRoadParts(){
		return $roadParts;
	}
	
	/**
	  * Returns the level of this road.
	  *
	  * @return The level of this road. 
	  */
	public Level getLevel(){
		return $LEVEL;
	}
	
	
	// MUTATORS
	
	/**
	  * Adds a roadpart at the end of this road.
	  *
	  * @param roadPart: The roadPart to be added. 
	  * @exception NullPointerException
	  */
	void addRoadPart(RoadPart roadPart){
		if (roadPart==null)	
			throw new NullPointerException("Invalid roadpart");
		int length = $roadParts.length;
		RoadPart[] roadPartsOud = $roadParts;
		$roadParts = new RoadPart[length+1];
		for (int i=0;i<length;i++) 
			$roadParts[i]=roadPartsOud[i];
		$roadParts[length]=roadPart;
	}
	
	
	// VARIABLES
	
	/*
	 * The roadparts of which this road consists. 
	 */
	private RoadPart[] $roadParts;
	
	/* 
	 * The dificulty-level of this road 
	 */
	private final Level $LEVEL;
}