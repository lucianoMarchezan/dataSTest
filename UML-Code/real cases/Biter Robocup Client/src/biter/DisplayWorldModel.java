package biter;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.geom.*;
import java.io.*;

/**
  * Creates a window containing a simple representation
  * of what the agent is seeing.  The agent is black, the
  * ball is yellow, the flags orange, and the lines light green.
  * Currently makes use of regular expressions to display the ball
  * other players.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.14 $, $Date: 2001/02/27 22:24:31 $
  *
*/
class DisplayWorldModel extends JFrame
{
    /**
     * A reference to the GlobalMemory object.
     * Information that will be displayed comes from
     * this object.
	*/
    WorldModel worldModel;
    /** Color the teammates appear on the screen. */
    private final Color teamMateColor = Color.blue;
    /** Color the opponents appear on the screen. */
    private final Color opponentColor = Color.red;
    /** Color the unknown players appear on the screen. */
    private final Color unknownPlayerColor = Color.gray;
    /** Color the ball appears on the screen. */
    private final Color ballColor = Color.yellow;
	/** The maximum number of cycles the GlobalMemory retains stale data. */
	private int age;

    /**
	  * Creates a display window using the specified worldModel
	  * @param worldModel holds the data needed to create
	  * a display
	*/
	DisplayWorldModel(WorldModel worldModel, int age )
    {
		super();
	
		this.worldModel = worldModel;
		this.age = age;

		setTitle( "Display of Agent's World Model" );
		setSize( 400, 400 );

		// ignore the windowClosing event - I do not want to kill
		// the agent by closing the window.

		setVisible(true);
    }

    /**
	  * Displays the agent's view of the field.
	  * Uses double buffering to create smoother
	  * graphics, and calls displayAccessToGlobalMemory.
	  * to get and display what the agent sees
	  * @param g a reference to the current graphics
	  * context
	*/
    public void paint(Graphics g )
    {
		final int BORDER = 20;

		Dimension d = getSize();
		Insets insets = getInsets();
		d.height -= insets.top + insets.bottom + 2 * BORDER;
		d.width -= insets.left + insets.right + 2 * BORDER;

		// we need to get a little sophisticated and do double-buffering
		// the screen flicker is too noticeable.
		Image offScreenImage = 
			createImage( d.width, d.height );

		Graphics offScreenGraphics = offScreenImage.getGraphics();

		Graphics2D g2 = (Graphics2D)offScreenGraphics;

		g2.translate( d.width/2, d.height/2 );

		// the default orientation in java has x increasing to the right and
		// y increasing down... create an affine transformation to mirror the 
		// coordinate system on the x-axis resulting in y increasing going up
		AffineTransform flip_vertical = new AffineTransform( 1, 0, 0, -1, 0, 0 );

		// add the flip to the transform in the graphics 2d object
		g2.transform( flip_vertical );

		// scale the data by a factor of 3 in x and y
		g2.scale( 3, 3 );
		
		displayit(g2);

		g.drawImage( offScreenImage, 
					 insets.left+BORDER, insets.top+BORDER, this );
    }

