package command;

import parser.IRCMessage;
import parser.IRCPrefix;
import event.IRCEventBroadcaster;

/**
 * Is used to give user a nickname or change the existing one.
 * 
 * @author Tomas
 */
public class NickCommand extends IRCCommand {

	private static final int NICK_INDEX = 0;

	private String nickname;

	/**
	 * @param nickname
	 *            Nickname to be used from now onwards.
	 * @throws InvalidCommandException
	 *             If nickname is null.
	 */
	public NickCommand(String nickname) throws InvalidCommandException {
		this(new IRCMessage(NICK_COMMAND, nickname), null);
	}

	public NickCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		nickname = ircMessage.getParameter(NICK_INDEX);
		if (nickname == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		IRCPrefix prefix = getIRCMessage().getPrefix();
		getBroadcaster().onNick(prefix != null ? prefix.toString() : null, nickname);
	}
}
