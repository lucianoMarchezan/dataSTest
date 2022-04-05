package GameSystem;

/** 
  * A class of roadParts. 
  */
public class RoadPart{


	// CONSTRUCTORS
		
	/**
	  * Creates a new roadPart of the given type. 
	  *
	  * @param roadPartType: The type of the roadpart to be created
	  * @exception NullPointerException
	  */
	RoadPart(RoadPartType roadPartType){
		if(roadPartType==null)
			throw new NullPointerException("Invalid roadparttype");
		$ROADPARTTYPE = roadPartType;
		$roadObjects = new RoadObject[0];
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the type of this roadPart.
	  *
	  * @return The type of this roadPart. 
	  */
	public RoadPartType getRoadPartType(){
		return $ROADPARTTYPE;
	}
	
	/**
	  * Returns the roadobjects lying on this roadPart. 
	  *
	  * @return The roadobjects lying on this roadPart.    
	  */
	public RoadObject[] getRoadObjects(){
		return $roadObjects;
	}
	
	/**
	  * checks whether a roadobject is lying on this roadPart. 
	  *
	  * @param roadObject The roadobject being checked.    
	  * @return A boolean indicating whether the given roadobject is lying on this roadPart
	  */
	public boolean hasRoadObject(RoadObject roadObject){
		int i=0;
		while(i<$roadObjects.length && $roadObjects[i]!=roadObject) i++;
		return i!=$roadObjects.length;
	}
	
	
	// MUTATORS
	
	/** 
	  * Adds a roadobject to this roadPart.
	  *
	  * @param roadObject The roadobject to be added.
	  * @exception NullPointerException	  
	  */	
	void addRoadObject(RoadObject roadObject){
		if(roadObject==null)
			throw new NullPointerException("Invalid roadobject");
		int length = $roadObjects.length;
		RoadObject[] roadObjectsOld = $roadObjects;
		$roadObjects = new RoadObject[length+1];
		for (int i=0;i<length;i++) 
			$roadObjects[i]=roadObjectsOld[i];
		$roadObjects[length]=roadObject;
	}
	
	/**
	  * Removes a roadobject from this roadPart. 
	  *
	  * @param roadObject: The roadobject to be removed
	  * @pre the given roadobject has to lie on this road
	  * @exception NullPointerException
	  */
	void removeRoadObject(RoadObject roadObject){
		if(roadObject==null)
			throw new NullPointerException("Invalid roadobject");
		int length = $roadObjects.length;		
		RoadObject[] roadObjectsNew = new RoadObject[length-1];
		int i=0;
		for (;i<length && $roadObjects[i]!=roadObject;i++){
			roadObjectsNew[i]=$roadObjects[i];
		}
		if (i<length-1){
			i++;
			for (;i<length;i++){ 
				roadObjectsNew[i-1]=$roadObjects[i];
			}
		}
		$roadObjects=roadObjectsNew;	
	}
	
	
	// VARIABLES
	
	/*
	 * The type of this roadPart.
	 */
	private RoadPartType $ROADPARTTYPE;
	
	/* 
	 * The roadobjects lying on this roadPart.
	 */
	private RoadObject[] $roadObjects;
	
	/* 
	 * The length of every roadPart in pixels.
	 */
	public static final int LENGTH=10000;
}