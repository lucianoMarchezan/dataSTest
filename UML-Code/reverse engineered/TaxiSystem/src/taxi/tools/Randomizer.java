/*
 * Created on 21.09.2004
 */
package tools;
import java.util.Random;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Randomizer {
	/**
	 * This method returns a randomly generated int.
	 * 
	 * @param n The range of the returned int is from 0 to n).
	 * @return An integer that is element of [0, n].
	 */
	private static Random r = new Random();
	public static int num = -1;

	public static int getInt(int n) {
		if (num == -1)
			return r.nextInt(n + 1);
		else
			return num;
	}
}