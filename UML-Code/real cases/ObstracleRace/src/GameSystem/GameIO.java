package GameSystem;
import java.io.*;

public class GameIO{

	/**
	  * A new GameIO is created.
	  */
	public GameIO(){
	}

	/**
	  * Read the keys from a file.
	  */
	public void initKeyboard(){
		try{
			FileInputStream istream = new FileInputStream("keys.dat");
			ObjectInputStream p = new ObjectInputStream(istream);
			char [] hulp = (char [])p.readObject();
			istream.close();
			try{
				RaceController.getReference().getKeyboard().setLeftKey(hulp[0]);
				RaceController.getReference().getKeyboard().setRightKey(hulp[1]);
				RaceController.getReference().getKeyboard().setAccelerateKey(hulp[2]);
				RaceController.getReference().getKeyboard().setDecelerateKey(hulp[3]);
				RaceController.getReference().getKeyboard().setGearDownKey(hulp[4]);
				RaceController.getReference().getKeyboard().setGearUpKey(hulp[5]);
			}
			catch(Exception e){
				RaceController.getReference().getKeyboard().setDefaultKeys();
			}
		}
		catch(Exception e){
			RaceController.getReference().getKeyboard().setDefaultKeys();
		}
	}
	
	/**
	  * Read the gearbox from a file.
	  */
	public void initGearbox(){
		try{
			FileInputStream istream = new FileInputStream("gearbox.dat");
			ObjectInputStream p = new ObjectInputStream(istream);
			Gearbox gearbox = (Gearbox)p.readObject();
			istream.close();
			try{
				RaceController.getReference().setDefaultGearbox(gearbox);
			}
			catch(Exception e){
				GearboxBuilder gearboxBuilder = new GearboxBuilder();
				gearboxBuilder.buildAutomaticGearbox(); 
				RaceController.getReference().setDefaultGearbox(gearboxBuilder.getGearbox());
			}
		}
		catch(Exception e){
			GearboxBuilder gearboxBuilder = new GearboxBuilder();
			gearboxBuilder.buildAutomaticGearbox(); 
			RaceController.getReference().setDefaultGearbox(gearboxBuilder.getGearbox());
		}
	}
	
	/**
	  * Read the highscores from a file.
	  */
	public void initHighScores(){
		try{
			FileInputStream istream = new FileInputStream("highscores.dat");
			ObjectInputStream p = new ObjectInputStream(istream);
			Player [] hulp = (Player [])p.readObject();
			istream.close();
			for(int i=0; i < hulp.length; i++){
				try{
					RaceController.getReference().getHighScores().addPlayer(hulp[i]);
					}
				catch(Exception e){}
			}
		}
		catch(Exception e){}
	}
		
	/**
	  * Write the keys to a file.
	  */
	public void writeKeyboard(){
		try{
			char [] hulp = {RaceController.getReference().getKeyboard().getLeftKey(),
			                RaceController.getReference().getKeyboard().getRightKey(),
			                RaceController.getReference().getKeyboard().getAccelerateKey(),
			                RaceController.getReference().getKeyboard().getDecelerateKey(),
			                RaceController.getReference().getKeyboard().getGearDownKey(),
							RaceController.getReference().getKeyboard().getGearUpKey()};
			FileOutputStream ostream = new FileOutputStream("keys.dat");
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			p.writeObject(hulp);
			p.flush();
			ostream.close();
		}
	
		catch(Exception e){}
	}
	
	/**
	  * Write the gearbox to a file.
	  */
	public void writeGearbox(){
		try{
			FileOutputStream ostream = new FileOutputStream("gearbox.dat");
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			p.writeObject(RaceController.getReference().getDefaultGearbox());
			p.flush();
			ostream.close();
		}
		catch(Exception e){}
	}
	
	/**
	  * Write the HighScores to a file.
	  */
	public void writeHighScores(){
		try{
			FileOutputStream ostream = new FileOutputStream("highscores.dat");
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			p.writeObject(RaceController.getReference().getHighScores().getPlayers());
			p.flush();
			ostream.close();
		}
		catch(Exception e){}
	}
}