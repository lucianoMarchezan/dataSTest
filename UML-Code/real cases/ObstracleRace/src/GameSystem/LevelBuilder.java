package GameSystem;

/**
  * A class for building levels.
  */
class LevelBuilder{

	
	// INSPECTORS
		
	/**
	  * Return the level built by this level builder.
	  *
	  * @return The level built by this level builder.
	  */
	Level getLevel(){
		return $level;
	}
	
	
	// MUTATORS
	
	/**
	  * Build a level with a given level number.
	  *
	  * @param levelNumber: The level number of the level that has to be built.
	  * @exception IllegalArgumentException
	  */
	void buildLevel(int levelNumber){
		if(levelNumber <= 0 || levelNumber > LevelCatalog.getReference().getLevelTypes()[LevelCatalog.getReference().getLevelTypes().length-1].getLevelNumber())			
			throw new IllegalArgumentException("Invalid levelNumber");
		//get the array with all the leveltypes of this levelNumber
		LevelType[] levelTypes = LevelCatalog.getReference().getLevelTypes(levelNumber);
	
		//choose a levelType from this array at random
		int randomNumber = (int)(Math.floor(Math.random()*(levelTypes.length)));
	
		//get the level type with the generated number
		LevelType levelType = levelTypes[randomNumber];
		$level = new Level(levelType);
	}

		
	// VARIABLES
	
	/*
	 * The level this levelBuilder has built.
	 */
	private Level $level;
}