package client;

import util.IRCException;

public interface IRCClient {

	/**
	 * Connects to the server and then it occupies of the connection, reading
	 * and sending messages.
	 * 
	 * @throws IRCException
	 *             If some other I/O or connection error occurs.
	 */
	public void run() throws IRCException;

	/**
	 * Feeds the client with a IRC message that was read.
	 * 
	 * @param message
	 *            Message that has been read.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public void feed(String message);
}
