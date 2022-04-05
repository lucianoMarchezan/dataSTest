package GameSystem;

/**
  * A class of engines.
  */
public class Engine extends Part{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new engine with given engineType.
	  *
	  * @param engineType: The engineType of this engine.
	  * @exception NullPointerException
	  * @exception IllegalArgumentException
	  */
	Engine(EngineType engineType){
		super(engineType);
	}
}