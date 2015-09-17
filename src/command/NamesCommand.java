package command;

import java.util.Arrays;
import java.util.List;

import event.IRCEventBroadcaster;
import parser.IRCMessage;
import util.StringConverter;

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
 * 
 * @author Tomas
 */
public class NamesCommand extends IRCCommand {

	private static final int CHANNELS_INDEX = 0;
	private static final int TARGET_INDEX = 1;

	private String[] channels;
	@SuppressWarnings("unused") // TODO
	private String target;

	public NamesCommand() throws InvalidCommandException {
		this(new IRCMessage(NAMES_COMMAND, null, null), null);
	}

	/**
	 * @param channels
	 *            Channels' names to be asked the visible nicknames.
	 * @throws InvalidCommandException
	 *             If any of the channels is null.
	 */
	public NamesCommand(String... channels) throws InvalidCommandException {
		this(Arrays.asList(channels));
	}

	/**
	 * @param channels
	 *            List containing the channels' names to be asked the visible
	 *            nicknames.
	 * @throws InvalidCommandException
	 *             If any of the channels is null.
	 */
	public NamesCommand(List<String> channels) throws InvalidCommandException {
		this(channels, null);
	}

	/**
	 * @param channels
	 *            List containing the channels names to be asked the visible
	 *            nicknames.
	 * @param target
	 *            Target server to ask the command.
	 * @throws InvalidCommandException
	 *             If any of the channels is null.
	 */
	public NamesCommand(List<String> channels, String target) throws InvalidCommandException {
		this(new IRCMessage(NAMES_COMMAND,
				channels != null ? StringConverter.stringfyList(channels, "" + IN_PARAM_SEPARATOR, "", "") : null,
				target), null);
	}

	public NamesCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster) throws InvalidCommandException {
		super(ircMessage, broadcaster);
		String channelsString = ircMessage.getParameter(CHANNELS_INDEX);
		if (channelsString != null) {
			channels = channelsString.split("" + IN_PARAM_SEPARATOR);
			for (String channel : channels) {
				if (channel == null) {
					throw new InvalidCommandException();
				}
			}
		}
		target = ircMessage.getParameter(TARGET_INDEX);
	}

	@Override
	public void onExecute() {
		throw new UnsupportedOperationException();
	}
}
