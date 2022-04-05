//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
  * @author Shaun P. Wood
  * @version $Revision: 1.6 $, $Date: 2001/02/27 22:24:31 $
  *
  */

public class SelfInfo extends PlayerInfo
{
	/**
	 * The side of the field the player plays on.
	 * 'l' or 'r'.
	 */
	private char  side;
	/** The information from the latest 'sense_body' message. */
	private SenseBody sense;

	/**
     * Constructs a new SelfInfo object with
     * the given data.  This constructor is called
     * from within the constructor of the GlobalMemory object
     * to create a new "self".
     * @param team the name of the player's team
     * @param uniformNumber the player's uniformNumber
     * @param side 'l' or 'r' for the player's side of the field 
     */
	public SelfInfo(String team, int uniformNumber, char side)
	{
		//call parent constructor (time stamp is zero)
		super(team, uniformNumber, 0);
		this.side = side;
		sense = new SenseBody();
	}

	/**
     * Gets the side of the field.
     */
	public char getSide()
	{
		return side;
	}

	/**
     * Gets the latest 'sense_body' information.
     */
	public SenseBody getSense()
	{
		return sense;
	}

	/**
     * Updates sense to the current 'sense_body' information.
     */
	public void setSense(SenseBody sense)
	{
		this.sense = sense;
	}
}
