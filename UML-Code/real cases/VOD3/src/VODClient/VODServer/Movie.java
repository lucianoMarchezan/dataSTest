/* Decompiled by Mocha from Movie.class */
/* Originally compiled from Movie.java */

package VODServer;

import java.io.Serializable;
import java.util.Vector;

public class Movie implements Serializable 
{
   String title;
   String actor;
   String director;
   String description;
   Vector screen_shut;
   String stream_url;
   static final String default_string = "no info";
   
   /**
   @roseuid 3B2037680159
   */
   public Movie(String title, String actor, String director, String description, String stream_url) 
   {
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.description = description;
        this.stream_url = stream_url;
        screen_shut = new Vector();
   }
   
   /**
   @roseuid 3B2037680177
   */
   public Movie(String title, String stream_url) 
   {
        this(title, "no info", "no info", "no info", stream_url);
   }
   
   /**
   @roseuid 3B2037680182
   */
   public void addscreen_shut(String shut_url) 
   {
        screen_shut.addElement(shut_url);
   }
   
   /**
   @roseuid 3B203768018C
   */
   public String gettitle() 
   {
        return title;
   }
   
   /**
   @roseuid 3B2037680195
   */
   public String getactor() 
   {
        return actor;
   }
   
   /**
   @roseuid 3B2037680196
   */
   public String getdescription() 
   {
        return description;
   }
   
   /**
   @roseuid 3B2037680197
   */
   public String getdirector() 
   {
        return director;
   }
   
   /**
   @roseuid 3B203768019F
   */
   public Vector getscreen_shut() 
   {
        return screen_shut;
   }
   
   /**
   @roseuid 3B20376801A0
   */
   public String getstream_url() 
   {
        return stream_url;
   }
}
