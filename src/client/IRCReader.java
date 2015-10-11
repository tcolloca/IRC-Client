package client;

import java.nio.charset.Charset;

import util.IRCConnectionException;

/**
 * This class is occupied of reading from the connection channel and composing
 * the IRC messages to later send them to the listeners.
 * 
 * @author Tomas
 */
public interface IRCReader {

	/**
	 * Reads from the connection channel IRC messages and sends them to the
	 * listeners. The messages sent to the listeners should end with the proper
	 * IRC message ending, and they should be complete (As long as if in the
	 * connection where complete).
	 * 
	 * @param channel
	 *            IRCConnectionChannel to read from.
	 * @throws IRCConnectionException
	 *             If any reading or connection related exception occurs.
	 * @throws {@link IllegalArgumentException} If channel is null.
	 */
	public void read(IRCConnectionChannel channel)
			throws IRCConnectionException;

	/**
	 * Sets the charset that determines how messages will be read from the
	 * connection channel.
	 * 
	 * @param charset
	 *            Charset that determines how messages will be read from the
	 *            connection channel.
	 * @throws IllegalArgumentException
	 *             If charset is null.
	 */
	public void setCharset(Charset charset);

	/**
	 * Adds the listener to the listeners of the IRC messages read by the
	 * reader.
	 * 
	 * @param listener
	 *            Listener of the IRC messages read by the reader.
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addListener(IRCInputListener listener);

	/**
	 * Removes the listener from the listeners of the IRC messages read by the
	 * reader.
	 * 
	 * @param listener
	 *            Listener of the IRC messages read by the reader.
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void removeListener(IRCInputListener listener);

	/**
	 * Returns the charset that determines how messages will be read from the
	 * connection channel.
	 * 
	 * @return the cthe charset that determines how messages will be read from
	 *         the connection channel.
	 */
	public Charset getCharset();
}
