package GameSystem;

import java.io.*;

/*
 * A class of file parsers.  This is a very simple stream like class that can read doubles,
 * Strings and integers from a certain file.
 */

class FileParser{
	protected StreamTokenizer $st;
	
	// Constructor
	/**
	  * Create a new file parser with a given reader
	  *
	  * @param reader: The reader you want to use
	  */
	public FileParser(Reader reader){
		// Default state ok ?
		$st = new StreamTokenizer(new BufferedReader(reader));
		$st.resetSyntax();
		$st.wordChars('0', '9');
		$st.wordChars('-', '-');
		$st.wordChars('.', '.');
		$st.wordChars('\\', '\\');
		$st.wordChars('a', 'z');
		$st.wordChars('A', 'Z');
		$st.whitespaceChars(' ', ' ');
		$st.whitespaceChars('\t', '\t');
		$st.whitespaceChars('\r', '\r');
		$st.whitespaceChars('\n', '\n');
		$st.eolIsSignificant(false);
	}

	//INSPECTORS
	/**
	  *	Read and return the next token
	  */
	private String readNext() throws IOException{
		$st.nextToken();
		if($st.ttype == StreamTokenizer.TT_WORD)
		return($st.sval);
      	else
      	{	switch($st.ttype){
				case StreamTokenizer.TT_WORD:System.out.print("Word token");
	  					break;
				case StreamTokenizer.TT_NUMBER:System.out.print("Number token");
	  					break;
				case StreamTokenizer.TT_EOL:
	  			return(readNext());
				case StreamTokenizer.TT_EOF:
	  			;
			throw new EOFException("End of file");
			}
			throw new IOException("Syntax error");
		}
	}
  
  	/**
	  * Read an int from the input file
	  *
	  * @return The int read from the input file
	  */
	public int readInt() throws IOException{
		String str = readNext();
		return((Integer.valueOf(str)).intValue());
	}

	/**
	  * Read a double from the input file
	  *
	  * @return The double read from the input file
	  */
	public double readDouble() throws IOException{
		String str = readNext();
		return((Double.valueOf(str)).doubleValue());
	}

	/**
	  * Read a string from the input file
	  *
	  * @return The string read from the input file
	  */
	public String readString() throws IOException{
		String str=readNext();
		return str;
	}
}


