package GameSystem;
import java.awt.*;

/** 
  * A class for building roadparts.
  */
class RoadPartBuilder{


	// CONSTRUCTORS
		
	/**
	  * Builds a roadPart of the given level.
	  *
	  * @param level: The level which this roadPart belongs to. 
	  * @exception NullPointerException
	  */
	void buildRoadPart(Level level){
		if(level==null)
			throw new NullPointerException("Invalid level");
		LevelType ltype = level.getLevelType();
		
		// determine type of new roadpart
		int randomInt = (int)Math.floor(Math.random()*10+1);
		int[] partProb = ltype.getPartProbability();
		int i=0;
		for(;i<partProb.length && randomInt>0;i++)
			randomInt-=partProb[i];
	    RoadPartType rpType = RoadPartCatalog.getReference().getRoadPartTypes()[i-1];
		
		// create new roadpart
		$roadPart = new RoadPart(rpType);		
		
		// determine amount of static obstacles
		int nbObstacles = ltype.getNumberOfStaticObstacles()/ltype.getNumberOfParts();

		// add static obstacles
		for (i=1;i<=nbObstacles;i++){
			// determine type of new static obstacle
			randomInt = (int)Math.floor(Math.random()*10+1);
			int[] obsProb = ltype.getObstacleTypeProbability();
			int type=0;
			for(;type<obsProb.length && randomInt>0;type++){
				randomInt-=obsProb[type];
			}
			// create new obstacle
			ObstacleType obstacleType = ObstacleCatalog.getReference().getObstacleTypes()[type-1];
			int posX = (int)Math.floor(Math.random()*(473-obstacleType.getSize()))+10;
			int posY = (int)Math.floor(Math.random()*RoadPart.LENGTH);
			$roadPart.addRoadObject(new StaticObstacle(obstacleType, posX, posY));
		}
		
		//determine amount of moving obstacles
		int nbMovObst = ltype.getNumberOfMovingObstacles()/ltype.getNumberOfParts();
		
		//get obstacletype "medium"
		ObstacleType mediumType = ObstacleCatalog.getReference().getObstacleType("middle");
		
		//add moving obstacles
		for(i=1;i<=nbMovObst;i++){			
			int posX = (int)Math.floor(Math.random()*(473-mediumType.getSize()))+10;
			int posY = (int)Math.floor(Math.random()*RoadPart.LENGTH);
			$roadPart.addRoadObject(new MovingObstacle(mediumType, posX, posY, 3));
		}
		
		//determine amount of bonus items
		int nbBonus = ltype.getNumberOfMovingObstacles()/ltype.getNumberOfParts();

		//add bonus items
		for(i=1;i<=nbBonus;i++){			
			int posX = (int)Math.floor(Math.random()*(473-mediumType.getSize()))+10;
			int posY = (int)Math.floor(Math.random()*RoadPart.LENGTH);
			$roadPart.addRoadObject(new BonusItem(posX, posY, ltype.getBonusTime(),"bonusitem",15,new Color(184,24,120)));
		}
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the built roadPart (if no roadPart was built, null will be returned)
	  *
	  * @return The built roadPart and if no roadPart was built, null will be returned.  
	  */
	public RoadPart getRoadPart(){
		return $roadPart;
	}
	  
	// VARIABLES
	
	/*
	 * The built roadPart. 
	 */
	private  RoadPart $roadPart;
}