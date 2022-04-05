package GUISystem;

import javax.swing.*;
import java.awt.*;

/**
  * A class to represent the MainWindow.
  */

class MainWindow extends Frame{

	// CONSTRUCTORS
	/**
	  * A new MainWindow with given hostFrame and dModal is created. 
	  *
	  */
	MainWindow(){
		super();
		setTitle("MOP: Practicum Deel 1: Obstacle Race");
    	setSize(800,600);
    	setResizable(false);
    	setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
    	                   Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
    	JLabel label = new JLabel();
    	label.setIcon(new ImageIcon("Images\\ObstacleRace.PNG"));
    	add(label);
	}
}