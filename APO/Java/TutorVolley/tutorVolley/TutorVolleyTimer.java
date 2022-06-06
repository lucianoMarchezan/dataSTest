package tutorVolley;

import java.util.TimerTask;

/**
 * Klasse, die sich um den Timer k�mmert und somit dass es fl�ssig alles in einer
 * bestimmten Reihenfolge abl�uft
 * @author Dirk Aporius
 *
 */
public class TutorVolleyTimer extends TimerTask
{
	private TutorVolleyPanelGame	tutorVolleyPanelGame;
	private boolean					bRunning;
	
	public TutorVolleyTimer( TutorVolleyPanelGame tutorVolleyPanelGame )
	{
		super();
		
		this.tutorVolleyPanelGame	= tutorVolleyPanelGame;
		
		this.bRunning				= false;
	}

	/**
	 * gibt zur�ck, ob es gerade gewollt ist den Timer zu nutzen
	 * @return TRUE, falls Spiel grad l�uft sonst FALSE
	 */
	protected boolean isBRunning()
	{
		return this.bRunning;
	}

	protected void setBRunning( boolean bRunning )
	{
		this.bRunning = bRunning;
	}
	
	@Override
	public void run()
	{
		if ( bRunning )
		{
			this.tutorVolleyPanelGame.think();
			this.tutorVolleyPanelGame.repaint();
		}
	}

}
