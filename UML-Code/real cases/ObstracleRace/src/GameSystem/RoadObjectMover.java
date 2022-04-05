package GameSystem;

/**
  * A class to move the RoadObjects a road
  */
class RoadObjectMover implements Runnable{
	public void run() {
		try{
			while(true){
				while(!RaceController.getReference().getGUIController().raceViewVisible());									
				RoadPart currentRoadPart = RaceController.getReference().getRace().getCurrentRoadPart();
				RoadPart nextRoadPart = RaceController.getReference().getRace().getNextRoadPart();
				RoadObject[] roadobjects = currentRoadPart.getRoadObjects();
				while(RaceController.getReference().getGUIController().raceViewVisible()){
					//wait
					Thread.currentThread().sleep(10);
					if(currentRoadPart!=RaceController.getReference().getRace().getCurrentRoadPart()){									
						currentRoadPart = RaceController.getReference().getRace().getCurrentRoadPart();
						nextRoadPart = RaceController.getReference().getRace().getNextRoadPart();
						roadobjects = currentRoadPart.getRoadObjects();
					}
					
					// move roadobjects
					for(int i=0;i<roadobjects.length;i++){
						// advance roadobject
						roadobjects[i].move();
						
						// move to next roadpart if neccesary
						if(roadobjects[i].getPosY()>=RoadPart.LENGTH){
							if(currentRoadPart.hasRoadObject(roadobjects[i])){
								currentRoadPart.removeRoadObject(roadobjects[i]);
								nextRoadPart.addRoadObject(roadobjects[i]);
								roadobjects[i].setPosition(roadobjects[i].getPosX(),roadobjects[i].getPosY()-RoadPart.LENGTH);
							}
							else if(nextRoadPart.hasRoadObject(roadobjects[i])){
								nextRoadPart.removeRoadObject(roadobjects[i]);
							}
						}
					}
				}
			}
		}
		catch(InterruptedException e){
		}
	}
}
