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
	 * @param dao
	 *            Dao that will be used to get the users and channels.
	 * @param ircMessage
	 * @return the {@link IRCCommand} associated with the ircMessage received.
	 * @throws InvalidCommandException
	 *             If the command in the ircMessage is not recognized.
	 * @throws IllegalArgumentException
	 *             If dao or ircMessage are null.
	 */
	public IRCCommand build(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException;
}
