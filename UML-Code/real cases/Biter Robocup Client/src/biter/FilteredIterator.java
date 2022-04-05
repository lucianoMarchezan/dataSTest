//-*- Mode: JDE -tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*-
package biter;
import java.util.Iterator;

/**
  * A generic class that can be used to iterate through
  * lists, returning only data with a certain filter value.
  * The objects contained in the list of information must
  * implement Comparable.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.7 $, $Date: 2001/02/27 22:24:31 $
  *
*/
class FilteredIterator implements Iterator
{
	/**
	 * Iterator that the FilteredIterator is 
	 * constructed from.  It actually contains all
	 * the data that the FilteredIterator uses.
  */
	private Iterator  iterator;
	/** The filter value of the iterator. */
	private Comparable  filterValue;
	/** Object returned by the next() method. */
	private Object    next;

	/**
    * Constructs a new FilteredIterator from
    * the specified iterator and filter value.
    * @param iterator the iterator containing
    * the data that the FilteredIterator will
    * use
    * @param filterValue an object implementing
    * Comparable that will provide the filter value.
    * This object is usually a String, but does not have to be. 
    * @see Comparable
  */
	public FilteredIterator( Iterator iterator, 
													 Comparable filterValue )
	{
		this.iterator = iterator;
		this.filterValue = filterValue;
		// initialize the next object field to null... this will
		// serve as a flag to the hasNext() method, if next is null,
		// hasNext() will iterate to the next object that meets the
		// filtering critera (if any) PAB 4/11/2000
		next = null;
	}

	/**
    * Method to determine whether or not there are
    * any more values in the iterator that match
    * filterValue.
    * @return true if there are any more values
    * matching filterValue, false if there are not.
  */
	public boolean hasNext()
	{
		boolean rtnval = false;

		// if next contains an object reference, it has not been 
		// consumed therefore at least one object that meets the 
		// filtering critera is left... return true
		if( next != null )
      rtnval = true;
		else
		{
			// traverse the base iterator and to see if any objects 
			// remain that pass the filtering critera
			while( iterator.hasNext() )
			{
				Object object = iterator.next();

        // verify the object implements the comparable interface
        // before casting to type Comparable
				if( object instanceof Comparable && 
						((Comparable)object).compareTo( filterValue ) == 0 )
				{
					next = object;            
					rtnval = true;
					break;
				}
			}
		}

		return rtnval;
	}

	/**
    * Gets the next value matching filterValue.
    * @return the next Object matching filterValue
    * if one exists, null if one does not.
  */
	public Object next()
	{
		// it is possible to call next() without calling hasNext() first.
		// if next is null... call hasNext() first to be certain that there
		// is not an Object that meets the filtering critera
		if( next == null )
      this.hasNext();

    // copy the value of next to rtnval
		Object rtnval = next;

		// set next to null so that the hasNext() method will 
		// know that it has been consumed
		next = null;

		return rtnval;
	}

	/**
    * Removes the current value in the iterator.
  */
	public void remove()
	{
		// call the remove method of the underlying iterator
		iterator.remove();
	}
} 
