package GUISystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for main navigation through the program.
  */
class MainDialog extends Dialog implements ActionListener{
	
  	
  	// CONSTRUCTOR
  	
  	/**
	  * A new mainDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this mainDialog.
	  * @param dModal: The dModal of this mainDialog.
	  */	
	MainDialog(Frame hostFrame, boolean dModal){
    	super(hostFrame, "Main Menu", dModal);
		
    	// setting size, location and layout
		setSize(200, 200);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new GridLayout(5,1,20,10));
		setResizable(false);
		
    	// add components
		addElements();

		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
	
	
	// INSPECTOR
	
	/** 
	  * Returns the choice made in the main menu. 
	  *
	  * @return 1 if "Start New Race"-button was clicked
	  *         2 if "Define Keyboard"-button was clicked
	  *         3 if "Define Gearbox"-button was clicked
	  *			4 if "View High-Scores"-button was clicked
	  *			5 if "End Race"-button was clikced
	  */
	int getChoice(){
		return $choice;
	}
	
	
	// MUTATORS
	/*
	 * add the components to this dialog.
	 */
	private void addElements(){
		$start = new Button("Start New Race");
		$start.addActionListener(this);
		add($start);
	
		$keys = new Button("Define Keyboard");
		$keys.addActionListener(this);
		add($keys);

		$gearbox = new Button("Define Gearbox");
		$gearbox.addActionListener(this);
		add($gearbox);

		$high = new Button("View High-Scores");
		$high.addActionListener(this);
		add($high);

		$exit = new Button("End Race");
		$exit.addActionListener(this);
		add($exit);
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == $start)
			$choice=1;
		if (e.getSource() == $keys)
			$choice=2;
		if (e.getSource() == $gearbox)
			$choice=3;
		if (e.getSource() == $high)
			$choice=4;
		if (e.getSource() == $exit)
			$choice=5;
		setVisible(false);
	}
	
	
	// VARIABLES
	
	/*
	 * StartNewRace-button. 
	 */
	private Button $start;
	
    /* 
     * DefineKeys-button. 
     */
	private Button $keys;

	/* 
     * DefineGearbox-button. 
     */
	private Button $gearbox;

    
    /*
     * ViewHighScores-button. 
     */
	private Button $high;
    
    /*
     * Exit-button. 
     */
	private Button $exit;
	
	/*
	 * A variable (int) for holding the choice of the player.
	 */	
	private int $choice = 0;
}
