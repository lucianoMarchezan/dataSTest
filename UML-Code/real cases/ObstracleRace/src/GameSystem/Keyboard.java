package GameSystem;

/**
  * A class for representing the keys used by the race.
  */
public class Keyboard{

	public Keyboard(){
	}
	
	/**
	  * Return the leftKey.
	  *
	  * @return: The leftKey to steer the car left.
	  */
	public char getLeftKey(){
		return $leftKey;
	}
	
	/**
	  * Return the rightKey.
	  *
	  * @return: The rightKey to steer the car right.
	  */
	public char getRightKey(){
		return $rightKey;
	}
	
	/**
	  * Return the accelerateKey.
	  *
	  * @return: The accelerateKey for accelerate the car.
	  */
	public char getAccelerateKey(){
		return $accelerateKey;
	}
	
	/**
	  * Return the decelerateKey.
	  *
	  * @return: The accelerateKey for decelerate the car.
	  */
	public char getDecelerateKey(){
		return $decelerateKey;
	}

	/**
	  * Return the gearDownKey.
	  *
	  * @return: The gearDownKey for changing the gear of the car down.
	  */
	public char getGearDownKey(){
		return $gearDownKey;
	}

	/**
	  * Return the gearUpKey.
	  *
	  * @return: The gearUpKey for changing the gear of the car up.
	  */
	public char getGearUpKey(){
		return $gearUpKey;
	}


	/**
	  * Set the given key as the leftKey.
	  * 
	  * @param left: The key which will be used to steer the car left.
	  */
	public void setLeftKey(char left){
		$leftKey = left;
	}
	
	/**
	  * Set the given key as the rightKey.
	  * 
	  * @param right: The key which will be used to steer the car right.
	  */
	public void setRightKey(char right){
		$rightKey = right;
	}
	
	/**
	  * Set the given key as the accelerateKey.
	  *
	  * @param accelerate: The key which will be used to accelerate the car.
	  */
	public void setAccelerateKey(char accelerate){
		$accelerateKey = accelerate;
	}
	
	/**
	  * Set the given key as the decelerateKey.
	  *
	  * @param decelerate: The key which will be used to decelerate the car.
	  */
	public void setDecelerateKey(char decelerate){
		$decelerateKey = decelerate;
	}

	/**
	  * Set the given key as the gearDownKey.
	  *
	  * @param gearDown: The key which will be used for changing the gear of the car down.
	  */
	public void setGearDownKey(char gearDown){
		$gearDownKey = gearDown;
	}

	/**
	  * Set the given key as the gearUpKey.
	  *
	  * @param gearUp: The key which will be used for changing the gear of the car up.
	  */
	public void setGearUpKey(char gearUp){
		$gearUpKey = gearUp;
	}

	/**
	  * Set default keys for driving the car.
	  */
	public void setDefaultKeys(){
		setLeftKey('4');
		setRightKey('6');
		setAccelerateKey('8');
		setDecelerateKey('2');
		setGearDownKey('e');
		setGearUpKey('d');
	}
	
	// VARIABLES 
	 
	/*
	 * A variable (char) for holding the leftKey.
	 */
	private char $leftKey;
	
	/*
	 * A variable (char) for holding the rightKey.
	 */
	private char $rightKey;
	
	/*
	 * A variable (char) for holding the accelerateKey.
	 */
	private char $accelerateKey;
	
	/*
	 * A variable (char) for holding the decelerateKey.
	 */
	private char $decelerateKey;

	/*
	 * A variable (char) for holding the gearDownKey.
	 */
	private char $gearDownKey;

	/*
	 * A variable (char) for holding the gearUpKey.
	 */
	private char $gearUpKey;
}