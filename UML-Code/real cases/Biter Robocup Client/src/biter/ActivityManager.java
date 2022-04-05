//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- 
package biter;
import java.util.*;
import java.io.*;

/**
 * Manage an agent's activities.
 * 
 * @see Activity
 * @author Jose M. Vidal
 * @version $Revision: 1.12 $, $Date: 2001/02/27 22:24:31 $
 *
 * 
 */

public class ActivityManager implements Runnable
{
  
  /** A vector of the activities */
  private Vector activities;
  
  /** A pointer to the agent */
  private PlayerFoundation agent;

  private Thread myThread;
  
  public ActivityManager(PlayerFoundation agent) {
    activities = new Vector();
    pq = new PriorityQueue();
    currentCycle = 0;
    this.agent = agent;
    myThread = new Thread(this, "AM-" + agent.playerNumber);
  }

  public void addActivity(Activity a){
    activities.add(a);
  }

  public void removeActivity(Activity a){
    activities.remove(a);
  }

  
  /** Start this manager running */
  public void start(){
    myThread.start();
  }

  /** Handle the input by sending it to the most appropiate activity.
   @param input the input
   @return true if the activity was handled, false otherwise*/
  public boolean handle(Input input){ 

    //    System.out.println("handle: cycle=" + input.getTimeStamp() + " time=" + input.getRealTime());
    
    //Find all the activites that match
    Vector matches = new Vector();
    for (Enumeration i = activities.elements(); i.hasMoreElements();){
      Activity a = (Activity)i.nextElement();
      if (a.canHandle(input))
        matches.addElement(a);
    }

    //Find all that are not inhibited by anyone else.
    Vector uninhibited = new Vector();
    for (Enumeration i = matches.elements(); i.hasMoreElements();){
      Activity a = (Activity)i.nextElement();
      boolean inhibited = false;
      for (Enumeration j = matches.elements(); j.hasMoreElements();){
        Activity b = (Activity)j.nextElement();
        if (a.equals(b))
          continue; 
        if (b.inhibits(a)){
//           System.out.println("Inhibition happened");
          inhibited = true;
          break;
        }
      }
      if (!inhibited) {
        uninhibited.addElement(a);
      }
    }
    
    if (uninhibited.size() == 0) {
      System.out.println("ERROR: No Activity matches input:" + input);
      return false;
    }
    int chosenIndex = (int)(Math.random()*uninhibited.size());
    Activity chosenOne = (Activity)(uninhibited.elementAt(chosenIndex));

    //ask it to handle the input. If it returns true then its done so we
    // remove it.
    if (chosenOne.handle(input))
      removeActivity(chosenOne);
    return true;
  }


  /** Standard Binary Heap implementation of a priority queue */
  private class PriorityQueue {
    /** The array of events, it is a binary heap. The top of the heap is at index 1. */
    Event[] events;
    
    /** Index of first available (free) index. */
    private int last;
    
    private PriorityQueue(){
      events = new Event[1000];
      last = 1;
    }
    
    /** Add an event in proper place */
    private void add(Event e){
      int hole = last;
      for ( ; (hole > 1) && e.isBefore(events[hole/2]) ; hole /= 2)
        events[hole] = events[hole/2];
      events[hole] = e;
      last++;
      
    }
    
    /** Return a copy of the event at the top.
     * @return the Event with the smallest time or null if none. */
    private Event top(){
      if (last <= 1)
        return null;
      return new Event(events[1]);
    }
    
    /** Return the event at the top and pop it (that is, delete it)
     * @return the Event with the smallest time or null if none. */
    private Event pop(){
      if (last <= 1)
        return null;
      Event top = events[1];
      delete(1);
      return top;
    }

    /** Delete the element at index
     @param index*/
    private void delete(int index){
      if ((index >= last) || (index < 1)) return;

      Event tmp = events[--last];
      events[index] = tmp;
      int hole = index;
      int child;

      //bubble tmp down to its location.
      for (; hole* 2 < last ; hole = child){
        //    System.out.println("hole="+hole);
        child = hole * 2;
        if (child+1 < last && events[child+1].isBefore(events[child]))
          child++;
        if (events[child].isBefore(tmp)){
          events[hole] = events[child];
          events[child] = null;
        }
        else 
          break;
      }
      events[hole] = tmp;
      return;
    }

    /** Return the index of the first event that "equals" e
        @param e the event we are looking for 
        @return the index, or 0 if not found */
    private int getIndex(Event e){
      for (int i = 1; i < last; i++){
        if (events[i].equals(e))
          return i;
      }
      return 0;
    }

    /** Delete the event from the queue
        @param e the event */
    private void deleteEvent(Event e){
      int i = getIndex(e);
      if (i > 0)
        delete(i);
    }
      
  }
    
  private PriorityQueue pq;

  public void addEventToQueue(Event e){
    pq.add(e);
  }

