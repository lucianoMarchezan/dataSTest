package GameSystem;

/**
  * A class of bodies.
  */
public class Body extends Part{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new body with given bodyType.
	  *
	  * @param bodyType: The bodyType of this body.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Body(BodyType bodyType){
		super(bodyType);
	}
}