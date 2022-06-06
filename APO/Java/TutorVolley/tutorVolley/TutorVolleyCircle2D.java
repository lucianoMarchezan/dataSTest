package tutorVolley;

import java.awt.geom.Rectangle2D;

/**
 * eine Klasse, um einen Kreis mit einem Viereck zu vergleichen, ob sie sich schneiden
 * @author Dirk Aporius
 *
 */
public class TutorVolleyCircle2D
{

	private double		circle_x, circle_y, circle_radius;
	private double 		rectangle_x, rectangle_y, rectangle_width, rectangle_height;
	
	public TutorVolleyCircle2D( double circle_x, double circle_y, double circle_radius )
	{
		this.circle_x				= circle_x;
		this.circle_y				= circle_y;
		this.circle_radius			= circle_radius;
		this.rectangle_x			= -1;
		this.rectangle_y			= -1;
		this.rectangle_width		= 0;
		this.rectangle_height		= 0;
	}
	
	public TutorVolleyCircle2D( double circle_x, double circle_y, double circle_radius, double rectangle_x, double rectangle_y, double rectangle_width, double rectangle_height )
	{
		this.circle_x				= circle_x;
		this.circle_y				= circle_y;
		this.circle_radius			= circle_radius;
		this.rectangle_x			= rectangle_x;
		this.rectangle_y			= rectangle_y;
		this.rectangle_width		= rectangle_width;
		this.rectangle_height		= rectangle_height;
	}
	
	/**
	 * den CircleX-Wert zur�ck
	 * @return x-Wert
	 */
	public double getCircleX()
	{
		return this.circle_x;
	}
	
	/**
	 * den CircleY-Wert zur�ck
	 * @return y-Wert
	 */
	public double getCircleY()
	{
		return this.circle_y;
	}
	
	/**
	 * den Circle-Radius zur�ck
	 * @return radius des circles
	 */
	public double getCircleRadius()
	{
		return this.circle_radius;
	}
	
	/**
	 * den X-Wert des Rechteckes zur�ck
	 * @return x-Wert des Rechteckes
	 */
	public double getRectangleX()
	{
		return this.rectangle_x;
	}
	
	/**
	 * gibt den Y-Wert des Rechteckes zur�ck
	 * @return y-Wert des Rechteckes
	 */
	public double getRectangleY()
	{
		return this.rectangle_y;
	}
	
	/**
	 * gibt die Weite des Rechteckes zur�ck
	 * @return Weite des Rechteckes
	 */
	public double getRectangleWidth()
	{
		return this.rectangle_width;
	}
	
	/**
	 * gibt die H�he des Rechteckes zur�ck
	 * @return H�he des Rechteckes
	 */
	public double getRectangleHeight()
	{
		return this.rectangle_height;
	}
	
	/**
	 * �berpr�ft ob sich 2 TutorVolleyCircle2D Objekt sich schneiden
	 * @param circle = der Kreis mit den �berpr�ft werden soll
	 * @return TRUE, falls �berschneidung sonst FALSE
	 */
	public boolean intersects( TutorVolleyCircle2D circle )
	{
		Rectangle2D	rBall	= new Rectangle2D.Double( this.circle_x, this.circle_y, this.circle_radius * 2, this.circle_radius * 2 );
		Rectangle2D	rPlayer	= new Rectangle2D.Double( circle.getRectangleX(), circle.getRectangleY(), circle.getRectangleWidth(), circle.getRectangleHeight() );
		if ( rBall.intersects( rPlayer ) )
		{
			return true;
		}
		double distance	= ( ( this.circle_x - circle.getCircleX() ) * ( this.circle_x - circle.getCircleX() ) ) + ( ( this.circle_y - circle.getCircleY() ) * ( this.circle_y - circle.getCircleY() ) );
		double radius	= ( this.circle_radius + circle.getCircleRadius() ) * ( this.circle_radius + circle.getCircleRadius() );
		if ( radius > distance )
		{
			return true;
		}
		return false;
	}

}
