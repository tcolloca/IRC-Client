package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import util.IRCConnectionException;

class IRCNioSocketChannel implements IRCConnectionChannel {

	private SocketChannel channel;

	IRCNioSocketChannel(SocketChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
	}

	@Override
	public void read(ByteBuffer buffer) throws IRCConnectionException {
		try {
			channel.read(buffer);
		} catch (IOException e) {
			throw new IRCConnectionException();
		}
	}

	@Override
	public void write(ByteBuffer buffer) throws IRCConnectionException {
		try {
			channel.write(buffer);
		} catch (IOException e) {
			throw new IRCConnectionException();
		}
	}
}
