package GameSystem;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.spi.*;

/**
  * A class offering methods to play sound effects in a car race
  */

public class SoundPlayer{
	
	// CONSTRUCTORS
	
	/**
	  * Constructs a new sound player
	  */
	public SoundPlayer(){
		$collision = new Clip[ObstacleCatalog.getReference().getObstacleTypes().length];
		for(int i=0;i<ObstacleCatalog.getReference().getObstacleTypes().length;i++){	
			$collision[i] = loadSound(new File(ObstacleCatalog.getReference().getObstacleTypes()[i].getSound()));
		}
		$bonus = loadSound(new File("sound\\bonus.wav"));
		$crash = loadSound(new File("sound\\crash.wav"));
	}
	
	// MUTATORS
	
	/**
	  * Plays the sound you hear when hit by an obstacle of a given obstacletype
	  *
	  * @param obstacleType the obstacletype of the hit obstacle
	  */
	public void playCollision(ObstacleType obstacleType){
		int i=0;
		while(i<ObstacleCatalog.getReference().getObstacleTypes().length &&
		      obstacleType!=ObstacleCatalog.getReference().getObstacleTypes()[i])
			i++;
		playSound($collision[i]);
	}
	
	/**
	  * Plays the sound you hear when hit by a bonus item
	  */
	public void playBonus(){
		playSound($bonus);
	}
	
	/**
	  * Plays the sound you hear when the car has crashed
	  */
	public void playCrash(){
		playSound($crash);
	}
	
	/*
	 * Loads the given soundfile and returns it as a playable soundclip
	 *
	 * @param file the given soundfile
	 * @return the playable soundclip
	 */
	private Clip loadSound(File file){		
		try {
            AudioInputStream stream = (AudioInputStream) AudioSystem.getAudioInputStream(file);
	        AudioFormat format = stream.getFormat();
	        DataLine.Info info = new DataLine.Info(Clip.class,stream.getFormat(),((int) stream.getFrameLength()*format.getFrameSize()));;
		    Clip clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
			return clip;
        } 
        catch (Exception ex) {
        	System.out.println(ex.getMessage());
        	return null;
        }	          
    }

	/*
	 * Plays the given soundclip from the beginning
	 *
	 * @param clip the given soundclip
	 */
    private void playSound(Clip clip) {	
		clip.stop();
		clip.setFramePosition(0);
		clip.start();	    
    }       
    
    // The soundclips of the collisions
    Clip[] $collision;
    // The soundclip of the bonusitem
    Clip $bonus;
    // The soundclip of the crash
    Clip $crash;
}