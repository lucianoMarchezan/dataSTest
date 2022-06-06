package apoDefence;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * ein spezieller Button um ein Objekt zu ziehen.
 * @author Dirk Aporius
 *
 */
public class ApoDefenceDragObject extends ApoDefenceButton
{
	private boolean		bSelected;
	private int			diffX, diffY;
	
	public ApoDefenceDragObject( int x, int y, int width, int height )
	{
		super( null, x, y, width, height, "" );
		
		this.diffX		= 0;
		this.diffY		= 0;
	}
	
	/**
	 * gibt zur�ck, ob der Button ausgew�hlt ist oder nicht
	 * @return TRUE falls ausgew�hlt, sonst FALSE
	 */
	public boolean isBSelected()
	{
		return this.bSelected;
	}

	/**
	 * setzt die boolean Variable, ob der Button ausgew�hlt ist oder nicht, auf den �bergebenen Wert
	 * @param bSelected
	 */
	public void setBSelected(boolean bSelected)
	{
		this.bSelected = bSelected;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld gedr�ckt wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, falls �ber Button Maus gedr�ckt, sonst FALSE
	 */
	public boolean getPressed( int x, int y )
	{
		boolean bPressed	= super.getPressed( x, y );
		if ( bPressed )
		{
			this.diffX			= (int)( x - this.getX() );
			this.diffY			= (int)( y - this.getY() );
			this.bSelected		= true;
		}
		return bPressed;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld losgelassen wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, wenn die Maustaste losgelassen wurde und der Spieler auch diesen Button gedr�ckt hatte, sonst FALSE
	 */
	public boolean getReleased( int x, int y )
	{
		boolean bReleased	= super.getReleased( x, y );
		this.diffX			= 0;
		this.diffY			= 0;
		return bReleased;
	}
	
	/**
	 * �berpr�ft, ob die �bergebenen Werte auf dem Button liegen
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return: TRUE, falls drin, sonst FALSE
	 */
	public boolean getIn( int x, int y )
	{
		if ( 	( this.getX() - this.getWidth()/2 < x ) && ( this.getX() + this.getWidth()/2 + this.getWidth() >= x ) &&
				( this.getY() - this.getHeight()/2 < y ) && ( this.getY() + this.getHeight()/2 + this.getHeight() >= y ) )
		{
			return true;
		}
		return false;
	}
	
	/**
	 * setzt die x Variable des Button auf den �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde
	 * @param x: x-Koordinate
	 */
	public void setDragX( int x )
	{
		this.setX( x - this.diffX );
	}
	
	/**
	 * setzt die y Variable des Button auf den �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde
	 * @param x: y-Koordinate
	 */
	public void setDragY( int y )
	{
		this.setY( y - this.diffY );
	}

	/**
	 * gibt die x Variable des Button vom �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde, zur�ck
	 * @param x: x-Koorinate
	 * @return gibt die x Variable des Button vom �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde, zur�ck
	 */
	public int getNewX( int x )
	{
		return ( x - this.diffX );
	}
	
	/**
	 * gibt die y Variable des Button vom �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde, zur�ck
	 * @param y: y-Koorinate
	 * @return gibt die y Variable des Button vom �bergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedr�ckt wurde, zur�ck
	 */
	public int getNewY( int y )
	{
		return ( y - this.diffY );
	}
	
	/**
	 * malt den Button hin
	 */
	public void render( Graphics g )
	{
		this.render( (Graphics2D)g );
	}
	
	/**
	 * malt den Button hin
	 */
	public void render( Graphics2D g )
	{
		if ( this.isBVisible() == true )
		{
			if ( this.isBSelected() )
			{
				g.setColor( Color.WHITE );
				g.drawRect( (int)this.getX() - this.getWidth()/2, (int)this.getY() - this.getHeight()/2, this.getWidth(), this.getHeight() );
			}
			g.setColor( Color.BLACK );
			g.fillRect( (int)( this.getX() - 2 ), (int)( this.getY() - 2 ), 4, 4 );
		}
	}

}
