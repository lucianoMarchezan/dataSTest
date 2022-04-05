package GameSystem;

import java.awt.*;
import java.io.*;

/**
  * A class containing all types of obstacles.
  */
public class ObstacleCatalog{
	
	
	// CONSTRUCTORS 
		
	/**
	  * A new ObstacleCatalog is created (only one instance of this class can be created).
	  */
	private ObstacleCatalog(){	
		init();
	}
	
	
	// INSPECTORS 
	 
	/**
	  * Return the obstacleTypes of this obstacleCatalog.
	  *
	  * @return The obstacleTypes of this obstacleCatalog.
	  */
	public ObstacleType [] getObstacleTypes (){
		return $obstacleTypes;
	}
	
	/**
	  * Return the obstacleType with the given name
	  *
	  * @param name a given name
	  * @return the obstacle type with the given name if one exists
	  *         otherwise null will be returned
	  */
	public ObstacleType getObstacleType(String name){
		int i=0;
		while(i<$obstacleTypes.length && !$obstacleTypes[i].getName().equals(name))
			i++;
		if(i<$obstacleTypes.length)
			return $obstacleTypes[i];
		else
			return null;
	}
	
	/**
	  * Return a reference to the obstacleCatalog.
	  *
	  * @return A reference to the obstacleCatalog.
	  */
	public static ObstacleCatalog getReference(){
		return(OBSTACLECATALOG);
	}
		
		
	// MUTATORS 
	
	/**
	  * Make new obstacleTypes and add them in this obstacleCatalog.
	  */
	private void init(){
		try{FileParser fp = new FileParser(new FileReader("catalog\\obstacle.txt"));		
			while(true){
				String name = fp.readString();
				int red = fp.readInt();
				int green = fp.readInt();
				int blue = fp.readInt();
				Color color = new Color(red,green,blue);
				int size = fp.readInt();
				double damageengine = fp.readDouble();
				double damagetires = fp.readDouble();
				double damagebody = fp.readDouble();
				String sound = fp.readString();
				String image = fp.readString();
				addObstacleType(new ObstacleType(name,color,size,damageengine,damagetires,damagebody,sound,image));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the obstacle.txt file is in the right directory");
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
	  * Add a given obstacleType to this obstacleCatalog. 
	  *
	  * @param obstacleType: The obstacleType that must be added 
	  *						 in this obstacleCatalog
	  * @exception NullPointerException
	  */
	void addObstacleType(ObstacleType obstacleType){
		if(obstacleType==null)
			throw new NullPointerException("Invalid obstacle type");
		int length= $obstacleTypes.length;
		ObstacleType[] hulp = $obstacleTypes;
		$obstacleTypes = new ObstacleType[length + 1];
		for(int i=0; i < length; i++) 
			$obstacleTypes[i] = hulp[i];
		$obstacleTypes[length] = obstacleType;
	}
	
	
	// VARIABLES 
	
	/*
	 * A variable (ObstacleCatalog) for holding a reference 
	 * to the one and only instance of ObstacleCatalog.
	 */
	private static ObstacleCatalog OBSTACLECATALOG = new ObstacleCatalog();
	 
	/*
	 * A variable (array ObstacleType) for holding the obstacleTypes 
	 * of this obstacleCatalog.
	 */
	private ObstacleType [] $obstacleTypes = new ObstacleType[0];
}
