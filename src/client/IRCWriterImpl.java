package client;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import util.IRCConnectionException;

class IRCWriterImpl implements IRCWriter {

	private static final int BUFFER_SIZE = 512;

	private List<IRCOutputListener> listeners;
	private Charset charset;

	IRCWriterImpl() {
		this.listeners = new ArrayList<IRCOutputListener>();
	}

	@Override
	public synchronized void write(IRCConnectionChannel channel, String message)
			throws IRCConnectionException {
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
			channel.write(buffer);
		}
		while (buffer.hasRemaining()) {
			printBuffer(buffer);
			channel.write(buffer);
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
	public void addListener(IRCOutputListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void removeListener(IRCOutputListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.remove(listener);
	}

	@Override
	public Charset getCharset() {
		return charset;
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
