package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import util.IRCConnectionException;

class IRCConnectionHandlerWithNonBlockingNio implements IRCConnectionHandler {

	IRCConnectionHandlerWithNonBlockingNio() {
	}

	@Override
	public IRCConnectionChannel openConnection(String hostname, int port)
			throws IRCConnectionException {
		if (hostname == null) {
			throw new IllegalArgumentException();
		}
		try {
			SocketChannel channel = SocketChannel.open();
			channel.configureBlocking(false);
			if (channel.isConnected()) {
				throw new IllegalStateException();
			}
			System.out.println("Connecting to server and port...");
			channel.connect(new InetSocketAddress(hostname, port));
			System.out.println("Connected to server and port.");
			int i = 0;
			while (!channel.finishConnect()) {
				if (i++ % 10000 == 0) {
					System.out.println("Trying to connect...");
				}
			}
			return new IRCNioSocketChannel(channel);
		} catch (IOException e) {
			throw new IRCConnectionException();
		}
	}

}
