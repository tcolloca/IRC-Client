package client;

import java.nio.ByteBuffer;

import util.IRCConnectionException;

public interface IRCConnectionChannel {

	/**
	 * @see java.nio.channels.SocketChannel#read
	 * 
	 * @param buffer
	 * @throws IRCConnectionException
	 *             If any connection or reading related exception occurs.
	 * @throws IllegalArgumentException
	 *             If buffer is null.
	 */
	public void read(ByteBuffer buffer) throws IRCConnectionException;

	/**
	 * @see java.nio.channels.SocketChannel#write
	 * 
	 * @param buffer
	 * @throws IRCConnectionException
	 *             If any connection or reading related exception occurs.
	 * @throws IllegalArgumentException
	 *             If buffer is null.
	 */
	public void write(ByteBuffer buffer) throws IRCConnectionException;
}
