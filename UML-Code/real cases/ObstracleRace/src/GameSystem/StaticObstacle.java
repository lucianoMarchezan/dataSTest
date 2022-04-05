package GameSystem;
import java.awt.*;

/**
  * A class to represent non-moving obstacles lying on the road surface
  */
public class StaticObstacle extends Obstacle{
	
	// CONSTRUCTORS
	
	/**
	  * Constructs a new static obstacle
	  */
	StaticObstacle(ObstacleType obstacleType, int posX, int posY){
		super(obstacleType, posX, posY);
	}
	
	// INSPECTOR
	
	/**
	  * Return the image of this static obstacle
	  *
	  * @return the image of this static obstacle
	  */
	public Image getImage(){
		return getObstacleType().getImage();
	}
}
