package command;

import parser.IRCMessage;
import util.IRCValues;

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

	@Override
	public String getMessage() {
		return ircMessage.getString();
	}

	IRCMessage getIRCMessage() {
		return ircMessage;
	}
}
