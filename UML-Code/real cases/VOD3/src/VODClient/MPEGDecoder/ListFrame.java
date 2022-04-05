/* Decompiled by Mocha from ListFrame.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

public class ListFrame extends Frame
{
    BorderPanel bevelPanel1;
    BorderPanel bevelPanel2;
    List listControl1;
    Button buttonControl1;
    Button buttonControl2;
    Button buttonControl3;
    Button buttonControl4;
    Label label1;
    ServerReq server;
    Vector movielist;
    VODClient parent;
    Detail detail;
    ServerSelect serverselect;

    public ListFrame(ServerReq serverReq, VODClient vODClient)
    {
        bevelPanel1 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        bevelPanel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        listControl1 = new List();
        buttonControl1 = new Button();
        buttonControl2 = new Button();
        buttonControl3 = new Button();
        buttonControl4 = new Button();
        label1 = new Label();
        movielist = null;
        server = serverReq;
        parent = vODClient;
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        hostreset();
        detail = new Detail();
        serverselect = new ServerSelect(this);
    }

    void buttonControl1_actionPerformed(ActionEvent actionEvent)
    {
		// new server
        serverselect.show();
    }

    void buttonControl2_actionPerformed(ActionEvent actionEvent)
    {
		// select movie
        String string = listControl1.getSelectedItem();
        if (string != null)
            parent.selectmovie(string);
    }

    void buttonControl3_actionPerformed(ActionEvent actionEvent)
    {
		// show detail window
        String string = listControl1.getSelectedItem();
        if (string != null)
        {
            Movie movie = server.getmovie(string);
            if (movie != null)
            {
                detail.setmovie(movie);
                detail.show();
            }
        }
    }

    void buttonControl4_actionPerformed(ActionEvent actionEvent)
    {
		//cancel
        setVisible(false);
    }

    public void changeserver(String string)
    {
        server.connect(string);
        hostreset();
        parent.clearmovie();
    }

    public void hostreset()
    {
        if (server.isConnected())
            label1.setText(new StringBuffer("Connected to ").append(server.gethost()).toString());
        else
            label1.setText("Disconnected");
        listControl1.removeAll();
        if (!server.isConnected())
            return;
        movielist = server.getmovielist();
        if (movielist == null)
            return;
        for (Enumeration enumeration = movielist.elements(); enumeration.hasMoreElements(); )
        {
            String string = (String)enumeration.nextElement();
            listControl1.addItem(string);
        }
    }

    private void jbInit()
        throws Exception
    {
        setBackground(Color.lightGray);
        setSize(new Dimension(284, 360));
        setTitle("Movie List");
        bevelPanel1.setLayout(null);
        bevelPanel1.setBounds(new Rectangle(9, 30, 262, 37));
        bevelPanel2.setLayout(null);
        bevelPanel2.setBounds(new Rectangle(9, 72, 262, 249));
        listControl1.setBounds(new Rectangle(15, 15, 231, 171));
        buttonControl1.setBounds(new Rectangle(12, 197, 53, 39));
        buttonControl1.setLabel("Servers");
        buttonControl1.addActionListener(new ListFrame$1(this));
        buttonControl2.setBounds(new Rectangle(76, 196, 53, 39));
        buttonControl2.setLabel("Select");
        buttonControl2.addActionListener(new ListFrame$2(this));
        buttonControl3.setBounds(new Rectangle(139, 196, 53, 39));
        buttonControl3.setLabel("Detail");
        buttonControl3.addActionListener(new ListFrame$3(this));
        buttonControl4.setBounds(new Rectangle(199, 196, 53, 39));
        buttonControl4.setLabel("Cancel");
        buttonControl4.addActionListener(new ListFrame$4(this));
        label1.setFont(new Font("Dialog", 2, 14));
        label1.setBounds(new Rectangle(9, 4, 247, 28));
        //parent.setLayout(null);
        setLayout(null);
        add(bevelPanel1, null);
        bevelPanel1.add(label1, null);
        add(bevelPanel2, null);
        bevelPanel2.add(listControl1, null);
        bevelPanel2.add(buttonControl4, null);
        bevelPanel2.add(buttonControl3, null);
        bevelPanel2.add(buttonControl2, null);
        bevelPanel2.add(buttonControl1, null);
    }
}
