
package testCentral;
import structures.TaxiStruct;
import tools.*;
import central.Operator;

public class TestTaxiState {
	
	public int points = 20;
	public boolean test(){
		TaxiStruct ts;
		try{
		Output.resetresult();
		CentralTesting cen = new CentralTesting();
		Operator op = new Operator(cen);
		
		cen.comLinkTM.loginSignalC(1,1);
		ts = cen.tdp.get(1);
		
		}
		catch(Exception e){
			return false;
		}
		
		if((Output.result.equals("Central:loginSignal\nComLinkTM:loginOk\nCentral:detailedPosition\n"))&&
				(ts.status==TaxiStruct.AVAILABLE)){
			
			System.out.println("Test successful");
			return true;
		}
		else{
			System.out.println("Test failed");
			return false;
		}
	}
}
