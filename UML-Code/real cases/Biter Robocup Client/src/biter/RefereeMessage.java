//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.StringTokenizer;

/**
  * Holds the information sent in a referee message.
  * @author Paul A. Buhler
  * @version $Revision: 1.10 $, $Date: 2001/02/27 22:24:31 $
  *
  */

public class RefereeMessage extends SensorInput
{
	/** Referee's message, determines the new play mode. */
	private String  playMode;

  /**
    * Constructs a new RefereeMessage object
    * from the provided information.
    * @param timeStamp the time stamp of the message
    * @param playMode the new play mode of
  */
	public RefereeMessage(long timeStamp, String playMode )
	{
    super(timeStamp);
    this.playMode = playMode;
	}

  public RefereeMessage(String data, long timeReceived){
    realTime = timeReceived;
    parse(data);
  }
  
  public RefereeMessage(String data){
    parse(data);
  }

  private void parse(String data){
    StringTokenizer strtok = new StringTokenizer(data.substring(1, data.length() - 1));
    int ts;
    String sender, message;
    SensorInput rtnval = null;
    
    // verify that this is indeed a "hear" message
    if( strtok.nextToken().equals( "hear" ) )
    {
      ts = Integer.parseInt( strtok.nextToken() );
      sender = strtok.nextToken();
      if( sender.equals("referee") )
      {
        // return the referee message
        message = strtok.nextToken(") ");
        timeStamp = ts;
        playMode = message;
      }
      else {
        System.out.println("ERROR: RefereeMessage() trying to parse bad message");
      }
    }
    else
      System.out.println("ERROR: RefereeMessage() trying to parse bad message");
  }
    


	/**
    * Gets the current playing mode.
  */
	public String getPlayMode()
	{
    return playMode;
	}
  
	/**
    * Sets the current playing mode.
  */
	public void setPlayMode(String playMode)
	{
    this.playMode = playMode;
	}

  public String toString(){
    String s = new String("playMode: " + playMode);
    return s;
  }
} 