    /**
	  * Draws the agent's view to an offscreen buffer.
	*/
    private void displayit( Graphics2D g2)
    {
		ArrayList dynamicObjects = worldModel.dynamicObjects;
		ArrayList hearInformation = worldModel.hearInformation;
		HashMap staticObjects = worldModel.staticObjects;
		SelfInfo self = worldModel.self;
		RefereeMessage lastMessage = worldModel.lastMessage;
		// restore the Graphics2D object via a cast of myData

		if (self == null)
			return;
		long currentTime = self.getTimeStamp();
		Iterator iterator = staticObjects.entrySet().iterator(); 
		//draw the static objects
		while( iterator.hasNext() )
	    {
			Map.Entry map = (Map.Entry)iterator.next();
			Object value = map.getValue();

			if( value instanceof Point2D.Double )
		    {
				Point2D.Double point = (Point2D.Double)map.getValue();

				// draw flags in orange
				g2.setPaint( new Color( 255, 128, 0) );
				g2.draw( new Ellipse2D.Double( point.getX() - .25, point.getY() - .25,
											   0.5, 0.5 ));
		    }
			else if( value instanceof Line2D.Double )
		    {
				// draw lines in medium green
				g2.setPaint( new Color( 0, 128, 0) );
				g2.draw( (Line2D.Double)value);

		    }
			else if( value instanceof Rectangle2D.Double )
			{
				// draw lines in medium green
				g2.setPaint( new Color( 0, 128, 0 ) );
				g2.draw( (Rectangle2D.Double)value );
		    }
	    }
		// draw the player  (self)
		Point2D.Double point = self.getAbsoluteFieldPosition();
		
		g2.setPaint( Color.black );
		g2.draw( new Ellipse2D.Double( point.getX() - 2.5, point.getY() - 2.5,
									   5, 5 ));
		g2.setPaint( Color.white );
		g2.draw( new Line2D.Double( point.getX(), point.getY(), point.getX() + 5. * Math.cos(Math.toRadians(self.getDirection())),
									point.getY() + 5. * Math.sin(Math.toRadians(self.getDirection())) ) );

		// iterate throught the dynamic objects and draw them
		//		iterator = dynamicObjects.iterator();

		BallInfo ball = worldModel.getBallInfo();
		if (ball != null && (point = ball.getAbsoluteFieldPosition()) != null){
			//			System.out.println("-" + point);
			//			System.out.println(age + " " + currentTime + " " + ball.getTimeStamp());
			g2.setPaint(fade(ballColor, (int)currentTime - (int)ball.getTimeStamp()));
			g2.draw(  new Ellipse2D.Double( point.getX() - .625, point.getY() - .625,
																			1.25, 1.25 ));
		}

		// draw the other players
		iterator = dynamicObjects.iterator();
		//Use a regular expression to find the other players
		FilteredIterator filteredIterator = new FilteredIterator( iterator, "[pP]layer.*" );
		Object nextObject;
		PlayerInfo playerInfo;
		while( filteredIterator.hasNext() )
	    {
			playerInfo = (PlayerInfo)filteredIterator.next();

			point = playerInfo.getAbsoluteFieldPosition();
			if (playerInfo.getTeam() == null)
				g2.setPaint(fade(unknownPlayerColor, (int)currentTime - (int)playerInfo.getTimeStamp()));
			else
		    {
				if (playerInfo.getTeam().equals(self.getTeam()))
					g2.setPaint(fade(teamMateColor, (int)currentTime - (int)playerInfo.getTimeStamp()));
				else
					g2.setPaint(fade(opponentColor, (int)currentTime - (int)playerInfo.getTimeStamp()));
		    }
			g2.draw( new Ellipse2D.Double( point.getX() - .5, point.getY() - .5,
										   1.0, 1.0 ));
	    }
		// set the paint color back to black
		g2.setPaint( Color.black );
		// undo the previous transforms and scale
		g2.setTransform( new AffineTransform() );
		
		//Write the timestep, player's team name and the current play mode
		//to the upper lefthand corner of the picture
		g2.drawString("Time stamp: " + self.getTimeStamp(), 0, 15);
		g2.drawString("Team: " + self.getTeam(), 120, 15);
		g2.drawString(lastMessage.getPlayMode(), 200, 15);
    }
	/**
	  * Fades the color to the background
	  * depending on the time variable.  If time
	  * equals 0, then the foreground color is returned.
	  * If time equals the age value the class was
	  * constructed with, the background color is returned.
	  * @param foreground the foreground color
	  * @param time how old the data from the server is
	  * @return the foreground color faded towards the
	  * background color
	*/
	public Color fade(Color foreground, int time)
	{

		if (time < 0)
			time = 0;

		//alpha=0 => use foreground
		double alpha;
		if (age > 0)
			alpha = (double)time/(double)age;
		else
			alpha = 0;
			
		int red = (int)(foreground.getRed() * (1-alpha) + getBackground().getRed() * alpha) % 256;
		int green = (int)(foreground.getGreen() * (1-alpha) + getBackground().getGreen() * alpha) % 256;
		int blue = (int)(foreground.getBlue() * (1-alpha) + getBackground().getBlue() * alpha) % 256;

		Color rtnval = new Color( red, green, blue );

		return rtnval;
	}
}
