package GameSystem;
import java.awt.*;
import java.awt.image.*;

/**
  * A class to represent all objects which are lying on the road surface
  * (e.g. BSE cows, cars, etc.)
  */

public abstract class RoadObject implements ImageObserver{
	
	// CONSTRUCTORS
	
	/**
	  * Constructs a new roadobject with the given position
	  *
	  * @param posX the x-position of this roadobject
	  * @param posY the y-position of this roadobject
	  */
	RoadObject(int posX,int posY){	
		if (posX<0)
			throw new IllegalArgumentException("Invalid X position");
		if (posY<0)
			throw new IllegalArgumentException("Invalid Y position");
		$position = new Point(posX,posY);
		$hit=false;
	}
	
	// INSPECTORS
	
	/**
	  * Checks whether this object collides with the given car
	  *
	  * @return a boolean indicating whether this object collides with the given car
	  */
	public boolean checkCollision(Car car){		
	    // Calculate position car
	    Race race = RaceController.getReference().getRace();
		int totalRoadLength = race.getRoads()[race.getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
		int done = (totalRoadLength - car.getDistanceToCheckPoint())%RoadPart.LENGTH;
		    
		// check whether obstacle is approaching
		if(getPosY()-done<600+getHeight()){
			// check collision with obstacle
			Rectangle carBox = new Rectangle(car.getPosition(),493,50,80);
			Rectangle obstacle = new Rectangle(getPosX(),600-(getPosY()-done),getWidth(),getHeight());
			return carBox.intersects(obstacle);		
		}
		return false;
	}
		
	/**
	  * Returns the horizontal position of the road object on the roadpart
	  *
	  * @return the horizontal position of the road object
	  */
	public int getPosX(){
		return (int)$position.getX();
	}
	
	/**
	  * Returns the vertical position of the road object on the roadpart
	  *
	  * @return the vertical position of the road object
	  */
	public int getPosY(){
		return (int)$position.getY();
	}
	
	/**
	  * Returns the image of this road object
	  * 
	  * @return the image of this road object
	  */
	abstract public Image getImage();
		
	/**
	  * Returns the width of this road object in pixels
	  *
	  * @return the width of this road object in pixels
	  */
	public int getWidth(){
		return getImage().getWidth(this);
	}
		
	/**
	  * Returns the height of this road object in pixels
	  *
	  * @return the height of this road object in pixels
	  */
	public int getHeight(){
		return getImage().getHeight(this);
	}
	
	/**
	  * Returns the colour of this road object
	  *
	  * @return the colour of this road object
	  */	  
	abstract public Color getColour();
	
	// MUTATORS
		
	/**
	  * The given car collides with this road object
	  *
	  * @param car with which this object collides
	  */
	abstract public void collideWith(Car car);
	
	/*
	 * The image of this road object is updated
	 */	
    public boolean imageUpdate(Image img,  int infoflags, int x, int y, int width, int height){
    	return true;
    }
		
    /**
      * Returns a boolean indicating whether this car was hit or not
      *
      * @return A boolean indicating whether this car was hit or not
      */
    protected boolean isHit(){
    	return $hit;
    } 
    
	/**
	  * Moves the object if necessary
	  */
	abstract public void move();
	
	/**
	  * Sets the position of the current object in the given position
	  *
	  * @param posX the horizontal position
	  * @param posY the vertical position
	  */
	protected void setPosition(int posX, int posY){
		$position = new Point(posX,posY);
	}
	
	/**
	  * Changes the state of this car to "hit" or "not hit"
	  *
	  * @param hit a boolean indicating whether this car was hit or not
	  */
	protected void setHit(boolean hit){
		$hit=hit;
	}
	
	// VARIABLES
		
	// A boolean indicating whether this roadobject was hit or not	 
	private boolean $hit;
	
	// The position of the object on the roadpart
	private Point $position;
	
	
}
