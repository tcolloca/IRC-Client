package command.event;

import event.IRCEventListener;

public interface IRCEvent {

	/**
	 * This method is called when the command related to the event was read, and calls the
	 * corresponding method of the IRCEventListener if there is any.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 * @throws UnsupportedOperationException
	 *             If there is no associated method for the listener.
	 */
	public abstract void onExecute(IRCEventListener listener);
}
