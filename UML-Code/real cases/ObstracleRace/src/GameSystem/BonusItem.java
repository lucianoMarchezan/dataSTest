package GameSystem;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
  * A class to represent bonusitems which are lying on the road surface
  * Bonusitems give the player extra time to complete a level
  */
public class BonusItem extends RoadObject{
	
	// CONSTRUCTORS
	
	/**
	  * Constructs a new bonusitem with the given characteristics
	  *
	  * @param posX the x-position of this bonusitem.
	  * @param posY the y-position of this bonusitem.
	  * @param bonusTime the bonustime added to the player's time when hit by this roadobject
	  * @param name the name of this bonusitem
	  * @param colour the color of this bonusitem
	  */
	BonusItem(int posX, int posY, int bonusTime, String name, int size, Color colour){
		super(posX, posY);
		if(bonusTime<0)
			throw new IllegalArgumentException("Invalid bonus time: negative value");	
		$BONUSTIME = bonusTime;
		if(name.equals(""))
			throw new IllegalArgumentException("Invalid name: empty string");
		if(name == null)
			throw new NullPointerException("Invalid name: null pointer");
		$NAME = name;
		if(colour == null)
			throw new NullPointerException("Invalid colour: null pointer");
		$COLOUR = colour;		 
	}
	
	// INSPECTORS
	
	/**
	  * Returns the bonustime added to the player's time when hit by this bonusitem
	  *
	  * @return the bonustime added to the player's time when hit by this bonusitem
	  */
	public int getBonusTime(){
		return $BONUSTIME;
	}
	
	/**
	  * Returns the name of this bonusitem
	  *
	  * @return the name of this bonusitem
	  */
	public String getName(){
		return $NAME;
	}
	
	/**
	  * Returns the colour of this bonusitem
	  *
	  * @return the colour of this bonusitem
	  */
	public Color getColour(){
		return $COLOUR;
	}
	
	/**
	  * Returns the image of this bonusitem
	  *
	  * @return the image of this bonusitem
	  */
	public Image getImage(){
		return IMAGE;
	}
	
	// MUTATORS
	
	/**
	  * The given car collides with this bonusitem and the player receives bonus time
	  *
	  * @param car which collides with this bonusitem
	  */
	public void collideWith(Car car){
		RaceController.getReference().getRace().changeTime(getBonusTime());
	 	if(!isHit())RaceController.getReference().getSoundPlayer().playBonus();
	 	setHit(true);
	}
	
	/**
	  * Leaves the bonusitem in place
	  */
	public void move(){
	}
	  
	// VARIABLES
	
	// the bonustime added to the player's time when hit by this roadobject
	private final int $BONUSTIME;
	
	// the name of this bonusitem
	private final String $NAME;
	
	// the colour of this bonusitem
	private final Color $COLOUR;	
	
	// the image of this bonusitem
	private final static Image IMAGE=Toolkit.getDefaultToolkit().getImage("images\\bonusitem.gif");
}
