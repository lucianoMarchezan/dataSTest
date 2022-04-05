package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;

/**
  * A dialog class for querying the user for his/her preferred keyset.
  */
class KeyDialog extends Dialog implements ActionListener{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new keyDialog with given hostFrame, dModal and keys is created. 
	  *
	  * @param hostFrame: The hostFrame of this keyDialog.
	  * @param dModal: The dModal of this keyDialog.
	  */	
	KeyDialog(Frame hostFrame, boolean dModal){    	
		//creating dialog: host, size, layout
		super(hostFrame, "Define Keyboard", dModal);
		setSize(400, 500);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
		setLayout(new GridLayout(15,1, 20, 10));
		setResizable(false);
			
    	//add the labels and textfields to this dialog.
    	addElements();
    	
		//creating and adding ok-button to the dialog
		$ok = new Button("Ok");
		$ok.addActionListener(this);
		add($ok);

	    //creating and adding cancel-button
	    $cancel = new Button("Cancel");
	    $cancel.addActionListener(this);
	    add($cancel);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
			}
		});
	}
	
	
	// INSPECTORS
	
	/**
	  * Returns the keys entered in the dialog.
	  *
	  * @return The keys given in by the player of the game.	  
	  */
	char[] getKeys(){
		return $keys;
	}
	
	
	// MUTATORS
	
	/*
	 * Add the labels and textfields to the dialog
	 */
	private void addElements(){
		add(new Label("Accelerate"));  

		$accelerate = new TextField(""+RaceController.getReference().getKeyboard().getAccelerateKey());
		add($accelerate);

		add(new Label("Decelerate"));

		$decelerate = new TextField(""+RaceController.getReference().getKeyboard().getDecelerateKey());
		add($decelerate);

		add(new Label("Steer Left"));
		
		$steerLeft = new TextField(""+RaceController.getReference().getKeyboard().getLeftKey());
		add($steerLeft);

	    add(new Label("Steer Right"));

		$steerRight = new TextField(""+RaceController.getReference().getKeyboard().getRightKey());
    	add($steerRight);
    	
	    add(new Label("Gear Up"));

		$gearUp = new TextField(""+RaceController.getReference().getKeyboard().getGearUpKey());
    	add($gearUp);
    	
		add(new Label("Gear Down"));
		
		$gearDown = new TextField(""+RaceController.getReference().getKeyboard().getGearDownKey());
		add($gearDown);




		$msg = new Label("");
		add($msg);
	}
	
	/**
	  * Set the entered keys if correct.
	  */
	private void setKeys(){
  		//test for void keys
		if($accelerate.getText().equals("") ||
		   $decelerate.getText().equals("") ||
		   $steerLeft.getText().equals("") ||
		   $steerRight.getText().equals("") ||
		   $gearDown.getText().equals("") ||
		   $gearUp.getText().equals("")){
				$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
	 	  		$msg.setText("At least one key not set");
	 	}
    	//test for multiple chars in inputfield
	    else if($accelerate.getText().length() > 1 ||
				$decelerate.getText().length() > 1 ||
				$steerLeft.getText().length() > 1 ||
				$steerRight.getText().length() > 1 ||
				$gearDown.getText().length() > 1 ||
		   		$gearUp.getText().length() > 1){
					$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
	        		$msg.setText("Input key is only one char");
	    }
  		//test for duplicate keys
		else if($accelerate.getText().equals($decelerate.getText())||
				$accelerate.getText().equals($steerLeft.getText()) ||
				$accelerate.getText().equals($steerRight.getText()) ||
				$accelerate.getText().equals($gearDown.getText()) ||
				$accelerate.getText().equals($gearUp.getText()) ||
				$decelerate.getText().equals($steerLeft.getText()) ||
				$decelerate.getText().equals($steerRight.getText()) ||
				$decelerate.getText().equals($gearDown.getText()) ||
				$decelerate.getText().equals($gearUp.getText()) ||
				$steerLeft.getText().equals($steerRight.getText()) ||
				$steerLeft.getText().equals($gearDown.getText()) ||
				$steerLeft.getText().equals($gearUp.getText()) ||
				$steerRight.getText().equals($gearDown.getText()) ||
				$steerRight.getText().equals($gearUp.getText()) ||
				$gearDown.getText().equals($gearUp.getText())){
					$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
	        		$msg.setText("Duplicate use of key");
	    }
		else{ 
			$keys = new char[]{($accelerate.getText().charAt(0)), 
								$decelerate.getText().charAt(0),
					  			$steerLeft.getText().charAt(0), 
					  			$steerRight.getText().charAt(0),
					  			$gearDown.getText().charAt(0),
					  			$gearUp.getText().charAt(0)};	     
			setVisible(false);
		}
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that is occured.
	  */	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == ($ok))
			setKeys();
		else
			setVisible(false);
	}
	
	
	// VARIABLES
	
	/*
	 * The keys entered in the dialog by the user. 
	 */	
	private char[] $keys;
	
	/*
	 * Ok-button for submitting choosen values.
	 */
	private Button $ok;
	
	/*
	 * Cancel-button for (closing) aborting the keyDialog.
	 */
	private Button $cancel;
	
	/*
	 * TextField for entering the accelerate key.
	 */
	private TextField $accelerate;
	
	/*
	 * TextField for entering the decelerate key.
	 */
	private TextField $decelerate;
	
	/*
	 * TextField for entering the steerLeft key.
	 */ 
	private TextField $steerLeft;
	
	/*
	 * TextField for entering the steerRight key.
	 */
	private TextField $steerRight;

	/*
	 * TextField for entering the gearDown key.
	 */
	private TextField $gearDown;

	/*
	 * TextField for entering the gearUp key.
	 */
	private TextField $gearUp;
	
	/* 
	 * Label for showing error messages
	 */
	private Label $msg;
}