package GUISystem.Shop;

import GameSystem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Hashtable;

/**
  * A dialog class for querying the user what he wants to
  * repair of the given part.
  */
public class RepairDialog extends Dialog implements ActionListener, ChangeListener{
	
  	
  	// CONSTRUCTOR
  	
  	/**
	  * A new repairDialog with given hostFrame, dModal, part is created. 
	  *
	  * @param hostFrame: The hostFrame of this mainDialog.
	  * @param dModal: The dModal of this mainDialog.
	  * @param part: The part of the car that is selected by the user to repair.
	  */	
	public RepairDialog(Frame hostFrame, boolean dModal, Part part){
    	super(hostFrame, "Repair some part of the car.", dModal);
		
		$part = part;
    	    	
    	// setting size, location and layout
		setSize(500, 200);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,
                    Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);  
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setResizable(false);
		
		//add type Components
		addElements();
	
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
	  * Returns the choice made in this repairDialog
	  *
	  * @return 1 if "Repair"-button was clicked
	  *         2 if "Back"-button was clicked
	  *			3 if "Exit Shop"-button was clicked
	  *			4 if "Change"-button was clicked
	  */
	public int getChoice(){
		return $choice;
	}
	
	/**
	  * Return the part last selected by the player to repair.
	  *
	  * @return The part last selected by the player to repair.
	  */
	public Part getPart(){
		return $part;
	}

	/**
	  * Return the percentage given by the player.
	  *
	  * @return The percentage given by the player.
	  */
   public int getPercentage(){
		return ($damageSlider.getValue());
	}

	
	// MUTATORS
	
	/*
	 * add the components to this repairDialog.
	 */
	private void addElements(){
		add(Box.createVerticalGlue());
		addUserInfo();
		add(Box.createVerticalGlue());
		if(!($part.getPartType() instanceof TiresetType))
			addDamageInfo();
		add(Box.createVerticalGlue());
		addButtons();
		add(Box.createVerticalGlue());
	}
	
	/**
	  * Add user info to this repairDialog.
	  */
	private void addUserInfo(){
		JLabel infoLabel = new JLabel();
		infoLabel.setForeground(new Color(0.0f, 0.0f, 0.0f));
		infoLabel.setText(RaceController.getReference().getRace().getPlayer().getName()+
						  ", you can maximally repair the "+
						  $part.getPartType().getName()+ " of your car for  " +
						  RaceController.getReference().getRace().getPlayer().getMoney()+
						  " EUR.    " );
		
		$msg = new JLabel();
		$msg.setForeground(new Color(0.0f, 0.0f, 0.0f));
		if(($part.getPartType() instanceof TiresetType))
			$msg.setText(" You can't repair tires, you have to change them for "+$part.getPartType().getPrice()+" EUR.");
		else
			$msg.setText(" Use the damageSlider for see the cost for repairing some percentage.");
		
		//A box-layout that has the information for the user.					
		JPanel infoUserPane = new JPanel();
		infoUserPane.setLayout(new BoxLayout(infoUserPane, BoxLayout.Y_AXIS));
		infoUserPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		infoUserPane.add(Box.createVerticalGlue());
		infoUserPane.add(infoLabel);
		infoUserPane.add(Box.createVerticalGlue());
		infoUserPane.add($msg);
		infoUserPane.add(Box.createVerticalGlue());
		infoUserPane.setAlignmentX(CENTER_ALIGNMENT);
		add(infoUserPane);
	}
	
	/**
	  * Add damage info to this repairDialog.
	  */
	private void addDamageInfo(){
		//A slider for selecting the percentage
		int hulp = (int)(Math.ceil($part.getDamage()*100)); 
		$damageSlider = new JSlider(JSlider.HORIZONTAL,0,hulp, 0);
		$damageSlider.addChangeListener(this);
		$damageSlider.setMajorTickSpacing((int)(hulp/4));
		$damageSlider.setMinorTickSpacing((int)(hulp/8));
		$damageSlider.setPaintTicks(true);
		$damageSlider.setPaintLabels(true);
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("No repair") );
		labelTable.put( new Integer( hulp ), new JLabel("Maximum repair") );
		$damageSlider.setLabelTable( labelTable );
		$damageSlider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		
        //damagebar for the damage of the selected part
        $damageBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		$damageBar.setStringPainted(true);
		$damageBar.setValue(hulp);
		$damageBar.setString(""+Math.floor(1000*$part.getDamage())/10+"%");
			
