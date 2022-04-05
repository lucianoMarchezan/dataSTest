package GameSystem;

import java.io.*;

/**
  * A catalog for storing different kind of engine types.
  */
public class EngineCatalog{

	
	// CONSTRUCTORS
	
	/**
	  * A new EngineCatalog is created.
	  */
	private EngineCatalog(){	
		init();
	}
	
	
	// INSPECTORS
	
	/**
	  * Return the engineTypes of the engineCatalog.
	  *
	  * @return The engineTypes of this engineCatalog.
	  */
	public EngineType[] getEngineTypes(){
		return $engineTypes;
	}
	
	/**
	  * Return a reference to the engineCatalog.
	  *
	  * @return A reference to the engineCatalog.
	  */
	public static EngineCatalog getReference(){
		return(ENGINECATALOG);
	}
	

	// MUTATORS
	
	/**
	  * Add a given engineType to this EngineCatalog. 
	  *
	  * @param engineType: The engineType that must be added 
	  *						 in this engineCatalog
	  * @exception NullPointerException
	  */
	void addEngineType(EngineType engineType){
		if(engineType==null)
			throw new NullPointerException("Invalid engine type");
		int length= $engineTypes.length;
		EngineType[] hulp = $engineTypes;
		$engineTypes = new EngineType[length + 1];
		for(int i=0; i < length; i++) 
			$engineTypes[i] = hulp[i];
		$engineTypes[length] = engineType;
	}
			
	/**
	  * Creates all engineTypes and adds them to the engineCatalog.
	  */
	private void init(){
		try{FileParser fp = new FileParser(new FileReader("catalog\\engine.txt"));		
			
			while(true){
				String name = fp.readString()+" "+fp.readString();
				double accelerationFactor = fp.readDouble();
				int maximumSpeed = fp.readInt();
				int price = fp.readInt();
				double strength = fp.readDouble();
				int repairPriceIndex = fp.readInt();
				String filename = fp.readString();
				addEngineType(new EngineType(name,accelerationFactor,maximumSpeed,price,strength,repairPriceIndex,filename));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the engine.txt file is in the right directory");
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
			
	// VARIABLES
	
	/*
	 * The types of engines.
	 */
	private EngineType[] $engineTypes = new EngineType[0]; 
	
	/*
	 * A variable (EngineCatalog) for holding a reference 
	 * to this EngineCatalog.
	 */
	private final static EngineCatalog ENGINECATALOG = new EngineCatalog();
}