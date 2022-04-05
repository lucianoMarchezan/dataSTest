//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.awt.geom.Point2D;
/**
 * A mathematical vector which also keeps starting point.
  * @author Jose M. vidal
  * @version $Revision: 1.5 $, $Date: 2001/02/27 22:24:31 $
  *
  */

class AbsoluteMathVector extends MathVector{

  private double xLocation;
  private double yLocation;

  public AbsoluteMathVector() 
  {
    super();
    xLocation = 0;
    yLocation = 0;
  }

  public AbsoluteMathVector (double xLocation, double yLocation, double magnitude, double angle)
  {
    super(magnitude, angle);
    this.xLocation = xLocation;
    this.yLocation = yLocation;
  }

  /** A vector from p1 to p2 */
  public AbsoluteMathVector(Point2D.Double p1, Point2D.Double p2){
    super(p1, p2);
    xLocation = p1.getX();
    yLocation = p1.getY();
  }

  public AbsoluteMathVector(Point2D.Double p1, double magnitude, double angle){
    super(magnitude, angle);
    xLocation = p1.getX();
    yLocation = p1.getY();
  }
  
  public AbsoluteMathVector(AbsoluteMathVector o){
    super(o.getMagnitude(), o.getAngle());
    this.xLocation = o.xLocation;
    this.yLocation = o.yLocation;
  }

  /** Sets the angle and magnitude to match the endpoint
			@param x x coordinate of endpoint
			@param y y coordinate of endpoint */
  public void setEndPoint(double x, double y)
  {
    double dx = x - xLocation;
    double dy = y - yLocation;
    set(dx,dy);
  }

  public void setEndPoint(Point2D.Double point)
  {
    setEndPoint(point.getX(), point.getY());
  }

  public void set(Point2D.Double loc)
  {
    xLocation = loc.getX();
    yLocation = loc.getY();
  }

  public void set(AbsoluteMathVector o)
  {
    super.set(o);
    xLocation = o.getX();
    yLocation = o.getY();
  }

  public Point2D.Double getLocation()
  {
    return new Point2D.Double(xLocation, yLocation);
  }

  public String toString()
  {
    return "(" + xLocation + "," + yLocation + ")" + super.toString();
  }

  /** Calculate the enpoint of this vector and return a new vector like this
			one but placed at that location (with the same magnitude and angle).
			This function is used to extrapolate the next location of an object.
			@return the new AbsoluteMathVector
  */
  public AbsoluteMathVector getEndPointVector()
  {
    double x = xLocation + Math.cos(Math.toRadians(getAngle()))*getMagnitude();
    double y = yLocation + Math.sin(Math.toRadians(getAngle()))*getMagnitude();
    return new AbsoluteMathVector(x,y,getMagnitude(),getAngle());
  }

  /**
     @return the point at the end of this vector. */
  public Point2D.Double getEndPointLocation()
  {
    double x = xLocation + Math.cos(Math.toRadians(getAngle()))*getMagnitude();
    double y = yLocation + Math.sin(Math.toRadians(getAngle()))*getMagnitude();
    return new Point2D.Double(x,y);
  }

  public void setLocationToEndPoint(){
    xLocation = xLocation + Math.cos(Math.toRadians(getAngle()))*getMagnitude();
    yLocation = yLocation + Math.sin(Math.toRadians(getAngle()))*getMagnitude();
  }

  /** Sets the location to the one pointed at by the vector with the magnitude reduced
      by (multiplied by) decay.
      @param decay the decay
	 */
  public void extrapolateToNextPosition(double decay)
  {
    setMagnitude(getMagnitude() * decay);
    set(getEndPointLocation());

//     double magnitude = getMagnitude() * 0.94; //smae as ball_decay. Make this a parameter
//     if (magnitude >= 1)
//       magnitude = 1;
//     setMagnitude(magnitude);
//     set(getEndPointLocation());
  }


  public double getDistance(Point2D.Double o){
    return Math.sqrt((o.getX() - xLocation)*(o.getX() - xLocation) +
                     (o.getY() - yLocation)*(o.getY() - yLocation));
  }

  public double getX() {
    return xLocation;
  }

  public double getY() {
    return yLocation;
  }

  /** Find the closest point from x to this vector. That is, the
      point P (on this vector) on a perpendicular from x to this vecotr.
      @param x the point
      @return the closest point to x on this vector. */
  public Point2D.Double getClosestPoint(Point2D.Double x){
    Point2D.Double myLocation = getLocation();
    Point2D.Double myEndPointLocation = getEndPointLocation();
    MathVector AC = new MathVector(myLocation, x);
    MathVector AB = this;
    if (getMagnitude() == 0) //this vector is a point
      return (Point2D.Double)myLocation.clone();
    double r = AC.dotProduct(AB)/ getDeltaX()*getDeltaX() + getDeltaY()*getDeltaY();
    Point2D.Double result;
    if (r < 0)
      result = (Point2D.Double)myLocation.clone();
    else if (r > 1)
      result = (Point2D.Double)myEndPointLocation.clone();
    else 
      result = new Point2D.Double(myLocation.getX() + r*(myEndPointLocation.getX() - myLocation.getX()),
                                  myLocation.getY() + r*(myEndPointLocation.getY() - myLocation.getY()));
    return result;
  }

}
