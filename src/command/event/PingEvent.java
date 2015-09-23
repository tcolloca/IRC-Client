package command.event;

import event.IRCEventListener;

public class PingEvent implements IRCEvent {

	private String server;

	public PingEvent(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		this.server = server;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onPing(server);
	}

}
