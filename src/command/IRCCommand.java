package command;

import parser.IRCMessage;
import util.IRCValues;
import event.IRCEventBroadcaster;

public abstract class IRCCommand implements IRCValues {

	private IRCMessage ircMessage;
	private IRCEventBroadcaster broadcaster;

	/**
	 * Constructs the corresponding IRCCommand given the ircMessage.
	 * 
	 * @param ircMessage
	 * @param broadcaster
	 * @throws IllegalArgumentException
	 *             If iRCMessage is null.
	 * @throws InvalidCommandException
	 *             If the command can't be constructed from the ircMessage.
	 */
	IRCCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		if (ircMessage == null) {
			throw new IllegalArgumentException();
		}
		this.ircMessage = ircMessage;
		this.broadcaster = broadcaster;
	}

	/**
	 * Calls the corresponding method of the broadcaster given in the
	 * constructor.
	 * 
	 * @throws NullPointerException
	 *             If the command was created with a null broadcaster.
	 */
	public abstract void onExecute();

	/**
	 * Returns the message that represents the IRCCommand.
	 * 
	 * @return the message that represents the IRCCommand.
	 */
	public String getMessage() {
		return ircMessage.toString();
	}

	IRCMessage getIRCMessage() {
		return ircMessage;
	}

	IRCEventBroadcaster getBroadcaster() {
		return broadcaster;
	}
}
