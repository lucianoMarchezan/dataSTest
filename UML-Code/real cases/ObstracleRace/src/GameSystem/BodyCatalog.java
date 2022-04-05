package GameSystem;

import java.io.*;

/**
  * A catalog for storing different kind of body types.
  */
public class BodyCatalog{

	
	// CONSTRUCTORS
	
	/**
	  * A new BodyCatalog is created.
	  */
	private BodyCatalog(){	
		init();
	}
	
	
	// INSPECTORS
	
	/**
	  * Return the bodyTypes of the bodyCatalog.
	  *
	  * @return The bodyTypes of this bodyCatalog.
	  */
	public BodyType[] getBodyTypes(){
		return $bodyTypes;
	}
	
	/**
	  * Return a reference to the bodyCatalog.
	  *
	  * @return A reference to the BodyCatalog.
	  */
	public static BodyCatalog getReference(){
		return(BODYCATALOG);
	}
	

	// MUTATORS
	
	/**
	  * Add a given bodyType to this BodyCatalog. 
	  *
	  * @param bodyType: The bodyType that must be added 
	  *						 in this bodyCatalog
	  * @exception NullPointerException
	  */
	void addBodyType(BodyType bodyType){
		if(bodyType==null)
			throw new NullPointerException("Invalid body type");
		int length= $bodyTypes.length;
		BodyType[] hulp = $bodyTypes;
		$bodyTypes = new BodyType[length + 1];
		for(int i=0; i < length; i++) 
			$bodyTypes[i] = hulp[i];
		$bodyTypes[length] = bodyType;
	}
			
	/**
	  * Creates all bodyTypes and adds them to the bodyCatalog.
	  */
	private void init(){
		try{FileParser fp = new FileParser(new FileReader("catalog\\body.txt"));		
			
			while(true){
				String name = fp.readString()+" "+fp.readString();
				double accelerationFactor = fp.readDouble();
				int price = fp.readInt();
				double strength = fp.readDouble();
				int repairPriceIndex = fp.readInt();
				String filename = fp.readString();
				addBodyType(new BodyType(name,accelerationFactor,price,strength,repairPriceIndex,filename));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the body.txt file is in the right directory");
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
	 * The types of bodies.
	 */
	private BodyType[] $bodyTypes = new BodyType[0]; 
	
	/*
	 * A variable (BodyCatalog) for holding a reference 
	 * to this BodyCatalog.
	 */
	private final static BodyCatalog BODYCATALOG = new BodyCatalog();
}