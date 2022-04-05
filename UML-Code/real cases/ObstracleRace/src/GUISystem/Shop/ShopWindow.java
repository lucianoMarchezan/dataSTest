package GUISystem.Shop;

import javax.swing.*;
import java.awt.*;

/**
  * A class to represent the ShopWindow.
  */

public class ShopWindow extends Frame{
	
	/**
	  * A new ShopWindow with given hostFrame and dModal is created. 
	  *
	  */
	public ShopWindow(){
		super();
		setTitle("Shop");
    	setSize(800,600);
    	setResizable(false);
    	setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
    	                   Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
    	JLabel label = new JLabel();
    	label.setIcon(new ImageIcon("Images\\Garage.jpg"));
    	add(label);
	}
}