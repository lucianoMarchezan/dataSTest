package GUISystem;

import GameSystem.*;
import GUISystem.Shop.*;
import javax.swing.*;
import java.awt.*;

/**
  * Class for controlling all GUI-activity in the game.
  * Class originated out off the idea for separating game-control and
  * controlling visualisation of the game.
  */
public class GUIController{
  

	// CONSTRUCTORS
   
	/**
	  * A new guiController is created.
	  */
	public GUIController(){
		showMainWindow();
		showSplash();
	}
	
	
	// INSPECTORS
	
	/**
      * Return the location of the main window.
      *
      * @return The location of the main window.
      */
	Point getMainWindowLocation(){
    	return $frame.getLocation();
	}

	/**
      * Return the size of the main window.
      *
      * @return The size of the main window
      */
	Dimension getMainWindowSize(){
    	return $frame.getSize();
	}
  
	/**
      * Return a boolean value indicated whether the raceview window is visible.
      *
      * @return True  if the race view window is visible
      *         False if the race view window is hidden
      */
	public boolean raceViewVisible(){
    	return $raceInformation.isVisible();
	}
	
	/**
      * Return a boolean value indicated whether the message dialog is visible.
      *
      * @return a boolean indicating whether the message dialog is visible.
      */
	public boolean messageDialogVisible(){
		if($messageDialog==null)
			return false;
		else
    		return $messageDialog.isVisible();
	}
		
	// MUTATORS
	
	/**
	  * Method for asking the player's name.
	  *
	  * @return The name the player has given in (players name).
	  */
	public String getName(){
    	NameDialog nameDialog = new NameDialog($frame, true);
    	nameDialog.setVisible(true);
		return nameDialog.getName();
	}
	
	/**
      * Method for asking the startLevel.
      *
	  * @return The level the player wants to start in.
	  */
  	public int getLevel(){  	
		LevelDialog levelDialog = new LevelDialog($frame, true);   
		levelDialog.setVisible(true);
		return levelDialog.getLevel();
	} 

	/**
      * Show a window with which the player can define the key-bindings
      *
      * @return The keys entered by the user
      */
	public char[] getKeys(){
		KeyDialog keyDialog = new KeyDialog($frame, true);
		keyDialog.setVisible(true);
		return keyDialog.getKeys();
	}

	/**
      * Show a window with which the player can define the gearbox
      *
      * @return The name of the gearbox defined by the player
      */
	public String getGearbox(){
		GearboxDialog gearboxDialog = new GearboxDialog($frame, true);
		gearboxDialog.setVisible(true);
		return gearboxDialog.getGearbox();
	}
	
	/**
	  * Show the mainDialog to the user and catches his choice and
	  * calls a method depending on the choice of the user.
	  *
	  */
	public void showMainMenu(){  	
    	MainDialog mainDialog = new MainDialog($frame, true);
		mainDialog.setVisible(true);
		switch (mainDialog.getChoice()){
			case 1:
				RaceController.getReference().startNewRace();
				break;
			case 2:
				RaceController.getReference().defineKeyboard();
				break;
			case 3:
				RaceController.getReference().defineGearbox();
				break;
			case 4:
				RaceController.getReference().showHighScores();
				break;
			case 5:
				RaceController.getReference().endGame();
				break;
		}
	}
	
	/**
	  * Show the shopDialog to the user and catches his choice and
	  * calls a method depending on the choice of the user.
	  *
	  */
	private void showShopMenu(){  	
    	ShopDialog shopDialog = new ShopDialog($frame, true);
		shopDialog.setVisible(true);
		switch (shopDialog.getChoice()){
			case 1:
				showBuyPartsMenu();
				break;
			case 2:
				if(RaceController.getReference().getRace().getCar().hasAllParts())
					showRepairPartsMenu();
				else{
					showMessageDialog("You must first buy all parts.");
					showShopMenu();
				}
				break;
			case 3:
				if(RaceController.getReference().getRace().getCar().hasAllParts())
					closeShop();
				else{
					showMessageDialog("You must first buy all parts.");
					showShopMenu();
				}
				break;
		}
	}
	
	/**
	  * Show the buyPartsDialog to the user and catches his choice and
	  * calls a method depending on the choice of the user.
	  *
	  */
	private void showBuyPartsMenu(){
   		BuyPartsDialog buyPartsDialog = new BuyPartsDialog($frame,true);
    	buyPartsDialog.setVisible(true);
    	switch (buyPartsDialog.getChoice()){
			case 1:
				showBuyDialog(EngineCatalog.getReference().getEngineTypes());
				break;
			case 2:
				showBuyDialog(BodyCatalog.getReference().getBodyTypes());
				break;
			case 3:
				showBuyDialog(TiresetCatalog.getReference().getTiresetTypes());
				break;
			case 4:
				showShopMenu();
				break;
			case 5:
				if(RaceController.getReference().getRace().getCar().hasAllParts())
					closeShop();
				else{
					showMessageDialog("You must first buy all parts.");
					showBuyPartsMenu();
				}
				break;
		}
  	}
  	