  public void deleteEventFromQueue(Event e){
    pq.deleteEvent(e);
  }
  
  /** Schedule an event.
   * @param name the name of the event. Some behavior needs to understand this name.
   * @param time the time (real time) at which this event will be triggered. Note that
   * if we are running behind, events might be triggered later than requested.*/
  void addEvent(String name, long time){
    Event e = new Event(name, time);
    pq.add(e);
  }

  /**
   * Tracks the time stamp of the current information it receives.
   * We receive information from cycle t until we receive
   * something from t+1, then we store that information and call
   * the Player.act() method.
   */
  private long currentCycle;

  
  /**
   * Get the value of currentCycle.
   * @return value of currentCycle.
   */
  public long getCurrentCycle() {
    return currentCycle;
  }
  
  /**
   * Set the value of currentCycle.
   * @param v  Value to assign to currentCycle.
   */
  public void setCurrentCycle(long  v) {
    
    this.currentCycle = v;
  }
  


  //The number of ms to wait for a message. The first time we wait
  //for something a long time.
  private long delay;

  /**
   * Get the value of delay.
   * @return value of delay.
   */
  public long getDelay() {
    return delay;
  }
  
  /**
   * Set the value of delay.
   * @param v  Value to assign to delay.
   */
  public void setDelay(long  v) {
    
    this.delay = v;
  }
  

  public Event actEvent;
  
  /** The main loop */
  public void run(){
    Input i;
    Event next;
    SensorInput dataFromServer = null;

    //The time of an input
    long inputTime;

    delay = 10000;
    currentCycle = 0;
    actEvent = new Event("act",0);

    Date now = new Date(); //current time, to time how long handle() takes.
    long nowms;
    //Print a message if we are running more than maxBehind behind.
    int maxBehind  = 50;
    
    Date end;
    long lastMsgMS = (new Date()).getTime();
    long lastInputTime = 0;
    while (true){

      try {
        dataFromServer = ProcessSensoryInput.parse(agent.wm.dgWrapper.receive(delay));
        if (dataFromServer != null){
          //nowms = (new Date()).getTime();
          nowms = dataFromServer.getRealTime();
          inputTime = dataFromServer.getTimeStamp();
          if ((inputTime > lastInputTime ) &&
              (Math.abs(nowms + 100 - actEvent.time) < 10)) { //if next act is off, reset it
            pq.deleteEvent(actEvent);
            actEvent.time = lastMsgMS + 100; //110 works
            pq.add(actEvent);
          }
          lastInputTime = inputTime;
          if (inputTime > currentCycle) { //received message with new time, act and resect act event.
            //            System.out.println("Act Event is too late: currentCycle=" + currentCycle + " inputTime=" + inputTime + " " + nowms);
            if ((nowms - actEvent.time) > maxBehind) {
              System.out.println("We are running " + (nowms - actEvent.time) + "ms behind.");
            }
            agent.wm.updateAbsolutePositions();
            //            System.out.println("****Act event forced " + nowms + " lastMsgMS=" + lastMsgMS);
            handle(actEvent);
            pq.deleteEvent(actEvent);
            actEvent.time = lastMsgMS + 100; //110 works
            //            System.out.println("****Next act at  " + actEvent.time);
            pq.add(actEvent);
            currentCycle = inputTime;
            //            System.out.println("====================Time=" + currentCycle);
          }
          else if (inputTime < currentCycle) {
//             System.out.println("We acted before receiving all the data for time " + inputTime);
            currentCycle = inputTime;
            //            System.out.println("====================Time=" + currentCycle);
          }
          //Deal with this input (e.g., add it to world model)
          handle(dataFromServer);
          lastMsgMS = nowms;
        }
        agent.wm.currentCycle = currentCycle;

        
      }
      catch (EOFException e){ //soccerserver died, quit
        System.out.println("Agent " + agent.playerNumber + " quiting.");
        break;
      }
      catch (InterruptedIOException e){
        //receive timed out, keep going
      };
      nowms =(new Date()).getTime();
      while (((next = pq.top()) != null) && (next.time <= nowms)){
        if ((nowms - next.time) > maxBehind){
          System.out.println("We are running " + (nowms - next.time) + "ms behind.");
        }
        if ((next instanceof Event) && (((Event)next).name.equals("act"))){ //if its an act, refresh it.
          agent.wm.updateAbsolutePositions(); 
          actEvent = pq.pop();
          actEvent.time = next.time + 100; //set next to 100ms from now.
          pq.add(actEvent);
          currentCycle++;
          //          System.out.println("====================Time=" + currentCycle);
        }
        handle((Input)next);
      }

      //set the delay so we wake up before the next event is set to fire.
      nowms =(new Date()).getTime();
      if (next != null)
        delay = next.time - nowms;
      else
        delay = 10;
      if (delay < 0 )
        delay = 0;
    }
  }
}
