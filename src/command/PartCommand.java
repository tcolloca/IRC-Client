package command;

import java.util.Arrays;
import java.util.List;

import model.IRCChannel;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import util.StringConverter;
import event.IRCRawEventListener;

/**
 * Leaves the list of channels received, and if a message is set with that
 * message. When this command is read, it shows the user that has left channel.
 * 
 * @author Tomas
 */
public class PartCommand extends IRCCommandImpl {

	public static final String PART_COMMAND = "PART";
	private static final int CHANNEL_INDEX = 0;
	private static final int MESSAGE_INDEX = 1;

	private String nickname;
	private String channelName;
	private String message;

	/**
	 * @param channel
	 *            Channel to be left.
	 * @throws InvalidCommandException
	 *             If channel is null.
	 */
	public PartCommand(IRCChannel channel) throws InvalidCommandException {
		this(channel, null);
	}

	/**
	 * @param channel
	 *            Channel to be left.
	 * @param message
	 *            Message to show when leaving.
	 * @throws InvalidCommandException
	 *             If channel is null.
	 */
	public PartCommand(IRCChannel channel, String message)
			throws InvalidCommandException {
		this(Arrays.asList(channel), message);
	}

	/**
	 * @param channels
	 *            List of the channels to be left.
	 * @throws InvalidCommandException
	 *             If channels is null or any of the channels is null.
	 */
	public PartCommand(List<IRCChannel> channels)
			throws InvalidCommandException {
		this(channels, null);
	}

	/**
	 * @param channels
	 *            List of the channels to be left.
	 * @param message
	 *            Message to show when leaving.
	 * @throws InvalidCommandException
	 *             If channels is null or any of the channels is null.
	 */
	public PartCommand(List<IRCChannel> channels, String message)
			throws InvalidCommandException {
		super(new IRCMessageImpl(PART_COMMAND,
				channels != null ? StringConverter
						.stringfyList(channels, "" + IN_PARAM_SEPARATOR, "",
								"", new ChannelNameFunction()) : null, message));
		if (channels == null) {
			throw new InvalidCommandException();
		}
	}

	public PartCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		nickname = ircMessage.getPrefix().getString();
		channelName = ircMessage.getParameter(CHANNEL_INDEX);
		message = ircMessage.getParameter(MESSAGE_INDEX);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		listener.onPart(nickname, channelName, message);
	}
}
