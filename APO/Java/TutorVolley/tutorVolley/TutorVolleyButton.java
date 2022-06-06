package tutorVolley;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Diese Klasse handelt einen Button
 * @author Dirk Aporius
 *
 */
public class TutorVolleyButton extends TutorVolleyEntity
{
	private String		function;
	private boolean 	bOver, bPressed, bVisible;
	
	public TutorVolleyButton( BufferedImage iBackground, int x, int y, int width, int height, String function )
	{
		super( iBackground, x, y, width, height );
		
		this.function	= function;
		this.bOver		= false;
		this.bPressed	= false;
		this.bVisible	= true;
	}

	/**
	 * gibt zur�ck, ob die Maus �ber dem Button ist oder nicht
	 * @return TRUE, falls Maus dr�ber, sonst FALSE
	 */
	protected boolean isBOver()
	{
		return this.bOver;
	}

	/**
	 * setzt den boolean-Wert f�r bOver auf den �bergebenen Wert
	 * @param bOver
	 */
	protected void setBOver(boolean bOver)
	{
		this.bOver = bOver;
	}

	/**
	 * gibt zur�ck, ob eine Maustaste �ber dem Button gedr�ckt ist oder nicht
	 * @return TRUE, falls Maustaste gedr�ckt, sonst FALSE
	 */
	protected boolean isBPressed()
	{
		return this.bPressed;
	}

	/**
	 * setzt den boolean-Wert f�r bPressed auf den �bergebenen Wert 
	 * @param bPressed
	 */
	protected void setBPressed(boolean bPressed)
	{
		this.bPressed = bPressed;
	}

	/**
	 * gibt an, ob der Button sichtbar ist oder nicht
	 * @return TRUE, falls sichtbar, sonst FALSE
	 */
	protected boolean isBVisible()
	{
		return this.bVisible;
	}

	/**
	 * setzt die Sichtbarkeit des Buttons auf den �bergebenen Wert
	 * @param bVisible
	 */
	protected void setBVisible(boolean bVisible)
	{
		this.bVisible = bVisible;
	}

	/**
	 * gibt die Funktion des Buttons zur�ck
	 * @return function
	 */
	protected String getFunction()
	{
		return this.function;
	}

	/**
	 * sezt die Funktion des Buttons auf den �bergebenen Wert
	 * @param function
	 */
	protected void setFunction(String function)
	{
		this.function = function;
	}
	
	/**
	 * was passiert, wenn die Maus im Spielfeld bewegt wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, falls Maus dr�ber, sonst FALSE
	 */
	protected boolean getMove( int x, int y )
	{
		if ( ( this.bOver == false ) && ( getIn( x, y ) == true ) && ( this.bVisible == true ) )
		{
			this.bOver		= true;
			return true;
		} else if ( ( this.bOver == true ) && ( getIn( x, y ) == false ) )
		{
			this.bOver		= false;
			this.bPressed	= false;
			return true;
		}
		return false;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld gedr�ckt wurde wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, falls �ber Button Maus gedr�ckt, sonst FALSE
	 */
	protected boolean getPressed( int x, int y )
	{
		if ( ( this.bOver == true ) && ( getIn( x, y ) == true ) && ( this.bVisible == true ) )
		{
			this.bPressed	= true;
			return true;
		}
		return false;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld losgelassen wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, wenn die Maustaste losgelassen wurde und der Spieler auch diesen Button gedr�ckt hatte, sonst FALSE
	 */
	protected boolean getReleased( int x, int y )
	{
		if ( ( this.bPressed == true ) && ( getIn( x, y ) == true ) && ( this.bVisible == true ) )
		{
			this.bPressed	= false;
			return true;
		}
		return false;
	}
	
	/**
	 * �berpr�ft, ob die �bergebenen Werte auf dem Button liegen
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return: TRUE, falls drin, sonst FALSE
	 */
	private boolean getIn( int x, int y )
	{
		if ( 	( this.getX() < x ) && ( this.getX() + this.getWidth() >= x ) &&
				( this.getY() < y ) && ( this.getY() + this.getHeight() >= y ) )
		{
			return true;
		}
		return false;
	}
	
	/**
	 * malt den Button hin
	 */
	protected void render( Graphics2D g )
	{
		if ( this.bVisible == true )
		{
			if ( this.bPressed == true ) 
				g.drawImage( this.getIBackground(), (int)this.getX(), (int)this.getY(), (int)this.getX() + this.getWidth(), (int)this.getY() + this.getHeight(), 2*this.getWidth(), 0, 3*this.getWidth(), this.getHeight(), null);
			else if ( this.bOver == true )
				g.drawImage( this.getIBackground(), (int)this.getX(), (int)this.getY(), (int)this.getX() + this.getWidth(), (int)this.getY() + this.getHeight(), 1*this.getWidth(), 0, 2*this.getWidth(), this.getHeight(), null);
			else
				g.drawImage( this.getIBackground(), (int)this.getX(), (int)this.getY(), (int)this.getX() + this.getWidth(), (int)this.getY() + this.getHeight(), 0*this.getWidth(), 0, 1*this.getWidth(), this.getHeight(), null);
		}
	}

}
