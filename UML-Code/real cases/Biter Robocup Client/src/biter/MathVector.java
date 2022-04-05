//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.awt.geom.Point2D;

/**
 * A mathematical vector.
 * @author Jose M. vidal
 * @version $Revision: 1.7 $, $Date: 2001/02/27 22:24:31 $
 *
 */

class MathVector{

  private double magnitude;
  private double angle;

  public MathVector() 
  {
    magnitude = 0;
    angle = 0;
  }

  public MathVector (double magnitude, double angle)
  {
    this.magnitude = magnitude;
    this.angle = angle;
  }

  /** A vector from p1 to p2 */
  public MathVector(Point2D.Double p1, Point2D.Double p2){
    set(p2.getX() - p1.getX(), p2.getY() - p1.getY());
  }

  public void set(double dx, double dy)
  {
    if (dx == 0)
      angle = (dy >= 0) ? 90 : 270;
    else
    {
      angle = Math.toDegrees(Math.atan(dy/dx));
      if (dx < 0)
        angle += 180;
    }
    magnitude = Math.sqrt(dx*dx + dy*dy);
  }

  /**
   * Get the value of magnitude.
   * @return value of magnitude.
   */
  public double getMagnitude() {
    return magnitude;
  }
  
  /**
   * Set the value of magnitude.
   * @param v  Value to assign to magnitude.
   */
  public void setMagnitude(double  v) {
    
    this.magnitude = v;
  }

  /**
   * Get the value of angle.
   * @return value of angle.
   */
  public double getAngle() {
    return angle;
  }
  
  /**
   * Set the value of angle.
   * @param v  Value to assign to angle.
   */
  public void setAngle(double  v) {
    
    this.angle = v;
  }

  
  /** Add other to this vector. The location of other is ignored.
			@param other the other vector */
  public void add(MathVector other)
  {
    double otherXComponent = other.magnitude * Math.cos(Math.toRadians(other.angle));
    double otherYComponent = other.magnitude * Math.sin(Math.toRadians(other.angle));

    double myXComponent = magnitude * Math.cos(Math.toRadians(angle));
    double myYComponent = magnitude * Math.sin(Math.toRadians(angle));

    set(otherXComponent + myXComponent, otherYComponent + myYComponent);
  } 

  
  public void subtract(MathVector o){
    set(getDeltaX() - o.getDeltaX(), getDeltaY() - o.getDeltaY());
  }


  public void set(MathVector o)
  {
    magnitude = o.magnitude;
    angle = o.angle;
  }

  public String toString()
  {
    return " M:" + magnitude + " A:" + angle;
  }

  public void addToAngle(double deltaAngle)
  {
    angle += deltaAngle;
    if (angle > 180)
      while (angle > 180) angle -= 360;
    else if (angle < -180)
      while (angle < -180) angle += 360;
  }

  public double getDeltaX(){
    return  magnitude * Math.cos(Math.toRadians(angle));
  }

  public double getDeltaY(){
    return  magnitude * Math.sin(Math.toRadians(angle));
  }

  public double dotProduct(MathVector o){
    return getDeltaX() * o.getDeltaX() + getDeltaY() * o.getDeltaY();
  }
  
}
