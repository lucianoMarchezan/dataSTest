//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.*;

/**
  * Holds the necessary information about field objects.
  * Uses regular expressions in the compareTo method
  * for greater flexibility.
  * @author Paul A. Buhler
  * @version $Revision: 1.10 $, $Date: 2001/02/27 22:24:31 $
  */
class ObjectInfoContainer extends SensorInput
{
  /** A Vector of ObjectInfo objects */
	public ArrayList objects;

	public ObjectInfoContainer() 
	{
	}

  public ObjectInfoContainer(String data, long timeReceived) 
	{
    realTime = timeReceived;
    parse(data);
	}

  public ObjectInfoContainer(String data){
    parse(data);
  }

  /**
   * Takes a 'see' message from the server and parses the information
   * it contains into a list of visual data that is used by the player.
   */
  private void parse (String data){

    objects = null;
    // init the string tokenizer with the data sans the outer '(' & ')'
    StringTokenizer strtok = new StringTokenizer( data.substring(1, data.length()-1), "(" );
  
    String token = strtok.nextToken();

    // verify that this is indeed a 'see' message
    if( token.startsWith("see" ) )
		{
      // create the ArrayList
      objects = new ArrayList();

      timeStamp = Integer.parseInt( token.substring( 4 ).trim());
      
      // parse the seen objectss
      while( strtok.hasMoreTokens() )
			{
        String objName;
        String objInfo = null;
        StringTokenizer strtokObjInfo;
        ObjectInfo objectInfo;

        // the next token is the object name
        objName = strtok.nextToken( ")" );

        // if there are preceeding '(' replace with spaces and then trim
        objName = objName.replace('(',' ').trim();
        objectInfo = new ObjectInfo( objName, timeStamp );
        try{
          objInfo = strtok.nextToken();
        }
        catch (NoSuchElementException e){
          break;
        }

        strtokObjInfo = new StringTokenizer( objInfo );
        for( int i= 1; strtokObjInfo.countTokens() > 0; i++ )
        {
          // the cases below indicate position number; for instance, distance
          // is always the first argument, direction the second,...
          // of course not every argument will appear for each object... the outer
          // loop takes care of this issue.
          double num;
          try {
            num = Double.parseDouble( strtokObjInfo.nextToken());
          }
          catch (NumberFormatException e){ //its "nan", so set it to 0 instead
            num = 0;
          }
          switch( i )
          {
          case 1:
            objectInfo.setDistance( num);
            break;
          case 2:
            objectInfo.setDirection(num);
            break;
          case 3:
            objectInfo.setDistChng(num);
            objectInfo.setGotChanges(true);
            break;
          case 4:
            objectInfo.setDirChng(num);
            break;
          case 5:
            objectInfo.setBodyDir(num);
            break;
          case 6:
            objectInfo.setHeadDir(num);
            break;
          } // end switch
        } // end for
        
        objects.add( objectInfo );
			} // end while
		} // end if "see"
    else
      System.out.println("ERROR: ObjectInfoContainer: String does not have see: " + data);
	}


  public ObjectInfoContainer(ArrayList objects) 
	{
    this.objects = objects;
	}

  /** Turn it into a string, for debugging purposes */
  public String toString() {
    String s = new String();
    if (objects == null)
      return "EMPTY\n";
    for (Iterator i = objects.iterator(); i.hasNext();){
      ObjectInfo o = (ObjectInfo)i.next();
      s += o.toString() + "\n";
    }
    return s;
  }

}
