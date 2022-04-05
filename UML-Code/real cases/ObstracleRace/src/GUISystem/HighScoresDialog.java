package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;

/**
  * A dialog class for showing the highscores.
  */
class HighScoresDialog extends Dialog implements ActionListener{
  
	
	// CONSTRUCTOR

	/**
	  * A new highScoresDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this highScoresDialog.
	  * @param dModal: The dModal of this highScoresDialog.
	  */	
	HighScoresDialog(Frame hostFrame, boolean dModal){
       	super(hostFrame, "View High-scores", dModal);
		
		// setting size, location and layout
		setSize(400, 300);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
	                Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		setLayout(new GridLayout(12,3, 5, 5));
		setResizable(false);
	
	    // creating and adding components
	   	addComponents();
	    	
	    add(new Label(""));
		$ok = new Button("Ok");
		$ok.addActionListener(this);
		add($ok);
		
		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);	
			}
		});
	}


	// MUTATOR
	/*
	 * Add the label components to this dialog.
	 */
	private void addComponents(){
		add(new Label("Position"));
	    add(new Label("Player"));
	    add(new Label("Score"));
	    
	    // read players
	    Player[] $players = RaceController.getReference().getHighScores().getPlayers();
	    	
		for(int i=1;i<=10;i++){
			add(new Label(""+ i +"."));
			if ($players[i-1]!=null){
				add(new Label($players[i-1].getName()));
				add(new Label(""+$players[i-1].getScore()));
			}
			else{
				add(new Label("-"));
				add(new Label("-"));
			}
		}
	}	
	
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
