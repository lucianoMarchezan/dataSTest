package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
  * A class to represent the raceinformation-window.
  */
class RaceInformation extends Dialog implements ActionListener,KeyListener,Runnable{


	// CONSTRUCTORS	
	
	/**
	  * A new raceInformation with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this raceInformation.
	  * @param dModal: The dModal of this raceInformation.
	  */
	RaceInformation(Frame hostFrame,boolean dModal){
		super(hostFrame, "Race Information", dModal);
		
		// setting location, size, layout
		setSize(300, 600);		
  		setLocation((int)RaceController.getReference().getGUIController().getMainWindowLocation().getX()-
  		                 getWidth()+(int)RaceController.getReference().getGUIController().getMainWindowSize().getWidth(),
  					(int)RaceController.getReference().getGUIController().getMainWindowLocation().getY());
		setLayout(new GridLayout(19, 1, 20, 1));
		setResizable(false);
		
		// creating fonts and colors used
		$titlefont = new Font("SansSerif",Font.BOLD,20);
		$valuefont = new Font("SansSerif",Font.BOLD,17);
		$bgcolor = new Color(150,150,150);
		
		// creating and adding components	
		addElements();
			
		addKeyListener(this);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				RaceController.getReference().getGUIController().closeRaceView();
			}	
		});
		
		
	}
	
	
	// MUTATORS
	/*
	 * Add the elements to the dialog.
	 */
	private void addElements(){
		addScoreElements();
		
		addLevelElements();

		addTimeElements();
		
		addCheckpointElements();
				
		addGearElements();	

		addSpeedElements();	
			
		addDamageElements();
		
		$exit = new Button("Exit");		
		$exit.addActionListener(this);
		$exit.setFont($valuefont);	
		add($exit);	
	}
	
	/*
	 * Add the elements to the dialog concerning score.
	 */
	private void addScoreElements(){
		Label $score = new Label("Score");
		$score.setBackground($bgcolor);
		$score.setFont($titlefont);	
		add($score);
		
		$scoreLabel = new Label("");
		$scoreLabel.setFont($valuefont);	
		add($scoreLabel);
	}
	
	
	/*
	 * Add the elements to the dialog concerning level.
	 */
	private void addLevelElements(){
		Label level = new Label("Level");
		level.setBackground($bgcolor);
		level.setFont($titlefont);	
		add(level);
		
		$levelLabel = new Label("");
		$levelLabel.setFont($valuefont);	
		add($levelLabel);
	}
	
	
	/*
	 * Add the elements to the dialog concerning the time left.
	 */
	private void addTimeElements(){
		Label time = new Label("Time");
		time.setBackground($bgcolor);
		time.setFont($titlefont);	
		add(time);
		
		$timeLabel = new Label("");
		$timeLabel.setFont($valuefont);	
		add($timeLabel);
	}
	
	/*
	 * Add the elements to the dialog concerning checkpoint.
	 */
	private void addCheckpointElements(){
		Label $checkpoint = new Label("Checkpoint");
		add($checkpoint);
		$checkpoint.setBackground($bgcolor);
		$checkpoint.setFont($titlefont);
		
		$distanceToCheckPointLabel = new Label("");
		$distanceToCheckPointLabel.setFont($valuefont);
		add($distanceToCheckPointLabel);
	}
	
	
	/*
	 * Add the elements to the dialog concerning gear.
	 */
	private void addGearElements(){
		Label gear = new Label("Gear");
		gear.setBackground($bgcolor);
		gear.setFont($titlefont);	
		add(gear);
		
		$gearLabel = new Label("");
		$gearLabel.setFont($valuefont);	
		add($gearLabel);
	}
	
	/*
	 * Add the elements to the dialog concerning speed.
	 */
	private void addSpeedElements(){
		Label speed = new Label("Speed");
		speed.setBackground($bgcolor);
		speed.setFont($titlefont);	
		add(speed);
		
		$speedLabel = new Label("");
		$speedLabel.setFont($valuefont);	
		add($speedLabel);
	}
	
	
	/*
	 * Add the elements to the dialog concerning damage.
	 */
	private void addDamageElements(){
		Label damage = new Label("Damage of Tires");
		damage.setBackground($bgcolor);
		damage.setFont($titlefont);
		add(damage);
				
		$damageBarTires = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		$damageBarTires.setStringPainted(true);
		$damageBarTires.setFont($valuefont);
		add($damageBarTires);

		damage = new Label("Damage of Engine");
		damage.setBackground($bgcolor);
		damage.setFont($titlefont);
		add(damage);
				
		$damageBarEngine = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		$damageBarEngine.setStringPainted(true);
		$damageBarEngine.setFont($valuefont);
		add($damageBarEngine);

		damage = new Label("Damage of Body");
		damage.setBackground($bgcolor);
		damage.setFont($titlefont);
		add(damage);
				
		$damageBarBody = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		$damageBarBody.setStringPainted(true);
		$damageBarBody.setFont($valuefont);
		add($damageBarBody);
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */	
	public void actionPerformed(ActionEvent e){			
		if(e.getSource() == $exit){
	    	setVisible(false);
			RaceController.getReference().endRace();
	    }
	}
	
	/**
	  * Invoked when an keyEvent occurs.
	  *
	  * @param e: The keyEvent that has occured.
	  */
	public void keyTyped(KeyEvent e){ 
	}
	
	/**
	  * Look what key is pressed and take the wright action.
	  *
	  * @param e: The keyEvent that has occured.
	  */	
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getAccelerateKey())
			RaceController.getReference().accelerate();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getDecelerateKey())
			RaceController.getReference().decelerate();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getLeftKey())
			RaceController.getReference().steerLeft();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getRightKey())
			RaceController.getReference().steerRight();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getGearDownKey())
			RaceController.getReference().gearDown();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getGearUpKey())
			RaceController.getReference().gearUp();
	}
		
	/**
	  * The pressed key is released.
	  *
	  * @param e: The keyEvent that has occured.
	  */
	public void keyReleased(KeyEvent e) { 
	}
	    
	/**
	  * Run the raceInformation.
	  */
    public void run(){
    	try{
	    	while(isVisible()){	    		
		    	Thread.currentThread().sleep(40);
		    	if(!RaceController.getReference().getGUIController().messageDialogVisible()) update();
		    }
		}
		catch(InterruptedException e){
		}
	}
    
	/**
	  * Updates the race information appropriately.
	  */
	public void update(){
		double speed = Math.floor(((RaceController.getReference().getRace().getCar().getDistanceToCheckPoint())/1000.0)*10)/10;		
		$distanceToCheckPointLabel.setText(""+speed+" km");
		$speedLabel.setText(""+RaceController.getReference().getRace().getCar().getSpeedString()+" km/h");
		$levelLabel.setText(""+RaceController.getReference().getRace().getRoads()[RaceController.getReference().getRace().getCurrentRoad()].getLevel().getLevelType().getLevelNumber());		
		$gearLabel.setText(""+RaceController.getReference().getRace().getCar().getGearbox().getCurrentGear().getNumber());
		$timeLabel.setText(""+RaceController.getReference().getRace().getTimeString());
		$damageBarEngine.setValue((int)(RaceController.getReference().getRace().getCar().getEngine().getDamage()*100));
		$damageBarBody.setValue((int)(RaceController.getReference().getRace().getCar().getBody().getDamage()*100));
		$damageBarTires.setValue((int)(RaceController.getReference().getRace().getCar().getTireset().getDamage()*100));
		$scoreLabel.setText(""+RaceController.getReference().getRace().getPlayer().getScore());
		double percentEngine = Math.floor(1000*RaceController.getReference().getRace().getCar().getEngine().getDamage())/10;
		double percentBody = Math.floor(1000*RaceController.getReference().getRace().getCar().getBody().getDamage())/10;
		double percentTires = Math.floor(1000*RaceController.getReference().getRace().getCar().getTireset().getDamage())/10;
		$damageBarEngine.setString(""+percentEngine+"%");
		$damageBarBody.setString(""+percentBody+"%");
		$damageBarTires.setString(""+percentTires+"%");
	}
	
	
	// VARIABLES
	
	/*
	 * A variable (Label) for showing the distancToCheckPoint.
	 */	
	private Label $distanceToCheckPointLabel;
	
	/*
	 * A variable (Label) for showing the time left.
	 */	
	private Label $timeLabel;
	
	/*
	 * A variable (Label) for showing the gear of the car.
	 */	
	private Label $gearLabel;
	
	/*
	 * A variable (Label) for showing the speed of the car.
	 */	
	private Label $speedLabel;
	
	/*
	 * A variable (Label) for showing the level the car is racing in..
	 */	
	private Label $levelLabel;
	
	/*
	 * A variable (Label) for showing the score of the player.
	 */	
	private Label $scoreLabel;
	
	/*
	 * A variable (JProgressBar) for showing the damage of the engine.
	 */	
	JProgressBar $damageBarEngine;

	/*
	 * A variable (JProgressBar) for showing the damage of the tires.
	 */	
	JProgressBar $damageBarTires;

	/*
	 * A variable (JProgressBar) for showing the damage of the body.
	 */	
	JProgressBar $damageBarBody;
	
	/*
	 * A variable (Button) for ending the race.
	 */	
	Button $exit;
	
	/*
	 * A variable (Font) for the FontType in titles.
	 */
	Font $titlefont;
	
	/*
	 * A variable (Font) for the FontType in values.
	 */
	Font $valuefont;
	
	/*
	 *  variable (Color) for the background color.
	 */
	Color $bgcolor;
}