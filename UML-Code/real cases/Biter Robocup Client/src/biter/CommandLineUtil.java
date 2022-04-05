//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;

/**
  * A utility for command line arguments.
  *
  * @author Paul A. Buhler, 3/10/2000
  * @version $Revision: 1.7 $, $Date: 2001/02/27 22:24:31 $
*/
public class CommandLineUtil
{
	/**
	 * Searches command line arguments for an entry
	 * that starts with the prefix string.  If found the
	 * prefix is stripped and the remaining String is returned.
	 * If it is not, the default string is returned.
	 * @param args the command line arguments
	 * @param prefix the parameter to search for
	 * @defaultValue the default return value
	 * @return the value of the parameter, or
	 * defaultValue if it cannot be found
	 */
	public static String LocateParameter( String args[], String prefix, 
																				String defaultValue )
	{
		String rtnString = defaultValue;

		for( int i=0; i<args.length; i++ )
		{
			if( args[i].startsWith( prefix ) )
			{
        // strip the prefix from the string
				rtnString = args[i].substring( prefix.length() );
			}
		}

		return( rtnString );
	}
} 
