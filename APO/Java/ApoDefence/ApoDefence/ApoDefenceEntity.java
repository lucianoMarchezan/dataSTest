package apoDefence;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Klasse von der Button, Tower und Enemy erben und einige
 * grundlegene Sachen zur Verf�gung stellt 
 * @author Dirk Aporius
 *
 */
public class ApoDefenceEntity
{
	private double 			x, y;
	private int				width, height;
	private BufferedImage	iBackground;
	
	public ApoDefenceEntity( BufferedImage iBackground, double x, double y, int width, int height )
	{
		this.iBackground		= iBackground;
		this.x					= x;
		this.y					= y;
		this.width				= width;
		this.height				= height;
	}

	/**
	 * gibt das Bild zur�ck
	 * @return Bild
	 */
	public BufferedImage getIBackground()
	{
		return this.iBackground;
	}

	/**
	 * setzt das Bild auf den �bergebenen Wert
	 * @param background
	 */
	public void setIBackground(BufferedImage background)
	{
		iBackground = background;
	}

	/**
	 * gibt die Weite des Objektes zur�ck
	 * @return Weite des Objektes
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * setzt die Weite des Objektes auf den �bergebenen Wert
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * gibt die H�he des Objektes zur�ck
	 * @return H�he des Objektes
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * setzt die H�he des Objektes auf den �bergebenen Wert
	 * @param height
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * gibt den x-Wert des Objektes zur�ck (also den linken Rand des Bildes
	 * @return x-Wert des Objektes
	 */
	public double getX()
	{
		return this.x;
	}
	
	/**
	 * gibt den mittigen x-Wert des Objektes (also die Kopfmitte sozusagen)
	 * @return x-Wert des Objektes
	 */
	public double getXMiddle()
	{
		return this.x + this.width/2;
	}

	/**
	 * setzt den X-Wert auf den �bergebenen Wert
	 * @param x
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * gibt den y-Wert des Objektes zur�ck (also den h�chsten Punkt am Kopf)
	 * @return y-Wert des Objektes
	 */
	public double getY()
	{
		return this.y;
	}
	
	/**
	 * setzt den y-Wert des Objektes auf den �bergebenen
	 * @param y
	 */
	public void setY(double y)
	{
		this.y = y;
	}
	
	/**
	 * malt das Objekt
	 * @param g
	 */
	public void render( Graphics2D g )
	{
		g.drawImage( this.iBackground, (int)this.x, (int)this.y, (int)this.x + this.width, (int)this.y + this.height, 0, 0, this.width, this.height, null );
	}
	
	/**
	 * malt das Objekt
	 * @param g
	 */
	public void render( Graphics g )
	{
		g.drawImage( this.iBackground, (int)this.x, (int)this.y, (int)this.x + this.width, (int)this.y + this.height, 0, 0, this.width, this.height, null );
	}

}
