package GameSystem;

/**
  * A class of races
  */
public class Race{
	
	
	// CONSTRUCTORS
	
	/**
	  * Create a new race with a given player,car,roads, currentRoad and shop
	  *
	  * @pre roads has to contain real Road instances.
	  * @param player: The player who rides this race.
	  * @param car: The car in which this race is driven.
	  * @param roads: The roads on which this race is driven.
	  * @param currentRoad: The number of the current road.
	  * @param shop: The shop which is used in this race.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Race(Player player,Car car,Road[] roads,int currentRoad, Shop shop){
		if(player==null)
			throw new NullPointerException("Invalid player");
		if(car==null)
			throw new NullPointerException("Invalid car");		
		if(roads == null)
			throw new NullPointerException("Invalid roads");
		if(currentRoad<0 || currentRoad>=roads.length)
			throw new IllegalArgumentException("Invalid current road");
		if(shop==null)
			throw new NullPointerException("Invalid shop");
		$player = player;
		$car = car;
		$roads = roads;
		$currentRoad = currentRoad;
		$shop = shop;
	}
	
	
	// INSPECTORS
	
	/**
	  * Return the roads on which this race is driven.
	  *
	  * @return The roads on which this race is driven.
	  */
	public Road[] getRoads(){
		return $roads;
	}
	
	/**
	  * Return the player who rides this race.
	  *
	  * @return The player who rides this race.
	  */
	public Player getPlayer(){
		return $player;
	}

	/**
	  * Return the car in which this race is driven.
	  *
	  * @return The car in which this race is driven.
	  */
	public Car getCar(){
		return $car;
	}	
	
	/**
	  * Return the shop attached to this race.
	  *
	  * @return The shop attached to this race.
	  */
	public Shop getShop(){
		return $shop;
	}	

	/**
	  * Returns the time left in seconds.
	  *
	  * @return The time left in seconds.
	  */
	public double getTime(){
		return Math.max(($time-System.currentTimeMillis())/1000.0,0);
	}
	
	/**
	  * Returns the time left in string-form.
	  *
	  * @return The time left in string-form.
	  */	  
	public String getTimeString(){
		return ""+(Math.floor(getTime()*10)/10);
	}

	/**
	  * Return the number of the current road in which the race is driven.
	  *
	  * @return The number of the current road in which the race is driven.
	  */
	public int getCurrentRoad(){
		return $currentRoad;
	}
	
	/**
	  * Return the current roadPart.
	  *
	  * @return The current roadPart in which the race is driven. 
	  */
	public RoadPart getCurrentRoadPart(){
		int totalRoadLength = getRoads()[getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
		int done = totalRoadLength - getCar().getDistanceToCheckPoint();
		int partindex = Math.min(done / RoadPart.LENGTH,getRoads()[getCurrentRoad()].getRoadParts().length-1);
		return(getRoads()[getCurrentRoad()].getRoadParts()[partindex]);		
	}
	
	/**
	  * Return the next roadPart.
	  *
	  * @return The next roadPart in which the car will drive. 
	  */
	public RoadPart getNextRoadPart(){
		if(getCar().getDistanceToCheckPoint()>RoadPart.LENGTH){
			int totalRoadLength = getRoads()[getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
			int done = totalRoadLength - getCar().getDistanceToCheckPoint();
			int partindex = done / RoadPart.LENGTH;
			return(getRoads()[getCurrentRoad()].getRoadParts()[partindex+1]);		
		}
		else{
			return getRoads()[getCurrentRoad()+1].getRoadParts()[0];
		}
	}
	
	
	// MUTATORS

	/**
	  * Changes the time left with the given difference
	  * 
	  * @param difference in time
	  */	  
	public void changeTime(double difference){
		$time = $time+difference;
	}
	
	/**
	  * Resets the time left with the starttime of the current level
	  */
	public void resetTime(){		
		$time = System.currentTimeMillis()+getRoads()[getCurrentRoad()].getLevel().getLevelType().getStartTime();
	}

	/**
	  * Set the number of the current road in which the race is driven.
	  *
	  * @param index: the number of the current road in which the race is driven.
	  * @exception IllegalArgumentException
	  */
	void setCurrentRoad(int index){
		if(index<0 && index>=getRoads().length)
			throw new  IllegalArgumentException("Invalid currentroad");		
		$currentRoad=index;
	}
	
	
	// VARIABLES
	
	/*
	 * The roads on which this race is driven.
	 */
	private Road[] $roads;
	
	/*
	 * The car in which this race is driven.
	 */
	private Car $car;
	
	/*
	 * The player who rides this race.
	 */
	private Player $player;
	
	/*
	 * The current road on which the race is driven
	 */
	private int $currentRoad;
	
	/*
	 * The shop attached on this race.
	 */
	private Shop $shop;
	
	/*
 	 * The time the player started
 	 */	
 	private double $time;
}
