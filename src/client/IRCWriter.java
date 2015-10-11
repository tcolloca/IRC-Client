package client;

import java.nio.charset.Charset;

import util.IRCConnectionException;

/**
 * This class is occupied of writting to the connection channel the IRC messages
 * received and notifying the output listeners.
 * 
 * @author Tomas
 */
public interface IRCWriter {

	/**
	 * Writes the received message through the given channel. The message should
	 * end with the proper IRC message ending.
	 * 
	 * @param channel
	 *            Channel to write to.
	 * @param message
	 *            Message that will be written.
	 * @throws IRCConnectionException
	 *             If any writing or connection related exception occurs.
	 * @throws IllegalArgumentException
	 *             If channel or message are null, or if message doesn't end
	 *             with the proper IRC message ending.
	 */
	public void write(IRCConnectionChannel channel, String message)
			throws IRCConnectionException;

	/**
	 * Sets the charset that determines how messages will be written to the
	 * connection channel.
	 * 
	 * @param charset
	 *            Charset that determines how messages will be written to the
	 *            connection channel.
	 * @throws IllegalArgumentException
	 *             If charset is null.
	 */
	public void setCharset(Charset charset);

	/**
	 * Adds the listener to the listeners of the IRC messages written by the
	 * writer.
	 * 
	 * @param listener
	 *            Listener of the IRC messages written by the writer.
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addListener(IRCOutputListener listener);

	/**
	 * Remove the listener from the listeners of the IRC messages written by the
	 * writer.
	 * 
	 * @param listener
	 *            Listener of the IRC messages written by the writer.
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void removeListener(IRCOutputListener listener);

	/**
	 * Returns the charset that determines how messages will be written to the
	 * connection channel.
	 * 
	 * @return the charset that determines how messages will be written to the
	 *         connection channel.
	 */
	public Charset getCharset();
}
