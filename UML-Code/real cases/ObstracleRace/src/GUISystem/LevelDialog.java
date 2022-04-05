package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;

/**
  * A dialog class for querying the user for his/her preferred startlevel.
  */
class LevelDialog extends Dialog implements ActionListener{
	
	
	// CONSTRUCTOR
  
	/**
	  * A new levelDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this levelDialog.
	  * @param dModal: The dModal of this levelDialog.
	  */
  	LevelDialog(Frame hostFrame, boolean dModal){
    	super(hostFrame, "Enter Level", dModal);

		// seting size, location and layout
		setSize(200, 200);	
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
		            Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new GridLayout(4,1, 20, 20));
		setResizable(false);
				
		// creating and adding components
		add(new Label("Enter level to start game (1-" + LevelCatalog.getReference().getLevelTypes()[LevelCatalog.getReference().getLevelTypes().length - 1].getLevelNumber() + ")"));
		
		$level = new TextField("1");
		add($level);
		
		$ok = new Button("Ok");
		$ok.addActionListener(this);
		add($ok);
		
		$cancel = new Button("Cancel");
		$cancel.addActionListener(this);
		add($cancel);
		
		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				$level.setText("0");	
      			setVisible(false);
			}
		});
	}
	
	
	// INSPECTOR
	
	/**
	  * Returns the level entered in the textfield.
	  *
	  * @return The level entered by the player. 
	  */
	int getLevel(){
		return (Integer.valueOf($level.getText())).intValue();
	}
	
	
	// MUTATOR
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == $ok){
			String stringLevel =  $level.getText();
			boolean test = true;
			for(int i = 0; i < stringLevel.length(); i++){
				if (stringLevel.charAt(i) != '0' &&
				stringLevel.charAt(i) != '1' &&
				stringLevel.charAt(i) != '2' &&
				stringLevel.charAt(i) != '3' &&
				stringLevel.charAt(i) != '4' &&
				stringLevel.charAt(i) != '5' &&
				stringLevel.charAt(i) != '6' &&
				stringLevel.charAt(i) != '7' &&
				stringLevel.charAt(i) != '8' &&
				stringLevel.charAt(i) != '9' ){
					test = false;
				}
			}
		
			if (test && 
			   (Integer.valueOf(stringLevel)).intValue()>0 &&
			   (Integer.valueOf(stringLevel)).intValue()<=LevelCatalog.getReference().getLevelTypes()[LevelCatalog.getReference().getLevelTypes().length-1].getLevelNumber()){
				setVisible(false);
			}
			else{
				$level.setForeground(new Color(1.0f, 1.0f, 1.0f));
				$level.setText("Enter a valid level!!");
				$level.setForeground(new Color(0.5f, 0.5f, 0.5f));
			}
		}
		if(e.getSource() == $cancel){
			$level.setText("0");	
		  	setVisible(false);
		}
	}
  
  
	// VARIABLES
	  
	/*
	 * An ok-button to submit the specified levelnumber.
	 */
	private Button $ok;
	
	/*
	 * A cancel-button for aborting the levelDialog.
	 */
	private Button $cancel;
	
	/*
	 * A TextField for entering the desired information.
	 */
	private TextField $level;
}