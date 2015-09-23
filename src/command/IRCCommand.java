package command;

import event.IRCRawEventListener;

public interface IRCCommand {

	/**
	 * This method is called when the command related to the event was read, and
	 * calls the corresponding method of the IRCRawEventListener if there is
	 * any.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 * @throws UnsupportedOperationException
	 *             If there is no associated method for the listener.
	 */
	public abstract void onExecute(IRCRawEventListener listener);

	/**
	 * Returns the message that represents the IRCCommand.
	 * 
	 * @return the message that represents the IRCCommand.
	 */
	public String getMessage();
}
