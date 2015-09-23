package command;

import model.IRCDao;
import parser.IRCMessage;

/**
 * Builds an {@link IRCCommand} given am {@link IRCDao} and an
 * {@link IRCMessage}.
 * 
 * @author Tomas
 */
public interface IRCCommandFactory {

	/**
	 * Returns the {@link IRCCommand} associated with the ircMessage received.
	 * 
	 * @param ircMessage
	 * @return the {@link IRCCommand} associated with the ircMessage received.
	 * @throws InvalidCommandException
	 *             If the command in the ircMessage is not recognized.
	 * @throws IllegalArgumentException
	 *             If ircMessage is null.
	 */
	public IRCCommand build(IRCMessage ircMessage)
			throws InvalidCommandException;
}
