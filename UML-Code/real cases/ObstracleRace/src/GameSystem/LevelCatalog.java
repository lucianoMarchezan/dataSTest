package GameSystem;


import java.io.*;

/**
  * A catalog for storing different kind of level types.
  */
public class LevelCatalog{

	// CONSTRUCTORS
	
	/**
	  * A new LevelCatalog is created.
	  */
	private LevelCatalog(){	
		init();
	}
	
	
	// INSPECTORS
	
	/**
	  * Return the levelTypes of the levelCatalog.
	  *
	  * @return The levelTypes of this levelCatalog.
	  */
	public LevelType[] getLevelTypes(){
		return $levelTypes;
	}
	
	/**
	  * Get the levelTypes of this catalog of a certain level number.
	  *
	  * @param levelNumber: Which level number you want to get levelTypes from.
	  * @return The levelTypes of this catalog of a certain level number.
	  * @exception IllegalArgumentException
	  */
	public LevelType[] getLevelTypes(int levelNumber){
		if(levelNumber <= 0 || levelNumber > LevelCatalog.getReference().getLevelTypes()[LevelCatalog.getReference().getLevelTypes().length-1].getLevelNumber())			
			throw new IllegalArgumentException("Invalid levelNumber");
		//determine the begin and end index of the array with the given level number
		int count = 0;
		while($levelTypes[count].getLevelNumber() != levelNumber)
			count++;
		int begin = count;
		count++;
		while(count<$levelTypes.length && $levelTypes[count].getLevelNumber() == levelNumber)
			count++	;
		int end = count;
		
		//create a new array and copy the levelTypes with as level number the given level number in this array
		LevelType[] levels = new LevelType[end-begin];
		for(int i=begin;i<end;i++)
			levels[i-begin]=$levelTypes[i];
	
		return levels;	
	}
	
	/**
	  * Return a reference to the levelCatalog.
	  *
	  * @return A reference to the levelCatalog.
	  */
	public static LevelCatalog getReference(){
		return(LEVELCATALOG);
	}
	

	// MUTATORS
	
	/** 
	  * Add a levelType to the catalog.
	  *
	  * @param levelType: The levelType you want to add to the catalog.
	  */
	private void addLevelType(LevelType levelType){
		//The given level type is inserted in the array so that the array stays sorted
		if(getLevelTypes().length == 0)
			insertLevelType(levelType,0);
		else
			{int newLevelNumber = levelType.getLevelNumber();
			 int arrayBeginLevel = 0;
			 try{while($levelTypes[arrayBeginLevel].getLevelNumber() < newLevelNumber)
			 		arrayBeginLevel++;
				}
			 catch(ArrayIndexOutOfBoundsException e){}	
			 insertLevelType(levelType,arrayBeginLevel);
			}
    }
	
	/**
	  * Insert the given levelType in the given position in this catalog,
	  * and the necessary levels are shuffled.
	  *
	  * @param levelType: The level you want to add.
	  * @param position: The position on which the level has to be added.
	  */
	private void insertLevelType(LevelType levelType,int position){
		LevelType[] temp = new LevelType[$levelTypes.length+1];
		for(int i=0;i<position;i++)
			temp[i] = $levelTypes[i];
		for(int i=position;i<$levelTypes.length;i++)
			temp[i+1] = $levelTypes[i];
		temp[position] = levelType; 	
		$levelTypes = temp;
	}
	
		
	/**
	  * Make new levelTypes and add them in this LevelCatalog.
	  */
	private void init(){
		int[] obstacleTypeProbability;
		int[] partProbability;
		int temp1,temp2,temp3,temp4,temp5;
		
		try{FileParser fp = new FileParser(new FileReader("catalog\\level.txt"));		
			
			while(true){
				
				temp1 = fp.readInt();
				temp2 = fp.readInt();
				temp3 = fp.readInt();
				obstacleTypeProbability = new int[]{temp1,temp2,temp3};
				temp1 = fp.readInt();
				temp2 = fp.readInt();
				temp3 = fp.readInt();
				temp4 = fp.readInt();
				temp5 = fp.readInt();
				partProbability = new int[]{temp1,temp2,temp3,temp4,temp5};
								
				int numberOfParts = fp.readInt();
				int numberOfStaticObstacles = fp.readInt();
				int numberOfMovingObstacles = fp.readInt();
				int numberOfBonusItems = fp.readInt();
				int bonusTime = fp.readInt();
				int startTime = fp.readInt();
				int levelNumber = fp.readInt();
				addLevelType(new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles,
			  	numberOfMovingObstacles,numberOfBonusItems,bonusTime,startTime,partProbability,levelNumber));
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File not found, make sure the level.txt file is in the right directory");
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
	 * The types of levels.
	 */
	private LevelType[] $levelTypes = new LevelType[0]; 
	
	/*
	 * A variable (LevelCatalog) for holding a reference 
	 * to this levelCatalog.
	 */
	private final static LevelCatalog LEVELCATALOG = new LevelCatalog();
}