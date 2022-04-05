package GameSystem;

/**
  * A class of level types
  */
public class LevelType{

	
	// CONSTRUCTORS	
	
	/**
	  * Create a new levelType with a given name, obstacleTypeProbability,numberOfparts,
	  *	 numberOfObstacles and partProbability.
	  *
	  * @pre obstacleTypeProbability elements have to be non-negative and have to add up to 10.
	  * @pre partProbability elements have to be non-negative and have to add up to 10.
	  * @param obstacleTypeProbability The probabilities of appearances of obstacles 
	  *                                 in the new levelType.
	  * @param numberOfParts The number of road parts of the new levelType.
	  * @param numberOfStaticObstacles The number of static obstacles of the new levelType.
	  * @param numberOfMovingObstacles The number of moving obstacles of the new levelType.
	  * @param numberOfBonusItems The number of bonus items of the new levelType.
	  * @param bonusTime The bonustime (in seconds) of the bonusitems of this new levelType
	  * @param startTime The time the player gets (in seconds) when he starts a level of this levelType
	  * @param partProbability The probability of the appearance of certain roadPartTypes.
	  * @param levelNumber The number of this levelType.
	  * @exception IllegalArgumentException
	  */	 	 
	LevelType(int[] obstacleTypeProbability,int numberOfParts,int numberOfStaticObstacles,
			  int numberOfMovingObstacles, int numberOfBonusItems,int bonusTime, int startTime, int[] partProbability,int levelNumber){
		$OBSTACLETYPE_PROBABILITY = obstacleTypeProbability;
		$PARTPROBABILITY = partProbability;
		if(numberOfParts <= 0)
			throw new IllegalArgumentException("Invalid number of parts");
		$NB_PARTS = numberOfParts;
		if(numberOfStaticObstacles <= 0)	
			throw new IllegalArgumentException("Invalid number of static obstacles");
		$NB_STATIC_OBSTACLES = numberOfStaticObstacles;
		if(numberOfMovingObstacles <= 0)	
			throw new IllegalArgumentException("Invalid number of moving obstacles");
		$NB_MOVING_OBSTACLES = numberOfMovingObstacles;
		if(numberOfBonusItems <= 0)	
			throw new IllegalArgumentException("Invalid number of bonus items");
		$NB_BONUSITEMS = numberOfBonusItems;
		if(startTime <= 0)	
			throw new IllegalArgumentException("Invalid starttime");
		$STARTTIME = startTime;
		if(levelNumber <=0)
			throw new IllegalArgumentException("Invalid level number");
		$LEVELNUMBER = levelNumber;	
		if(bonusTime <= 0)
			throw new IllegalArgumentException("Invalid bonus time");
		$BONUSTIME = bonusTime;
	}


	// INSPECTORS
	
	/**
	  * Return the bonustime of the bonusitems in this levelType
	  *
	  * @return the bonustime of the bonusitems on this levelType
	  */
	public int getBonusTime(){
		return $BONUSTIME;
	}
	
	/**
	  * Return the number of static obstacles in this levelType.
	  *
	  * @return The number of static obstacles in this levelType.
	  */
	public int getNumberOfStaticObstacles(){
		return $NB_STATIC_OBSTACLES;
	}
	
	/**
	  * Return the number of moving obstacles in this levelType.
	  *
	  * @return The number of moving obstacles in this levelType.
	  */
	public int getNumberOfMovingObstacles(){
		return $NB_MOVING_OBSTACLES;
	}
	
	/**
	  * Return the number of bonusitems in this levelType.
	  *
	  * @return The number of bonusitems in this levelType.
	  */
	public int getNumberOfBonusItems(){
		return $NB_BONUSITEMS;
	}
	
	/**
	  * Return the number of roadParts of this levelType
	  *
	  * @return The number of roadParts of this levelType.
	  */
	public int getNumberOfParts(){
		return $NB_PARTS;
	}
	
	/**
	  * Returns the probabilities at which obstacletypes appear in this levelType.
	  * These probabilities only refer to static obstacletypes
	  *
	  * @return The probabilities at which obstacletypes appear in this levelType.
	  */
	public int[] getObstacleTypeProbability(){
		return $OBSTACLETYPE_PROBABILITY;
	}
	
	/**
	  * Returns the probability of the appearance of certain roadPartTypes of this levelType.
	  *
	  * @return The probability of the appearance of certain roadPartTypes of this levelType.
	  */
	public int[] getPartProbability(){
		return $PARTPROBABILITY;
	}
	
	/**
	  * Returns the time the player gets when he starts a level of this levelType
	  *
	  * @return The time the player gets when he starts a level of this levelType
	  */
	public double getStartTime(){
		return $STARTTIME;
	}
	
	/**
	  * Return the number of this levelType.
	  *
	  * @return The number of this levelType.
	  */
	public int getLevelNumber(){
		return $LEVELNUMBER;
	}
		
	// VARIABLES
	
	/*
	 * The bonustime of the bonusitems in this levelType
	 */
	private final int $BONUSTIME;
	
	/*
	 * The level number of this levelType.
	 */
	private final int $LEVELNUMBER;
	
	/*
	 * The number of bonusitems in this levelType.
	 */
	private final int $NB_BONUSITEMS;
	
	/*
	 * The number of static obstacles in this levelType.
	 */
	private final int $NB_STATIC_OBSTACLES;
	
	/*
	 * The number of moving obstacles in this levelType.
	 */
	private final int $NB_MOVING_OBSTACLES;
	
	/*
	 * The number of road parts of this levelType.
	 */
	private final int $NB_PARTS;
	
	/*
	 * The probability at which obstacletypes appear in this levelType.
	 * These probabilities refer only to static obstacles
	 */
	private final int[] $OBSTACLETYPE_PROBABILITY;
	
	/*
	 * The probality of the appearance of roadPartTypes.
	 */
	private final int[] $PARTPROBABILITY;
	
	/*
	 * The time the player gets (in seconds) when he starts a level of this levelType
	 */
	private final int $STARTTIME;
}