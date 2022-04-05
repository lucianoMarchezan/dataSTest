//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.io.*;
import java.net.*;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;

// Copyright (C) 2001  Paul Buhler, Jose M Vidal

// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 2, as published by the Free Software Foundation.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.


/**
  * Runable program that creates new Players and WorldModel threads.
  * These two objects, along with their components, constitute what is
  * necessary to play RoboCup.
  * <P>The main method accepts several different command line parameters.<BR>
  * They are:
  * <UL>
  * <LI>-debug=[filename] //all communications are saved in file [filename]</LI>
  * <LI>-display=[true|false] //if true, enables displayWorldModel</LI>
  * <LI>-config=[filename]  //specifies name of the debug file</LI>
  * <LI>-port=[port number] //specifies port to connect to server</LI>
  * <LI>-team=[team name] //sets name of agent's team</LI>
  * <LI>-num=[number]   //starts [number] threads</LI>
  * <LI>-version=[version]  //sets the version for soccerserver</LI>
  * <LI>-use_goalie=[true|false]  //whether or not to use a goalie[default false]</LI>
  * <LI>-age=[int]    //sets how long stale information can remain in the dynamicObjects</LI>
  * </UL>
  * If command line parameters are not used, defaults are.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.16 $, $Date: 2001/02/27 22:24:31 $
  *
*/

public class SoccerClient 
{
	/**
	 * The main entry point to the soccer program.
	 * Gets command line arguments and constructs a
	 * Player thread and WorldModel object accordingly.
	 * @param args the command line arguments
	 * @throws SocketException if there is an error connecting to the host
	 * @throws IOException if there is an error oppening a log file
	 */
	public static void main(String args[])
    throws SocketException, IOException
	{
    // variables for holding data from the init message
    int numberOfAgents;
    String  playMode;
    PrintWriter debug;

    // obtain data from command line
    // Note: the 3rd argument to LocateParameter is a default value.  PAB

    String debugFileName = CommandLineUtil.LocateParameter( args, "-debug=", "" );

    boolean display = 
      Boolean.valueOf( 
											CommandLineUtil.LocateParameter( args, "-display=", "false" ) 
											).booleanValue();

    String configFilename = CommandLineUtil.LocateParameter( args, "-config=", "rcssserver-server.conf" );

    int port = Integer.parseInt(
																CommandLineUtil.LocateParameter( args, "-port=", "6000" ));

    String team = CommandLineUtil.LocateParameter( args, "-team=", "BITER" );

    numberOfAgents = Integer.parseInt(
																			CommandLineUtil.LocateParameter(args, "-num=", "1"));
    
    numberOfAgents = (numberOfAgents > 0) ? numberOfAgents : 1;

    String hostName = CommandLineUtil.LocateParameter( args, "-host=", "" );

    String version = CommandLineUtil.LocateParameter( args, "-version=", "5.00" );

    boolean useGoalie = Boolean.valueOf(CommandLineUtil.LocateParameter( 
																																				args, "-use_goalie=", "false")).booleanValue();

    int age = Integer.parseInt(CommandLineUtil.LocateParameter(args, "-age=", "5"));

  // variable to hold the configuration data, typically loaded from a
  // the file named server.conf
    ConfigurationData cfgData = new ConfigurationData( configFilename );

    //create the agents   
    for (int counter = 1; counter <= numberOfAgents; counter++)
		{
      if (debugFileName.length() != 0)
        debug = new PrintWriter(new FileWriter(debugFileName + counter));
      else
        debug = null;
      DatagramWrapper dgWrapper = new DatagramWrapper(hostName, port,
																											1024, debug); 

      // initialize communciations with the server by sending an init command
      if (useGoalie && counter == numberOfAgents)
        dgWrapper.send("(init " + team + "(version " + version + ") (goalie))");
      else
        dgWrapper.send("(init " + team + "(version " + version + "))");
        
      // read the server's reply... format of the response message is found in
      // the soccer server manual
      String initReply = dgWrapper.receive();

      // strip the leading '(' and tokenize on ' ' and ')'
      StringTokenizer strtok = new StringTokenizer( initReply.substring(1), " )" );

      // verify the message read is indeed the initReply
      if( strtok.nextToken().equals( "init" ) )
			{
        char side = strtok.nextToken().charAt(0);
        int uniformNumber = Integer.parseInt( strtok.nextToken() );
        String Mode = strtok.nextToken();
        RefereeMessage lastMessage = new RefereeMessage(0, Mode);
        // communication with server is up... initialize the GlobalMemory and Player objects

        WorldModel worldModel = new WorldModel(cfgData, dgWrapper,
																							 null, display, age, team, uniformNumber, side, lastMessage);
        PlayerFoundation player;
        if (useGoalie && counter == numberOfAgents)
          player = new Goalie(worldModel, uniformNumber);
        else
          //          player = new Player(worldModel, new Point2D.Double(-1.5, 0), uniformNumber);
          player = new Player(worldModel, new Point2D.Double(-20, 10), uniformNumber);

        worldModel.player = player;
        //new Point2D.Double(-52.5 * Math.random(), 34 * Math.random()), counter );

			}
      else
			{
        System.err.println( "Failure during server initialization process!" );
        System.exit( 0 );
			}
		}
	}
}