		//A box-layout that has the slider and the bar.
		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.X_AXIS));
		infoPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		infoPane.add(Box.createHorizontalGlue());
		infoPane.add($damageSlider);
		infoPane.add(Box.createHorizontalGlue());
		infoPane.add($damageBar);
		infoPane.add(Box.createHorizontalGlue());
		infoPane.setAlignmentX(CENTER_ALIGNMENT);
		add(infoPane);
	}
	
	/*
	 * Add the buttons to this repairDialog.
	 */
	private void addButtons(){
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		
		if($part.getPartType() instanceof TiresetType){
			$change = new Button("Change");
			$change.addActionListener(this);
			buttonPane.add($change);
			buttonPane.add(Box.createHorizontalGlue());
		}
		
		else{						
			$repair = new Button("Repair");
			$repair.addActionListener(this);
			buttonPane.add($repair);
			buttonPane.add(Box.createHorizontalGlue());
		}
		
		$back = new Button("Back");
		$back.addActionListener(this);
		buttonPane.add($back);
		buttonPane.add(Box.createHorizontalGlue());

		$exitShop = new Button("Exit Shop");
		$exitShop.addActionListener(this);
		buttonPane.add($exitShop);
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.setAlignmentX(CENTER_ALIGNMENT);
		add(buttonPane);
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that has occured.
	  */
	public void actionPerformed(ActionEvent e){
		$msg.setText("");
		if(e.getSource() == ($repair)){
			$choice = 1;
			if($damageSlider.getValue()*$part.getPartType().getRepairPriceIndex() <= RaceController.getReference().getRace().getPlayer().getMoney())
				setVisible(false);
			else{
				$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
				$msg.setText("You don't have enough money to repair the selected percentage.");
			}
		}
		if (e.getSource() == $back){
			$choice = 2;
			setVisible(false);
		}
		if (e.getSource() == $exitShop){
			$choice = 3;
			setVisible(false);
		}
		if(e.getSource() == ($change)){
			$choice = 4;
			if($part.getPartType().getPrice() <= RaceController.getReference().getRace().getPlayer().getMoney())
				setVisible(false);
			else{
				$msg.setForeground(new Color(1.0f, 0.0f, 0.0f));
				$msg.setText("You don't have enough money to change the tires.");
			}
		}
	}
	
	/**
	  * Invoked when the state of the slider is changed.
	  *
	  * @param e: The changeEvent that has occured.
	  */
	public void stateChanged(ChangeEvent e){
		double hulp = Math.max(0,Math.floor(1000*$part.getDamage()/10)-$damageSlider.getValue());
		$damageSlider.getValue();
		$damageBar.setValue((int)(hulp));
		$damageBar.setString(""+hulp+"%");
		$msg.setForeground(new Color(0.0f, 0.0f, 0.0f));
		$msg.setText("The cost = " + $damageSlider.getValue()*$part.getPartType().getRepairPriceIndex() + " EUR.");
	}
		
	// VARIABLES
	
	/*
	 * A variable (Part) for holding the part last selected by the player for repairing. 
	 */
	private Part $part;

	/* 
     * Buy-button. 
     */
	private Button $repair;
	
	/* 
     * Change-button. 
     */
	private Button $change;
	
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
	 * A variable (int) for holding the choice of the player.
	 */	
	private int $choice = 0;
	
	/* 
	 * Label for showing messages
	 */
	private JLabel $msg;

	/*
	 * A variable (JSlider) for selecting the percentage.
	 */
	private JSlider $damageSlider;
	
	/*
	 * A variable (JProgressBar) for showing the result on the damage
	 * when a certain percentage is selected.
	 */
	private JProgressBar $damageBar;
}