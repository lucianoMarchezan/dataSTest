package GUISystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
  * A dialog class for showing the player a message.
  */
  
class MessageDialog extends Dialog implements ActionListener{
  
	
	// CONSTRUCTOR

	/**
	  * A new messageDialog with given hostFrame, dModal and message is created. 
	  *
	  * @param hostFrame: The hostFrame of this gameOverDialog.
	  * @param dModal: The dModal of this gameOverDialog.
	  * @param message: The message of this gameOverDialog
	  */
	MessageDialog(Frame hostFrame, boolean dModal,String message){
    	// creating dailog: owner Frame is set, setting title, modality, size, position, layout
		super(hostFrame, "Message", dModal);
		setSize(400, 150);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
	                Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setResizable(false);
	
	    // creating and adding components
		Font titlefont = new Font("SansSerif",Font.BOLD,20);
	    Font valuefont = new Font("SansSerif",Font.BOLD,15);
		
		add(Box.createVerticalGlue());
	    Label comment = new Label(message);
	    comment.setFont(titlefont);
	    add(comment);
	    add(Box.createVerticalGlue());
	    
	    $ok = new Button("Ok");
	    $ok.setFont(valuefont);
	    $ok.addActionListener(this);
	    add($ok);
	    add(Box.createVerticalGlue());
	
		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
	
	
	// MUTATOR
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == $ok)
      		setVisible(false);
    }
	
	
	// VARIABLE
	
	/*
	 * An ok-button.
	 */
	private Button $ok;
}
