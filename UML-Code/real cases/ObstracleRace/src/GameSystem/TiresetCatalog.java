package GameSystem;

import java.io.*;

/**
  * A catalog for storing different kind of Tireset types.
  */
public class TiresetCatalog{

	
	// CONSTRUCTORS
	
	/**
	  * A new TiresetCatalog is created.
	  */
	private TiresetCatalog(){	
		init();
	}
	
	
	// INSPECTORS
	
	/**
	  * Return the TiresetTypes of the TiresetCatalog.
	  *
	  * @return The tiresetTypes of this tiresetCatalog.
	  */
	public TiresetType[] getTiresetTypes(){
		return $tiresetTypes;
	}
	
	/**
	  * Return a reference to the tiresetCatalog.
	  *
	  * @return A reference to the tiresetCatalog.
	  */
	public static TiresetCatalog getReference(){
		return(TIRESETCATALOG);
	}
	

	// MUTATORS
	
	/**
	  * Add a given tiresetType to this tiresetCatalog. 
	  *
	  * @param tiresetType: The tiresetType that must be added 
	  *						in this tiresetCatalog
	  * @exception NullPointerException
	  */
	void addTiresetType(TiresetType tiresetType){
		if(tiresetType==null)
			throw new NullPointerException("Invalid tireset type");
		int length= $tiresetTypes.length;
		TiresetType[] hulp = $tiresetTypes;
		$tiresetTypes = new TiresetType[length + 1];
		for(int i=0; i < length; i++) 
			$tiresetTypes[i] = hulp[i];
		$tiresetTypes[length] = tiresetType;
	}
			
	/**
	  * Creates all tiresetTypes and adds them to the tiresetCatalog.
	  */
	private void init(){
		try{FileParser fp = new FileParser(new FileReader("catalog\\tireset.txt"));		
			while(true){
				String name = fp.readString()+" "+fp.readString();
				double durability = fp.readDouble();
				double grip = fp.readDouble();
				double steerability = fp.readDouble();
				int price = fp.readInt();
				double strength = fp.readDouble();
				int repairPriceIndex = fp.readInt();
				String filename = fp.readString();
				addTiresetType(new TiresetType(name,durability,grip,steerability,price,strength,repairPriceIndex,filename));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the tireset.txt file is in the right directory");
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
	 * The types of tiresets.
	 */
	private TiresetType[] $tiresetTypes = new TiresetType[0]; 
	
	/*
	 * A variable (TiresetCatalog) for holding a reference 
	 * to this tiresetCatalog.
	 */
	private final static TiresetCatalog TIRESETCATALOG = new TiresetCatalog();
}