/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.net.URL;

public class VODClient extends Applet
{
    boolean isStandalone;
    BorderPanel bevelPanel2;
    Button buttonControl1;
    Button buttonControl2;
    Button buttonControl3;
    BorderPanel bevelPanel3;
    Label label1;
    Label label2;
    ListFrame listframe;
    ServerReq server;
    Movie curmovie;
    BorderPanel panel1;
    BorderPanel panel2;
    Label label3;
    Video video;
    Thread vthread;

    public VODClient()
    {
        isStandalone = false;
        bevelPanel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        buttonControl1 = new Button();
        buttonControl2 = new Button();
        buttonControl3 = new Button();
        bevelPanel3 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        label1 = new Label();
        label2 = new Label();
        panel1 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        panel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        label3 = new Label();
        curmovie = null;
        video = new Video(panel1, label1, label2, this);
    }

    void buttonControl1_actionPerformed(ActionEvent actionEvent)
    {
        listframe.show();
    }

    void buttonControl2_actionPerformed(ActionEvent actionEvent)
    {
        if (buttonControl2.getLabel().equals("PLAY"))
        {
            if (curmovie == null)
                return;
            vthread = new Thread(video);
            vthread.start();
            buttonControl2.setLabel("PAUSE");
        }
        else if (buttonControl2.getLabel().equals("PAUSE"))
        {
            vthread.suspend();
            buttonControl2.setLabel("RESUME");
        }
        else
        {
            vthread.resume();
            buttonControl2.setLabel("PAUSE");
        }
    }

    void buttonControl3_actionPerformed(ActionEvent actionEvent)
    {
        if (curmovie == null)
            return;
        vthread.stop();
        video.stopmovie();
        buttonControl2.setLabel("PLAY");
    }

    public final void clearmovie()
    {
        vthread.stop();
        video.setvideostream(null);
        curmovie = null;
        label3.setText("Title : ");
    }

    public final void destroy()
    {
        vthread.stop();
        video.stopmovie();
        buttonControl2.setLabel("PLAY");
    }

    public final String getAppletInfo()
    {
        return "Applet Information";
    }

    public String getParameter(String string1, String string2)
    {
        return isStandalone ? System.getProperty(string1, string2) : ((getParameter(string1) != null) ? getParameter(string1) : string2);
    }

    public final String[][] getParameterInfo()
    {
        return null;
    }

    public final void init()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String string = getParameter("rate", "off");
        if (!string.equals("off"))
            video.setratecontrol(true);
        short s = 5006;

        server = new ServerReq( "192.168.1.101", s);
        server.connect();
        listframe = new ListFrame(server, this);
    }

    private final void jbInit()
        throws Exception
    {
        setLayout(null);
        setBackground(Color.lightGray);
        setSize(new Dimension(376, 407));
        bevelPanel2.setBounds(new Rectangle(5, 314, 364, 87));
        bevelPanel2.setLayout(null);
        buttonControl1.setBounds(new Rectangle(14, 22, 47, 41));
        buttonControl1.setLabel("Movies");
        buttonControl1.addActionListener(new VODClient$1(this));
        buttonControl2.setBounds(new Rectangle(85, 22, 52, 45));
        buttonControl2.setLabel("PLAY");
        buttonControl2.addActionListener(new VODClient$2(this));
        buttonControl3.setBounds(new Rectangle(156, 21, 52, 45));
        buttonControl3.setLabel("STOP");
        bevelPanel3.setLayout(null);
        bevelPanel3.setBounds(new Rectangle(229, 15, 124, 56));
        buttonControl3.addActionListener(new VODClient$3(this));
        label1.setBounds(new Rectangle(7, 5, 110, 22));
        label2.setBounds(new Rectangle(9, 27, 110, 21));
        label2.setText("Time :");
        panel1.setBounds(new Rectangle(5, 63, 364, 244));
        panel2.setBounds(new Rectangle(5, 10, 364, 45));
        panel2.setLayout(null);
        label3.setFont(new Font("Dialog", 3, 20));
        label3.setBounds(new Rectangle(32, 11, 330, 26));
        label3.setText("Title :");
        add(bevelPanel2, null);
        bevelPanel2.add(buttonControl1, null);
        bevelPanel2.add(buttonControl2, null);
        bevelPanel2.add(buttonControl3, null);
        bevelPanel2.add(bevelPanel3, null);
        bevelPanel3.add(label1, null);
        bevelPanel3.add(label2, null);
        add(panel1, null);
        add(panel2, null);
        panel2.add(label3, null);
    }

    public static void main(String astring[])
    {
        VODClient vODClient = new VODClient();
        vODClient.isStandalone = true;
        Frame frame = new Frame();
        frame.setTitle("Applet Frame");
        frame.add(vODClient, "Center");
        vODClient.init();
        vODClient.start();
        frame.setSize(393, 441);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dimension.width - frame.getSize().width) / 2, (dimension.height - frame.getSize().height) / 2);
        frame.setVisible(true);
    }

    public final void selectmovie(String string)
    {
        curmovie = server.getmovie(string);
        listframe.setVisible(false);
        if (curmovie == null)
            return;
        if (vthread != null && vthread.isAlive())
        {
            vthread.stop();
            video.stopmovie();
            buttonControl2.setLabel("PLAY");
        }
        panel1.clearscreen();
        video.setvideostream(curmovie.getstream_url());
        label3.setText(new StringBuffer("Title : ").append(string).toString());
    }

    public final void stopmovie()
    {
        if (curmovie == null)
            return;
        buttonControl2.setLabel("PLAY");
    }
}
