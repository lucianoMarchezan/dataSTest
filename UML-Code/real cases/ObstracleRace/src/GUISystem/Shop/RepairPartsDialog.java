package GUISystem.Shop;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for querying the user what part of the car 
  *	he wants to repair.
  */
public class RepairPartsDialog extends Dialog implements ActionListener{
	
  	
  	// CONSTRUCTOR
  	
  	/**
	  * A new repairPartsDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this mainDialog.
	  * @param dModal: The dModal of this mainDialog.
	  */	
	public RepairPartsDialog(Frame hostFrame, boolean dModal){
    	super(hostFrame, "Repair Parts", dModal);
		
    	// setting size, location and layout
		setSize(200, 200);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
	  * Returns the choice made in this repairPartsDialog
	  *
	  * @return 1 if "Repair Engine"-button was clicked
	  *         2 if "Repair Body"-button was clicked
	  *			3 if "Repair Tireset"-button was clicked
	  *         4 if "Return To Shop"-button was clicked
	  *			5 if "Exit Shop"-button was clicked
	  */
	public int getChoice(){
		return $choice;
	}
	
	// MUTATORS
	/*
	 * add the components to this dialog.
	 */
	private void addElements(){
		$engines = new Button("Repair the engine of your car");
		$engines.addActionListener(this);
		add($engines);
		add(Box.createVerticalGlue());
		
		$bodies = new Button("Repair the body of your car");
		$bodies.addActionListener(this);
		add($bodies);
		add(Box.createVerticalGlue());
		
		$tires = new Button("Change the tires of your car");
		$tires.addActionListener(this);
		add($tires);
		add(Box.createVerticalGlue());
		
		$returnToShop = new Button("Return To Shop");
		$returnToShop.addActionListener(this);
		add($returnToShop);
		add(Box.createVerticalGlue());

		$exitShop = new Button("Exit Shop");
		$exitShop.addActionListener(this);
		add($exitShop);
		add(Box.createVerticalGlue());
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == $engines)
			$choice=1;
		if (e.getSource() == $bodies)
			$choice=2;
		if (e.getSource() == $tires)
			$choice=3;
		if (e.getSource() == $returnToShop)
			$choice=4;
		if (e.getSource() == $exitShop)
			$choice=5;
		setVisible(false);
	}
		
	// VARIABLES
	
	/*
	 * Engines-button. 
	 */
	private Button $engines;
	
    /* 
     * Bodies-button. 
     */
	private Button $bodies;
	
	/* 
     * Tires-button. 
     */
	private Button $tires;

	/* 
     * ReturnToShop-button. 
     */
	private Button $returnToShop;
	
	/* 
     * ExitShop-button. 
     */
	private Button $exitShop;
	
	/*
	 * A variable (int) for holding the choice of the player.
	 */	
	private int $choice = 0;
}