/* Decompiled by Mocha from VODClient$2.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class VODClient$2 implements ActionListener
{
    private VODClient this$0 = null;

    VODClient$2(VODClient vODClient)
    {
		this$0 = vODClient;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.buttonControl2_actionPerformed(actionEvent);
    }
}
