/* Decompiled by Mocha from VODClient$1.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class VODClient$1 implements ActionListener
{
    private VODClient this$0 = null;

    VODClient$1(VODClient vODClient)
    {
		this$0 = vODClient;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.buttonControl1_actionPerformed(actionEvent);
    }
}
