package GameSystem;

import GUISystem.*;
import java.awt.*;
import java.io.*;

/**
  * A class RaceController.
  */
public class RaceController implements Serializable{
	
	/**
	  * Main method
	  */
	public static void main(String args[]){		
		RaceController.getReference().getGameIO().initKeyboard();
		RaceController.getReference().getGameIO().initGearbox();
		RaceController.getReference().getGameIO().initHighScores();
		// view main menu
		while (true)
			getReference().getGUIController().showMainMenu();
	}
	
	
	// CONSTRUCTORS 
	
	/**
	  * A new raceController is created.
	  */
	private RaceController(){		
		$HIGHSCORES = new HighScores();
		$KEYBOARD = new Keyboard();
		$GAMEIO = new GameIO();  
		// create GUIController		
	    setGUIController(new GUIController());
	}
	
	
	// INSPECTORS 
	
	/**
	  * Return the reference to the unique instance of RaceController.
	  *
	  * @return: The reference to the unique instance of RaceController.
	  */	  
	public static RaceController getReference(){
		return RACECONTROLLER;
	}
	
	/**
	  * Returns the race controlled by this RaceController.
	  *
	  * @return: The race controlled by this RaceController.
	  */
	public Race getRace(){
		return $race;
	}
	
	/**
	  * Returns the soundplayer used by this RaceController
	  *	
	  * @return The soundplayer used by this RaceController
	  */
	public SoundPlayer getSoundPlayer(){
		return $soundPlayer;
	}
	
	/**
	  * Return the guiController used by this RaceController.
	  *
	  * @return: The guiController used by this RaceController.
	  */
	public GUIController getGUIController(){
		return $guiController;
	}

	/**
	  * Return the keyboard used by this RaceController.
	  *
	  * @return: The keyboard used by this RaceController.
	  */
	public Keyboard getKeyboard(){
		return $KEYBOARD;
	}

	/**
	  * Return the highScores used by this RaceController.
	  *
	  * @return: The highScores used by this RaceController.
	  */
	public HighScores getHighScores(){
		return $HIGHSCORES;
	}

	/**
	  * Return the gameIO used by this RaceController.
	  *
	  * @return: The gameIO used by this RaceController.
	  */
	public GameIO getGameIO(){
		return $GAMEIO;
	}
	
	/**
	  * Return the defaultGearbox used attached to this RaceController.
	  *
	  * @return: The defaultGearbox attached to this RaceController.
	  */
	public Gearbox getDefaultGearbox(){
		return $defaultGearbox;
	}

	// MUTATORS
			
	/**
	  * Start a new Race, ask the player for the right information and start it.
	  */
	public void startNewRace(){
		String name = getGUIController().getName();
		if (!name.equals(""))
		{
			Player player = new Player(name);
			int level = getGUIController().getLevel();
			if(level!=0){	
				// create race
				RaceBuilder raceBuilder = new RaceBuilder();
				raceBuilder.buildRace(player, level);
				$race = raceBuilder.getRace();
				getDefaultGearbox().reset();
				// go to shop
				getGUIController().openShop();		
				// create GUI
				getGUIController().showRaceInformation();	
				getGUIController().showRaceView();
				// set time
				getRace().resetTime();
				// init roadobjectmover		
				$roadObjectMover = new RoadObjectMover();		
	    		Thread thread = new Thread($roadObjectMover);
	    		thread.start();
	    		// create soundplayer
	    		$soundPlayer = new SoundPlayer();
	    		
				while(getGUIController().raceViewVisible()){								
					// sleep for a while
					sleep();

					// adjust gears
					getRace().getCar().getGearbox().check();

					// wear down tires
					getRace().getCar().wearDownTires();
					
					// move car
					getRace().getCar().setDistanceToCheckPoint(getRace().getCar().getDistanceToCheckPoint()-5);					
					// checkpoint reached					
					if(getRace().getCar().getDistanceToCheckPoint()==0)
						reachCheckpoint();
					
					// Additional tasks if race hasn't ended yet
					if(getGUIController().raceViewVisible()){
						// collision detection
						detectCollision();
						// check the time left
						checkTime();						
						// check speed-limit
						checkSpeedlimit();					
					}
				}
			}
		}
	}
	
	/**
	  * Accelerate Car.
	  */
	public void accelerate(){
		getRace().getCar().accelerate();
	}
	
