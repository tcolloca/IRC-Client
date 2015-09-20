package event;

import java.util.ArrayList;
import java.util.List;

import command.IRCCommand;

public class IRCEventDispatcherSingleThread implements IRCEventDispatcher {

	private List<IRCEventListener> listeners;

	public IRCEventDispatcherSingleThread() {
		super();
		listeners = new ArrayList<IRCEventListener>();
	}

	@Override
	public void addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
	}

	@Override
	public void onExecute(IRCCommand command) {
		if (command == null) {
			throw new IllegalArgumentException();
		}
		for (IRCEventListener listener : listeners) {
			command.onExecute(listener);
		}
	}
}
