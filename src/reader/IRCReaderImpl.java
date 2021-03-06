package reader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import util.IRCException;
import util.IRCValues;
import client.IRCClient;
import client.IRCClientImpl;

/**
 * This class is constantly reading from the channel given in the read method.
 * Each IRC message (ended with \r\n) is sent to the {@link IRCClientImpl}.
 * 
 * @author Tomas
 */
public class IRCReaderImpl implements IRCReader, IRCValues {

	private static final char NULL = '\0';
	private static final int BUFFER_SIZE = 512;

	private IRCClient ircClient;
	private Charset charset;
	private byte[] byteBuffer;
	private int index;
	private boolean keepReading;

	/**
	 * @param ircClient
	 *            The IRCClient that created this instance.
	 * @param charset
	 *            Charset of the messages being written.
	 * @throws IllegalArgumentException
	 *             If ircClient or charset are null.
	 */
	public IRCReaderImpl(IRCClient ircClient, Charset charset) {
		if (ircClient == null) {
			throw new IllegalArgumentException();
		}
		this.ircClient = ircClient;
		this.charset = charset;

		this.byteBuffer = new byte[MSG_MAX_SIZE];
		this.index = 0;
	}

	@Override
	public void read(SocketChannel channel) throws IRCException {
		keepReading = true;
		ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		while (keepReading) {
			// Reads bytes from channel into readBuffer
			try {
				channel.read(readBuffer);
			} catch (IOException e) {
				throw new IRCException();
			}
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
			byteBuffer[index++] = b;
			if (index >= 2 && byteBuffer[index - 2] == MSG_END_SEQ.charAt(0)
					&& byteBuffer[index - 1] == MSG_END_SEQ.charAt(1)) {
				String message = new String(byteBuffer, 0, index, charset);
				System.out.print("READING: " + message);
				ircClient.feed(message);
				byteBuffer = new byte[MSG_MAX_SIZE];
				index = 0;
			}
		}
	}
}
