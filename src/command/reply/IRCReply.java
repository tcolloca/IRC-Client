package command.reply;

import event.IRCRawEventListener;

/**
 * Represents any message read from the connection with the server.
 * 
 * @author Tomas
 */
public interface IRCReply {

	/**
	 * This method is called when a message is read, and calls the corresponding
	 * method of the IRCRawEventListener if there is any.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 * @throws UnsupportedOperationException
	 *             If there is no associated method for the listener.
	 */
	public abstract void onExecute(IRCRawEventListener listener);
}
