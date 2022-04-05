package GameSystem;

import java.awt.*;

/**
  * A class of obstacleTypes (e.g. cat, cow, olifant,....).
  */
public class ObstacleType{
	
	
	// CONSTRUCTORS 
	
	/**
	  * A new obstacleType with given name, color, size and damage is created.
	  *
	  * @param name: The name of this obstacleType.
	  * @param colour: The colour of this obstacleType.
	  * @param size: The size of this obstacleType.
	  * @param damageengine: The damage causes by this obstacleType on the engine.
	  * @param damagetires: The damage causes by this obstacleType on the tires.
	  * @param damagebody: The damage causes by this obstacleType on the body.
	  * @param sound: The filename of the sound played when the car hits an obstacle of this type
	  * @param image: The filename of the image of an obstacle of this type
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	ObstacleType(String name, Color colour, int size, double damageengine, double damagetires, double damagebody, String sound, String image){
		if(name.equals(""))
			throw new IllegalArgumentException("Invalid name (empty string)");
		if(name==null)
			throw new NullPointerException("Invalid name (null)");
		$NAME = name;
		if(colour==null)
			throw new NullPointerException("Invalid colour");
		$COLOUR = colour;
		if(size<=0)
			throw new IllegalArgumentException("Invalid size");
		$SIZE = size;
		if(damageengine<0)
			throw new IllegalArgumentException("Invalid engine damage");
		$DAMAGEENGINE = damageengine;
		if(damagebody<0)
			throw new IllegalArgumentException("Invalid body damage");
		$DAMAGEBODY = damagebody;
		if(damagetires<0)
			throw new IllegalArgumentException("Invalid tires damage");
		$DAMAGETIRES = damagetires;
		if(sound.equals(""))
			throw new IllegalArgumentException("Invalid sound (empty string)");
		if(sound==null)
			throw new NullPointerException("Invalid sound (null)");
		$SOUND=sound;
		if(image.equals(""))
			throw new IllegalArgumentException("Invalid image (empty string)");
		if(image==null)
			throw new NullPointerException("Invalid image (null)");
		$IMAGE = Toolkit.getDefaultToolkit().getImage(image);
	}
		
		
	// INSPECTORS 
	 
	/**
	  * Return the name of this obstacleType.
	  *
	  * @return The name of this obstacleType.
	  */
	public String getName(){
		return $NAME;
	}
	
	/**
	  * Return the colour of this obstacleType.
	  *
	  * @return The colour of this obstacleType.
	  */
	public Color getColour(){
		return $COLOUR;
	}
	
	/**
	  * Return the image of an obstacle of this type
	  *
	  * @return the image of an obstacle of this type
	  */
	public Image getImage(){
		return $IMAGE;
	}
	
	/**
	  * Return the size of this obstacleType.
	  *
	  * @return The size of this obstacleType.
	  */
	public int getSize(){
		return $SIZE;
	}
	
	/**
	  * Returns the filename of the sound played when the car hits an obstacle of this type
	  *
	  * @ return the filename of the sound played when the car hits an obstacle of this type
	  */
	public String getSound(){
		return $SOUND;
	}
	  
	/**
	  * Return the damage caused by this obstacleType on the engine.
	  *
	  * @return The damage caused by this obstacleType on the engine.
	  */
	public double getDamageEngine(){
		return $DAMAGEENGINE;
	}	
	
	/**
	  * Return the damage caused by this obstacleType on the body.
	  *
	  * @return The damage caused by this obstacleType on the body.
	  */
	public double getDamageBody(){
		return $DAMAGEBODY;
	}	
	
	/**
	  * Return the damage caused by this obstacleType on the tireset.
	  *
	  * @return The damage caused by this obstacleType on the tireset.
	  */
	public double getDamageTires(){
		return $DAMAGETIRES;
	}	
	
	// VARIABLES 
	 
	/*
	 * A variable (String) for holding the name of this obstacleType.
	 */
	private final String $NAME;
	
	/*
	 * A variable (Color) for holding the color of this obstacleType.
	 */
	private final Color $COLOUR;
	
	/*
	 * A variable (int) for holding the size of this obstacleType.
	 */
	private final int $SIZE;
	
	/*
	 * A variable (double) for holding the damage caused by this obstacleType on the engine.
	 */
	private final double $DAMAGEENGINE;
	
	/*
	 * A variable (double) for holding the damage caused by this obstacleType on the body.
	 */
	private final double $DAMAGEBODY;
	
	/*
	 * A variable (double) for holding the damage caused by this obstacleType on the tires.
	 */
	private final double $DAMAGETIRES;
	
	/*
	 * A variable (String) for holding the filename of the sound played when the car hits an obstacle of this type
	 */
	private final String $SOUND;
	
	/*
	 * A variable (String) for holding the filename of the image of an obstacle of this type.
	 */
	private final Image $IMAGE;
}