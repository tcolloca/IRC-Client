package event;

import java.util.ArrayList;
import java.util.List;

public class IRCEventBroadcaster implements IRCEventListener {

	private List<IRCEventListener> listeners;

	public IRCEventBroadcaster() {
		super();
		listeners = new ArrayList<IRCEventListener>();
	}

	/**
	 * Adds the listener to the listeners' list.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void onConnect() {
		for (IRCEventListener listener : listeners) {
			listener.onConnect();
		}
	}

	@Override
	public void onPing(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		for (IRCEventListener listener : listeners) {
			listener.onPing(server);
		}
	}

	@Override
	public void onLogin() {
		for (IRCEventListener listener : listeners) {
			listener.onLogin();
		}
	}

	@Override
	public void onNick(String user, String nickname) {
		if (nickname == null) {
			throw new IllegalArgumentException();
		}
		for (IRCEventListener listener : listeners) {
			listener.onNick(user, nickname);
		}
	}

	@Override
	public void onNotice(String msgtarget, String message) {
		if (msgtarget == null || message == null) {
			throw new IllegalArgumentException();
		}
		for (IRCEventListener listener : listeners) {
			listener.onNick(msgtarget, message);
		}
	}
}
