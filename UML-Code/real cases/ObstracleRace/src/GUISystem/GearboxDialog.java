package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for querying the user for his/her preferred gearbox.
  */
class GearboxDialog extends Dialog implements ActionListener{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new gearboxDialog with given hostFrame and dModalis created. 
	  *
	  * @param hostFrame: The hostFrame of this keyDialog.
	  * @param dModal: The dModal of this keyDialog.
	  */	
	GearboxDialog(Frame hostFrame, boolean dModal){    	
		//creating dialog: host, size, layout
		super(hostFrame, "Define Gearbox", dModal);
		setSize(500, 200);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
		setLayout(new GridLayout(4,1, 20, 5));
		setResizable(false);
			
    	//add the labels and buttons to this dialog.
    	addElements();

		//Set the button of the default gearbox on.
		setDefaultGearButton();
				    			
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
			}
		});
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the name of the gear choosen by the player.
	  *
	  * @return The name of the gear choosen by the player.
	  */
	String getGearbox(){
		return $nameGearbox;
	}
	
	
	// MUTATORS
	
	/**
	  * Add the labels and buttons to this gearboxDialog
	  */
	private void addElements(){
		ButtonGroup gear =  new ButtonGroup();
		
		//creating and adding automatic-button to the dialog
		$automatic = new JRadioButtonMenuItem("Automatic Gearbox: The computer will change gears for you.");
		$automatic.addActionListener(this);
		gear.add($automatic);
		add($automatic);
		
		//creating and adding manual-button to the dialog		
		$manual = new JRadioButtonMenuItem("Manual Gearbox: You have to change gears with the predefined keys.");
		$manual.addActionListener(this);
		gear.add($manual);
		add($manual);

		//creating and adding ok-button to the dialog
		$ok = new Button("Ok");
		$ok.addActionListener(this);
		add($ok);

	    //creating and adding cancel-button
	    $cancel = new Button("Cancel");
	    $cancel.addActionListener(this);
	    add($cancel);
	}

	/**
	  * Set the button of the default gearbox on
	  */
	private void setDefaultGearButton(){
		String defaultGearbox = RaceController.getReference().getDefaultGearbox().getName();
		if(defaultGearbox.equals("Automatic")){
			$automatic.setSelected(true);
			$nameGearbox = "Automatic";
		}
		else{
			$manual.setSelected(true);
			$nameGearbox = "Manual";
		}
	}
	
	/**
	  * Set the name of the choosen gearbox.
	  *
	  * @param gearbox of the choosen gearbox.
	  */
	private void setGearbox(String gearbox){
		$nameGearbox = gearbox;
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that is occured.
	  */	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == ($ok)){
			if($automatic.isSelected())
				setGearbox("Automatic");
			else
				setGearbox("Manual");
			setVisible(false);
		}
		else if(e.getSource() == ($cancel))
			setVisible(false);
	}
	
	
	// VARIABLES
	
	/*
	 * A variable (String) for holding the name of the gearbox defined by the user.
	 */	
	private String $nameGearbox;
	
	/*
	 * Ok-button for submitting choosen values.
	 */
	private Button $ok;
	
	/*
	 * Cancel-button for (closing) aborting the gearboxDialog.
	 */
	private Button $cancel;
	
	/*
	 * Automatic-RadioButton for choosing the automatic gear.
	 */
	private JRadioButtonMenuItem $automatic;
	
    /*
	 * Manual-RadioButton for choosing the manual gear.
	 */
	private JRadioButtonMenuItem $manual;
}