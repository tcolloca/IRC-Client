package command;

import event.IRCEventListener;

public interface IRCCommand {

	/**
	 * Called when the command was read, and calls the corresponding method of
	 * the IRCEventListener if there is any.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 * @throws UnsupportedOperationException
	 *             If there is no associated method for the listener.
	 */
	public abstract void onExecute(IRCEventListener listener);

	/**
	 * Returns the message that represents the IRCCommand.
	 * 
	 * @return the message that represents the IRCCommand.
	 */
	public String getMessage();
}
