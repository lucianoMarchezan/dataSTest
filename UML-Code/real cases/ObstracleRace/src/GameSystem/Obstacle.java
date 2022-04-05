package GameSystem;
import java.awt.*;

/**
  * A class of obstacles.
  */
public abstract class Obstacle extends RoadObject{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new obstacle with given obstacleType, x-position and y-position is created.
	  *
	  * @param obstacleType: The obstacleType of this obstacle.
	  * @param posX: the x-position of this obstacle.
	  * @param posY the y-position of this obstacle.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Obstacle(ObstacleType obstacleType, int posX, int posY){
		super(posX, posY);
		if (obstacleType==null)
			throw new NullPointerException("Invalid obstacle type");
		$OBSTACLETYPE = obstacleType;
	}
		
	// INSPECTORS 
	 
	/**
	  * Returns the colour of this obstacle
	  *
	  * @return the colour of this obstacle
	  */
	public Color getColour(){
		return getObstacleType().getColour();
	}
	
	/**
	  * Returns the obstacleType of this obstacle.
	  *
	  * @return The obstacleType of this obstacle.
	  */
	public ObstacleType getObstacleType(){
		return $OBSTACLETYPE;
	}
		
	// MUTATORS
		
	/**
	  * The given car collides with the obstacle, gets damage and loses speed
	  *
	  * @param car the car that hits this obstacle
	  */
	public void collideWith(Car car){
		if(!isHit()) RaceController.getReference().getSoundPlayer().playCollision(getObstacleType());
		damageCar(car);
	}
	
	/**
	  * Damages the given car when hit by this obstacle
	  *
	  * @param car the car that hits this obstacle
	  */
	protected void damageCar(Car car){		
		car.getEngine().damage(getObstacleType().getDamageEngine()*(1-car.getEngine().getPartType().getStrength()));
		car.getBody().damage(getObstacleType().getDamageBody()*(1-car.getBody().getPartType().getStrength()));
		car.getTireset().damage(getObstacleType().getDamageTires()*(1-car.getTireset().getPartType().getStrength()));
		car.decelerate();
		setHit(true);
	}
	
	/**
	  * Leaves the obstacle in place
	  */
	public void move(){}
	
	// VARIABLES 
	 
	/*
	 * A variable (ObstacleType) for holding the obstacleType of this obstacle.
	 */
	private final ObstacleType $OBSTACLETYPE;
}