package command;

import java.util.Arrays;
import java.util.List;

import event.IRCEventBroadcaster;
import parser.IRCMessage;
import util.StringConverter;

/**
 * Joins the list of channels received with their respective keys (if
 * specified). If no argument is used to create the command, then it has the
 * {@link PartCommand} effect.
 * 
 * @author tomas
 */
public class JoinCommand extends IRCCommand {

	private static final int CHANNELS_INDEX = 0;
	private static final int KEYS_INDEX = 1;

	private String[] channels;
	private String[] keys;

	/**
	 * Creates a command that leaves all the channels joined.
	 */
	public JoinCommand() throws InvalidCommandException {
		this(new IRCMessage(JOIN_COMMAND, ZERO), null);
	}

	public JoinCommand(String channel) throws InvalidCommandException {
		this(Arrays.asList(channel), null);
	}

	public JoinCommand(String channel, String key) throws InvalidCommandException {
		this(Arrays.asList(channel), Arrays.asList(key));
	}

	/**
	 * @param channels
	 *            List containing the channels' names to be joined.
	 * @param keys
	 *            List of the keys of the channels in the same order than the
	 *            channels. If no keys are required, this parameter should be
	 *            null.
	 * @throws InvalidCommandException
	 *             If channels is null or any of the channels is null, if any
	 *             key is null, or if channels and keys sizes are different.
	 */
	public JoinCommand(List<String> channels, List<String> keys) throws InvalidCommandException {
		this(new IRCMessage(JOIN_COMMAND,
				channels != null ? StringConverter.stringfyList(channels, "" + IN_PARAM_SEPARATOR, "", "") : null,
				keys != null ? StringConverter.stringfyList(keys, "" + IN_PARAM_SEPARATOR, "", "") : null), null);
	}

	public JoinCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster) throws InvalidCommandException {
		super(ircMessage, broadcaster);
		String channelsString = ircMessage.getParameter(CHANNELS_INDEX);
		String keysString = ircMessage.getParameter(KEYS_INDEX);
		if (channelsString == null) {
			throw new InvalidCommandException();
		}
		try {
			Integer.valueOf(channelsString);
			return;
		} catch (NumberFormatException e) {
		}

		channels = channelsString.split("" + IN_PARAM_SEPARATOR);
		for (String channel : channels) {
			if (channel == null) {
				throw new InvalidCommandException();
			}
		}
		if (keysString != null) {
			keys = keysString.split("" + IN_PARAM_SEPARATOR);
			if (keys.length != channels.length) {
				throw new InvalidCommandException();
			}
			for (String key : keys) {
				if (key == null) {
					throw new InvalidCommandException();
				}
			}
		}
	}

	@Override
	public void onExecute() {
//		throw new UnsupportedOperationException();
		// TODO
	}
}
