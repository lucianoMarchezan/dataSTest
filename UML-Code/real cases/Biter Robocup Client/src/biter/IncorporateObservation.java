//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

import java.util.*;
import java.awt.geom.Point2D;
/**
 * This behavior simply incorporates a SensorInput object into the world model.
 * 
 * @author Jose M. Vidal
 * @version $Revision: 1.5 $
 *
 */

class IncorporateObservation extends RobocupBehavior
{

  IncorporateObservation(ActivityManager am, WorldModel wm){
    super(am, wm);
  }

  /** This behavior can always handle a sensor input.
      @param s the input */
  public boolean canHandle(SensorInput s){
    return true;
  }

  /** Always return false. */
  public boolean canHandle(Event e){
    return false;
  }

  /** Simply call WorldModel.addInput() with this sensor input
      @param s the input to add to the world model */
  public boolean handle (SensorInput s){

//     System.out.print("IncorporateObservation.handle(): ");
//     if (s instanceof RefereeMessage){
//       System.out.println("RefereeMessage");
//     }
//     else if (s instanceof SenseBody)
//       System.out.println("sense_body");
//     else if (s instanceof ObjectInfoContainer)
//       System.out.println("see");
//     else
//       System.out.println(s);

//     System.out.println(" time=" + s.getTimeStamp());
    
    //add this input information to world model
    wm.addInput(s);

    //if we just got a message from ref about kickoff, then start act event.
    if (s instanceof RefereeMessage){
      if (wm.isPlayMode("kick_off_l") || wm.isPlayMode("kick_off_r")) {
        manager.deleteEventFromQueue(manager.actEvent);
        manager.actEvent.time = (new Date()).getTime();
        manager.addEventToQueue(manager.actEvent);
        //      manager.setDelay(10);
        manager.setCurrentCycle(s.getTimeStamp());
      }
      else if (wm.isPlayMode("play_on")){
        //This is needed because the soccerserver lies about the ballposition in between kick_off and play_on
        //specifically, it tells us that it is still at [0,0], so we end up thinking that it is moving a lot
        //faster than it really is.
        wm.forgetBall();
      }
      else if (wm.isPlayMode("goal")){
        try {
          Thread.sleep(100);
        } catch (Exception e){
        };
        //        manager.deleteEventFromQueue(manager.actEvent);
        wm.forgetBall();
        move(wm.player.getStartLocation());
      }
    }
      
    return false; //never done!
  }

  /** Does nothing */
  public boolean handle(Event e){
    return false;
  }

  /** Always return true. Inhibit everyone else */
  public boolean inhibits (Activity a){
    return true;
  }
}
