/* Decompiled by Jasmine from Client.class */
/* Originally compiled from Client.java */

package VODServer;

import java.io.*;
import java.util.*;
import java.net.Socket;

public class Client extends Thread
{
    static Hashtable movielist;
    static Vector movietitle;
    Socket sock;
    DataInputStream instream;
    DataOutputStream outstream;
    boolean connected;

    public Client(Socket sock)
    {
        connected = true;
        this.sock = sock;
        try
        {
            instream = new DataInputStream(sock.getInputStream());
            outstream = new DataOutputStream(sock.getOutputStream());
        }
        catch (IOException e)
        {
            connected = false;
        }
    }

    public void run()
    {
		String parameter;
		StringTokenizer tok;
		String request;
		String command;
		
        do
        {
			try {
	            request = instream.readUTF();
			} catch (Exception ex) { request = ""; }
            System.out.println(String.valueOf("Request : ").concat(String.valueOf(request)));
			try {
	            tok = new StringTokenizer(request);
				command = tok.nextToken();
			} catch (Exception ex) { stop(); destroy(); return; }
			//************************************************************
			// here the client is shut down!
            if (tok.hasMoreTokens())
                parameter = request.substring(request.indexOf(tok.nextToken()));
            else
                parameter = "";
            if (command.indexOf("LIST") >= 0)
			{
				try {
					sendmovielist();
				} catch (Exception ex) { }
			}
            else if (command.indexOf("GET") >= 0)
			{
				try {
		            sendmovieinfo(parameter);
				} catch (Exception ex) { }
            }
			else if (command.equals(""))
				continue;
			else if (command.indexOf("REQUEST_TERMINATION") >= 0)
			{
				try {
					sendTerminationAcknowledgement();
					instream.close();
					outstream.close();
					sock.close();
					this.stop();
				} catch (Exception ex) { }

				return;
			}
            //if (!connected)
        }
        while (true);
		
			/*
        if (command.indexOf("PLAY") <= 0) goto 0 else 122;
        playmovie(parameter);
        pop e
        System.out.println("IOException occurred while reading or writing to socket");
        pop local6
        return;
*/
    }

    void sendmovielist()
        throws IOException
    {
        Enumeration e;
        String title;
        Vector titlelist = new Vector();
        outstream.writeInt(movielist.size());
        for (e = movietitle.elements(); e.hasMoreElements(); )
        {
            title = (String)e.nextElement();
            outstream.writeUTF(title);
        }
        outstream.flush();
    }

    void sendmovieinfo(String parameter)
        throws IOException
    {
        Movie movie = (Movie)movielist.get(parameter);
        if (movie == null)
        {
            outstream.writeUTF("NONE");
            return;
        }
        outstream.writeUTF("FOUND");
        outstream.writeUTF(movie.getactor());
        outstream.writeUTF(movie.getdirector());
        outstream.writeUTF(movie.getdescription());
        outstream.writeUTF(movie.getstream_url());
        outstream.flush();
    }

    void playmovie(String parameter)
        throws IOException
    {
        Movie movie = (Movie)movielist.get(parameter);
        if (movie == null)
        {
            outstream.writeUTF("NONE");
            return;
        }
        outstream.writeUTF("FOUND");
        outstream.flush();
    }

    void sendTerminationAcknowledgement()
        throws IOException
    {
        outstream.writeUTF("CONFIRM_TERMINATION");
        outstream.flush();
    }

    public static void setmovielist(Hashtable tmovielist, Vector tmovietitle)
    {
        movielist = tmovielist;
        movietitle = tmovietitle;
    }
}