	/**
	  * Decelerate Car.
	  */
	public void decelerate(){
		getRace().getCar().decelerate();
	}
	
	/**
	  * Steer car left.
	  */
	public void steerLeft(){
		getRace().getCar().steerLeft();
	}
	
	/**
	  * Steer car right.
	  */
	public void steerRight(){	
		getRace().getCar().steerRight();
	}

	/**
	  * Change the gear of the car down.
	  */
	public void gearDown(){	
		getRace().getCar().gearDown();
	}

	/**
	  * Change the gear of the car up.
	  */
	public void gearUp(){
		getRace().getCar().gearUp();
	}
	
	/**
	  * The race has ended because of a crash or timeout..
	  *
	  * @param message telling where the player failed.
	  */
	public void gameOver(String message){
		getGUIController().showMessageDialog(message);
		getHighScores().addPlayer(getRace().getPlayer());
		showHighScores();
		getGUIController().closeRaceInformation();
		getGUIController().closeRaceView(); 
	}
	
	/**
	  * The race has ended because the player clicked "Exit"
	  */
	public void endRace(){		
		getGUIController().closeRaceView();
		getGUIController().closeRaceInformation();		
	}
	  
	  
	/**
	  * Win the race.
	  */
	public void winRace(){
		getGUIController().showWinRaceDialog();
		getHighScores().addPlayer(getRace().getPlayer());
		showHighScores();
		getGUIController().closeRaceInformation();
		getGUIController().closeRaceView(); 
	}
	
	/**
	  * Let the user define his/her keys by asking them through a dialog and setting them.
	  */
	public void defineKeyboard(){	
		char[] keys = getGUIController().getKeys();
		if (keys!=null){	
			getKeyboard().setAccelerateKey(keys[0]);
			getKeyboard().setDecelerateKey(keys[1]);
			getKeyboard().setLeftKey(keys[2]);
			getKeyboard().setRightKey(keys[3]);
			getKeyboard().setGearDownKey(keys[4]);
			getKeyboard().setGearUpKey(keys[5]);			
		}
	}

	/**
	  * Let the user define his/her gearbox by asking through a optionbox.
	  */
	public void defineGearbox(){
		String gearbox = getGUIController().getGearbox();
		if(!gearbox.equals($defaultGearbox.getName())){
			GearboxBuilder gearboxBuilder = new GearboxBuilder();
			if(gearbox.equals("Automatic")){
				gearboxBuilder.buildAutomaticGearbox();
				setDefaultGearbox(gearboxBuilder.getGearbox());
			}
			else{
				gearboxBuilder.buildManualGearbox();
				setDefaultGearbox(gearboxBuilder.getGearbox());
			}
		}
	}
	
	/**
	  * Show the highscores to the user.
	  */
	public void showHighScores(){
		getGUIController().showHighScores();
	}
	
	/**
	  * End the game.
	  */
	public void endGame(){
		getGameIO().writeKeyboard();
		getGameIO().writeGearbox();
		getGameIO().writeHighScores();
		System.exit(0);
	}
	
	/**
	  * sleep for a while 
	  */
	private void sleep()
	{
	  try{
	  	 if(getRace().getCar().getSpeed()!=0){
	  		int wait = (int)(1000.0/(getRace().getCar().getSpeed()));
	  		Thread.currentThread().sleep(wait);
	  	 }
	  	 else{
	  	 	while(getRace().getCar().getSpeed()==0 && getGUIController().raceViewVisible()){
	  	 	}
	  	 }
	  }
	  catch(InterruptedException e){
	  }
	}
	
	/**
	  * performs the necessary actions when a checkpoint is reached
	  */
	private void reachCheckpoint(){
		getRace().getPlayer().setScore(getRace().getPlayer().getScore()+getRace().getRoads()[getRace().getCurrentRoad()].getLevel().getLevelType().getLevelNumber()*100);
		getRace().getPlayer().changeMoney((int)getRace().getTime() * getRace().getRoads()[getRace().getCurrentRoad()].getLevel().getLevelType().getLevelNumber());
		getGUIController().openShop();
		// normal checkpoint
		if((getRace().getRoads().length-1>getRace().getCurrentRoad())){
			getRace().setCurrentRoad(getRace().getCurrentRoad()+1);
			getRace().getCar().setDistanceToCheckPoint(RoadPart.LENGTH*getRace().getRoads()[getRace().getCurrentRoad()].getLevel().getLevelType().getNumberOfParts());
		}
		// final checkpoint
		else{
			winRace();
		}	
		getRace().resetTime();
		getRace().getCar().setSpeed(0);
		getRace().getCar().getGearbox().reset();

		// create GUI
 		getGUIController().showRaceInformation();	
 		getGUIController().showRaceView();
								 
	}
	
