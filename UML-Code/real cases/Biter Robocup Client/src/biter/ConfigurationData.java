//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.io.*;

/**
  * Holds copies of the data used to initialize the soccer server.
  * A description of all fields can be found in the SoccerServer
  * manual.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.7 $, $Date: 2001/02/27 22:24:31 $
  *
*/
public class ConfigurationData 
{
	/** The width of the goal */
	public final double goal_width;
	/** The player's radius */
	public final double player_size;
	/** The rate at which the player slows down */
	public final double player_decay;
	/** The randomness added to the players moves and turns */
	public final double player_rand;
	/** Used along with the wind factor */
	public final double player_weight;
	/** Maximum player speed */
	public final double player_speed_max;
	/** Maximum player stamina */
	public final double stamina_max;
	/** Amount of stamina player gains in one cycle */
	public final double stamina_inc_max;
	/** Threshold for the decrement of the player's recover */
	public final double recover_dec_thr;
	/** Step by which the player's recovery decreases */
	public final double recover_dec;
	/** The player's minimum recovery */
	public final double recover_min;
	/** Threshold for the player's effort capacity increment */
	public final double effort_inc_thr;
	/** Amount by which the player's effort capacity is incremented */
	public final double effort_inc;
	/** Minimum for the player's effort capacity */
	public final double effort_min;
	/** Player's maximum hearing capacity */
	public final int hear_max;
	/** Player's minimum hearing capacity */
	public final int hear_inc;
	/** Player's hearing capacity decay rate */
	public final int hear_decay;
	/** Moment of inertia for a player */
	public final double inertia_moment;
	/** Length of Goalie's catchable area */
	public final double catchable_area_l;
	/** Width of Goalie's catchable area */
	public final double catchable_area_w;
	/** Goalie's probability of catching a ball */
	public final double catch_probability;
	/** After catching a ball, the number of cycles the Goalie is banned from catching again */
	public final int catch_ban_cycle;
	/** The ball's radius */
	public final double ball_size;
	/** The rate at which the ball slows down */
	public final double ball_decay;
	/** Randomness of the ball's movement */
	public final double ball_rand;
	/** Used along with wind factor */
	public final double ball_weight;
	/** The ball's maximum speed */
	public final double ball_speed_max;
	/** The wind's force */
	public final double wind_force;
	/** The wind's direction in degrees */
	public final double wind_dir;
	/** The change of value for the wind */
	public final double wind_rand;
	/** Used to determine the ball's kickable area */
	public final double kickable_margin;
	/** The margin for the corner kick */
	public final double ckick_margin;
	/** Factor the dash power in the dash command is multiplied by */
	public final double dash_power_rate;
	/** Factor the power in the kick command is multiplied by */
	public final double kick_power_rate;
	/** Width of the player's view cone */
	public final double visible_angle;
	/** Furthest distance a player can hear from */
	public final double audio_cut_dist;
	/** The step by which values for dynamic objects are quantized (rounded) */
	public final double quantize_step;
	/** The step by which values for landmarks are quantized */
	public final double quantize_step_l;
	/** The maximum power the player can use for dash and kick commands */
	public final int maxpower;
	/** The minimum power the player can use for dash and kick commands */
	public final int minpower;
	/** The maximum turn and kick angles */
	public final int maxmoment;
	/** The minimum turn and kick angles */
	public final int minmoment;
	/** The Length of simulator cycles */
	public final int simulator_step;
	/** The time length for sending visual information to a player in standard view mode */
	public final int send_step;
	/** The time length of the server's polling sockets */
	public final int recv_step;
	/** The longest a say message can be */
	public final int say_msg_size;

  // computed values
  /** The area within which the ball is kickable */
	public final double kickable_area;

