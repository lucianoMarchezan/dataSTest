package apoDefence;

import java.util.TimerTask;

import apoDefence.game.ApoDefenceGame;

/**
 * Klasse, die sich um den Timer k�mmert und somit dass es fl�ssig alles in einer
 * bestimmten Reihenfolge abl�uft
 * @author Dirk Aporius
 *
 */
public class ApoDefenceTimer extends TimerTask
{
	private ApoDefenceGame			game;
	private boolean					bRunning;
	
	public ApoDefenceTimer( ApoDefenceGame game )
	{
		super();
		
		this.game		= game;
		
		this.bRunning	= true;
	}

	/**
	 * gibt zur�ck, ob es gerade gewollt ist den Timer zu nutzen
	 * @return TRUE, falls Spiel grad l�uft sonst FALSE
	 */
	public boolean isBRunning()
	{
		return this.bRunning;
	}

	public void setBRunning( boolean bRunning )
	{
		this.bRunning = bRunning;
	}
	
	@Override
	public void run()
	{
		if ( bRunning )
		{
			if ( ( !this.game.isBWin() ) && ( !this.game.isBLoose() ) )
				this.game.think();
			this.game.repaint();
		}
	}

}
