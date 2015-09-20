package reader;

import java.nio.channels.SocketChannel;

import util.IRCException;

public interface IRCReader {

	/**
	 * Reads from the channel received an IRC message and feeds the
	 * {@link IRCClient} with it.
	 * 
	 * @param channel
	 *            Channel to read from.
	 * @throws IRCException
	 *             If any reading or connection related exception occurs.
	 * @throws {@link IllegalArgumentException} If channel is null.
	 */
	public void read(SocketChannel channel) throws IRCException;
}
