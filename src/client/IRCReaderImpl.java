package client;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import util.IRCConnectionException;
import util.IRCValues;

class IRCReaderImpl implements IRCReader, IRCValues {

	private static final char NULL = '\0';
	private static final int BUFFER_SIZE = 512;

	private List<IRCInputListener> listeners;
	private Charset charset;
	private byte[] byteBuffer;
	private int index;
	private boolean keepReading;

	IRCReaderImpl() {
		this.listeners = new ArrayList<IRCInputListener>();
		this.byteBuffer = new byte[MSG_MAX_SIZE];
		this.index = 0;
	}

	@Override
	public void read(IRCConnectionChannel channel)
			throws IRCConnectionException {
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

	@Override
	public void setCharset(Charset charset) {
		if (charset == null) {
			throw new IllegalArgumentException();
		}
		this.charset = charset;
	}

	@Override
	public void addListener(IRCInputListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void removeListener(IRCInputListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.remove(listener);
	}

	@Override
	public Charset getCharset() {
		return charset;
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
				for (IRCInputListener listener : listeners) {
					listener.onRead(message);
				}
				byteBuffer = new byte[MSG_MAX_SIZE];
				index = 0;
			}
		}
	}
}
