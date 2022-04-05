//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.StringTokenizer;
/**
 * Used to store the visible information
 * on the other players.
 * @author Paul A. Buhler
 * @version $Revision: 1.9 $, $Date: 2001/02/27 22:24:31 $
*/
public class PlayerInfo extends DynamicObjectInfo
{
	/** The player's team name. */
	private String  team;
	/** The player's uniform number. */
	private int   uniformNumber = -1;
  
    /**
     * Constructs a new PlayerInfo object with
     * the given data.  Called by the SelfInfo
     * constructor.
     * @param team the name of the player's team
     * @param uniformNumber the player's uniformNumber
     * @param timeStamp the current time step
     */
	public PlayerInfo( String team, int uniformNumber, int timeStamp)
	{
		super("player", timeStamp);

		this.team = team;
		this.uniformNumber = uniformNumber;
	}

	/**
     * Constructs a new PlayerInfo object from
     * the data contained in an ObjectInfo object.
     * @param object the object that contains the
     * data to create the PlayerInfo object from
     */
	public PlayerInfo(ObjectInfo object)
	{
		super(object);
		StringTokenizer tokenizer = new StringTokenizer(object.getObjectName());
		tokenizer.nextToken(); //throw out the first token - 'player'
		if (tokenizer.hasMoreTokens())
			team = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens())
			uniformNumber = Integer.parseInt(tokenizer.nextToken());
	}

	/**
     * Gets the team name.
     */
	public String getTeam()
	{
		return team;
	}

	/**
     * Gets the uniform number.
     */
	public int getUniformNumber()
	{
		return uniformNumber;
	}

	/**
     * Sets the team name.
     */
	public void setTeam(String team)
	{
		this.team = team;
	}

	/**
     * Sets the uniform number.
     */
	public void setUniformNumber(int uniformNumber)
	{
		this.uniformNumber = uniformNumber;
	}

  public boolean equals(PlayerInfo p){
    if ((getUniformNumber() == -1) || (p.getUniformNumber() == -1))
      return false;
    return (getUniformNumber() == p.getUniformNumber()) && getTeam().equals(p.getTeam());
  }
} 
