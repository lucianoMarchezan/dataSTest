package GUISystem;

import java.awt.*;
import java.awt.event.*;

/**
  * A dialog class for quering the user for his/her name.
  */
class NameDialog extends Dialog implements ActionListener{


	// CONSTRUCTOR
	
	/**
	  * A new nameDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this nameDialog.
	  * @param dModal: The dModal of this nameDialog.
	  */
	NameDialog(Frame hostFrame, boolean dModal){
		super(hostFrame, "Enter Name", dModal);
		
		// setting size, location and layout
		setSize(200, 200);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new GridLayout(4, 1, 20, 20));
		setResizable(false);
		
    	// add labels
		addElements();
				
		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				$name.setText("");
	    		setVisible(false);
			}
		});
	}
	
	
	// INSPECTOR
	
	/**
	  * Returns the name entered in the text-field. 
	  *
	  * @return The name entered in the text-field. 
	  *         If the cancel or close button was pressed, an empty string will be returned.
	  */
	public String getName(){
		return $name.getText();  	
	}

	
	// MUTATORS
	/*
	 * Add the necessary labels to this dialog.
	 */
	 private void addElements(){
	 	
		add(new Label("Enter your name"));

		$name = new TextField("Jakke");
		add($name);

		$ok = new Button("Ok");
		$ok.addActionListener(this);
		add($ok);

	    $cancel = new Button("Cancel");
	    $cancel.addActionListener(this);
	    add($cancel);
	}
	  
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == $ok){			
			if(($name.getText().equals("")) ||
			   ($name.getText().equals("Enter a valid name!!"))){
					$name.setForeground(new Color(1.0f, 1.0f, 1.0f));
					$name.setText("Enter a valid name!!");
					$name.setForeground(new Color(0.5f, 0.5f, 0.5f));
			}
			else{
				setVisible(false);
			}		
		}
		
		if(e.getSource() == $cancel){
			$name.setText("");
			setVisible(false);
		}
	}


	// VARIABLES
	
	/*
	 * Ok-button for submit.
	 */
	private Button $ok;
	
	/* 
	 * Cancel-button for closing (aborting) the nameDialog.
	 */
	private Button $cancel;
  
	/*
	 * TextField for entering the name.
	 */
	private TextField $name;
}