package GameSystem;

import java.awt.*;

/**
  * A class to represent moving obstacles lying on the road surface
  */
public class MovingObstacle extends Obstacle {
	
	// CONSTRUCTORS
	
	/**
	  * A new moving obstacle with given obstacleType, x-position, y-position and speed is created.
	  *
	  * @param obstacleType The obstacleType of this obstacle.
	  * @param posX the x-position of this obstacle.
	  * @param posY the y-position of this obstacle.
	  * @param speed the speed of this moving obstacle
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	MovingObstacle(ObstacleType obstacleType, int posX, int posY, int speed){
		super(obstacleType, posX, posY);
		$SPEED = speed;
		$direction = 1;
	}
	
	// INSPECTORS
	
	/**
	  * Returns the image of this moving obstacle
	  *
	  * @return the image of this moving obstacle
	  */
	public Image getImage(){
		return IMAGE;
	}
		
	/**
	  * returns the speed of this moving obstacle
	  *
	  * @return the speed of this moving obstacle
	  */
    public double getSpeed(){
    	return $SPEED;
    }
    
    // MUTATORS
    		    
    /**
      * moves this moving obstacle
      */
    public void move(){	
       	setPosition(getPosX()+$direction,getPosY()+$SPEED);
		if(getPosX()<=10 || getPosX()>=438)
			$direction*=-1;	
		if(!checkCollision(RaceController.getReference().getRace().getCar())) setHit(false);			 
    }
    
    // VARIABLES
    
    // the speed of this moving obstacle
    private final int $SPEED;

	// the direction of this moving obstacle
	private int $direction;
		
	// the image of this moving obstacle
	private final static Image IMAGE=Toolkit.getDefaultToolkit().getImage("images\\movingobstacle.gif");
}

