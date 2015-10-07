package command.reply;

import parser.IRCMessage;

import command.IRCCommand;
import command.InvalidCommandException;

/**
 * Builds an {@link IRCReply} given an {@link IRCMessage}.
 * 
 * @author Tomas
 */
public interface IRCReplyFactory {

	/**
	 * Returns the {@link IRCReply} associated with the ircMessage received.
	 * 
	 * @param ircMessage
	 * @return the {@link IRCReply} associated with the ircMessage received.
	 * @throws InvalidCommandException
	 *             If the reply in the ircMessage is not recognized.
	 * @throws IllegalArgumentException
	 *             If ircMessage is null.
	 */
	public IRCCommand build(IRCMessage ircMessage)
			throws InvalidCommandException;
}
