package tutorVolley;

/**
 * TutorBlobbyAI ist die "K�nstliche Intelligenz" (AI) des Spielers und steuert diesen.
 * Dieses Interface mu� implementiert werden, damit der Spieler gesteuert werden kann.
 * @author Dirk Aporius
 */
public interface TutorVolleyAI
{
	/**
	 * Wird pro Zyklus einmal ausgef�hrt. Wird TRUE zur�ckgegeben, versucht der Spieler 
	 * zu springen. 
	 * 
	 */
	public boolean think( TutorVolleyBall ball, TutorVolleyPlayer player );
	
	/**
	 * �bergibt den Spielernamen
	 * @return Spielername
	 */
	public String getName();
	
	/**
	 * �bergibt den Namen des Autors
	 * @return Autorenname
	 */
	public String getAuthorName();
}