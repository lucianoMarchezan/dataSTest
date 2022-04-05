package GUISystem.Shop;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * A dialog class for querying the user for the specific part
  *	he wants to buy.
  */
public class BuyDialog extends Dialog implements ActionListener,ItemListener{
	
  	
  	// CONSTRUCTOR
  	
  	/**
	  * A new buyDialog with given hostFrame, dModal, partTypes is created. 
	  *
	  * @param hostFrame: The hostFrame of this mainDialog.
	  * @param dModal: The dModal of this mainDialog.
	  * @param partTypes: All the partTypes of a category.
	  */	
	public BuyDialog(Frame hostFrame, boolean dModal, PartType [] partTypes){
    	super(hostFrame, "Choose the part you want to buy.", dModal);
		
		$partTypes = partTypes;
    	
    	// setting size, location and layout
		setSize(600, 350);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setResizable(false);
		
		// add type Components
		addElements();
		
		setDefaultType();
		
		// adding listeners
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
	
	
	// INSPECTOR
	
	/** 
	  * Return the choice made by the player in this buyDialog.
	  *
	  * @return 1 if "Buy"-button was clicked
	  *         2 if "Back"-button was clicked
	  *			3 if "Exit Shop"-button was clicked
	  */
	public int getChoice(){
		return $choice;
	}
	
	/**
	  * Return the partType last bought by the player.
	  *
	  * @return The partType last bought by the player.
	  */
	public PartType getPartType(){
		return $partType;
	}
	
		
	// MUTATORS
	
	/**
	  * Make the string with the information about the players car.
	  */
	private String makeStringCarInfo(){
		//get the name of the engine
		String engine;
		try{
			engine = RaceController.getReference().getRace().getCar().getEngine().getPartType().getName();
		}
		catch(NullPointerException e){
			engine = "none";
		}
		//get the name of the body
		String body;
		try{
			body = RaceController.getReference().getRace().getCar().getBody().getPartType().getName();
		}
		catch(NullPointerException e){
			body = "none";
		}
		//get the name of the tireset
		String tireset;
		try{
			tireset = RaceController.getReference().getRace().getCar().getTireset().getPartType().getName();
		}
		catch(NullPointerException e){
			tireset = "none";
		}
		//make the string
		String hulp = "<html>\n" +
					  "You have the following parts in your car: \n"+
					  "<ul>\n" +
					  "<li>Engine: "+ engine  +"\n"+ 
					  "<li>Body: "  + body    +"\n"+
					  "<li>Tires: " + tireset +"\n"+
					  "</ul>"; 
		return(hulp); 
	}
	
	/*
	 * Activated the first partType that is in the list of partTypes
	 * and show his information and image.
	 */
	private void setDefaultType(){
		try{
			$lst.select(0);
			$label.setIcon(new ImageIcon("Images\\"+$partTypes[0].getFileName()));
		}
		catch(NullPointerException e){}
    }
	
	/*
	 * add the components to this buyDialog.
	 */
	private void addElements(){
		addUserInfo();
    	add(Box.createVerticalGlue());
		addPartTypesInfo();		
		add(Box.createVerticalGlue());
    	addButtons();
    }

	/**
	  * Add the userInfo to this buydialog.
	  */
	private void addUserInfo(){
		//Info about the car
		String hulp = makeStringCarInfo();		
		JLabel infoCar = new JLabel(hulp);
						
		JLabel infoLabel = new JLabel(RaceController.getReference().getRace().getPlayer().getName()+
							", you can maximally buy for " +
							RaceController.getReference().getRace().getPlayer().getMoney()+
							" EUR." );
		
		$msg = new JLabel("Select one of the types to see some information.");
		
		//A box-layout that has the information for the user.					
		JPanel infoUserPane = new JPanel();
		infoUserPane.setLayout(new BoxLayout(infoUserPane, BoxLayout.Y_AXIS));
		infoUserPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		infoUserPane.add(Box.createVerticalGlue());
		infoUserPane.add(infoLabel);
		infoUserPane.add(Box.createVerticalGlue());
		infoUserPane.add($msg);
		infoUserPane.add(Box.createVerticalGlue());
						
		//A box-layout that has the information about and for the user.
		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.X_AXIS));
		infoPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		infoPane.add(Box.createHorizontalGlue());
		infoPane.add(infoCar);
		infoPane.add(Box.createHorizontalGlue());
		infoPane.add(infoUserPane);
		infoPane.add(Box.createHorizontalGlue());
		add(infoPane);
	}
	
	/**
	  * Add PartTypesInfo to this buyDialog
	  */
	private void addPartTypesInfo(){
		//a list with all the partTypes
		$lst = new List($partTypes.length, false);
 		for(int i=0; i < $partTypes.length; i++){
			$lst.add($partTypes[i].getName(),i);
			$lst.addItemListener(this);
		}
		//image of the partType
		$label = new JLabel();
		//information about the partType        	
    	$typeInfo = new JLabel();
    	$typeInfo.setText($partTypes[0].getDescription());
    	
    	//a box-layout that has all the information about the partTypes.    	    	
    	JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.X_AXIS));
		listPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		listPane.add(Box.createHorizontalGlue());
		listPane.add($lst);
		listPane.add(Box.createHorizontalGlue());
		listPane.add($typeInfo);
		listPane.add(Box.createHorizontalGlue());
		listPane.add($label);
		listPane.add(Box.createHorizontalGlue());
		add(listPane);
	}
	
	/**
	  * Ad the buttons to this buyDialog
	  */
	private void addButtons(){
		$buy = new Button("Buy");
		$buy.addActionListener(this);
					
		$back = new Button("Back");
		$back.addActionListener(this);
				
		$exitShop = new Button("Exit Shop");
		$exitShop.addActionListener(this);
		
		//A box-layout that has all the buttons.		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add($buy);
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add($back);
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add($exitShop);
		buttonPane.add(Box.createHorizontalGlue());
		add(buttonPane);
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		//what to do if the buy-button is clicked
		if(e.getSource() == ($buy)){
			$choice = 1;
			for(int i=0; i < $lst.getItemCount(); i++){
				if($lst.isIndexSelected(i))
					$partType = $partTypes[i];
			}
		    if($partType.getPrice() > RaceController.getReference().getRace().getPlayer().getMoney()){
				$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
	        	$msg.setText("You don't have enough money.");	
	     	}
			else
				setVisible(false);
		}
		//what to do if the back-button is clicked
		if (e.getSource() == $back){
			$choice = 2;
			setVisible(false);
		}
		//what to do if the exitShop-button is clicked
		boolean allParts = true;
		if (e.getSource() == $exitShop){
			$choice = 3;
			if(RaceController.getReference().getRace().getCar().hasAllParts())
				setVisible(false);
			else{
				$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
				$msg.setText("You must first buy all parts.");
			}
		}
	}
	
	/**
	  * Invoked when an item event occurs.
	  *
	  * @param e: The itemEvent that has occured.
	  */
	public void itemStateChanged(ItemEvent e){
		for(int i=0;i<$lst.getItemCount();i++){
			if ($lst.isIndexSelected(i)){
				$typeInfo.setText($partTypes[i].getDescription());
				$label.setIcon(new ImageIcon("Images\\"+$partTypes[i].getFileName()));
			}
		}
	}
		
	
	// VARIABLES
	
	/*
	 * Avariable (Array of PartTypes) for holding all the partTypes that are 
	 * in the category selected by the player. 
	 */
	private PartType [] $partTypes;
	
	/*
	 * A variable (PartType) for holding the partType last bought
	 * by the player. 
	 */
	private PartType $partType;

	/* 
     * Buy-button. 
     */
	private Button $buy;
	
	/* 
     * Back-button. 
     */
	private Button $back;
	
	/* 
     * ExitShop-button. 
     */
	private Button $exitShop;
	
	/* 
     * A label for dealing with information to the user. 
     */
	private JLabel $label;
	
	/* 
	 * Label for showing messages to the user.
	 */
	private JLabel $msg;
	
	/* 
     * A variable (List) for for showing all the partTypes to the user. 
     */
	private List $lst;
	
	/* 
     * A variable (Label) for dealing with information about the partTypes. 
     */
	private JLabel $typeInfo;
	
	/*
	 * A variable (int) for holding the choice of the player.
	 */	
	private int $choice = 0;
}