	/**
    * Creates a new ConfigurationData object from
    * the information contained in the file
    * configFile.
    * @param configFile The name of the configuration file
  */
	public ConfigurationData( String configFile )
	{
		goal_width = getDoubleParam( configFile, "goal_width", 14.02 );
		player_size = getDoubleParam( configFile, "player_size", 0.8 );
		player_decay = getDoubleParam( configFile, "player_decay", 0.4 );
		player_rand = getDoubleParam( configFile, "player_rand", 0.1 );
		player_weight = getDoubleParam( configFile, "player_weight", 60.0 );
		player_speed_max = getDoubleParam( configFile, "player_speed_max", 1.0 );
		stamina_max = getDoubleParam( configFile, "stamina_max", 2000.0 );
		stamina_inc_max = getDoubleParam( configFile, "stamina_inc_max", 20.0 );
		recover_dec_thr = getDoubleParam( configFile, "recover_dec_thr", 0.3 );
		recover_dec = getDoubleParam( configFile, "recover_dec", 0.002 );
		recover_min = getDoubleParam( configFile, "recover_min", 0.5 );
		effort_inc_thr = getDoubleParam( configFile, "effort_inc_thr", 0.6 );
		effort_inc = getDoubleParam( configFile, "effort_inc", 0.01 );
		effort_min = getDoubleParam( configFile, "effort_min", 0.6 );
		hear_max = getIntParam( configFile, "hear_max", 2 );
		hear_inc = getIntParam( configFile, "hear_inc", 1 );
		hear_decay = getIntParam( configFile, "hear_decay", 2 );
		inertia_moment = getDoubleParam( configFile, "inertia_moment", 5.0 );
		catchable_area_l = getDoubleParam( configFile, "catchable_area_l", 2.0 );
		catchable_area_w = getDoubleParam( configFile, "catchable_area_w", 1.0 );
		catch_probability = getDoubleParam( configFile, "catch_probability", 1.0 );
		catch_ban_cycle = getIntParam( configFile, "catch_ban_cycle", 5 );
		ball_size = getDoubleParam( configFile, "ball_size", 0.085 );
		ball_decay = getDoubleParam( configFile, "ball_decay", 0.94 );
		ball_rand = getDoubleParam( configFile, "ball_rand", 0.05 );
		ball_weight = getDoubleParam( configFile, "ball_weight", 0.2 );
		ball_speed_max = getDoubleParam( configFile, "ball_speed_max", 2.7 );
		wind_force = getDoubleParam( configFile, "wind_force", 0.0 );
		wind_dir = getDoubleParam( configFile, "wind_dir", 0.0 );
		wind_rand = getDoubleParam( configFile, "wind_rand", 0.0 );
		kickable_margin = getDoubleParam( configFile, "kickable_margin", 1.0 );
		ckick_margin = getDoubleParam( configFile, "ckick_margin", 1.0 );
		dash_power_rate = getDoubleParam( configFile, "dash_power_rate", 0.01 );
		kick_power_rate = getDoubleParam( configFile, "kick_power_rate", 0.016 );
		visible_angle = getDoubleParam( configFile, "visible_angle", 90.0 );
		audio_cut_dist = getDoubleParam( configFile, "audio_cut_dist", 50.0 );
		quantize_step = getDoubleParam( configFile, "quantize_step", 0.1 );
		quantize_step_l = getDoubleParam( configFile, "quantize_step_l", 0.01 );
		maxpower = getIntParam( configFile, "maxpower", 100 );
		minpower = getIntParam( configFile, "minpower", -30 );
		maxmoment = getIntParam( configFile, "maxmoment", 180 );
		minmoment = getIntParam( configFile, "minmoment", -180 );
		simulator_step = getIntParam( configFile, "simulator_step", 100);
		send_step = getIntParam( configFile, "send_step", 150 );
		recv_step = getIntParam( configFile, "recv_step", 10 );
		say_msg_size = getIntParam( configFile, "say_msg_size", 512 );

		// computed values
		kickable_area = kickable_margin + ball_size + player_size;
	}

	/**
    * Retrieves a parameter of type double from the configuration
    * file.
    * @param configFile name of the file to get data from
    * @param paramName name of the parameter
    * @param defaultValue default return value
    * @return the value of the parameter, or defaultValue
    * if it cannot be found
  */
	private double getDoubleParam( String configFile, String paramName, double defaultValue )
	{
		String line;

		// establish the default value as the rtnval
		double rtnval = defaultValue;

		line = getLineFromFile( configFile, paramName );

		if( line != null )
      rtnval = Double.parseDouble( 
																	(line.substring( line.indexOf(':')+1, line.length())).trim() );
		//I don't think the ...,line.length() is necessary----^
		return rtnval;    
	}

	/**
    * Retrieves a parameter of type int from the configuration file.
    * @param configFile name of the file to get data from
    * @param paramName name of the parameter
    * @param defaultValue default return value
    * @return the value of the parameter, or defaultValue
    * if it cannot be found
  */
	private int getIntParam( String configFile, String paramName, int defaultValue )
	{
		String line;

		// establish the default value as the rtnval
		int rtnval = defaultValue;

		line = getLineFromFile( configFile, paramName );

		if( line != null )
      rtnval = Integer.parseInt( 
																(line.substring( line.indexOf(':')+1, line.length())).trim() ); 
		//See same spot, last function
		return rtnval;    
	}

	/**
    * Searches for a line starting with the specified text in a file.
    * @param configFile name of the file to read
    * @param key the begining of the line to read
    * @return the line begining with the text in
    * key, or null if no such line was found
  */
	private String getLineFromFile( String configFile, String key )
	{
		BufferedReader buf = null;
		String line = null;

		try
		{
			buf = new BufferedReader( new FileReader( configFile ) );

			while( true ) 
			{
				line = buf.readLine();
    
        // short-circuit 'or' protects from dereference of a 
        // null reference
				if( line == null || line.startsWith( key ) ) 
          break;
			}   

			buf.close();
		}
		catch( IOException e )
		{
			System.err.println( e.getMessage() );     
		}

		return line;    
	}
}

