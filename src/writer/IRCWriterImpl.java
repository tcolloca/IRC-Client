package writer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import util.IRCException;

public class IRCWriterImpl implements IRCWriter {

	private static final int BUFFER_SIZE = 512;

	private Charset charset;

	/**
	 * @param charset
	 *            Charset of the messages being written.
	 * @throws IllegalArgumentException
	 *             If charset is null.
	 */
	public IRCWriterImpl(Charset charset) {
		this.charset = charset;
	}

	@Override
	public synchronized void write(SocketChannel channel, String message)
			throws IRCException {
		if (channel == null || message == null) {
			throw new IllegalArgumentException();
		}
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		byte[] byteArr = message.getBytes(charset);
		int bytesRead = 0, bytesToBeRead;
		while (bytesRead < byteArr.length) {
			bytesToBeRead = Math.min(buffer.capacity(), byteArr.length
					- bytesRead);
			buffer.put(byteArr, bytesRead, bytesToBeRead);
			bytesRead += bytesToBeRead;
			buffer.flip();
			if (bytesRead < byteArr.length) {
				buffer.compact();
			}
			printBuffer(buffer);
			try {
				channel.write(buffer);
			} catch (IOException e) {
				throw new IRCException();
			}
		}
		while (buffer.hasRemaining()) {
			printBuffer(buffer);
			try {
				channel.write(buffer);
			} catch (IOException e) {
				throw new IRCException();
			}
		}
	}

	private void printBuffer(ByteBuffer buffer) {
		byte[] arr = new byte[buffer.remaining()];
		byte[] buffArr = buffer.array();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = buffArr[i];
		}
		System.out.print("SENDING: "
				+ new String(arr, 0, arr.length, StandardCharsets.UTF_8));
	}
}
