package GameSystem;

/**
  * A class for levels in the race.
  */
public class Level{
	
	
	// CONSTRUCTORS
	
	/**
	  * Create a new level with a given levelType.
	  * 
	  * @param levelType: The levelType of this level.
	  * @exception NullPointerException
	  */  
	Level(LevelType levelType){
		if(levelType == null)
			throw new NullPointerException("Invalid levelType.");
		$LEVELTYPE = levelType;
	}

	
	// INSPECTORS
	
	/**
	  * Return the levelType of this level.
	  *
	  * @return The levelType of this level.
	  */
	public LevelType getLevelType(){
		return $LEVELTYPE;
	}
	
	
	// VARIABLES
	
	/*
	 * The type of the level
	 */
	private final LevelType $LEVELTYPE;
}
