package writer;

import java.nio.channels.SocketChannel;

import util.IRCException;

public interface IRCWriter {

	/**
	 * Writes the received message through the given channel.
	 * 
	 * @param channel
	 *            Channel to write to.
	 * @param message
	 *            Message that will be written.
	 * @throws IRCException
	 *             If any writing or connection related exception occurs.
	 * @throws IllegalArgumentException
	 *             If channel or message are null.
	 */
	public void write(SocketChannel channel, String message)
			throws IRCException;
}
