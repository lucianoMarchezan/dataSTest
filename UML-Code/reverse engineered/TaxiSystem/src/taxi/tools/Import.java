/*
 * Created on 20.08.2004
 */
package tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import structures.OrderStructT;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Import {
	//Some variables
	public static final int OFFLINE = 1;
	public static final int AVAILABLE = 2;
	public static final int WAITING_FOR_CUSTOMER = 3;
	public static final int DRIVING_A_CUSTOMER = 4;
	public static final int SOON_AVAILABLE = 5;

	public static Hashtable readOrders(String source) {
		//DEBUGString content = new String("");
		Hashtable orders = new Hashtable();
		int index = 0;
		File file = new File(source);
		String newLine = null;
		String filtered = new String("");
		int i;
		/***/
		int orderNumber = -1;
		int time = -1;
		int address = -1;
		String name = new String("");
		String misc = new String("");
		int priority = -1;
		int taxiNumber = -1; // not read out of file
		boolean manualDispatch = false;
		int dispatchTime = -1;
		/***/
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((newLine = br.readLine()) != null) {
				if (newLine.startsWith("//")) newLine = br.readLine();
				//DEBUGcontent += newLine + "\n";
				for (i = 1; i <= 8; i++) {
					index = newLine.indexOf(',');
					if (index != -1)
						filtered = newLine.substring(0, index).trim();
					else
						filtered = newLine.substring(0).trim();
					switch (i) {
						case 1 :
							orderNumber = Integer.valueOf(filtered).intValue();
							break;
						case 2 :
							time = Integer.valueOf(filtered).intValue();
							break;
						case 3 :
							address = Integer.valueOf(filtered).intValue();
							break;
						case 4 :
							name = filtered;
							break;
						case 5 :
							if (!filtered.equals(new String("")))
								misc = filtered;
							else
								misc = new String("");
							break;
						case 6 :
							priority = Integer.valueOf(filtered).intValue();
							break;
						case 7 :
							if (filtered.equals(new String("true")))
								manualDispatch = true;
							else
								manualDispatch = false;
							break;
						case 8 :
							dispatchTime = Integer.valueOf(filtered).intValue();
							break;
					} //switch
					newLine = newLine.substring(index + 1);
				}//for
				orders
						.put(new Integer(orderNumber), new OrderStructT(orderNumber, time, address,
																		name, misc, priority,
																		taxiNumber, manualDispatch));
			}//while
			br.close();
		}
		catch (IOException e) {
			System.err.println("Error occurred trying to read file: \"" + source + "\"");
		}
		//DEBUGSystem.out.println("Content:\n" + content + "\n\n");
		//DEBUGSystem.out.println(orders.toString());
		return orders;
	}
}