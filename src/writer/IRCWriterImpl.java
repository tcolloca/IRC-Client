package writer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import util.IRCException;

public class IRCWriterImpl implements IRCWriter {

	private static final int BUFFER_SIZE = 512;

	@Override
	public synchronized void write(SocketChannel channel, String message)
			throws IRCException {
		if (channel == null || message == null) {
			throw new IllegalArgumentException();
		}
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		byte[] byteArr = message.getBytes();
		int bytesRead = 0, bytesToBeRead;
		while (bytesRead < message.length()) {
			bytesToBeRead = Math.min(buffer.capacity(), message.length()
					- bytesRead);
			buffer.put(byteArr, bytesRead, bytesToBeRead);
			bytesRead += bytesToBeRead;
			buffer.flip();
			if (bytesRead < message.length()) {
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
		char[] arr = new char[buffer.remaining()];
		byte[] buffArr = buffer.array();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (char) buffArr[i];
		}
		System.out.print("SENDING: " + new String(arr));
	}
}
