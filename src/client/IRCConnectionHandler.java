package client;

import util.IRCConnectionException;

/**
 * This class is occupied of handling the connection to the server.
 * 
 * @author Tomas
 */
public interface IRCConnectionHandler {

	/**
	 * Opens a connection with the received hostname and port, and returns the
	 * channel where all messages will be sent and read.
	 * 
	 * @param hostname
	 *            Hostname to which it will try to connect.
	 * @param port
	 *            Port to which it will try to connect.
	 * @return the channel where all messages will be sent and read.
	 * @throws IRCConnectionException
	 *             If an exception occurs while trying to establish the
	 *             connection.
	 * @throws IllegalArgumentException
	 *             If hostname is null.
	 * @throws IllegalStateException
	 *             If it is already connected.
	 */
	public IRCConnectionChannel openConnection(String hostname, int port)
			throws IRCConnectionException;

}
