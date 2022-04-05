package GameSystem;

import java.io.*;

/**
  * A class of Players.
  */
public class Player implements Serializable{
	
	
	// CONSTRUCTORS 
	
	/**
	  * Create a new player with a given name.
	  *
	  * @param name: The name of the player you want to create.
	  * @exception IllegalArgumentException
	  * @exception NullPointerException
	  */
	Player(String name){
		if(name.equals(""))
			throw new IllegalArgumentException("Invalid name (empty string)");
		if(name==null)
			throw new NullPointerException("Invalid name (null)");
		$name = name;
		$money = 100;
		$score = 0;
	}
	
	
	// INSPECTORS 
		 
	/**
	  * Return the name of this player.
	  *
	  * @return The name of this player.
	  */
	public String getName()
		{
		return $name;
		}
	
	/**
	  * Return the score of this player.
	  *
	  * @return The score of this player.
	  */
	public long getScore(){
		return $score;
	}
	
	/**
	  * Return the money of this player.
	  *
	  * @return The money of this player.
	  */
	public int getMoney(){
		return $money;
	}
	
	
	// MUTATORS 
	 
	/**
	  * Set the score of this player to the given score.
	  *
	  * @param score: The score you want to set for this player.	  
	  * @exception IllegalArgumentException
	  */
	void setScore(long score){
		if(score<0)
			throw new IllegalArgumentException("Invalid score");
		$score = score;
	}
	
	/**
	  * Set the score of this player to the given score.
	  *
	  * @param difference: The score you want to set for this player.
	  * @exception IllegalArgumentException
	  */
	void changeMoney(int difference){
		if($money + difference < 0 )
			throw new IllegalArgumentException("Invalid difference");
		$money += difference;
	}
	
	
	// VARIABLES 
	 
	/*
	 * The name of this player
	 */
	private String $name;
	
	/*
	 * The score of this player
	 */
	private long $score;
	
	/*
	 * The money of this player
	 */
	private int $money;
}