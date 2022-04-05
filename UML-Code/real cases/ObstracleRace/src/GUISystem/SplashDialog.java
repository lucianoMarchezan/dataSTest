package GUISystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
  * A dialog class for welcomming the pklayer when a race program is started.
  */
  
class SplashDialog extends Dialog implements KeyListener{

	// CONSTRUCTOR
	
	/**
	  * A new SplashDialog with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this SplashDialog.
	  * @param dModal: The dModal of this SplashDialog.
	  */
	SplashDialog(Frame hostFrame, boolean dModal){ 		
		super(hostFrame, "Welcome", dModal);
    	setSize(200,50);
    	add(new Label("Welcome To Obstacle Race..."));   
    	setResizable(false); 
    	setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
    	                   Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
    	addKeyListener(this);

     	setVisible(true);
     	// sleep for a while     	
     	try{
			Thread.currentThread().sleep(1500);
		}
    	catch(InterruptedException e) {}
    	setVisible(false);
	}
	
	/**
	  * Invoked when an keyEvent occurs.
	  *
	  * @param e: The keyEvent that has occured.
	  */
	public void keyTyped(KeyEvent e){ 
		setVisible(false);
	}
	
	/**
	  * Look what key is pressed and take the wright action.
	  *
	  * @param e: The keyEvent that has occured.
	  */	
	public void keyPressed(KeyEvent e){
	}
	
	/**
	  * The pressed key is released.
	  *
	  * @param e: The keyEvent that has occured.
	  */
	public void keyReleased(KeyEvent e) { 
	}
}