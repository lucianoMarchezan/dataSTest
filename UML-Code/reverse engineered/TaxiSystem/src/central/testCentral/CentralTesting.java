package testCentral;

import central.Central;

/*
 * Created on 26.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CentralTesting extends Central {
	public CentralTesting(){
		super();
	}
	
	public void sendSignal(int signal){
		super.comLinkTM.sendSignal(signal);
	}
}
