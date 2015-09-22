package command;

import java.util.ArrayList;
import java.util.List;

import model.IRCChannel;
import model.IRCDao;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import util.StringConverter;
import event.IRCEventListener;

/**
 * Lists all nicknames that are visible to him.
 * 
 * The channels parameter specifies which channel(s) to return information
 * about. There is no error reply for bad channel names. If no channel parameter
 * is given, a list of all channels and their occupants is returned. At the end
 * of this list, a list of users who are visible but either not on any channel
 * or not on a visible channel are listed as being on 'channel' "*". // TODO :
 * Rewrite last sentence.
 * 
 * If the target parameter is specified, the request is forwarded to that server
 * which will generate the reply.
 * 
 * @author Tomas
 */
public class NamesCommand extends IRCCommandImpl {

	public static final String NAMES_COMMAND = "NAMES";
	
	public NamesCommand() throws InvalidCommandException {
		this(new ArrayList<IRCChannel>());
	}

	/**
	 * @param channels
	 *            List containing the channels to be asked the visible
	 *            nicknames.
	 * @throws InvalidCommandException
	 *             If any of the channels is null.
	 */
	public NamesCommand(List<IRCChannel> channels)
			throws InvalidCommandException {
		this(channels, null);
	}

	/**
	 * @param channels
	 *            List containing the channels to be asked the visible
	 *            nicknames.
	 * @param target
	 *            Target server to ask the command.
	 * @throws InvalidCommandException
	 *             If any of the channels is null.
	 */
	public NamesCommand(List<IRCChannel> channels, String target)
			throws InvalidCommandException {
		super(new IRCMessageImpl(NAMES_COMMAND,
				channels != null ? StringConverter
						.stringfyList(channels, "" + IN_PARAM_SEPARATOR, "",
								"", new ChannelNameFunction()) : null, target));
	}

	public NamesCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
