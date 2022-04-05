package GameSystem;

/**
  * A class Highscores
  */
public class HighScores{
	
	// CONSTRUCTORS	
	/**
	  * Creates a HighScores-instance
	  */
	public HighScores(){
		$players = new Player[10];
	}	
	
	// INSPECTORS 
	 
	/**
	  * Return the players who are standing in this highScores.
	  *
	  * @return The players who are standing in this highScores.
	  */
	public Player [] getPlayers(){
		return $players;
	}
	
	// MUTATORS 
	 
	/**
	  * Set the given player on a given position in this highScores.
	  *
	  * @param position: The position where the must stand in this HighSocres.
	  * @param player: The player who has one of the 10 best scores.
	  * @exception IllegalArgumentException
	  */
	private void setPlayer(int position, Player player){
		if(position < 0 || position > getPlayers().length-1)
			throw new IllegalArgumentException("Invalid position.");
		$players[position] = player;		
	}
	 
	/**
	  * Add a player to highScores if his score is better than the score of one of the 
	  * players who is in highScores.
	  *
	  * @param: player: Look if the player has one of the 10 best scores.
	  *					and put him in the HighSores if necessary. 
	  */
	void addPlayer(Player player){
		int i=0;
		while (i < getPlayers().length && 
		       getPlayers()[i] != null &&
		       getPlayers()[i].getScore() > player.getScore())   	   
			i++;
		if(i < getPlayers().length)
			changeHighScores(i, player); 
	}

	/**
	  * A given player with a better score is set on the given position.
	  * The player on the given position in highScores and all other players 
	  * after the given position (except for the last one), are moved one place to the right.
	  *
	  * @param position: The position where the must stand in this HighSocres.
	  * @param player: The player who has one of the 10 best scores.
	  */
	void changeHighScores(int position, Player player){
		for(int j = getPlayers().length - 1; j > position; j--)
			setPlayer(j, getPlayers()[j-1]);
		setPlayer(position, player);
	}
	

	// VARIABLES 
	 
	/*
	 * A variable (array Player) for holding the 10 best players of this highScores.
	 */
	private Player [] $players;
}