package tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import proxy.DriverDataProxy;
import proxy.TaxiDataProxy;

import structures.DriverStruct;
import structures.OrderStructC;
import structures.TaxiStruct;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class Import {	

	public static Hashtable readOrders(String source) {
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
				for (i = 1; i <= 8; i++) {					
					index = newLine.indexOf(',');
					if(index != -1)
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
					newLine = newLine.substring(index+1);
				}//for
				orders.put(new Integer(orderNumber), new OrderStructC(orderNumber, time, address,
																		name, misc, priority,
																		taxiNumber, manualDispatch,
																		dispatchTime));
			}//while
			br.close();
		}
		catch (IOException e) {
			System.err.println("Error occurred trying to read file: \"" + source + "\"");
		}
		return orders;
	}
	
	/**
	 * Reads operatorsnumbers and their passwords from a file.
	 * @param source
	 * @return
	 */
	public static Hashtable readOperators(String source) {
				Hashtable operators = new Hashtable();
				int index = 0;
				File file = new File(source);
				String newLine = null;
				String filtered = new String("");
				int i;
				/***/
				int operatorNumber = -1;
				String password = null;
				/***/
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					while ((newLine = br.readLine()) != null) {
						if (newLine.startsWith("//")) newLine = br.readLine();
						//DEBUGcontent += newLine + "\n";
						for (i = 1; i <= 2; i++) {					
							index = newLine.indexOf(',');
							if(index != -1)
							filtered = newLine.substring(0, index).trim();
							else
								filtered = newLine.substring(0).trim();
							switch (i) {
								case 1 :
									operatorNumber = Integer.valueOf(filtered).intValue();
									break;
								case 2 :
									if (!filtered.equals(new String("")))
										password = filtered;
									else
										password = new String("");
									break;
							} //switch
							newLine = newLine.substring(index+1);
						}//for
						operators.put(new Integer(operatorNumber), new String(password));
					}//while
					br.close();
				}
				catch (IOException e) {
					System.err.println("Error occurred trying to read file: \"" + source + "\"");
				}
				return operators;
			}

	/**
	 * Reads taxis and their properties from a file.
	 */
	public static TaxiDataProxy readTaxis(String source) {
			TaxiDataProxy tdp = new TaxiDataProxy();
			
			int index = 0;
			File file = new File(source);
			String newLine = null;
			String filtered = new String("");
			int i;
			/***/
			int taxiNumber = -1;
			int passengers = -1;
			int size = -1;
			String properties = new String("");
			/***/
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((newLine = br.readLine()) != null) {
					if (newLine.startsWith("//")) newLine = br.readLine();
					for (i = 1; i <= 4; i++) {					
						index = newLine.indexOf(',');
						if(index != -1)
						filtered = newLine.substring(0, index).trim();
						else
							filtered = newLine.substring(0).trim();
						switch (i) {
							case 1 :
								taxiNumber = Integer.valueOf(filtered).intValue();
								break;
							case 2 :
								passengers = Integer.valueOf(filtered).intValue();
								break;
							case 3 :
								size = Integer.valueOf(filtered).intValue();
								break;
							case 4 :
								properties = filtered;
								break;

						} //switch
						newLine = newLine.substring(index+1);
					}//for
					tdp.add(new TaxiStruct(taxiNumber,passengers,size,properties));
					}//while
				br.close();
			}
			catch (IOException e) {
				System.err.println("Error occurred trying to read file: \"" + source + "\"");
			}
			return tdp;
		}
	
	/**
	 * Reads drivers and their properties from a file.
	 * @param source
	 * @return
	 */
	public static DriverDataProxy readDriver(String source) {
		//	DEBUGString content = new String("");
			DriverDataProxy drivers = new DriverDataProxy();
			int index = 0;
			File file = new File(source);
			String newLine = null;
			String filtered = new String("");
			int i;
			/***/
			int driverNumber = -1;
			String name = new String("");
			String properties = new String("");
			/***/
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((newLine = br.readLine()) != null) {
					if (newLine.startsWith("//")) newLine = br.readLine();
					for (i = 1; i <= 3; i++) {					
						index = newLine.indexOf(',');
						if(index != -1)
						filtered = newLine.substring(0, index).trim();
						else
							filtered = newLine.substring(0).trim();
						switch (i) {
							case 1 :
								driverNumber = Integer.valueOf(filtered).intValue();
								break;
							case 2 :
								name = filtered;
								break;
							case 3 :
								properties = filtered;
								break;

						} //switch
						newLine = newLine.substring(index+1);
					}//for
					drivers.add(new DriverStruct(driverNumber,name,properties));
				}//while
				br.close();
			}
			catch (IOException e) {
				System.err.println("Error occurred trying to read file: \"" + source + "\"");
			}
			return drivers;
		}
}