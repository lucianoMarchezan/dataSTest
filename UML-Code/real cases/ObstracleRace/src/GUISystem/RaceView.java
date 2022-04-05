package GUISystem;

import GameSystem.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * The raceview-window which features a top-view of the road surface.
  */
class RaceView extends Dialog implements ActionListener,KeyListener,Runnable{
	
	
	// CONSTRUCTORS
	
	/**
	  * A new raceView with given hostFrame and dModal is created. 
	  *
	  * @param hostFrame: The hostFrame of this raceView.
	  * @param dModal: The dModal of this raceView.
	  */
	RaceView(Frame hostFrame, boolean dModal){
		super(hostFrame, "Race View", dModal);
		
		// setting size and location
	    setSize(500, 600);	    
  		setLocation(RaceController.getReference().getGUIController().getMainWindowLocation());
  		
  		// adding components
  		$img=createRoadImage();
  		$panel = new ImagePanel($img);
  		add($panel);  
  		
  		// loading car-image
		$car = Toolkit.getDefaultToolkit().getImage("images\\car.gif");
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage($car,0);
		try{
			tracker.waitForID(0);		
		}
		catch(InterruptedException e){}
		
  		update();  	
	    addKeyListener(this);		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				RaceController.getReference().getGUIController().closeRaceInformation();
			}
		});
	}
	
	
	// MUTATORS
	
	/**
      * Creates a background image for the road surface.
      */
    private Image createRoadImage() {
		BufferedImage img = new BufferedImage(500, 600, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.dispose();
		return img;
    }   
    
    /**
      * Repeadetly updates the road surface at regular intervals.
      */
    public void run(){    	
    	try{
	    	while(isVisible()){	    		
		    	Thread.currentThread().sleep(40);
		    	update();
		    }
		}
		catch(InterruptedException e){}
    }
    
    /*
     * Draw the car.
     */
    private void drawCar(){
		$g.drawImage($car,RaceController.getReference().getRace().getCar().getPosition(),493,50,80,this);
	}
	/*
	 * Draw the surface.
	 */
	private void drawSurface(){
		$g.setColor(RaceController.getReference().getRace().getCurrentRoadPart().getRoadPartType().getColour());
		$g.fillRect(0,0,500,600);
	}
	
	/*
	 * Draw the next surface.
	 */
	private void drawNewSurface(){
		$g.setColor(RaceController.getReference().getRace().getNextRoadPart().getRoadPartType().getColour());			
		$g.fillRect(0,0,500,600-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH);
	}
	
	/*
	 * Draw checkpoint
	 */
	private void drawCheckPoint(){
		if(RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()<RoadPart.LENGTH){
			for(int i=0;i<=500;i+=14){
				$g.setColor(Color.white);
				$g.fillRect(i,600-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH,7,7);
				$g.fillRect(i+7,593-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH,7,7);
				$g.setColor(Color.black);
				$g.fillRect(i+7,600-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH,7,7);
				$g.fillRect(i,593-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH,7,7);

			}
		}
	}
	
	/*
	 * Draw roadboundary.
	 */
	private void drawBoundary(){
		for(int i=0-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%100; i<600; i+=100) {
		    $g.setColor(Color.white);
		    $g.fillRect(0,i,10,50);
		    $g.fillRect(483,i,10,50);
		    $g.setColor(Color.red);
		    $g.fillRect(0,i+50,10,50);
		    $g.fillRect(483,i+50,10,50);
		}
	}
	
	/*
	 * Draw dotted stripes.
	 */
	 
	 private void drawStripes(){
		 $g.setColor(Color.white);
			for(int i=-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%100;i<600;i+=100){
			$g.fillRect(350,i,10,70);
			$g.fillRect(150,i,10,70);
		}
	}
	
	/*
	 * Draw solid center.
	 */ 
	private void drawSolidCenter(){
		$g.fillRect(250,0,10,600);	
	}
	
	/*
	 * Draw cycle path
	 */
	private void drawCyclePath(){
		for(int i=-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%100;i<600;i+=20){
			$g.fillRect(50,i,4,16);
			$g.fillRect(445,i,4,16);
		}
	}
	
	/*
	 * Draw the obstacles on the current roadpart.
	 */
	private void drawCurrentRoadPartObstacles(){
		if(RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()!=0 ||
	       RaceController.getReference().getRace().getCurrentRoad()!=RaceController.getReference().getRace().getRoads().length-1){
			RoadObject[] roadobjects = RaceController.getReference().getRace().getCurrentRoadPart().getRoadObjects();
			int totalRoadLength = RaceController.getReference().getRace().getRoads()[RaceController.getReference().getRace().getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
			int done = (totalRoadLength - RaceController.getReference().getRace().getCar().getDistanceToCheckPoint())%RoadPart.LENGTH;
			for(int i=0;i<roadobjects.length;i++){			
				// check whether road object is approaching
				if(roadobjects[i].getPosY()-done<600+roadobjects[i].getHeight()){
					//draw road object
					if(roadobjects[i].getImage()==null){
						$g.setColor(roadobjects[i].getColour());
						$g.fillRect(roadobjects[i].getPosX(),600-(roadobjects[i].getPosY()-done),roadobjects[i].getWidth(),roadobjects[i].getHeight());
						$g.setColor(Color.black);
						$g.drawRect(roadobjects[i].getPosX(),600-(roadobjects[i].getPosY()-done),roadobjects[i].getWidth(),roadobjects[i].getHeight());
					}
					else{
						$g.drawImage(roadobjects[i].getImage(),
									roadobjects[i].getPosX(),
									600-(roadobjects[i].getPosY()-done),
									roadobjects[i].getWidth(),roadobjects[i].getHeight(),
									this);
					}
				}
			}		
		}
	}
	
	/*
	 * Draw the road object on the next roadpart.
	 */
	private void drawNextRoadPartObstacles(){
		if(RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH<600 &&
		   RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH!=0){
		   	if(RaceController.getReference().getRace().getCurrentRoad()<RaceController.getReference().getRace().getRoads().length-1 ||
		   	   RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()>=600){
				RoadObject[] roadobjects = RaceController.getReference().getRace().getNextRoadPart().getRoadObjects();
				int totalRoadLength = RaceController.getReference().getRace().getRoads()[RaceController.getReference().getRace().getCurrentRoad()].getLevel().getLevelType().getNumberOfParts()*RoadPart.LENGTH;
				int done = (totalRoadLength - RaceController.getReference().getRace().getCar().getDistanceToCheckPoint())%RoadPart.LENGTH;
				for(int i=0;i<roadobjects.length;i++){			
					// check whether road object is approaching
					if(roadobjects[i].getPosY()-done+RoadPart.LENGTH<600+roadobjects[i].getHeight()){						
						if(roadobjects[i].getImage()==null){
							$g.setColor(roadobjects[i].getColour());
							$g.fillRect(roadobjects[i].getPosX(),600-(roadobjects[i].getPosY()-done+RoadPart.LENGTH),roadobjects[i].getWidth(),roadobjects[i].getHeight());
							$g.setColor(Color.black);
							$g.drawRect(roadobjects[i].getPosX(),600-(roadobjects[i].getPosY()-done+RoadPart.LENGTH),roadobjects[i].getWidth(),roadobjects[i].getHeight());
						}
						else{
							$g.drawImage(roadobjects[i].getImage(),
							roadobjects[i].getPosX(),
							600-(roadobjects[i].getPosY()-done+RoadPart.LENGTH),
							roadobjects[i].getWidth(),
							roadobjects[i].getHeight(),
							this);
						}
					}					
				}
			}
		}
	}
	
	/*
	 * Draw the final part of the road.
	 */
	private void drawFinalPart(){
		if(RaceController.getReference().getRace().getCurrentRoad()==RaceController.getReference().getRace().getRoads().length-1 &&
		   	   RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()<600){		   	   	
				$g.setColor(Color.black);
				$g.fillRect(0,0,			
				           500,600-RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH);
		}
	}
		
    /**
      * Draws the road surface of the race.
      */   
    private void updateRoadImage(){
		$g = $img.getGraphics();
		// Draw surface of current road part
		if(RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()!=0 ||
	       RaceController.getReference().getRace().getCurrentRoad()!=RaceController.getReference().getRace().getRoads().length-1){
		   	// Draw surface
			drawSurface();
		
		}
		
		// Draw surface of next roadpart if next roadpart is approaching
		if(RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH<600 &&
		   RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()%RoadPart.LENGTH!=0){
		   	if(RaceController.getReference().getRace().getCurrentRoad()<RaceController.getReference().getRace().getRoads().length-1 ||
		   	   RaceController.getReference().getRace().getCar().getDistanceToCheckPoint()>=600){
		   	   	// Draw nextsurface
				drawNewSurface();
			}
		}		
		// Draw stripes of road boundary
		drawBoundary();
		// Draw dotted stripes 
		drawStripes();	
		// Draw solid center stripe
		drawSolidCenter();
		// Draw stripes	for cyclists
		drawCyclePath();
		// Draw checkpoint();
		drawCheckPoint();
	    // Draw obstacles of current roadpart
		drawCurrentRoadPartObstacles();
	    // Draw obstacles of next roadpart
		drawNextRoadPartObstacles();
		// Draw road end if final checkpoint is approaching
		drawFinalPart();	
		// Draw car
		drawCar();
		$g.dispose();		
    }

	/**
	  * Updates the road surface of the race and shows it on screen.
	  */
	public void update(){
		updateRoadImage();
		$panel.repaint();
	}
	
	/**
	  * Invoked when an action occurs.
	  *
	  * @param e: The actionEvent that is occured.
	  */
	public void actionPerformed(ActionEvent e){	    
	}
	
	/**
	  * Invoked when an keyEvent occurs.
	  *
	  * @param e: The keyEvent that is occured.
	  */
	public void keyTyped(KeyEvent e){ 
	}
	
	/**
	  * Look what key is pressed and take the wright action.
	  *
	  * @param e: The keyEvent that is occured.
	  */
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getAccelerateKey())
			RaceController.getReference().accelerate();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getDecelerateKey())
			RaceController.getReference().decelerate();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getLeftKey())
			RaceController.getReference().steerLeft();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getRightKey())
			RaceController.getReference().steerRight();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getGearDownKey())
			RaceController.getReference().gearDown();
		if(e.getKeyChar() == RaceController.getReference().getKeyboard().getGearUpKey())
			RaceController.getReference().gearUp();
	}
	
	/**
	  * The pressed key is released.
	  *
	  * @param e: The keyEvent that is occured.
	  */
	public void keyReleased(KeyEvent e) { 
	}
	
	
	// VARIABLES
	
	/*
	 * A panel for showing the raceView.
	 */
	private ImagePanel $panel;
	
	/*
	 * A image that will be draw on the panel.
	 */
	private Image $img,$car;
	
	/*
     * The graphics object.
     */
     
	private Graphics $g;
}



