package GameSystem;

import java.awt.*;
import java.io.*;

/**
  * A class containing all types of roadParts.
  */
public class RoadPartCatalog{


	// CONSTRUCTORS	
	
	/**
	  * A new roadPartCatalog is created (only one instance of this class can be created).
	  */
	private RoadPartCatalog() {
		init();
	}
	
	
	// INSPECTORS

	/**
	  * Return a reference to the roadPartCatalog.
	  *
	  * @return A reference to the roadPartCatalog.  
	  */
	public static RoadPartCatalog getReference() {
		return(ROADPARTCATALOG);
	}
	
	/**
	  * Returns an array containing all roadPartTypes.
	  *
	  * @return The roadPartTypes of this roadPartCatalog.
	  */
	public RoadPartType[] getRoadPartTypes(){
		return $roadPartTypes;
	}
	
	
	// MUTATORS

	/**
	  * Creates all roadPartTypes and adds them to the roadPartCatalog.
	  */
	private void init(){
		try{FileParser fp = new FileParser(new FileReader("catalog\\roadpart.txt"));		
			while(true){
				String name = fp.readString();
				int maximumSpeed = fp.readInt();
				int minimumSpeed = fp.readInt();
				int steerability = fp.readInt();
				double accelerationFactor = fp.readDouble();
				double decelerationFactor = fp.readDouble();
				int red = fp.readInt();
				int green = fp.readInt();
				int blue = fp.readInt();
				Color color	= new Color(red,green,blue);
				addRoadPartType(new RoadPartType(name,maximumSpeed,minimumSpeed,steerability,accelerationFactor,decelerationFactor,color));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the roadpart.txt file is in the right directory");
			System.exit(1);
		}
		catch(EOFException e){
			//no problem, this is normal
		}
		catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/**
	  * Adds a roadPartType to the roadPartCatalog.
	  *
	  * @param roadPartType: The roadPartType to be added to the roadPartCatalog.
	  * @exception NullPointerException
	  */
	void addRoadPartType(RoadPartType roadPartType){
		if (roadPartType==null)
			throw new NullPointerException("Invalid roadpart type");
		int length=$roadPartTypes.length;
		RoadPartType[] roadPartTypesOld = $roadPartTypes;
		$roadPartTypes = new RoadPartType[length+1];
		for (int i=0;i<length;i++) 
			$roadPartTypes[i]=roadPartTypesOld[i];
		$roadPartTypes[length] = roadPartType;
	}
	
	
	// VARIABLES
	
	/*
	 * The one and only instance of the roadPartCatalog. 
	 */
	private static RoadPartCatalog ROADPARTCATALOG = new RoadPartCatalog();
	
	/* 
	 * The types of roadParts. 
	 */	 
	private RoadPartType[] $roadPartTypes = new RoadPartType[0];
}