	/**
	  * detects collision and performs the necessary actions when a collision is detected
	  */
	private void detectCollision(){		
		RoadObject[] roadobjects = getRace().getCurrentRoadPart().getRoadObjects();
		for(int i=0;i<roadobjects.length;i++){	
		    if(roadobjects[i].checkCollision(getRace().getCar()))
		    	roadobjects[i].collideWith(getRace().getCar());
		}
		if(getRace().getCar().isCrashed()){
			getSoundPlayer().playCrash();
			gameOver("Towed to pit, you've crashed the car!");
		}
	}

	/**
	  *	checks the time left to finish the race and performs the necessary actions when the time is up
	  */
	private void checkTime(){
		if(getRace().getTime()==0){
			gameOver("Towed to pit, you've run out of time!");
		}
	}
	
	
	/**
	  * checks whether the speed has exceed the limit on this roadpart and 
	  * changes the speed appropriately when necessary
	  */
	private void checkSpeedlimit(){
		if(getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH==0 && 
		getRace().getRoads().length>getRace().getCurrentRoad() &&
		getGUIController().raceViewVisible()){
			int totalRoadLength = getRace().getRoads()[getRace().getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
			int done = totalRoadLength - getRace().getCar().getDistanceToCheckPoint();
			int partindex = done / RoadPart.LENGTH;
			getRace().getCar().setSpeed(Math.min(getRace().getCar().getSpeed(),getRace().getRoads()[getRace().getCurrentRoad()].getRoadParts()[partindex].getRoadPartType().getMaximumSpeed()));
		}
	}
	
	/**
	  * Set the guiController of raceController to the given guiController.
	  *
	  * @param guiController: The GUIController that will be used to interact with the user.
	  */
	private void setGUIController(GUIController guiController){
		$guiController = guiController;
	}
	
	/**
	  * Set the given gearbox as the defaultGearbox of this raceController.
	  *
	  *	@param gearbox: The gearbox that will be attached to this raceController. 
	  */
	void setDefaultGearbox(Gearbox gearbox){
		$defaultGearbox = gearbox;
	}
	
	/**
	  * Buy a part of given partType.
	  *
	  * @param partType: The partType of which the player want to buy a part of.
	  */
	public void buyPart(PartType partType){
		Part part = getRace().getShop().getPart(partType);
		getRace().getShop().sellPart(part, getRace().getCar());	
		getRace().getPlayer().changeMoney(-partType.getPrice());	
	}

	/**
	  * Repair given part
	  *
	  * @param part: The partType of which the player want to buy a part of.
	  * @param percentage: The percentage with which the part has to be repaired.
	  */
	public void repairPart(Part part, int percentage){
		getRace().getCar().repairPart(part,percentage/100.0);
		getRace().getPlayer().changeMoney((int)(-part.getPartType().getRepairPriceIndex()*percentage));	
	}

	
	
	// VARIABLES 

	/*
	 * A reference to the GUIController attached to this gameController.
	 */
	private GUIController $guiController;
	
	/*
	 * A reference to the race controlled by this racecontroller.
	 */
	private Race $race;  
	
	/*
	 * A reference to the unique instance of RaceController.
	 */
	private static RaceController RACECONTROLLER = new RaceController();

	/*
	 * A reference to the gameIO attached to this raceController. 
	 */
	private final GameIO $GAMEIO;

	/*
	 * A reference to the roadObjectMover attached to this raceController
	 */
	private RoadObjectMover $roadObjectMover;
	 
	/*
	 * A reference to the keyboard attached to this raceController. 
	 */
	private final Keyboard $KEYBOARD;

	/*
	 * A reference to the highScores attached to this raceController. 
	 */
	private final HighScores $HIGHSCORES;
	
	/*
	 * A reference to the defaultGearbox attached to this raceController. 
	 */
	private Gearbox $defaultGearbox;
		
	/*
	 * A reference to the soundPlayer attached to this raceController. 
	 */
	private SoundPlayer $soundPlayer;
}
