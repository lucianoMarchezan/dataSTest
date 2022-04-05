package biter;
import java.io.*;
import java.net.*;

/**
  * Wraps up the Datagram server communications at a higher level.
  * If the logfile argument to the constructor is not null, then
  * all communications sent and received are saved to the specified
  * file.
  *
  * @author Paul A. Buhler
  * @version $Revision: 1.15 $, $Date: 2001/02/27 22:24:31 $
  *
*/

public class DatagramWrapper
{
	/** Socket through which network communications are sent and received. */
	private DatagramSocket	socket;
	/** Name of the server to connect to. */
	private String 			hostName;
	/** Holds the address for the server. */
	private InetAddress		host;
	/** Port to connect to the server through. */
	private int 			port;
	/** Used for file communications. */
	private PrintWriter 	logger;
	/** Size of messages to be received from the server. */
	private int 			bufsize;

	/**
	  * Opens a Datagram socket with the specified host, port, and buffer size.
	  * @param hostName the name of the server to connect to
	  * @param port the port through which to connect to the host server
	  * @param bufsize the size of the buffer to receive incomming communications
	  * @param logfile contains the name of the file to record all communications to.
	  *        If it is null, then no file is created.
	  * @throws SocketException If there is an error connecting to the server.
	  * @throws IOException If there is an error opening the file.
	*/
	public DatagramWrapper( String hostName, int port, 
													int bufsize, PrintWriter logger ) 
		throws SocketException, IOException
	{
		this.hostName = hostName;
		host = InetAddress.getByName( hostName );
		this.port = port;
		this.bufsize = bufsize;
		this.logger = logger;

		socket = new DatagramSocket();

	}

	/**
	  * Closes the network socket and log file if it exists.
	*/
	public void finalize()
	{
		socket.close();

		if( logger != null )
	    logger.close();
	}


	/**
	  * Sends a message to the server.
	  * Originally authored by Krzysztpf Langer and was distributed as 
	  * part of the Krislet code.  Modifications have been made by PAB
	  * to support the logging feature.
	  * @param message the message to be sent to the server
	*/
	public void send(String message)
	{
		byte[] buf = message.getBytes();

		DatagramPacket packet = new DatagramPacket(buf, buf.length, host, port);

		if( logger != null ) 
		{
			logger.println(" S : " + System.currentTimeMillis() + 
										 " " + message );
			// flush the data to be sure it makes it onto the disk
			logger.flush();
		}

		try
		{
			socket.send(packet);
		}
		catch(IOException e)
		{
			System.err.println("socket sending error " + e);
		}
	}
	
	/**
	  * Waits forever and receives a message from the server.
	  * @return the message received from the server
	*/
	public String receive() throws EOFException
	{
		byte[] buffer = new byte[bufsize];

		DatagramPacket packet = new DatagramPacket(buffer, bufsize);

		String rtnval = null;
		try
		{
			socket.setSoTimeout(0); //in case we had called the other receive();
			socket.receive(packet);
		}
		catch(IOException e)
		{
			System.err.println("socket receiving error " + e);
		}

		// update the port for further communications with the server
		port = packet.getPort();
		if (port == -1)
			throw new EOFException("Soccer Server died");

		rtnval = new String( buffer, 0, packet.getLength() );

		if( logger != null ) 
		{
			logger.println(" R : " + System.currentTimeMillis() + 
										 " " + rtnval );
			// flush the data to be sure it makes it onto the disk
			logger.flush();
		}

		return rtnval; 
	}


	/**
		 Waits some time and receives a message from the server.
		 On timeout it throws an InterruptedIOException
	  * @param wait the number of milliseconds to wait before a timeout
	  * @return the message received from the server, "" if there was a timeout.
	*/
	public String receive(long wait) throws InterruptedIOException, EOFException
	{
// 		System.out.println("receive: wait=" + wait);

		byte[] buffer = new byte[bufsize];

		DatagramPacket packet = new DatagramPacket(buffer, bufsize);

		String rtnval = null;

		try
		{
			socket.setSoTimeout((int)wait);
			socket.receive(packet);
		}
		catch(IOException e)
		{
			if (e instanceof InterruptedIOException)
				throw new InterruptedIOException("Receive timed out");
			else if (e.getMessage().equals("socket closed"))
				throw new EOFException("Soccer Server died");
			System.err.println("socket receiving error " + e);
		}


		// update the port for further communications with the server
		port = packet.getPort();
		if (port == -1)
			throw new EOFException("Soccer Server died");

		rtnval = new String( buffer, 0, packet.getLength() );

		if( logger != null ) 
		{
			logger.println(" R : " + System.currentTimeMillis() + 
										 " " + rtnval );
			// flush the data to be sure it makes it onto the disk
			logger.flush();
		}

		return rtnval; 
	}

	/**
	  * Allows classes with access to the DatagramWrapper to
	  * print debugging messages to the logfile.
	  * @param line text to send to the logfile
	*/
	public void println(String line)
	{
		if (logger != null)
	    logger.println(line);
	}
}
