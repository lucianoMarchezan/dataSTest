package GameSystem;

/**
  * A class of tiresets.
  */
public class Tireset extends Part{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new tireset with given tiresetType.
	  *
	  * @param tiresetType: The tiresetType of this tireset.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Tireset(TiresetType tiresetType){
		super(tiresetType);
	}
}