package GUISystem.Shop;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for main navigation in the shop of the game.
  */
public class ShopDialog extends Dialog implements ActionListener{
	
  	
  	// CONSTRUCTOR
  	
  	/**
	  * A new shopDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this mainDialog.
	  * @param dModal: The dModal of this mainDialog.
	  */	
	public ShopDialog(Frame hostFrame, boolean dModal){
    	super(hostFrame, "Welcome To The Shop", dModal);
		
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
	  * Returns the choice made in this shopDialog
	  *
	  * @return 1 if "Buy Parts"-button was clicked
	  *         2 if "Repair Parts"-button was clicked
	  *         3 if "Exit"-button was clicked
	  */
	public int getChoice(){
		return $choice;
	}
	
	// MUTATORS
	/*
	 * add the components to this shopDialog.
	 */
	private void addElements(){
		
		$buyParts = new Button("Buy New Parts");
		$buyParts.addActionListener(this);
		add($buyParts);
		add(Box.createVerticalGlue());
	
		$repairParts = new Button("Repair Parts");
		$repairParts.addActionListener(this);
		add($repairParts);
		add(Box.createVerticalGlue());

		$exit = new Button("Exit Shop");
		$exit.addActionListener(this);
		add($exit);
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == $buyParts)
			$choice = 1;
		if (e.getSource() == $repairParts)
			$choice = 2;
		if (e.getSource() == $exit)
			$choice = 3;
		setVisible(false);
	}
		
	// VARIABLES
	
	/*
	 * BuyParts-button. 
	 */
	private Button $buyParts;
	
    /* 
     * RepairParts-button. 
     */
	private Button $repairParts;

	/* 
     * Exit-button. 
     */
	private Button $exit;
	
	/*
	 * A variable (int) for holding the choice of the player.
	 */	
	private int $choice = 0;
	
	/* 
	 * Label for showing error messages
	 */
	private Label $msg;
}