  	/**
	  * Show the repairPartsDialog to the user and catches his choice and
	  * calls a method depending on the choice of the user and the damage
	  * of the selected part.
	  */
	private void showRepairPartsMenu(){
   		RepairPartsDialog repairPartsDialog = new RepairPartsDialog($frame,true);
    	repairPartsDialog.setVisible(true);
    	switch (repairPartsDialog.getChoice()){
			case 1:
				if(RaceController.getReference().getRace().getCar().getEngine().getDamage()!=0)
					showRepairDialog(RaceController.getReference().getRace().getCar().getEngine());
				else{
					showMessageDialog("Your "+ RaceController.getReference().getRace().getCar().getEngine().getPartType().getName()+" is not damaged.");
					showRepairPartsMenu();
				}
				break;
			case 2:
				if(RaceController.getReference().getRace().getCar().getBody().getDamage()!=0)
					showRepairDialog(RaceController.getReference().getRace().getCar().getBody());
				else{
					showMessageDialog("Your "+ RaceController.getReference().getRace().getCar().getBody().getPartType().getName()+" is not damaged.");
					showRepairPartsMenu();
				}
				break;
			case 3:
				if(RaceController.getReference().getRace().getCar().getTireset().getDamage()!=0)
					showRepairDialog(RaceController.getReference().getRace().getCar().getTireset());
				else{
					showMessageDialog("Your "+ RaceController.getReference().getRace().getCar().getTireset().getPartType().getName()+" is not damaged.");
					showRepairPartsMenu();
				}
				break;
			case 4:
				showShopMenu();
				break;
			case 5:
				closeShop();
				break;
		}
  	}
	
	/**
      * Show the raceInformation window.
      */
	public void showRaceInformation(){
  		$raceInformation = new RaceInformation($frame,false);
		Thread thread = new Thread($raceInformation);
		$raceInformation.setVisible(true); 
		thread.start();
	}
  
	/**
	  * Close the raceInformation window.
	  */
	public void closeRaceInformation(){
    	$raceInformation.setVisible(false);
	}
  
	/**
      * Show the raceView window.
      */
	public void showRaceView(){
  		$raceView = new RaceView($frame,false);
  		Thread thread = new Thread($raceView);
  		$raceView.setVisible(true);  
  		thread.start();
	}

	/**
	  * Close the raceView window.
	  */
	public void closeRaceView(){  
  		$raceView.setVisible(false);
	}

	/**
      * Display the highscores. 
      */
	public void showHighScores(){
    	HighScoresDialog highScoresDialog = new HighScoresDialog($frame, true);
    	highScoresDialog.setVisible(true);
    	while(highScoresDialog.isVisible()) {}
	}
  
	/**
      * Show a window informing the player he has won the race.
      */  
	public void showWinRaceDialog(){
  		WinRaceDialog winRaceDialog = new WinRaceDialog($frame,true);
    	winRaceDialog.setVisible(true);
  	}
  	
  	/**
      * Show a dialog with the given types of a category selected by the user
      * for buying purposes.
	  *
	  * @param partTypes: The partTypes of a category.
	  */  
	private void showBuyDialog(PartType [] partTypes){
  		BuyDialog buyDialog = new BuyDialog($frame,true,partTypes);
    	buyDialog.setVisible(true);
    	switch (buyDialog.getChoice()){
			case 1:
				RaceController.getReference().buyPart(buyDialog.getPartType());
				showBuyPartsMenu();
				break;
			case 2:
				showBuyPartsMenu();
				break;
			case 3:
				closeShop();
				break;
			}
  	}

	/**
      * Show a dialog for repairing the given part of the car.
	  *
	  * @param part: The part of the car that is selected by the player for repairing.
	  */  
	private void showRepairDialog(Part part){
  		RepairDialog repairDialog = new RepairDialog($frame,true,part);
    	repairDialog.setVisible(true);
    	switch (repairDialog.getChoice()){
			case 1:
				RaceController.getReference().repairPart(repairDialog.getPart(),repairDialog.getPercentage());
				showRepairPartsMenu();
				break;
			case 2:
				showRepairPartsMenu();
				break;
			case 3:
				closeShop();
				break;
			case 4:
				RaceController.getReference().buyPart(repairDialog.getPart().getPartType());
				showRepairPartsMenu();
				break;
			}
  	}
     
  	/**    
      * Show a splash screen to show at the start of the game.
      */ 
	public void showSplash(){  	
    	SplashDialog splashDialog = new SplashDialog($frame, false);
    }
  
	/**
      * Show a dialog with a message to the user.
      *
      * @param: A message with information for the user.
      */
	public void showMessageDialog(String message){
   		$messageDialog = new MessageDialog($frame,true,message);
    	$messageDialog.setVisible(true);
  	}

	/**
      * Show the main window on the screen.
      */
	private void showMainWindow(){
		$frame = new MainWindow();
		$frame.setVisible(true);
	}
	
	/**
      * Open the shop.
      */
	public void openShop(){
		$shop = new ShopWindow();
		$shop.setVisible(true);
		closeMainWindow();
		showShopMenu();
	}
	
	/**
	  * Close the mainWindow.
	  */
	private void closeMainWindow(){  
  		$frame.setVisible(false);
  	}
  	
	/**
	  * Close the shop.
	  */
	private void closeShop(){  
  		$shop.setVisible(false);
  		showMainWindow();
  	}
	

	// VARIABLES
	
	/*
	 * A variable (Frame) for the guiController and his dialogs.
	 */
	private MainWindow $frame;

	/*
	 * A variable (Frame) for the shop and his dialogs.
	 */
	private ShopWindow $shop;

	/*
	 * A variable (RaceInformation) for the raceInformation in the game.
	 */
	private RaceInformation $raceInformation;
	
	/*
	 * A variable (MessageDialog) for the messagedialogs in the game
	 */ 
	private MessageDialog $messageDialog;
	
	/*
	 * A variable (RaceView) for the raceView in the game.
	 */
	private RaceView $raceView;
}