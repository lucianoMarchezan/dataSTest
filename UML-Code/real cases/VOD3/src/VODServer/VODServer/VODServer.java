/* Decompiled by Mocha from VODServer.class */
/* Originally compiled from VODServer.java */

package VODServer;

import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Vector;

public class VODServer extends Thread 
{
   int listen_port;
   private boolean invokedStandalone;
   Hashtable movielist;
   Vector movietitle;
   
   /**
   @roseuid 3B203769018C
   */
   public VODServer(int port, String fileName) 
   {
        invokedStandalone = false;
        System.out.println("Make movie list...");
        movielist = new Hashtable();
        movietitle = new Vector();
        makemovielist(fileName);
        Client.setmovielist(movielist, movietitle);
        listen_port = port;
   }
   
   /**
   @roseuid 3B2037690196
   */
   private void makemovielist(String fileName) 
   {
        FileInputStream file;
        try
        {
            file = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Can not found "+fileName+" file");
            System.exit(-1);
            return;
        }
        if (file == null)
            return;
        DataInputStream instream = new DataInputStream(file);
        try
        {
            String title;
            String actor;
            String director;
            String description;
            String stream_url;
            int i;
            Movie movie;
            String line = instream.readLine();
            int movie_size = Integer.parseInt(line);
            for (i = 0; i < movie_size; i++)
            {
                title = instream.readLine();
                actor = instream.readLine();
                director = instream.readLine();
                description = instream.readLine();
                stream_url = instream.readLine();
                movie = new Movie(title, actor, director, description, stream_url);
                movielist.put(title, movie);
                movietitle.addElement(title);
            }
            instream.close();
            file.close();
        }
        catch (Exception e)
        {
            System.out.println("Error during reading movie.txt");
            System.exit(-1);
        }
   }
   
   /**
   @roseuid 3B20376901A0
   */
   public void run() 
   {
        System.out.println("Start stream server...");
        try
        {
            ServerSocket server = new ServerSocket(listen_port);
            while (true)
            {
                Socket cli = server.accept();
                Client new_client = new Client(cli);
                new_client.start();
                System.out.println(String.valueOf("New client arrives from ").concat(String.valueOf(cli.getInetAddress().getHostName())));
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException occurred while waiting a client");
            System.exit(-1);
        }
   }
   
   /**
   @roseuid 3B20376901A1
   */
   public static void main(String args[]) 
   {
        int port = 0;
		String fileName = "movie.txt";

        if (args.length != 2)
        {
            System.out.println("Usage : java VODServer [port] [movie file]");
            System.exit(-1);
        }
        try
        {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e)
        {
            System.out.println("Only integer value of port is possible");
            System.exit(-1);
        }

		fileName = args[1];

        VODServer vODServer = new VODServer(port, fileName);
        vODServer.invokedStandalone = true;
        vODServer.start();
   }
}
