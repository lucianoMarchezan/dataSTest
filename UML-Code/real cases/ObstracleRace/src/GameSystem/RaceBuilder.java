package GameSystem;

/**
  * A class for building a race.
  */
class RaceBuilder{
	
	// INSPECTORS
	
	/**
	  * Return the race built by this raceBuilder.
	  *
	  * @return The race that was built.
	  */
	public Race getRace(){
		return $race;
	}
	
	// MUTATORS
		
	/**
	  * Build a new race with given player and startLevel.
	  *
	  * @param player: The player that wants to drive the race.
	  * @param startLevel: The level the player wants to start in.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	void buildRace(Player player,int startLevel){
		if(player==null)
			throw new NullPointerException("Invalid player");
		//determine the number of levels
		int numberOfLevels = LevelCatalog.getReference().getLevelTypes()[LevelCatalog.getReference().getLevelTypes().length-1].getLevelNumber();
		if(startLevel>numberOfLevels || startLevel<1)
			throw new IllegalArgumentException("Invalid start level");		
		//build the levels
		Level[] levels = new Level[numberOfLevels];
		LevelBuilder levelBuilder = new LevelBuilder();
		for(int i=1;i<=numberOfLevels;i++){
			levelBuilder.buildLevel(i);
			levels[i-1]=levelBuilder.getLevel(); 	
		}
				
		//build the roads
		Road[] roads = new Road[numberOfLevels];
		RoadBuilder roadBuilder =  new RoadBuilder();
		for(int i=1;i<=numberOfLevels;i++){
			roadBuilder.buildRoad(levels[i-1]);
			roads[i-1]=roadBuilder.getRoad();
		}	
		
		//car
		Gearbox gearbox = RaceController.getReference().getDefaultGearbox();
		Car car = new Car(378,RoadPart.LENGTH*roads[startLevel-1].getLevel().getLevelType().getNumberOfParts(),gearbox);
		
		//shop
		ShopBuilder shopBuilder = new ShopBuilder();
		shopBuilder.buildShop();
		Shop shop = shopBuilder.getShop();
		// race
		$race = new Race(player,car,roads,startLevel-1,shop);	
	}
	
	// VARIABLES
	
	/*
	 * A variable (Race) for a holding a reference to the race
	 * this raceBuilder has built.
	 */
	private Race $race;
}
