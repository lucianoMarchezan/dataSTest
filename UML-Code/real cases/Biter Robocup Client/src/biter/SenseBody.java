//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.StringTokenizer;
/**
 * A class that contains information received
 * from a sense_body message.
 * @author Shaun P. Wood
 * @version $Revision: 1.10 $, $Date: 2001/02/27 22:24:31 $
 *
*/
public class SenseBody extends SensorInput
{
	/** The ViewQuality. */
	private String viewQuality;
	/** The ViewWidth. */
	private String viewWidth;
	/** The player's Stamina. */
	private double stamina;
	/** The player's Effort. */
	private double effort;
	/** Estimate of the player's speed. */
	private double speed;
	/** The player's head direction.  This is relative to the body. */
	private int headDirection;
	/** The number of kick messages sent by the player. */
	private int kickCount;
	/** The number of dash messages sent by the player. */
	private int dashCount;
	/** The number of turn messages sent by the player. */
	private int turnCount;
	/** The number of say messages sent by the player. */
	private int sayCount;
	/** The number of turn_neck messages sent by the player. */
	private int turnNeckCount;

    /**
     * A simple constructor that only initializes the timeStamp.
     */
	public SenseBody()
	{
    super(-1);
	}

  public SenseBody(String data, long timeReceived){
    realTime = timeReceived;
    parse(data);
  }

  public SenseBody(String data){
    parse(data);
  }
  
	/**
   * Parses information in a 'sense_body' message.
   */
  private void parse(String data){
    StringTokenizer strtok = new StringTokenizer(data.substring(1)), tokenizer;
    String temp;
    //verify that this is indeed a 'sense_body' message
    if (strtok.nextToken().equals("sense_body"))
		{
      setTimeStamp(Integer.parseInt( strtok.nextToken() ));

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "view_mode"
      viewQuality = tokenizer.nextToken();
      viewWidth = tokenizer.nextToken();

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "stamina"
      stamina = Double.parseDouble(tokenizer.nextToken());
      effort = Double.parseDouble(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "speed"
      speed = Double.parseDouble(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "head_angle"
      headDirection = Integer.parseInt(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "kick"
      kickCount = Integer.parseInt(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "dash"
      dashCount = Integer.parseInt(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "turn"
      turnCount = Integer.parseInt(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "say"
      sayCount = Integer.parseInt(tokenizer.nextToken());

      temp = strtok.nextToken(")");
      tokenizer = new StringTokenizer(temp.substring(1));
      tokenizer.nextToken(); //equals "turn_neck"
      turnNeckCount = Integer.parseInt(tokenizer.nextToken());
		}
  }

	/**
     * Converts the information in the class into readable output.
     * For debugging purposes.
     * @return a String containing the information in the class.
     */
	public String toString()
	{
		StringBuffer rtnval = new StringBuffer();
		rtnval.append(super.toString());
		rtnval.append("view_quality: " + viewQuality);
		rtnval.append("view_width: " + viewWidth);
		rtnval.append("stamina: " + stamina);
		rtnval.append("effort: " + effort);
		rtnval.append("speed: " + speed);
		rtnval.append("head_angle: " + headDirection);
		rtnval.append("kick: " + kickCount);
		rtnval.append("dash: " + dashCount);
		rtnval.append("turn: " + turnCount);
		rtnval.append("say: " + sayCount);
		rtnval.append("turn_neck: " + turnNeckCount);
		return rtnval.toString();
	}

	public void setViewQuality(String viewQuality)
	{
		this.viewQuality = viewQuality;
	}

	public void setViewWidth(String viewWidth)
	{
		this.viewWidth = viewWidth;
	}

	public void setStamina(double stamina)
	{
		this.stamina = stamina;
	}

	public void setEffort(double effort)
	{
		this.effort = effort;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public void setHeadDirection(int headDirection)
	{
		this.headDirection = headDirection;
	}

	public void setKickCount(int kickCount)
	{
		this.kickCount = kickCount;
	}

	public void setDashCount(int dashCount)
	{
		this.dashCount = dashCount;
	}

	public void setTurnCount(int turnCount)
	{
		this.turnCount = turnCount;
	}

	public void setSayCount(int sayCount)
	{
		this.sayCount = sayCount;
	}

	public void setTurnNeckCount(int turnNeckCount)
	{
		this.turnNeckCount = turnNeckCount;
	}

	public String getViewQuality()
	{
		return viewQuality;
	}

	public String getViewWidth()
	{
		return viewWidth;
	}

	public double getStamina()
	{
		return stamina;
	}

	public double getEffort()
	{
		return effort;
	}

	public double getSpeed()
	{
		return speed;
	}

	public int getHeadDirection()
	{
		return headDirection;
	}

	public int getKickCount()
	{
		return kickCount;
	}

	public int getDashCount()
	{
		return dashCount;
	}

	public int getTurnCount()
	{
		return turnCount;
	}

	public int getSayCount()
	{
		return sayCount;
	}

	public int getTurnNeckCount()
	{
		return turnNeckCount;
	}
}
