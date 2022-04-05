package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for shwoing the player his/she has won the race.
  */
class WinRaceDialog extends Dialog implements ActionListener{
	
	
	// CONSTRUCTOR
	
	/**
	  * A new winRaceDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this winRaceDialog.
	  * @param dModal: The dModal of this winRaceDialog.
	  */
	WinRaceDialog(Frame hostFrame, boolean dModal){
		//creating dialog: host, size, layout
		super(hostFrame, "Win Race", dModal);
		setSize(250, 150);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
    	            Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new GridLayout(3, 1, 20, 5));
		setResizable(false);
		
    	//creating the labels fot showing score and name.	
		Font titlefont = new Font("SansSerif",Font.BOLD,20);
    	Font valuefont = new Font("SansSerif",Font.BOLD,15);

    	Label comment = new Label("You've won the race!");
    	comment.setFont(titlefont);
    	add(comment);
    	
    	Label score = new Label(RaceController.getReference().getRace().getPlayer().getName()+" - "+RaceController.getReference().getRace().getPlayer().getScore());
    	score.setFont(valuefont);
    	add(score);

    	//creating and adding okbutton
    	$ok = new Button("Ok");
    	$ok.setFont(valuefont);
    	$ok.addActionListener(this);
    	add($ok);

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
	  * @param e: The actionEvent that is occured.
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
