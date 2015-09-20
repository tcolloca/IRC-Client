package command;

import parser.IRCMessage;
import util.IRCValues;
import event.IRCEventListener;

public abstract class IRCCommandImpl implements IRCCommand, IRCValues {

	private IRCMessage ircMessage;

	/**
	 * Constructs the corresponding IRCCommand given the ircMessage.
	 * 
	 * @param ircMessage
	 * @throws IllegalArgumentException
	 *             If iRCMessage is null.
	 * @throws InvalidCommandException
	 *             If the command can't be constructed from the ircMessage.
	 */
	IRCCommandImpl(IRCMessage ircMessage) throws InvalidCommandException {
		if (ircMessage == null) {
			throw new IllegalArgumentException();
		}
		this.ircMessage = ircMessage;
	}

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
	public String getMessage() {
		return ircMessage.getString();
	}

	IRCMessage getIRCMessage() {
		return ircMessage;
	}
}
