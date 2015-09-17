package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import util.IRCValues;

/**
 * This class is constantly reading from the channel given in the read method.
 * Each IRC message (ended with \r\n) is sent to the {@link IRCClient}.
 * 
 * @author Tomas
 */
public class IRCReader implements IRCValues {

	private static final char NULL = '\0';
	private static final int BUFFER_SIZE = 10;

	private IRCClient ircClient;
	private StringBuilder messageBuilder;
	private boolean keepReading;

	/**
	 * @param ircClient
	 *            The IRCClient that created this instance.
	 * @throws IllegalArgumentException
	 *             If ircClient is null.
	 */
	public IRCReader(IRCClient ircClient) {
		if (ircClient == null) {
			throw new IllegalArgumentException();
		}
		this.ircClient = ircClient;
		this.messageBuilder = new StringBuilder();
	}

	/**
	 * Reads endlessly from the channel received. When there is anything to be
	 * processed, it will stop reading until control is returned, and will feed
	 * the IRCClient with the message read.
	 * 
	 * @param channel
	 *            Channel where it will read of.
	 * @throws IOException
	 */
	public void read(SocketChannel channel) throws IOException {
		keepReading = true;
		ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		while (keepReading) {
			// Reads bytes from channel into readBuffer
			channel.read(readBuffer);
			// Flips the buffer so that it can be read
			readBuffer.flip();
			// If there is anything to read, feed the IRCParser
			if (readBuffer.hasRemaining()) {
				composeAndDivide(readBuffer);
			}
			// Compact the buffer so that all the read space is used
			readBuffer.compact();
		}
	}

	private void composeAndDivide(ByteBuffer buffer) {
		byte[] byteArr = new byte[BUFFER_SIZE];
		buffer.get(byteArr, 0, Math.min(byteArr.length, buffer.remaining()));
		for (byte b : byteArr) {
			if ((char) b == NULL) {
				continue;
			}
			messageBuilder.append((char) b);
			if (messageBuilder.toString().endsWith(MSG_END_SEQ)) {
				ircClient.feed(messageBuilder.toString());
				messageBuilder = new StringBuilder();
			}
		}
	}
}
