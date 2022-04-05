package GUISystem;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A class of imagePanels.
  */
class ImagePanel extends JPanel{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new imagePanel with given image is created.
	  *
	  * @param image: The image of this imagePanel.
	  */
	ImagePanel(Image image){
		this.$image = image;
	}
	
	
	// MUTATORS

	/**
	  * Paint the given graphics.
	  *
	  * @param g: The graphics that must be paint.
	  */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background		
        //Draw image at its natural size first.
        g.drawImage($image, 0, 0, this);
    }
    
    
    // VARIABLES
    
    /*
     * A image.
     */
    private Image $image;
}